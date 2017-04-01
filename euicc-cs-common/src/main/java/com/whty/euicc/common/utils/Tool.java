package com.whty.euicc.common.utils;
/**
 * 进制转换工具类
 * @author Administrator
 *
 */
public class Tool {
	/**
	 * 将10进制的数字转化成16进制的数字，
	 * 需要注意的是传入的字符串必须能Integer，
	 * 配合方法isNumber使用
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
}
