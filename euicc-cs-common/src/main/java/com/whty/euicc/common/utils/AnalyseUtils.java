package com.whty.euicc.common.utils;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


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


}