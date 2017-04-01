package com.whty.euicc.impl;

/*
 * 功能: 将用户数据组装成下行短信
 */
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.whty.euicc.client.sms.NotificationTest;
import com.whty.euicc.constant.CmdType;
import com.whty.euicc.constant.ConstantParam;
import com.whty.euicc.pcsinterface.PCSInterface5java;
import com.whty.euicc.server.ManageSC;
import com.whty.euicc.tls.AnalyseUtils;
import com.whty.euicc.tls.BipUtils;
import com.whty.euicc.tls.SocketUtils;

public class SMSPP_MT {

	static int length = 100;
	static int PacketNum;
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
	static String TerminalResponse = "";
	static int sClaEN = 0x80;
	static String TimerTrigger_Exist = "false";
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
/*		
		//////启用联通PROFILE，使用电信默认AID
//		String currentAdfAid = enableProfile();
		//////禁用联通PROFILE，使用联通默认AID
		String currentAdfAid = disableProfile();
		String len = "10";
		String head = "00A4040C";
		String apdu = head + len + currentAdfAid;
		PCSInterface5java.sendText(apdu, CmdType.FETCh);
		
*/	
		System.out.println("发给卡触发短信---------->" + Packet[0]);
		// 级联短信分包的情况下 向卡发送触发短信
		Cardresp = PCSInterface5java.sendText(Packet[0], CmdType.SMS_0348);
		// 在这个地方返回的有可能是6110
		if (Cardresp.substring(0, 2).equals("61")) {
			sw2 = Cardresp.substring(Cardresp.length() - 2, Cardresp.length());
			System.out.println("卡发给卡----->:" +"00C00000" + sw2);
			Cardresp = PCSInterface5java.sendText("00C00000" + sw2,
					CmdType.FETCh);
			//isdp
			if (sw2.equals("39")) {
//				ispdUpdate(sw2, Cardresp);
				NotificationTest.pushNotificationBySms(Cardresp);
				return;
			} else {
				System.out.println("sw2 is :" + sw2);
			}
			//System.out.println("00co Test__"+Cardresp);
			sw2 = Cardresp.substring(Cardresp.length() - 2, Cardresp.length());// 脚本跑的时候37
			System.out.println("卡发给卡----->:" +"80120000" + sw2);																	// 服务器有可能则是其他的
			Cardresp = PCSInterface5java.sendText("80120000" + sw2,
					CmdType.FETCh);
		}

		AnalyseUtils.bipsixthByte(Cardresp);// 判断卡片返回信息是否为OpenChannel

		TerminalResponse = "80140000"
				+ AnalyseUtils.totlv("", "8103014003820282818301"
						+ ConstantParam.OPENTERMRESP + "3802"
						+ BipUtils.Channel_number + "00"
						+ "35070202040505100239020578", 0);
		Cardresp = PCSInterface5java.sendText(TerminalResponse,
				CmdType.TetminalResponse);
		// System.out.println("卡---" + Cardresp);
		Cardresp = PCSInterface5java
				.sendText("801200004A", CmdType.getSendData); // 获取卡端的sendData数据
		// System.out.println("卡端sendData数据：  ");
		AnalyseUtils.bipsixthByte(Cardresp);
		Cardresp = PCSInterface5java.sendText(
				"801400000F810301430182028281830100B701FF",
				CmdType.TetminalResponse);// 如果此时卡片没有数据的话则9000
		// 判断Cardresp的返回值是否为9000，如果为9000
		// 则说明没有数据要上发了，如果为91XX,则继续挖取卡片上发数据，直到卡片数据返回值为9000为止
		System.out.println("卡片返回值---" + Cardresp);

		/*
		 * 根据脚本还有Timer方法，赞无实现 String tkresp=Cardresp; if(Cardresp.equals("")){
		 * //响应获取时间的方法 if (TimerTrigger_Exist.equals("true")){ String time =
		 * AnalyseUtils.getTime(); String localTime =
		 * AnalyseUtils.localTimeFormat(time); setresp %stkresp CMP TLVS 08 D0
		 * 09 81 03 01 26 03 82 02 81 82 //Cardresp =
		 * pcs.sendText("A0140000"+AnalyseUtils.totlv("",
		 * "810301260382028281830100A607"+localTime, 0));
		 */
	}
	
	
	public static String enableProfile(){
		//////启用联通PROFILE，使用电信默认AID
		String DXADF = "A0000003431002FF86FF0389FFFFFFFF";
		return DXADF;
	}
	
	public static String disableProfile(){
		//////禁用联通PROFILE，使用联通默认AID
		String LTADF = "A0000000871002FF86FFFF89FFFFFFFF";
		return LTADF;
	}

	//	ispd更新02710000340A00001300000002CA0000AB27800101232200000000000000000000300370BE9E38C3D0E672AAF19453962970DD3200000390009000
	private static void ispdUpdate(String sw22, String cardres) {
		Socket socket = ManageSC.getClientthread();
		if (socket != null) {
			try {
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				byte[] ch = cardres.getBytes();
				out.writeInt(ch.length);
				SocketUtils.writeBytes(out, ch, ch.length);
				System.out.println("back to server is ok");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
