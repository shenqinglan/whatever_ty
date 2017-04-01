package com.whty.euicc.tls;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

import com.whty.euicc.client.ClientDemo;
import com.whty.euicc.client.ManageCS;
import com.whty.euicc.client.sms.NotificationTest;
import com.whty.euicc.tls.Bean.TLVBean;

/*
 * 解析拼装工具类
 */
public class AnalyseUtils {

	static DataOutputStream out;
	static DataInputStream in;

	public static String analyse(String str) {
		return null;
	}

	/*
	 * 10进制数转为16进制字符串
	 * 
	 * @param i 10进制数
	 * 
	 * @param num 几个字节表示
	 * 
	 * @return String 16进制字符串
	 * 
	 * @throws Exception
	 */

	public static String itoa(int i, int num) throws Exception {
		String target = Integer.toHexString(i);
		if (target.length() > num * 2 || i < 0) {
			throw new Exception("参数非法");
		}
		if (target.length() % 2 == 1) {
			target = "0" + target;
		}
		while (target.length() < num * 2) {
			for (int j = 0; j < num * 2 - target.length(); j++) {
				target = "0" + target;
			}
		}
		return target.toUpperCase();
	}

	/* 没有指定长度 */

	public static String itoa(int i) {
		String target = Integer.toHexString(i);
		return target.toUpperCase();
	}

	/*
	 * 10进制数转为10进制字符串
	 * 
	 * @param i 10进制数
	 * 
	 * @param num 几个字节表示
	 * 
	 * @return String 10进制字符串
	 * 
	 * @throws Exception
	 */

	public static String itoad(int i, int num) {
		String target = String.valueOf(i);
		while (target.length() < num * 2) {
			for (int j = 0; j < num * 2 - target.length(); j++) {
				target = "0" + target;
			}
		}
		return target;
	}

	/* 无长度 */
	public static String itoad(int i) {
		String target = String.valueOf(i);
		return target;
	}

	/*
	 * 16进制字符串10进制数
	 * 
	 * @param s 16进制字符串
	 * 
	 * @return int 10进制数
	 */

	public static int atoi(String s) {
		return Integer.valueOf(s, 16);
	}

	/*
	 * 截取16进制字符串
	 * 
	 * @param data 16进制字符串
	 * 
	 * @param beginIndex 起始位置(字节)
	 * 
	 * @param length 截取长度(字节)
	 * 
	 * @return String 结果子串
	 */

	public static String strmidh(String data, int beginIndex, int length)
			throws Exception {
		if (beginIndex < 0 || beginIndex * 2 >= data.length() || length < 0
				|| length * 2 > data.length() - beginIndex * 2) {
			throw new Exception("参数非法");
		}
		int endIndex = beginIndex * 2 + length * 2;
		return data.substring(beginIndex * 2, endIndex);
	}

	/*
	 * 截取16进制字符串
	 * 
	 * @param data 16进制字符串
	 * 
	 * @param beginIndex 起始位置(字节)
	 * 
	 * @return String 结果子串
	 */

	public static String strmidh(String data, int beginIndex) throws Exception {
		if (beginIndex < 0 || beginIndex * 2 >= data.length()) {
			throw new Exception("参数非法");
		}
		return data.substring(beginIndex * 2);
	}

	/*
	 * 将16进制字符串转为tlv格式
	 * 
	 * @param tag tlv格式tag
	 * 
	 * @param data 16进制字符串
	 * 
	 * @return String 得到的tlv格式字符串
	 */

	public static String totlv(String tag, String data) throws Exception {
		// if (data.length() % 2 != 0) {
		// throw new Exception("参数非法");
		// }
		String length = itoa(data.length() / 2, 1);
		StringBuilder sb = new StringBuilder();

		if (data.length() / 2 > atoi("7F")) {
			sb.append(tag).append("81").append(length).append(data);
			return sb.toString();
		} 
		else if (data.length() / 2 > atoi("FF")) {
			sb.append(tag).append("82").append(length).append(data);
			return sb.toString();
		} else if (data.length() / 2 > atoi("FFFF")) {
			sb.append(tag).append("83").append(length).append(data);
			return sb.toString();
		}
		else{
			sb.append(tag).append(length).append(data);
			   //System.out.println("强势tlv格式=-="+sb.toString());
				return sb.toString();}
				
				//}
			}

	

