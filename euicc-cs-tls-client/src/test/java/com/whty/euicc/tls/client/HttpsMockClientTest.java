package com.whty.euicc.tls.client;

import java.io.IOException;
import java.net.Socket;

import org.junit.Test;

import com.telecom.http.tls.test.Util;

public class HttpsMockClientTest {
	@Test
	public void clientTest() throws  Exception{
		System.out.println("********begin*********");
		
		Socket socket = new Socket("127.0.0.1",8090);
		socket.setReceiveBufferSize(102400);
		socket.setKeepAlive(true);
		
		//CTlsHankUtils tlsHankUtils = new CTlsHankUtils();
		CTlsHankUtilsCard tlsHankUtils = new CTlsHankUtilsCard();
		int flag = tlsHankUtils.shakeHands(socket);
		if (flag == 0) {
			doHttpPost(tlsHankUtils, socket);
			System.out.println("shakeHands success");
		} else {
			System.out.println("shakeHands error ,error code " + flag);
			return ;
		}

		System.out.println("********end*********");
		socket.close();
    }

	private void doHttpPost(CTlsHankUtils tlsHankUtils, Socket socket)
		throws IOException {
		String data = "POST /AdminAgent HTTP/1.1\r\n" + "Host: localhost\r\n"
		+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
		+ "X-Admin-From: //se-id/eid/123456;//aa-id/aid/0001020304/05060708090A0B0C0D0E0F\r\n" + "\r\n";
		data = Util.byteArrayToHexString(Util.toByteArray(data), "");		
		byte[] dataByteFirst = Util.hexStringToByteArray(data);	
		tlsHankUtils.sendEncryptData(socket,dataByteFirst);
		
		tlsHankUtils.receiveServerData(socket);
		
		data = "POST /server/adminagent?cmd=2 HTTP/1.1\r\n"
			+ "Host: localhost\r\n"
			+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
			+ "X-Admin-From: //se-id/eid/123456;//aa-id/aid/0001020304/05060708090A0B0C0D0E0F\r\n"
			+ "Content-Type: application/vnd.globalplatform.card-content-mgt-response;version=1.0\r\n"
			+ "Content-Length: 6\r\n" + "X-Admin-Script-Status: ok\r\n"
			+ "\r\n";
		data = Util.byteArrayToHexString(Util.toByteArray(data), "") + "AF80" + "9000" + "0000";	
		
		byte[] dataByteSec = Util.hexStringToByteArray(data);	
		tlsHankUtils.sendEncryptData(socket,dataByteSec);
		
		tlsHankUtils.receiveServerData(socket);
	}
	
	@Test
	public void clientTestCard() throws  Exception{
		System.out.println("********begin*********");
		
		Socket socket = new Socket("10.8.40.140",8090);
		socket.setReceiveBufferSize(102400);
		socket.setKeepAlive(true);
		
		CTlsHankUtilsCard tlsHankUtils = new CTlsHankUtilsCard();
		int flag = tlsHankUtils.shakeHands(socket);
		if (flag == 0) {
			doHttpPost(tlsHankUtils, socket);
			System.out.println("shakeHands success");
		} else {
			System.out.println("shakeHands error ,error code " + flag);
			return ;
		}

		System.out.println("********end*********");
		socket.close();
    }
	
	private void doHttpPost(CTlsHankUtilsCard tlsHankUtils, Socket socket)
			throws IOException {
		String data = "POST /AdminAgent HTTP/1.1\r\n" + "Host: localhost\r\n"
		+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
		+ "X-Admin-From: //se-id/eid/123456;//aa-id/aid/0001020304/05060708090A0B0C0D0E0F\r\n" + "\r\n";
		data = Util.byteArrayToHexString(Util.toByteArray(data), "");		
		byte[] dataByteFirst = Util.hexStringToByteArray(data);	
		tlsHankUtils.sendEncryptData(socket,dataByteFirst);
		
		tlsHankUtils.receiveServerData(socket);
		
		data = "POST /server/adminagent?cmd=2 HTTP/1.1\r\n"
			+ "Host: localhost\r\n"
			+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
			+ "X-Admin-From: //se-id/eid/123456;//aa-id/aid/0001020304/05060708090A0B0C0D0E0F\r\n"
			+ "Content-Type: application/vnd.globalplatform.card-content-mgt-response;version=1.0\r\n"
			+ "Content-Length: 6\r\n" + "X-Admin-Script-Status: ok\r\n"
			+ "\r\n";
		data = Util.byteArrayToHexString(Util.toByteArray(data), "") + "AF80" + "9000" + "0000";	

		byte[] dataByteSec = Util.hexStringToByteArray(data);	
		tlsHankUtils.sendEncryptData(socket,dataByteSec);
		
		tlsHankUtils.receiveServerData(socket);
	}
	
	
}
