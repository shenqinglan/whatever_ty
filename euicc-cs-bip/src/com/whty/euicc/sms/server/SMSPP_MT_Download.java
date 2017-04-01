package com.whty.euicc.sms.server;

import com.whty.euicc.client.sms.NotificationTest;
import com.whty.euicc.constant.CmdType;
import com.whty.euicc.impl.CMessage;
import com.whty.euicc.pcsinterface.PCSInterface5java;
import com.whty.euicc.tls.AnalyseUtils;

public class SMSPP_MT_Download {

	static int length = 100;
	public static int PacketNum;
	static String sStr;
	static String SMSPPDLTag = "D1";
	static String DevId = "82028381";
	static String sAddr;
	static String SMSTPDUTag = "8B";
	static String packet_Userdata[] = new String[length];
	static String Typesms = "40";
	static String TP_DA = "088101560805";
	static String TP_PID = "7F";
	static String TP_DCS = "F6";
	static String TP_SCTS = "20408011535200";
	static String Packet[] = new String[length];
	static String Cardresp = "";
	static int sClaEN = 0x80;
	static String sw2 = "";
	static int c;
	
	public static void Sms_mt(String sStr_in) throws Exception {
		sStr = sStr_in;
		// 级联短信分包
		sAddr = AnalyseUtils.totlv("86", "91683108100005F0", 0);
		packet_Userdata = CMessage.ConcatenateMessage(sStr);
		c = PacketNum;

		while (c > 0) {
			// 封装开始
			String TPDU = Typesms + TP_DA + TP_PID + TP_DCS + TP_SCTS
					+ AnalyseUtils.totlv("", packet_Userdata[c - 1], 0);
			TPDU = AnalyseUtils.totlv(SMSTPDUTag, TPDU);
			String Str1 = AnalyseUtils.totlv(SMSPPDLTag, DevId + sAddr + TPDU);
			Packet[c - 1] = AnalyseUtils.itoa(sClaEN) + "C20000"
					+ AnalyseUtils.totlv("", Str1, 0);

			// System.out.println(Packet[c - 1]);
			// 通过pcsc发给卡，并获取返回的状态吗 6110 再下发 00c00000 10
			c = c - 1;
		}
		
		System.out.println("发给卡触发短信---------->" + Packet[0]);
		// 级联短信分包的情况下 向卡发送触发短信
		Cardresp = PCSInterface5java.sendText(Packet[0], CmdType.SMS_0348);
		
		if (Cardresp.substring(0, 2).equals("61")) {
			sw2 = Cardresp.substring(Cardresp.length() - 2, Cardresp.length());
			System.out.println("卡发给卡----->:" +"00C00000" + sw2);
			
			Cardresp = PCSInterface5java.sendText("00C00000" + sw2,
					CmdType.FETCh);
			NotificationTest.pushNotificationBySms(Cardresp);
		}
	}
}
