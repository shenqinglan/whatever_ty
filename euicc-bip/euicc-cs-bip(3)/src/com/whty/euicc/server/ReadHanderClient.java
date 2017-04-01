package com.whty.euicc.server;
/*
 * 接收短信客户端的触发消息
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.whty.euicc.impl.SMSPP_MT;
import com.whty.euicc.tls.AnalyseUtils;
import com.whty.euicc.tls.BipUtils;
import com.whty.euicc.tls.SocketUtils;

public class ReadHanderClient extends Thread {

	private Socket s;

	public ReadHanderClient(Socket server) {
		this.s = server;
	}

	public void run() {
		DataInputStream in = null;
		int len;
		try {
			in = new DataInputStream(s.getInputStream());
			len = in.readInt();// 四个字节表示接收到的消息長度
			//System.out.println("=-=-=-" + len);
			in.skipBytes(4);// 跳過四個字節
			byte[] smsData = SocketUtils.readBytes(in, len);
			String strData = new String(smsData);
			System.out.println("触发短信-===-：" + strData);

			// 做一个触发短信简单的判断
			String tag = strData.substring(0, 3);
			if (tag.equals("027")) {
				// 随机生成通道号 1-7
				BipUtils.channel_number = AnalyseUtils.randi(7);
				BipUtils.Channel_number = AnalyseUtils.itoa(
						BipUtils.channel_number + 0x20, 1);
				// 短信分包封装
				SMSPP_MT.Sms_mt(strData);
			}
			
		} catch (IOException e2) {
			System.out.println("IO异常");
		} catch (Exception e) {
			System.out.println("参数非法--");
		}

	}
}
