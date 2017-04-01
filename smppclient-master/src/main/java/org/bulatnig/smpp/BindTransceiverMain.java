package org.bulatnig.smpp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;

import org.bulatnig.smpp.bean.MsgSend;
import org.bulatnig.smpp.net.Connection;
import org.bulatnig.smpp.net.impl.TcpConnection;
import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.impl.BindTransceiver;
import org.bulatnig.smpp.pdu.impl.DeliverSm;
import org.bulatnig.smpp.pdu.impl.SubmitSm;
import org.bulatnig.smpp.session.MessageListener;
import org.bulatnig.smpp.session.Session;
import org.bulatnig.smpp.session.impl.BasicSession;
import org.bulatnig.smpp.util.HttpUtil;
import org.bulatnig.smpp.util.PropertiesUtil;

import com.google.gson.Gson;
/**
 * @ClassName BindTransceiverMain
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindTransceiverMain {
	
	private Connection conn;
	private Session session;
	private final int POOL_SIZE = 10;
	private BindTransceiver bindTransceiver;
	
	public static void main(String[] args) {
		BindTransceiverMain client = new BindTransceiverMain();
		client.bind();
		client.service();
	}
	
	public BindTransceiverMain() {
		String host = PropertiesUtil.getValue("sms_host");
		int port = Integer.parseInt(PropertiesUtil.getValue("sms_port"));
		conn = new TcpConnection(new InetSocketAddress(host, port));
		session = new BasicSession(conn,"bindTransceiver");
		session.setSmscResponseTimeout(90000);
		session.setEnquireLinkTimeout(10000);
		session.setMessageListener(new MessageListener() {
			
			@Override
			public void received(Pdu pdu) {
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<receive message>>>>>>>>>>>>>>>>>>>>>>>>>>");
				if (CommandId.SUBMIT_SM_RESP == pdu.getCommandId()) {
					System.out.println("SUBMIT_SM_RESP");
				} else if (CommandId.DELIVER_SM == pdu.getCommandId()) {
					System.out.println("DELIVER_SM");
					final DeliverSm mo = (DeliverSm) pdu;
                	String receiveSmsUrl = PropertiesUtil.getValue("receive_sms_url");
            		Map<String, String> argsMap = new HashMap<String, String>() {{ 
            		    put( "src" , mo.getSourceAddr()); 
            		    put( "msg" , bytes2HexString(mo.getShortMessage()));
            		}};
            		System.out.println("src: "+ argsMap.get("src"));
            		System.out.println("msg: "+ argsMap.get("msg"));
            		String resp = HttpUtil.post(receiveSmsUrl, argsMap);
            		System.out.println(resp);
				}
			}
		});
	}
	
	private void bind() {
		System.out.println("begin bindTransceiver...");
		bindTransceiver = new BindTransceiver();
		bindTransceiver.setSystemId(PropertiesUtil.getValue("system_id"));
		bindTransceiver.setPassword(PropertiesUtil.getValue("password"));
		bindTransceiver.setInterfaceVersion(0x34);
		bindTransceiver.setAddrNpi(0x00);
		bindTransceiver.setAddrTon(0x00);
		bindTransceiver.setAddressRange(null);
		try {
			session.open(bindTransceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void service() {
    	ServerSocket serverSocket = null;
        ExecutorService executorService = null;
    	try {
	    	serverSocket = ServerSocketFactory.getDefault().createServerSocket(Integer.parseInt(PropertiesUtil.getValue("smpp_server_port")));
	    	executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
	                .availableProcessors() * POOL_SIZE);
	    	System.out.println("Send sms Server started...");
	    	System.out.println("Waiting for send submitSm...");
	    	while (true) {
    		 
	        	 final Socket socket = serverSocket.accept();
	             System.out.println("A submitSm has received");
	             executorService.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
				        	DataInputStream input = new DataInputStream(socket.getInputStream());
				        	String line = input.readUTF();
				        	System.out.println("submit data:"+line);
				        	
				        	Gson gson = new Gson();
				        	MsgSend msgSend = gson.fromJson(line, MsgSend.class);
		
				            SubmitSm submit = new SubmitSm();
				            submit.setServiceType("");
				            submit.setSourceAddrTon(0x00);
				            submit.setSourceAddrNpi(0x00);
				            submit.setSourceAddr(msgSend.getSrc());
				            submit.setDestAddrTon(0x00);
				            submit.setDestAddrNpi(0x00);
				            submit.setDestinationAddr(msgSend.getDest());
				            submit.setEsmClass(0x40);
				            submit.setProtocolId(0x7F);
				            submit.setPriorityFlag(0x00);
				            submit.setScheduleDeliveryTime(null);
				            submit.setValidityPeriod(null);
				        	submit.setRegisteredDelivery("1".equals(msgSend.getReport()) ? 0x01 : 0x00);
				        	submit.setReplaceIfPresentFlag(0x00);
				        	submit.setDataCoding(0xF6);
				        	submit.setSmDefaultMsgId(0x00);
				        	submit.setShortMessage(hexString2Bytes(msgSend.getMsg()));
				        	
				            boolean code = session.send(submit);
				            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				            out.writeUTF(String.valueOf(code));
				        	input.close();
				        	out.close();
				            socket.close();
						} catch (Exception e) {
		                 e.printStackTrace();
						}
					}
	             });
             
    		 }		
    	}catch (Exception e) {
			 e.printStackTrace();
		 }
    }
	
	public String bytes2HexString(byte[] b) {
		byte[] hex = "0123456789ABCDEF".getBytes(); 
        byte[] buff = new byte[2 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[2 * i + 1] = hex[b[i] & 0x0f];  
        }  
        return new String(buff);  
    }
	
	private byte[] hexString2Bytes(String hexString) throws Exception {
        byte[] array = new byte[hexString.length()/2];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) Integer.parseInt(hexString.substring(i*2, (i+1)*2), 16);
        }
        return array;
    }
}
