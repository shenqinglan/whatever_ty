package com.whty.euicc.client;
/*
 * 客户端socket存储和获取
 */

import java.net.Socket;

public class ManageCS {
    private static Socket s;
	
	public static void addClientConServerThread(Socket socket) {
		ManageCS.s = socket;
	}
	public static Socket getClientthread(){
		return s;
	}

}
