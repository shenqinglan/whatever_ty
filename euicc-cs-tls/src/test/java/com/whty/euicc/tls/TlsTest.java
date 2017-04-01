package com.whty.euicc.tls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class TlsTest {
	@Test
	public void test() throws UnknownHostException, IOException{
//		for(int i=0;i<100;i++){
			Socket socket = new Socket("localhost",9111);
			System.out.println("本客户端已连接上服务器！");
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String clientHello = "123456";
			int length = clientHello.getBytes().length;
			out.writeInt(length);
			SocketUtils.writeBytes(out, clientHello.getBytes(), length);
			
			int len = in.readInt();
			in.skipBytes(4);
			byte[] serverHello = SocketUtils.readBytes(in, len);
			String strServerHello = new String(serverHello);
			System.out.println("strServerHello >>> "+strServerHello);
//		}
		
	}

}
