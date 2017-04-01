package com.whty.euicc.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtil extends org.apache.commons.lang3.StringUtils{
    /**
	 * 将字符串左边填充指定字符到len长度
	 * 
	 * @param s
	 *            原始字符串
	 * @param paddingStr
	 *            填充字符串
	 * @param len
	 *            长度
	 * @return
	 */
	public static String paddingStrLeft(String s, String paddingStr, int len) {
		int strLen = s.length();
		StringBuffer zeros = new StringBuffer("");
		for (int loop = len - strLen; loop > 0; loop--) {
			zeros.append(paddingStr);
		}
		return zeros.append(s).toString();
	}
	
	
	public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	
	
	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获得长度L编码<br>
	 * 0 - 127 : '00' - '7F'<br>
	 * 128 - 2147483647 : '8180' - '847FFFFFFF'<br>
	 *
	 * @param strLength
	 *            注意，这里是字节长度，不是字符长度。调用前要算出字节长度
	 * @return
	 */
	public static String getLength(int strLength) {
		String len = "";
		String hexLen = Integer.toHexString(strLength).toUpperCase();
		int hexLength = hexLen.length();
		if (hexLength % 2 == 1) {
			// 长度为奇数时，高位补0
			hexLen = "0" + hexLen;
			hexLength++;
		}
		hexLength = hexLength / 2;
		if (strLength <= 127) {
			// short类型表示
			len = hexLen;
		} else {
			// long类型表示，字节长度不会超过0x8，而int类型字节长度不超过0x4，故数值不会溢出
			len = "8" + String.valueOf(hexLength) + hexLen;
		}
		return len;
	}
	
	
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
}
