package com.whty.tls.client.ecc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.tls.client.ClientConnection;
import com.whty.tls.client.ShakeHandsClient;
import com.whty.tls.util.SocketUtils;
import com.whty.tls.util.Util;

public class EccCardShakeHandsClient extends ShakeHandsClient {
	
	private Logger logger = LoggerFactory.getLogger(EccCardShakeHandsClient.class);
	
	private InputStream in;
	private OutputStream out;
	
	public String clientTestByRsp(String str,String ip,int port,String str2,String str3) throws Exception{
		System.out.println("********clientTestByRsp begin*********");
		
		Socket socket = new Socket(ip,port);
		socket.setReceiveBufferSize(102400);
		socket.setKeepAlive(true);
		
		ShakeHandsClient tlsHankUtils = new EccCardShakeHandsClient();
		ClientConnection flag = tlsHankUtils.doHttpsShakeHands(socket);
		if(flag != null){
			
			return doSocketTransportByRsp(str, socket, flag, str2, str3);
			
		} else {
			System.out.println("shakeHands error ,error code " + flag);
			
		}
		System.out.println("********clientTestByRsp end*********");
		socket.close();
		return null;
	}
	
	@Override
	public ClientConnection doHttpsShakeHands(Socket s) throws Exception {	
		in = s.getInputStream();
		out = s.getOutputStream();
		ClientConnection conn = new EccClientConnection();

		// ��һ�� �ͻ��˷���clientHello
		String clientHello = conn.clientHello();
	    SocketUtils.writeBytes(out, SocketUtils.hexToBuffer(clientHello));
	    
		// �ڶ��� �ͻ����յ�serverHello������
		byte[] serverHello = SocketUtils.readBytes(in);
		String strServerHello = SocketUtils.hexByteToString(serverHello);
		conn.receiveServerDataPaser(strServerHello);

		//������ �ͻ����յ�certificateSend����������Ҫ���湫Կ��Ϣ
		byte[] certificate = SocketUtils.readBytes(in);
		String certificateStr = SocketUtils.hexByteToString(certificate);
		conn.certificateDataPaser(certificateStr);

		//���Ĳ� �ͻ����յ�serverKeyExchange������
		//����ǩ����ȡECDH��ʱ��Կ���������ڼ���PMS
		byte[] serverKeyExchange = SocketUtils.readBytes(in);
		String serverKeyExchangeStr = SocketUtils.hexByteToString(serverKeyExchange);
		conn.serverKeyExchangeDataPaser(serverKeyExchangeStr);
		
		// ���岽 �ͻ����յ����������͵����ݣ��ж��ǲ���serverHelloDone���ǵĻ����������ݸ�������
		byte[] serverHelloDone = SocketUtils.readBytes(in);
		String strServerHelloDone = SocketUtils.hexByteToString(serverHelloDone);
		conn.receiveServerDataPaser(strServerHelloDone);
		
		// ������ �ͻ��˷���clientKeyExchange
		String clientKeyExchange = conn.clientKeyExchange();
		SocketUtils.writeBytes(out, SocketUtils.hexToBuffer(clientKeyExchange));

		// ���߲� �ͻ��˷���clientChangeCipherSpec
		String clientChangeCipherSpec = conn.clientChangeCipherSpec();
		SocketUtils.writeBytes(out, SocketUtils.hexToBuffer(clientChangeCipherSpec));

		// �ڰ˲�����ClientFinish
		String clientFinished = conn.clientFinished();
		SocketUtils.writeBytes(out, SocketUtils.hexToBuffer(clientFinished));

		// �ھŲ� �յ�serverChangeCipherSpec
		byte[] serverChangeCipherSpec = SocketUtils.readBytes(in);
		String strserverChangeCipherSpec = SocketUtils.hexByteToString(serverChangeCipherSpec);
		conn.serverChangeCipherSpecPaser(strserverChangeCipherSpec);

		// ��ʮ�� �յ�serverFinished
		byte[] serverFinishedPaser = SocketUtils.readBytes(in);
		String strserverFinishedPaser = SocketUtils.hexByteToString(serverFinishedPaser);
		conn.serverFinishedPaser(strserverFinishedPaser);
		
		System.out.println("************client shakeHands end*************\r\n");

		return conn;
	}

	@Override
	public void doSocketTransport(Socket s, ClientConnection conn)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public String doSocketTransportByRsp(String str, Socket s, ClientConnection conn, String str2, String str3) throws Exception{
		StringBuilder builder = new StringBuilder();
		
		sendEncryptData(s, conn, str.getBytes());
		byte[] serverBytes = receiveServerData(s, conn);
		String serverString = new String(serverBytes);
		String result = serverString.substring(serverString.indexOf("\r\n\r\n") + 4);
		builder.append(result);
		
		while(StringUtils.isNotBlank(result)){
			sendEncryptData(s, conn, str2.getBytes());
			serverBytes = receiveServerData(s, conn);
			serverString = new String(serverBytes);
			result = serverString.substring(serverString.indexOf("\r\n\r\n") + 4);
			builder.append(result);
		}
		
		if(StringUtils.isNotBlank(str3)){
			sendEncryptData(s, conn, str3.getBytes());
			receiveServerData(s, conn);
		}
		
		System.out.println("*******doSocketTransportByRsp success*********");
		
		return builder.toString();
		
	}
	
	@Override
	public void sendEncryptData(Socket s, ClientConnection conn, byte[] dataByte)
			throws IOException {
		out = new DataOutputStream(s.getOutputStream());
		byte[] encryptData = conn.encryptApplicationData(dataByte, 0, dataByte.length);	
		SocketUtils.writeBytes(out, encryptData);	
	}

	@Override
	public byte[] receiveServerData(Socket s, ClientConnection conn)
			throws IOException {
		in = new DataInputStream(s.getInputStream());
		byte[] serverResponse = SocketUtils.readBytes(in);
		String strserverResponse = SocketUtils.hexByteToString(serverResponse);
		System.out.println("收到来自服务器消息 >>" + strserverResponse);
		byte[] serverResponseToByte = Util.hexStringToByteArray(strserverResponse);
		byte[] result = conn.decryptApplicationData(serverResponseToByte);
		System.out.println(new String(result));
		return result;
		
	}
}
