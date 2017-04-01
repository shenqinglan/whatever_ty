package com.whty.tool.handler;

import static com.whty.tool.util.Util.replaceBlank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.whty.tool.util.Constant;
import com.whty.tool.util.Util;
/**
 *  预个人化脚本指令转换-Puk 模块
 * @author Administrator
 *
 */
public class PukHandler {
	
	public static String pukHandler(List<String> lines,int peID) throws Exception{
		if(lines == null)return null;
		List<String>list = new ArrayList<String>();
		for (String line : lines) {
			if(StringUtils.isBlank(line))continue;
			line = StringUtils.upperCase(replaceBlank(line));
			line = StringUtils.remove(line, " ");
			if (StringUtils.startsWith(line, Constant.PUK_PREFIX)) {
			dealPukApdus(list, line);
			}
		}
		String resultStr = addHeader(list,peID);
		System.out.println("the last puk:" + resultStr);
		return resultStr;
	}

	
	private static void dealPukApdus(List<String> list, String line) {
		String str = line.substring(10);
		String keyReference = str.substring(0, 2);//80
		String maxNumOfAtte = str.substring(12, 14);//82
		String pukValue = str.substring(16);//81
		String tagWithKeyRefer = Util.toTLV("80", "00"+keyReference);
		String tagWithpukValue = Util.toTLV("81", pukValue);
		String tagWithMaxOfAtt = Util.toTLV("82", maxNumOfAtte);
		str = Util.toTLV("30", tagWithKeyRefer + tagWithpukValue + tagWithMaxOfAtt);
		list.add(str);
	}
	
	
	private static String addHeader(List<String> list,int peId) throws Exception {
		String packagesString = Util.totalStr(list);
		String peString = Util.itoa(peId);//16
		String peHeader = Util.toTLV("A0", "80008101" + peString);
		String pinString = Util.toTLV("A1", packagesString);
		String resultStr = Util.toTLV("A3", peHeader + pinString);
		return resultStr;
	}


}
