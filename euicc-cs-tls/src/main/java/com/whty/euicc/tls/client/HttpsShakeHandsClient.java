package com.whty.euicc.tls.client;

import java.io.IOException;
import java.net.Socket;


import com.telecom.http.tls.test.Util;

public class HttpsShakeHandsClient {
	private static int cmdNum = 2;
	
	public void callSr(String eid,int step)throws Exception{
		new HttpsShakeHandsClient().clientTest(eid,"127.0.0.1",8090,step);
    }
	
	public void callSr_116(String eid,int step)throws Exception{
		new HttpsShakeHandsClient().clientTest(eid,"10.8.40.116",8090,step);
    }
	
	public void callSr_nginx_116(String eid,int step)throws Exception{
		new HttpsShakeHandsClient().clientTest(eid,"10.8.40.116",2015,step);
    }
	
	public void callSr_gz(String eid,int step)throws Exception{
		new HttpsShakeHandsClient().clientTest(eid,"121.32.89.211",7489,step);
    }
	
	public void clientTest(String eid,String ip,int port,int step) throws  Exception{
		System.out.println("********begin*********");
		
		Socket socket = new Socket(ip,port);
		socket.setReceiveBufferSize(102400);
		socket.setKeepAlive(true);
		
		AbstractCTlsHank tlsHankUtils = new CTlsHankUtils(); 
		//AbstractCTlsHank tlsHankUtils = new CTlsHankUtilsCard();
		int flag = tlsHankUtils.shakeHands(socket);
		if (flag == 0) {
			doHttpPost(tlsHankUtils, socket,step);
			System.out.println("shakeHands success");
		} else {
			System.out.println("shakeHands error ,error code " + flag);
			return ;
		}

		System.out.println("********end*********");
		socket.close();
    }
	
	public void clientTestByRsp(String str,String ip,int port) throws  Exception{
		System.out.println("********begin*********");
		
		Socket socket = new Socket(ip,port);
		socket.setReceiveBufferSize(102400);
		socket.setKeepAlive(true);
		
		//AbstractCTlsHank tlsHankUtils = new CTlsHankUtils(); 
		AbstractCTlsHank tlsHankUtils = new CTlsHankUtilsCard();
		int flag = tlsHankUtils.shakeHands(socket);
		if (flag == 0) {
			doHttpPostByRsp(tlsHankUtils, socket,str);
			System.out.println("shakeHands success");
		} else {
			System.out.println("shakeHands error ,error code " + flag);
			return ;
		}

		System.out.println("********end*********");
		socket.close();
    }

	
	
	private void doHttpPost(AbstractCTlsHank tlsHankUtils, Socket socket,int steps)
			throws IOException {
		firstSendPost(tlsHankUtils, socket);
		
		byte[] strserverResponse = tlsHankUtils.receiveServerData(socket);
		System.out.println(new String(strserverResponse));
		
		//第十一步发送//POST /server/adminagent?cmd=2 HTTP/1.1 CRLF
		for(int step = 0 ; step < steps; step++){
			sendPostRequest(socket,tlsHankUtils);  
			
		    //第十二步 收到最后一个
			strserverResponse = tlsHankUtils.receiveServerData(socket);
			System.out.println(new String(strserverResponse));
		}
		cmdNum = 2;
	}
	
	private void doHttpPostByRsp(AbstractCTlsHank tlsHankUtils, Socket socket,String str)
			throws IOException {
		firstSendPostByRsp(tlsHankUtils, socket,str);
		byte[] strserverResponse = tlsHankUtils.receiveServerDataByRsp(socket);
		System.out.println(new String(strserverResponse));
	}
	
	

	private void sendPostRequest(Socket socket,AbstractCTlsHank tlsHankUtils)
			throws IOException {
		String data = "POST /server/adminagent?cmd="+(cmdNum++)+" HTTP/1.1\r\n"
			+ "Host: localhost\r\n"
			+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
			 + "X-Admin-From: //se-id/eid/89001012012341234012345678901224;//aa-id/aid/A000000559/1010FFFFFFFF8900000100\r\n" + "\r\n"
			+ "Content-Type: application/vnd.globalplatform.card-content-mgt-response;version=1.0\r\n"
			+ "Content-Length: 6\r\n" + "X-Admin-Script-Status: ok\r\n"
			+ "\r\n";
		data = Util.byteArrayToHexString(Util.toByteArray(data), "") + "AF80" + "9000" + "0000";	

		byte[] dataByteSec = Util.hexStringToByteArray(data);	
		tlsHankUtils.sendEncryptData(socket,dataByteSec);
	}
	
	

	private void firstSendPost(AbstractCTlsHank tlsHankUtils, Socket socket)
			throws IOException {
		String data = "POST /AdminAgent HTTP/1.1\r\n" + "Host: localhost\r\n"
		+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
		 + "X-Admin-From: //se-id/eid/89001012012341234012345678901224;//aa-id/aid/A000000559/1010FFFFFFFF8900000100\r\n" + "\r\n";
		data = Util.byteArrayToHexString(Util.toByteArray(data), "");		
		byte[] dataByteFirst = Util.hexStringToByteArray(data);	
		tlsHankUtils.sendEncryptData(socket,dataByteFirst);
	}
	
	private void firstSendPostByRsp(AbstractCTlsHank tlsHankUtils, Socket socket,String str)
			throws IOException {
		tlsHankUtils.sendEncryptDataByRsp(socket,str.getBytes());
	}
	
	

	

	
}
