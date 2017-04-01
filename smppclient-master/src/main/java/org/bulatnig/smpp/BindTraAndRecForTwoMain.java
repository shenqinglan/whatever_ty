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
import org.bulatnig.smpp.pdu.impl.BindReceiver;
import org.bulatnig.smpp.pdu.impl.BindTransmitter;
import org.bulatnig.smpp.pdu.impl.DeliverSm;
import org.bulatnig.smpp.pdu.impl.SubmitSm;
import org.bulatnig.smpp.session.MessageListener;
import org.bulatnig.smpp.session.Session;
import org.bulatnig.smpp.session.impl.BasicSession;
import org.bulatnig.smpp.util.HttpUtil;
import org.bulatnig.smpp.util.PropertiesUtil;

import com.google.gson.Gson;
/**
 * @ClassName BindTraAndRecForTwoMain
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindTraAndRecForTwoMain {
	
	private Connection conn1;
	private Connection conn2;
	private Session session1;
	private Session session2;
	private final int POOL_SIZE = 10;
	private BindTransmitter bindTransmitter;
	private BindReceiver bindReceiver;
	
	public static void main(String[] args) {
		final BindTraAndRecForTwoMain client = new BindTraAndRecForTwoMain();
		client.bindTransmitter();
		client.bindReceiver();
		client.service();
		
	}
	
	public BindTraAndRecForTwoMain() {
		String host = PropertiesUtil.getValue("sms_host");
		int port = Integer.parseInt(PropertiesUtil.getValue("sms_port"));
		conn1 = new TcpConnection(new InetSocketAddress(host, port));
		conn2 = new TcpConnection(new InetSocketAddress(host, port));
		session1 = new BasicSession(conn1,"bindTransmitter");
		session1.setSmscResponseTimeout(90000);
		session1.setEnquireLinkTimeout(10000);
		session1.setMessageListener(new MessageListener() {
			
			@Override
			public void received(Pdu pdu) {
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<receive message for session1>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("CommandId: "+pdu.getCommandId());
				if (CommandId.SUBMIT_SM_RESP == pdu.getCommandId()) {
					System.out.println("SUBMIT_SM_RESP from session1");
				}/* else if (CommandId.DELIVER_SM == pdu.getCommandId()) {
					System.out.println("DELIVER_SM from session1");
					final DeliverSm mo = (DeliverSm) pdu;
                	String receiveSmsUrl = PropertiesUtil.getValue("receive_sms_url");
            		Map<String, String> argsMap = new HashMap<String, String>() {{ 
            		    put( "src" , mo.getSourceAddr()); 
            		    put( "msg" , new String(mo.getShortMessage()));
            		}};
            		System.out.println("src: "+ argsMap.get("src"));
            		System.out.println("msg: "+ argsMap.get("msg"));
            		String resp = HttpUtil.post(receiveSmsUrl, argsMap);
            		System.out.println(resp);
				} */
			}
		});
		
		session2 = new BasicSession(conn2,"bindReceiver");
		session2.setSmscResponseTimeout(90000);
		session2.setEnquireLinkTimeout(10000);
		session2.setMessageListener(new MessageListener() {
			
			@Override
			public void received(Pdu pdu) {
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<receive message for session2>>>>>>>>>>>>>>>>>>>>>>>>>>");
				if (CommandId.SUBMIT_SM_RESP == pdu.getCommandId()) {
					System.out.println("SUBMIT_SM_RESP from session2");
				} else if (CommandId.DELIVER_SM == pdu.getCommandId()) {
					System.out.println("DELIVER_SM from session2");
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
	
	
	private void bindTransmitter() {
		System.out.println("begin bindTransmitter...");
		bindTransmitter = new BindTransmitter();
		bindTransmitter.setSystemId(PropertiesUtil.getValue("system_id"));
		bindTransmitter.setPassword(PropertiesUtil.getValue("password"));
		bindTransmitter.setInterfaceVersion(0x34);
		bindTransmitter.setAddrNpi(0x00);
		bindTransmitter.setAddrTon(0x00);
		bindTransmitter.setAddressRange(null);
		try {
			session1.open(bindTransmitter);
			System.out.println("bindTransmitter success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void bindReceiver() {
		System.out.println("begin bindReceiver...");
		bindReceiver = new BindReceiver();
		bindReceiver.setSystemId(PropertiesUtil.getValue("system_id"));
		bindReceiver.setPassword(PropertiesUtil.getValue("password"));
		bindReceiver.setInterfaceVersion(0x34);
		bindReceiver.setAddrNpi(0x00);
		bindReceiver.setAddrTon(0x00);
		bindReceiver.setAddressRange(null);
		try {
			session2.open(bindReceiver);
			System.out.println("bindReceiver success");
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
	             System.out.println("A client has connected");
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
				        	submit.setRegisteredDelivery(0x00);
				        	submit.setReplaceIfPresentFlag(0x00);
				        	submit.setDataCoding(0xF6);
				        	submit.setSmDefaultMsgId(0x00);
				        	submit.setShortMessage(hexString2Bytes(msgSend.getMsg()));
				        	
				            boolean code = session1.send(submit);
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
