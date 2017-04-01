package com.whty.euicc.sms.commandpacket;

import com.whty.euicc.sms.Sms;
import com.whty.euicc.sms.util.AnalyseUtils;
/**
 * 级联短信分包
 * @author Administrator
 *
 */

public class CMessage {
	private static int length = 100;
	static String sStr;
	static String packet_Userdata[] = new String[length];
	static String userData;
	static String Packet[] = new String[length];
	static int clen;
	static int c = 1;
	static int cPacketNum = 1;
	static int LenPacket[] = new int[length ];
	static int PacketSize = 0x8C;
	static int UserData_len;
	static int Bactch = 1;

	public static String[] ConcatenateMessage(String sStr_in) throws Exception {
		sStr = sStr_in;
		clen = sStr.length();
		packet_Userdata[0] = sStr;

		// 如果User Data的长度大于分包数据长度，就进行分包处理
		if (clen/2 > AnalyseUtils.hexToInt(PacketSize)) {
            
			userData = AnalyseUtils.strmidh(sStr, 3);
			LenPacket[0] = AnalyseUtils.hexToInt(PacketSize) - 8;
			Packet[0] = AnalyseUtils.strmidh(userData, 0, LenPacket[0]);
			LenPacket[1] = AnalyseUtils.hexToInt(PacketSize) - 6;
			userData = AnalyseUtils.strmidh(userData, LenPacket[0]);
			UserData_len = userData.length();

			while (UserData_len/2 > LenPacket[1]) {
				c = c + 1;
				Packet[c - 1] = AnalyseUtils.strmidh(userData, 0, LenPacket[1]);
				userData = AnalyseUtils.strmidh(userData, LenPacket[1]);
				UserData_len = userData.length();
			}
			if (UserData_len == 0) {
                 throw new Exception("用户数据长度为0");
			} else {
				c = c + 1;
				Packet[c-1] = userData;
			}
			int c1 = c-1;
			while (c1 > 0) {
				// 将其转换为十六进制
				packet_Userdata[c1] = "050003" + AnalyseUtils.itoa(Bactch) + AnalyseUtils.itoa(c)
						+ AnalyseUtils.itoa(c1) + Packet[c1];
//				System.out.println(packet_Userdata[c1]);
				c1 = c1 - 1;
			}
			packet_Userdata[c1] = "0770000003" + AnalyseUtils.itoa(Bactch) + AnalyseUtils.itoa(c)
					+ AnalyseUtils.itoa(c1) + Packet[c1];
			//System.out.println(packet_Userdata[c1]);
			System.out.println("分包数---"+cPacketNum);
		}
		cPacketNum = c;
		//将cPacketNum赋值给PacketNum
		Sms.PacketNum = cPacketNum;
		return packet_Userdata;
	}
}
