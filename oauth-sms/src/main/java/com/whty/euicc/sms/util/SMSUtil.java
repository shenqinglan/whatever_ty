package com.whty.euicc.sms.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.whty.euicc.util.StringUtil;
/**
 * 短信工具类
 * @author Administrator
 *
 */

public class SMSUtil {
	
	public static String getLengthHexStr(String hexStr) {
		int length = hexStr.length();
		return StringUtil.byteToHex((byte) (length / 2));
	}

	public static String getExtLengthHexStr(String hexStr) {
		int headerLenInt = hexStr.length() / 2;
		String headerLen = null;
		if ((headerLenInt >= 0) && (headerLenInt <= 127)) {
			headerLen = StringUtil.byteToHex((byte) headerLenInt);
		} else {
			if ((headerLenInt > 127) && (headerLenInt <= 255)) {
				headerLen = "81" + StringUtil.byteToHex((byte) headerLenInt);
			} else {
				if ((headerLenInt > 255) && (headerLenInt <= 65535)) {
					headerLen = "82"
							+ StringUtil.shortToHex((short) headerLenInt);
				} else {
					if ((headerLenInt > 65535) && (headerLenInt <= 16777215)) {
						headerLen = "83"
								+ StringUtil.intToHex(headerLenInt)
										.substring(2);
					} else {
						headerLen = "";
						System.out.println("命令头长度有误");
					}
				}
			}
		}
		return headerLen;
	}

	public static String hexDataPad(String hexData, String pad, int radix) {
		if (hexData.length() % radix != 0) {
			hexData = hexData
					+ pad.substring(0, radix - hexData.length() % radix);
		}
		return hexData;
	}

	public static boolean isBetween(String needCmp, String cmp1, String cmp2) {
		int needCmpNum = Integer.parseInt(needCmp, 16);
		int cmpNum1 = Integer.parseInt(cmp1, 16);
		int cmpNum2 = Integer.parseInt(cmp2, 16);
		if ((needCmpNum < cmpNum1) || (needCmpNum > cmpNum2)) {
			return false;
		}
		return true;
	}

	public static boolean checkData(String expectedData, String realData) {
		if (expectedData.length() == 0) {
			return true;
		}

		expectedData = expectedData.toUpperCase();
		if ((-1 == expectedData.indexOf("|"))
				&& (-1 != expectedData.indexOf("X"))) {
			return equalIgnoreX(realData, expectedData);
		}
		if ((-1 != expectedData.indexOf("|"))
				&& (-1 == expectedData.indexOf("X"))) {
			Pattern p = Pattern.compile(expectedData.toUpperCase());
			Matcher m = p.matcher(realData.toUpperCase());
			return m.matches();
		}
		if ((-1 != expectedData.indexOf("|"))
				&& (-1 != expectedData.indexOf("X"))) {
			Pattern p = Pattern.compile(expectedData.replaceAll("X", "."));
			Matcher m = p.matcher(realData.toUpperCase());
			return m.matches();
		}

		return realData.equalsIgnoreCase(expectedData);
	}

	private static boolean equalIgnoreX(String str, String xStr) {
		if (str.length() != xStr.length()) {
			return false;
		}

		int length = xStr.length();
		for (int i = 0; i < length; i++) {
			char c = xStr.charAt(i);
			String cStr = Character.toString(c).toUpperCase();

			if (!cStr.equals("X")) {
				String CompareToStr = Character.toString(str.charAt(i))
						.toUpperCase();

				if (!cStr.equals(CompareToStr)) {
					return false;
				}
			}
		}

		return true;
	}
}
