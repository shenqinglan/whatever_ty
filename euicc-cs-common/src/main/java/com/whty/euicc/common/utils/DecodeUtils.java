package com.whty.euicc.common.utils;


import java.nio.charset.Charset;

/**
 * 接口数据加密工具类
 *
 * @author qimin
 *
 */
public class DecodeUtils {
	private static final Charset UTF8 = Charset.forName("UTF-8");

	/**
	 * 加密String
	 * @throws SecurityException 
	 */
	public static String enData(String data, String mallKey) throws SecurityException {
		String redata = "";
		// 生成随机数
		String randData = SecurityUtil.generalStringToAscii(8) + SecurityUtil.generalStringToAscii(8);
		// 获取过程密钥
		String processKey = SecurityUtil.desecb(mallKey, randData, 0);
		// 将actionInfo转换16进制后，补80
		redata = SecurityUtil.padding80(SecurityUtil.bytesToHexString(data.getBytes(UTF8)));
		// 将字符串编码成16进制数字,适用于所有字符（包括中文）
		redata = SecurityUtil.encodeHexString(redata);
		// 加密
		redata = SecurityUtil.desecb(processKey, redata, 0);
		// 最终生成密文
		String end = randData + redata;
		return end;
	}

	/**
	 * 解密String
	 * @throws SecurityException 
	 */
	public static String deData(String data, String mallKey) throws SecurityException {
		if (null == data || data.trim().length() < 32) {
			return null;
		}
		// 主 非空判断
		if (mallKey == null) {
			return null;
		}
		// 获取随机数
		String randData = data.substring(0, 32);
		// 获取应用密文
		String singData = data.substring(32, data.length());
		// 获取过程密钥
		String processKey = SecurityUtil.desecb(mallKey, randData, 0);
		// 解密singData
		String actionInfoString = SecurityUtil.desecb(processKey, singData, 1);
		// 将16进制数字解码成字符串,适用于所有字符（包括中文）
		actionInfoString = SecurityUtil.hexStringToString(actionInfoString);
		// 最后一个'80'出现的位置
		int num = actionInfoString.lastIndexOf("80");
		// 截取actionInfoString
		if (num != -1) {
			actionInfoString = actionInfoString.substring(0, num);
		}
		actionInfoString = new String(SecurityUtil.hexToBytes(actionInfoString), UTF8);
		return actionInfoString;
	}

}
