package com.whty.tool.handler;

import static com.whty.tool.util.Util.replaceBlank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.whty.tool.util.Constant;
import com.whty.tool.util.Util;


/**
 * 预个人化脚本指令转换-Pin 模块
 * @author sql
 *
 */
public class PinHandler {
	public static String pinHandler(List<String> lines,int peID) throws Exception{
		if(lines == null)return null;
		List<String>list = new ArrayList<String>();
		for (String line : lines) {
			if(StringUtils.isBlank(line))continue;
			line = StringUtils.upperCase(replaceBlank(line));
			line = StringUtils.remove(line, " ");
			if (StringUtils.startsWith(line, Constant.PIN_PREFIX)) {
			dealPinApdus(list, line);
			}
		}
		String resultStr = addHeader(list,peID);
		System.out.println("the last pin:" + resultStr);
		return resultStr;
	}

	/**
	 * 将指令相应字段的值转换并组成tlv结构
	 * 
	 * @param list
	 * @param line
	 */
	private static void dealPinApdus(List<String> list, String line) {
		String tagWithUnBlocking ="";
		String str = line.substring(10);
		String keyReference = str.substring(0, 2);//80
		String maxNumOfAtte = str.substring(12, 14);//84
		String pinAttribute = str.substring(14, 16);//83
		String pinValue = str.substring(16);//81
		if ("01".equals(keyReference) || "81".equals(keyReference)) {
			String unBlockingPinRefer = keyReference;//82
			tagWithUnBlocking = Util.toTLV("82", "00" + unBlockingPinRefer);
		}else {
			tagWithUnBlocking ="";
		}
		String tagWithKeyRefer = Util.toTLV("80", keyReference);
		String tagWithpinValue = Util.toTLV("81", pinValue);
		if ("01".equals(pinAttribute)) {
			pinAttribute = "06"; //0000 0110
		}else if ("00".equals(pinAttribute)) {
			pinAttribute = "07"; //0000 0111
		}else {
			throw new RuntimeException("pinAttribute value in scripts could only include 00 or 01!");
		}
		String tagWithpinAttri = Util.toTLV("83",pinAttribute);
		String tagWithMaxOfAtt = Util.toTLV("84", maxNumOfAtte);
		str = Util.toTLV("30", tagWithKeyRefer + tagWithpinValue + tagWithUnBlocking
				+ tagWithpinAttri + tagWithMaxOfAtt);
		list.add(str);
	}

	/**
	 * 拼装pin元素头
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private static String addHeader(List<String> list,int peId) throws Exception {
		String packagesString = Util.totalStr(list);
		String peString = Util.itoa(peId);//16
		String peHeader = Util.toTLV("A0", "80008101" + peString);
		String pinString = Util.toTLV("A1", Util.toTLV("A0", packagesString));
		String resultStr = Util.toTLV("A2", peHeader + pinString);
		return resultStr;
	}

}