	/*
	 * 将16进制字符串转为tlv格式
	 * 
	 * @param tag tlv格式tag
	 * 
	 * @param data 16进制字符串
	 * 
	 * @param i
	 * 
	 * @return String 得到的tlv格式字符串
	 */

	public static String totlv(String tag, String data, int i) throws Exception {
		if (i == 0) {
			if (data.length() % 2 != 0) {
				throw new Exception("参数非法");
			}
			String length = itoa(data.length() / 2, 1);
			StringBuilder sb = new StringBuilder();
			sb.append(tag).append(length).append(data);
			return sb.toString();
		} else {
			return totlv(tag, data);
		}
	}

	/*
	 * 16进制数转为10进制数
	 * 
	 * @param i 16进制数，以0x开头
	 * 
	 * @return int 10进制数
	 */

	public static int hexToInt(int i) {
		return Integer.parseInt(String.valueOf(i));
	}

	/*
	 * 获取系统当前时间
	 * 
	 * @return 系统当前时间（yyyyMMddHHmmss格式）
	 */

	public static String getTime() {
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return time;
	}

	/*
	 * 将16进制字符串每两个字节高低位互换
	 * 
	 * @param str 16进制字符串
	 * 
	 * @return String 结果字符串
	 * 
	 * @throws Exception
	 */

	public static String exchange(String str) throws Exception {
		if (str.length() % 2 != 0) {
			throw new Exception();
		}
		char[] c = str.toCharArray();
		for (int i = 0; i < str.length(); i++) {
			if (i % 2 == 0) {
				char a = c[i];
				c[i] = c[i + 1];
				c[i + 1] = a;
			}
		}
		String result = "";
		for (int j = 0; j < c.length; j++) {
			result = result + Character.toString(c[j]);
		}
		return result;
	}

	/*
	 * 截取16进制字符串的右边若干字节
	 * 
	 * @param data 原始字符串
	 * 
	 * @param num 截取的字节数
	 * 
	 * @return String 结果子串
	 * 
	 * @throws Exception
	 */

	public static String right(String data, int num) throws Exception {
		if (data.length() / 2 < num) {
			return data;
		} else {
			int beginIndex = data.length() - num * 2;
			return data.substring(beginIndex);
		}
	}

	/*
	 * 时间格式化
	 * 
	 * @param time 时间
	 * 
	 * @return String 格式化后时间
	 * 
	 * @return Exception
	 */

	public static String localTimeFormat(String time) throws Exception {
		final int FUN_TIMEZONE = 8 * 4;
		int FUN_NEG = 0;
		String fun_timeZone = exchange(itoad(FUN_TIMEZONE, 1));
		;
		if (FUN_NEG != 0) {
			// fun_timeZone = or(80, fun_timeZone);
		}
		String fun_time = exchange(time);
		fun_time = right(fun_time, 6) + fun_timeZone;
		return fun_time;
	}

	/*
	 * 获取字符串长度
	 * 
	 * @param s 字符串
	 * 
	 * @return int 字符串长度
	 */

	public static int strlen(String s) {
		return s.length();
	}

	// 包自增
	public static String increase_variable(String recevie_CMD_Number, int i)
			throws Exception {
		return itoa(atoi(recevie_CMD_Number) + i, 1);
	}

	// 产生随机数
	public static int randi(int i) {
		Random r = new Random();
		return r.nextInt(i) + 1;
	}

	/*
	 * Bip协议命令判断,第六字节 40 OpenChannel 41 CloseChannel 42 receiveData 43 sendData
	 */
	public static int bipsixthByte(String data) throws Exception {
		String sixthByte;

		if (data.length() > AnalyseUtils.atoi("7F") * 2 + 4) {
			sixthByte = data.substring(12, 14);
		} else {
			sixthByte = data.substring(10, 12);
		}

		if (sixthByte.equals("40")) {
			openChannelAnalyse(data);
			return 0;
		} else if (sixthByte.equals("43")) {
			sendDataAnalyse(data);
			return 0;
		} else if (sixthByte.equals("42")) {
			receiveDataAnalyse(data);
			return 0;
		} else if (sixthByte.equals("41")) {
			closeChannel();
			return 0;
		}
		return 0;
	}

