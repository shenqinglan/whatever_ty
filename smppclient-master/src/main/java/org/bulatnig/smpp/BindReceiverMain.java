package org.bulatnig.smpp;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.bulatnig.smpp.net.Connection;
import org.bulatnig.smpp.net.impl.TcpConnection;
import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.impl.BindReceiver;
import org.bulatnig.smpp.pdu.impl.DeliverSm;
import org.bulatnig.smpp.session.MessageListener;
import org.bulatnig.smpp.session.Session;
import org.bulatnig.smpp.session.impl.BasicSession;
import org.bulatnig.smpp.util.HttpUtil;
import org.bulatnig.smpp.util.PropertiesUtil;
/**
 * @ClassName BindReceiverMain
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindReceiverMain {
	
	private Connection conn;
	private Session session;
	private BindReceiver bindReceiver;
	
	public static void main(String[] args) {
		BindReceiverMain client = new BindReceiverMain();
		client.bind();
	}
	
	public BindReceiverMain() {
		String host = PropertiesUtil.getValue("sms_host");
		int port = Integer.parseInt(PropertiesUtil.getValue("sms_port"));
		conn = new TcpConnection(new InetSocketAddress(host, port));
		session = new BasicSession(conn, "bindReceiver");
		session.setSmscResponseTimeout(60000);
		session.setEnquireLinkTimeout(60000);
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
		System.out.println("begin bindReceiver...");
		bindReceiver = new BindReceiver();
		bindReceiver.setSystemId(PropertiesUtil.getValue("system_id"));
		bindReceiver.setPassword(PropertiesUtil.getValue("password"));
		bindReceiver.setInterfaceVersion(0x34);
		bindReceiver.setAddrNpi(0x00);
		bindReceiver.setAddrTon(0x00);
		bindReceiver.setAddressRange(null);
		try {
			session.open(bindReceiver);
		} catch (Exception e) {
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
}
