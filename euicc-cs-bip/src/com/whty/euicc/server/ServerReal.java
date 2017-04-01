package com.whty.euicc.server;
/*
 * 自测服务器
 */

import java.net.ServerSocket;
import java.net.Socket;

public class ServerReal{

	public static final int PORT = 8443;//监听的端口号   

	public static void main(String[] args) {  
		ServerReal server = new ServerReal();  
		server.init();  
	}  

	public void init() {  
		ServerSocket serverSocket = null;
		try {  
			serverSocket = new ServerSocket(PORT);  
			System.out.println("自测服务器已经开启...");
			while (true) {  
				Socket server = serverSocket.accept();  
				//一个客户端连接就开户两个线程处理读写
				ManageSC.addClientConServerThread(server);
				ReadHanderClientReal rh = new ReadHanderClientReal(server);
				rh.start();
			}  
		} catch (Exception e) {  
			System.out.println("客户端连接失败");
		}
	}  
}  