	private static void closeChannel() throws IOException {
		BipUtils.channelData = "";
		BipUtils.channelDataLength = 0;

		Socket s = ManageCS.getClientthread();
//		in = new DataInputStream(s.getInputStream());
//		
//in.close();
		try {
			s.close();
		} catch (IOException e) {
			System.out.println("关闭通道异常");
		}

	}

	/*
	 * private static void eventAdjust(String data, String tag) throws Exception
	 * { int index = data.indexOf("99"); if(index > 0) { String length =
	 * data.substring(index + 2,index + 4); int len = atoi(length); String
	 * eventList = data.substring(index + 4,index + 4 +2*len); index =
	 * eventList.indexOf("03"); if (index > -1) { BipUtils.envelope(); } }
	 * 
	 * }
	 */

	private static void receiveDataAnalyse(String data) {
		System.out.println("---receiveData通知---");
	}

	/*
	 * 卡片上行数据 tag = 43 解析并发送给服务器
	 */
	private static void sendDataAnalyse(String data) throws Exception {
		// 首要判断还是要继续判断，如果data的数据大于7F的，也要去掉那个81

		// data tag "B6"判断
		int index = data.indexOf("B6");
		if (index > 0) {
			if (data.length() > atoi("7F") * 2 + 4) {
				// 先判断B6后面是否有81
				int i;
				String t81 = data.substring(index + 2, index + 4);
				if (t81.equals("81")) {
					i = AnalyseUtils.atoi(data.substring(index + 4, index + 6));
					BipUtils.buffer = data.substring(index + 6, index + 2 * i
							+ 6);// 取出第一步要发给服务器的Data
				} else {
					i = AnalyseUtils.atoi(t81);
					BipUtils.buffer = data.substring(index + 4, index + 2 * i
							+ 4);
				}// 取出第一步要发给服务器的Data
				System.out.println("初步取出的data数据..." + BipUtils.buffer);
				// 再对卡片存在的170303..后续进行一个判断，如果那两个字节的数据大于后面的长度，则将其暂时存起来，而不发给服务器
				index = BipUtils.buffer.indexOf("170303");
				if (index == 0) {
					// 如果为1703
					// ,则有两种情况，一是刚刚好，直接发送，二是加到buffer中存起来，等后续再发。则需要将前面的BipData清空，重存。
					BipUtils.channelDataLength = AnalyseUtils
							.atoi(BipUtils.buffer.substring(index + 6,
									index + 10));// 将其长度存起来
					BipUtils.channelDataLength = BipUtils.channelDataLength + 5
							- i;// 5是前面tag和它自身的两个字节
					BipUtils.channelData = BipUtils.buffer;
					if (BipUtils.channelDataLength == 0) {
						// 虽然是170303，但这条指令后续长度没有错误，直接发送给服务器
						out = new DataOutputStream(ManageCS.getClientthread()
								.getOutputStream());
						byte[] ch = BipUtils.channelData.getBytes();
						out.writeInt(ch.length);
						SocketUtils.writeBytes(out, ch, ch.length);
						System.out.println("发给服务器的消息咯---"
								+ BipUtils.channelData);
						BipUtils.channelData = "";// 每发一次 就清空一次BipData
					} else {
						// 二是加到buffer中存起来
						System.out.println("此刻要缓存拼包再发给服务器的数据 --头  ："
								+ BipUtils.channelData);
						System.out.println("头后还需要加入多少字节的数据 ******"
								+ BipUtils.channelDataLength);
					}
				} else {
					// 没有的话也要判断，这个时候channel的长度是否为0
					if (BipUtils.channelDataLength == 0
							&& BipUtils.channelData.equals("")) {
						// 说明现在的是非17 开头，直接发给发给服务器
						BipUtils.channelData = BipUtils.buffer;
						out = new DataOutputStream(ManageCS.getClientthread()
								.getOutputStream());
						byte[] ch = BipUtils.channelData.getBytes();
						out.writeInt(ch.length);
						SocketUtils.writeBytes(out, ch, ch.length);
						System.out.println("非1703要发给服务器的消息---"
								+ BipUtils.channelData);
						BipUtils.channelData = "";// 每发一次 就清空一次BipData

					} else {
						// 不等于0，说明应该将此刻的数据，加入到起前面保存的data后面
						BipUtils.channelData += BipUtils.buffer;
						BipUtils.channelDataLength = BipUtils.channelDataLength
								- i;

						if (BipUtils.channelDataLength == 0) {
							// 说明现在的是非17 开头，直接发给发给服务器

							out = new DataOutputStream(ManageCS
									.getClientthread().getOutputStream());
							byte[] ch = BipUtils.channelData.getBytes();
							out.writeInt(ch.length);
							SocketUtils.writeBytes(out, ch, ch.length);
							System.out.println("加入新的缓存信息后要发给服务器的消息---"
									+ BipUtils.channelData);
							BipUtils.channelData = "";// 每发一次 就清空一次BipData

						} else {
							System.out.println("此刻要缓存拼包再发给服务器的数据  --身子  ："
									+ BipUtils.channelData);
							System.out
									.println("**********身后还需要加入多少字节的数据  ******"
											+ BipUtils.channelDataLength);
						}

					}
				}
			} else {
				// 数据长度小的状态，也要判断 是直接发
				int i = AnalyseUtils.atoi(data.substring(index + 2, index + 4));
				BipUtils.buffer = data.substring(index + 4, index + 2 * i + 4);

				if (BipUtils.channelDataLength == 0
						&& BipUtils.channelData.equals("")) {
					// 说明前面channelData数据为空
					BipUtils.channelData = BipUtils.buffer;
					if (BipUtils.channelData.substring(0, 6).equals("150303")) {
						System.out.println("关闭bip通道");
					} else {
						out = new DataOutputStream(ManageCS.getClientthread()
								.getOutputStream());
						byte[] ch = BipUtils.channelData.getBytes();
						out.writeInt(ch.length);
						SocketUtils.writeBytes(out, ch, ch.length);
						System.out.println("无缓存拼包直接=-=发给服务器的消息---"
								+ BipUtils.channelData);
						BipUtils.channelData = "";
					}
					// 每发一次 就清空一次BipData

				} else {
					BipUtils.channelData += BipUtils.buffer;
					BipUtils.channelDataLength = BipUtils.channelDataLength - i;
					if (BipUtils.channelDataLength == 0) {
						// 说明前面channelData数据为空
						BipUtils.channelData += BipUtils.buffer;
						out = new DataOutputStream(ManageCS.getClientthread()
								.getOutputStream());
						byte[] ch = BipUtils.channelData.getBytes();
						out.writeInt(ch.length);
						SocketUtils.writeBytes(out, ch, ch.length);
						System.out.println("又是=-=发给服务器的消息咯---"
								+ BipUtils.channelData);
						BipUtils.channelData = "";// 每发一次 就清空一次BipData

					} else {
						System.out.println("后续缓存消息 --  ："
								+ BipUtils.channelData);
						System.out.println("**********身后还需要加入多少字节的数据  ******"
								+ BipUtils.channelDataLength);
					}
				}
				

			}
		}
	}

