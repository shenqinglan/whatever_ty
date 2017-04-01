package com.whty.euicc.sms.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.whty.euicc.server.ManageSC;

public class SmsDownloadServer {


	public static final int PORT = 34567;// 监听的端口号

	public static void main(String[] args) {
		SmsDownloadServer server = new SmsDownloadServer();
		server.init();
	}

	//服務器初始化
	public void init() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("短信下发服务器已经开启...");
			while (true) {
				Socket server = serverSocket.accept();
				// 将此socket放入socket存储池里
				ManageSC.addClientConServerThread(server);
				// 开启一个读线程来读取客户端的触发短信
				ReadHanderClient rh = new ReadHanderClient(server);
				rh.start();
			}
		} catch (Exception e) {
			System.out.println("**短信客户端连接失败**");
		}
	}
}
