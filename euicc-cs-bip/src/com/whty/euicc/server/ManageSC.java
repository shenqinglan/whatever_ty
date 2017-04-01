package com.whty.euicc.server;
/*
 * 服务器socket存储和获取
 */

import java.net.Socket;

public class ManageSC {
	 static Socket s;
		
		public static void addClientConServerThread(Socket socket) {
			ManageSC.s = socket;
		}
		public static Socket getClientthread(){
			return s;
		}
}
