package com.whty.euicc.rsp.utils;

import java.io.ByteArrayOutputStream;


public class Tools {
	
	/**
	 * 将10进制的数字转化成16进制的数字
	 * @param num
	 * @return
	 */
	public static String toHex(String num) {
		String hex = Integer.toHexString(Integer.valueOf(num));
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex.toUpperCase();
	}
	
	/**
	 * 求数据前加上tag、length之后的总长度，比如加两个字节A0 7F或三个字节A0 81 80或四个字节A0 82 02 A0 
	 * @return
	 */
	public static String add8182(int len, int returnFlag) {
		String slen8182 = "";
		if (len < 128) {
			slen8182 = itoa(len, 1);
			len += 2;
		} else if (len < 256) {
			slen8182 = "81" + itoa(len, 1);
			len += 3;
		} else if (len < 65536) {
			slen8182 = "82" + itoa(len, 2);
			len += 4;
		}
		
		if (returnFlag == 0) {
			return String.valueOf(len);
		} else {
			return slen8182;
		}
	}
	
	public static String itoa(int value, int len) {
		String result = Integer.toHexString(value).toUpperCase(); // EEEEEEEEE
        int rLen = result.length();
        len = 2 * len;
        if (rLen > len) {
            return result.substring(rLen - len, rLen);
        }
        if (rLen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rLen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
	}
	
	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encodeHexString(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		final String hexString = "0123456789abcdef";
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}
	
	/**
	 * HEX解码 将形如122A01 转换为0x12 0x2A 0x01
	 * 
	 * @param data
	 * @return
	 */
	public static String hexDecode(String data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (int i = 0; i < data.length(); i += 2) {
			String onebyte = data.substring(i, i + 2);
			int b = Integer.parseInt(onebyte, 16) & 0xff;
			out.write(b);
		}
		return new String(out.toByteArray());
	}
	
	public static byte[] hexToBytes(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return buffer;
	}
}
