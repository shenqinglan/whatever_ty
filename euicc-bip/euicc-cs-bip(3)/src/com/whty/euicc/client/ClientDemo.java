package com.whty.euicc.client;

import java.io.DataOutputStream;
import java.net.Socket;

public class ClientDemo {

	public static String IP = "localhost";// 服务器地址
	public static int PORT = 34567;// 服务器端口号
	static DataOutputStream out;

	public ClientDemo(String ip, int port) {
		ClientDemo.IP = ip;
		ClientDemo.PORT = port;
		System.out.println(IP + "___" + port);

	}

	public ClientDemo() {
	}

	public void handler() {
		try {
			// 实例化一个Socket，并指定服务器地址和端口
			Socket client = new Socket(IP, PORT);
			client.setReceiveBufferSize(102400);
			client.setKeepAlive(true);
			System.out.println("连接服务器");
			new ReadHanderThread(client).start();
			ManageCS.addClientConServerThread(client);
			// 放入一个读的线程
		} catch (Exception e) {
			System.out.println("建立连接失败----");
		}
	}

}