	private static void openChannelAnalyse(String data) {
		String ip = "";
		// openchannel指令，第六个字节必须为“40”

		// 解析出缓存大小（因自身为手机终端模拟，暂无使用）
		int index = data.indexOf("39");
		if (index > 0) {
			BipUtils.bufferSize = data.substring(index + 4, index + 8);
			System.out.println("缓存大小为："
					+ AnalyseUtils.atoi(BipUtils.bufferSize));
		}

		// ip地址解析
		index = data.indexOf("3E");
		if (index > 0) {
			int i = AnalyseUtils.atoi(data.substring(index + 2, index + 4));
			BipUtils.ipAddress = data.substring(index + 6, index + 2 * i + 4);
			String ipAddressFormat = "";
			for (int j = 0; j < BipUtils.ipAddress.length(); j += 2) {
				int n = AnalyseUtils.atoi(BipUtils.ipAddress
						.substring(j, j + 2));
				ipAddressFormat = ipAddressFormat + String.valueOf(n) + ".";
			}
			ip = ipAddressFormat.substring(0, ipAddressFormat.length() - 1);
			System.out.println(ip);
		}
		
		//  port解析
		index = data.indexOf("3C");
		int port = 0;
		if (index > 0) {
			int i = AnalyseUtils.atoi(data.substring(index + 2, index + 4));
			BipUtils.port = data.substring(index + 6, index + 2 * i + 4);//	约定跳过一个字节
			port = AnalyseUtils.atoi(BipUtils.port);
			
			System.out.println("-----port hex String is : " + BipUtils.port + "------port is : " + port);
			
		} else {
			System.out.println("tag of EE is not found");
		}
		ClientDemo client = new ClientDemo(ip, port);
		// 全部解析出来后和服务器建立socket连接。
//		ClientDemo client = new ClientDemo(ip, 8090);// 端口号目前写死
		//ClientDemo client = new ClientDemo("localhost",8443); //自测时本地IP及端口号
		client.handler();

	}

