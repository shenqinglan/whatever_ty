package com.whty.rsp_lpa_app.utils;

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
}
