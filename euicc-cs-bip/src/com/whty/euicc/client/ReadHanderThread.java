package com.whty.euicc.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



import com.whty.euicc.impl.SMSPP_MT;
import com.whty.euicc.tls.BipUtils;
import com.whty.euicc.tls.SocketUtils;

public class ReadHanderThread extends Thread {
	Socket s;
	DataInputStream intmp;
	DataOutputStream out;

	public ReadHanderThread(Socket socket) {
		this.s = socket;
	}

	public void run() {
		int len;
		try {
		while(true){
				System.out.println("*********************************************");
				intmp = new DataInputStream(s.getInputStream());
				len = intmp.readInt();
				intmp.skipBytes(4);
				byte[] smsData = SocketUtils.readBytes(intmp, len);
				String strData = new String(smsData);
				System.out.println("服务器下发的指令--：" + strData);
				// 自我测试 检查多条触发短信
				if (strData.substring(0, 3).equals("027")) {
					SMSPP_MT.Sms_mt(strData);
				} else{
					BipUtils.recievedataOKClosetimer(strData);
				}	
		}
		} catch (Exception e) {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e1) {
					System.out.println("IO异常");
				}
				System.out.println("关闭socket:"+s.isClosed());
				System.out.println("*******************************************************************");
				//return;
			}
		} 
	}


}