	// 卡片返回状态码判断，将其修改为 Compcard,比较sw1，如果为90 则没有数据，如果为91 则说明有数据，则取最后一个字节，下发8012
	public static String LastByte(String CardStr) {
		String sw2 = "";
		if (CardStr.substring(0, 2).equals("90"))
			System.out.println("卡片返回为9000，没有数据可取了");
		if (CardStr.substring(0, 2).equals("91"))
			sw2 = CardStr.substring(CardStr.length() - 2, CardStr.length());
		return sw2;
	}
	
	public static void sms(String cardresp) {
		int index = cardresp.indexOf("E1");
		if(index == -1){
			cardresp = leftMoveOneBit(cardresp);
			index = cardresp.indexOf("E1"); 
		}
		String Card_sms = "";
		if(index>0){
			cardresp = cardresp.substring(index);
			TLVBean one = selectTlv(cardresp, "E1", "12000");
			while(one == null){
				index = cardresp.indexOf("E1",2);
				cardresp = cardresp.substring(index);
				one = selectTlv(cardresp, "E1", "12000");
			}
			int i = AnalyseUtils.atoi(one.getTaglen().substring(2));  //取出长度
			System.out.println("data长度"+i);
			Card_sms = one.getTaglen() + one.getValue();
			System.out.println("取出的短信"+Card_sms);
			System.out.println("********************************卡片返回通知短信********************************************"); 
		}else{
			System.out.println("********************************卡片没有返回通知短信********************************************");
			Card_sms = "E1354C10890010120123412340123456789012244D01024E0200004F10A0000005591010FFFFFFFF890000130014081122334455667788";
		}
		NotificationTest.pushNotificationBySms(Card_sms);
	}
	private static String leftMoveOneBit(String inputData){
			//十六进制字符串转化为二进制字符串
			StringBuilder result = new StringBuilder();
			String achar = "";
			for (int index=0;index<inputData.length();index++) {
				achar = "0000" + Integer.toBinaryString(Integer.parseInt(inputData.charAt(index)+"", 16));
				achar = achar.substring(achar.length()-4, achar.length());
				result.append(achar);
			}
			//左移一位
			String bresult = result.toString().substring(1) + "0";
			//二进制字符串转化为十六进制字符串
			StringBuilder cresult = new StringBuilder();
			for(int index=0;index<bresult.length()/4;index++){
				achar = Integer.toHexString(Integer.parseInt(bresult.substring(index*4, index*4+4), 2));
				cresult.append(achar);
			}
			return cresult.toString().toUpperCase();
	}
	
	/**
	 * 从数据中取出tag为给定值的TLV结构（后一个TLV结构的tag已知）
	 * @param inputData
	 * @param beginTag
	 * @param endTag
	 * @return
	 */
	private static TLVBean selectTlv(String inputData ,String beginTag ,String endTag) {
		int beginOfSeg = inputData.indexOf(beginTag);
		String s = inputData.substring(beginOfSeg + beginTag.length()); 
		if(s.substring(0, 2).equals("83")){
			int num83 = Integer.parseInt("83", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 8), 16)*2 + 8;
			if((num83 <= s.length())&&(s.substring(num83).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"83",s.substring(2, num83));
			}else if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 8),s.substring(8, num));
			}
		}else if(s.substring(0, 2).equals("82")){
			int num82 = Integer.parseInt("82", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 6), 16)*2 + 6;
			if((num82 <= s.length())&&(s.substring(num82).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"82",s.substring(2, num82));
			}else if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 6),s.substring(6, num));
			}
		}else if(s.substring(0, 2).equals("81")){
			int num81 = Integer.parseInt("81", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 4), 16)*2 + 4;
			if((num81 <= s.length())&&(s.substring(num81).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"81",s.substring(2, num81));
			}else if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 4),s.substring(4, num));
			}
		}else{
			int num = Integer.parseInt(s.substring(0, 2), 16)*2 + 2;
			if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 2),s.substring(2, num));
			}
		}
		return null;
	}


}
