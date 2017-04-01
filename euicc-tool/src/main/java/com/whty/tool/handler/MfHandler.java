package com.whty.tool.handler;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.whty.tool.util.Constant;
import com.whty.tool.util.Util;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import static com.whty.tool.util.Util.replaceBlank;

/**
 * mf的脚本指令转换为der
 * @author Administrator
 *
 */
public class MfHandler {
	private static List<String> seQuence =  new ArrayList<String>();//用于规则填充

	/**
	 * mf 转换入口
	 * @param lines
	 * @param peID
	 * @return
	 * @throws Exception
	 */
	public static String baseHandler(List<String> lines,int peID) throws Exception{
		if(lines == null)return null;
		List<String>list = new ArrayList<String>();
		for (String line : lines) {
			if(StringUtils.isBlank(line))continue;
		    line = StringUtils.upperCase(replaceBlank(line));
			String str = StringUtils.remove(line, " ");
			if (StringUtils.startsWith(str, Constant.MF_PREFIX) 
					|| StringUtils.startsWith(str, Constant.EF_PREFIX)) { 
				seQuence.clear();
				checkApduLength(str);
				String strOf00E0 = parseCmdFor00E0(str);
				list.add(strOf00E0);
			}else if (StringUtils.startsWith(str, Constant.FILE_CONTENT_PREFIX)) {
				checkApduLength(str);
				seQuence.add(str.substring(4, 6));
				String strOf00DC = parseCmdFor00DC(str);
				list.add(strOf00DC);
			}else {
				System.out.println("This line is not the command need to parse!");
			}
		}
		String resultStr = addHeader(list,peID);
		System.out.println("the last mf:" + resultStr);
		return resultStr;
	}

	private static void checkApduLength(String str) throws Exception {
		int len = str.substring(10).length()/2;
		String lenCmd = Util.itoa(len);
		if (!lenCmd.equals(str.substring(8, 10))) {
			throw new RuntimeException("apdu lc is not equals the reality length");
		}
	}
	
	/**
	 * 添加mf头指令
	 * A1..A0..A1..30
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String  addHeader(List<String>list,int peId) throws Exception {
		String packagesString = Util.totalStr(list);
		String peString = Util.itoa(peId);//16
		String peHeader = Util.toTLV("A0", "80008101" + peString);
		String mfString = Util.toTLV("A1", Util.toTLV("30", packagesString));
		String resultStr = Util.toTLV("A1", peHeader + mfString);
		return resultStr;
		
	}
	/**
	 * 分割00DC开头的指令
	 * @param str
	 * @throws Exception 
	 */
	public static String parseCmdFor00DC(String str) throws Exception {
		str = str.substring(10);
		String totalString = "";
		if (seQuence.size() >=  2) {
			int first = Util.atoi(seQuence.get(seQuence.size()-2));
			int second = Util.atoi(seQuence.get(seQuence.size()-1));
			if ((second - first) > 1 ) {
				for (int j = 0; j < (second - first); j++) {
					String fixStr = paddingFStr(str);
					String commandWithTag = Util.toTLV("81", fixStr);
					totalString += commandWithTag;
					System.out.println("对于缺失的规则补齐:" + totalString);
				}
				totalString = totalString + Util.toTLV("81", str);
				System.out.println("00DC开头:" + totalString);
				
			}else if ((second - first) == 1 ) {
				totalString = Util.toTLV("81", str);
				System.out.println("00DC开头:" + totalString);
			}else {//rule number 等于或小于上一条
				throw new RuntimeException("the rule number must higher than before one!!");
			}
		}else {
			totalString = Util.toTLV("81", str);
			System.out.println("00DC开头:" + totalString);
		}
		
		return totalString;
	}
	
	/**
	 * 分割00E0开头的指令
	 * 62..82..83..8A..8B
	 * 62..82..83..8B
	 * @param str
	 * @throws Exception
	 */
	public static String parseCmdFor00E0(String str) throws Exception{
		List<String> list = new ArrayList<String>();
		str = str.substring(10);//62
		if (!StringUtils.startsWith(str, "62")) {
			throw new RuntimeException("This command is not start with tag62");
		}

		str = str.substring(4);//到82处
		if (!StringUtils.startsWith(str, "82")) {
			throw new RuntimeException("This command is not start with tag82");
		}

		str = orgTlv("82",str,list);
		if (!StringUtils.startsWith(str, "83")) {
			throw new RuntimeException("This command is not start with tag83");
		}
		
		str = orgTlv("83",str,list);
		if (StringUtils.startsWith(str, "8A")) {
			str = remove("8A",str,list);
			parseCmdFor8B(list, str);
			
		}else {
			parseCmdFor8B(list, str);
		}
			
		//将tag62与后续指令组成TLV
		String total = Util.totalStr(list);
		String totalString = Util.toTLV("62", total);
		System.out.println("00E0开头：" + totalString);
		return totalString;
	}

	/**
	 * 8B..81..C6..A5
	 * 8B..C6..A5
	 * 8B..80..88
	 * @param list
	 * @param resultStr
	 * @throws Exception
	 */
	private static void parseCmdFor8B(List<String> list, String resultStr)
			throws Exception {
		if (!StringUtils.startsWith(resultStr,"8B")) {
			throw new RuntimeException("This command is not start with tag8B");
		}
		resultStr = remove2F06In8B("8B",resultStr,list);
		if (StringUtils.startsWith(resultStr,"81")) {//optional
			resultStr = orgTlv("81",resultStr,list);
			if (!StringUtils.startsWith(resultStr,"C6")) {
				throw new RuntimeException("This command is not start with tagC6");
			}
			resultStr = orgC6Tag("C6",resultStr,list);
			if (StringUtils.startsWith(resultStr,"A5")) {
				resultStr = remove("A5",resultStr,list);
			}
		}else if (StringUtils.startsWith(resultStr,"C6")) {//MF
			resultStr = orgC6Tag("C6",resultStr,list);
			if (StringUtils.startsWith(resultStr,"A5")) {//optional
				resultStr = remove("A5",resultStr,list);}
		
		}else if (StringUtils.startsWith(resultStr,"80")) {//EF
			resultStr = orgTlv("80",resultStr,list);
			if (StringUtils.startsWith(resultStr,"88")) {//optional
				resultStr = orgTlv("88",resultStr,list);
			}
		}else {
			throw new RuntimeException("This command is not start with essential tag C6 or 80");
		}
	} 

	public static String paddingFStr(String str){
		String fixStr = "";
		for (int i = 0; i < str.length(); i++) {
		    fixStr +="F";
		}
		return fixStr;
	}
	
	public  static String orgTlv(String tag,String str,List<String> list){
		StringBuilder cmdBuilder = new StringBuilder();
		String length = str.substring(2, 4);//16
		int tempLen = Util.atoi(length)*2;//10
		String value = str.substring(4, 4 + tempLen);
		cmdBuilder.append(tag).append(length).append(value);
		list.add(cmdBuilder.toString());
		str = str.substring((tag + length + value).length());
		return str;
	}
	/**
	 * 对于不需要的tlv，移除
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 */
	public static String remove(String tag,String str,List<String > list){
		String length = str.substring(2, 4);//16
		int tempLen = (Util.atoi(length))*2;//10
		String value = str.substring(4, 4 + tempLen);
		str = str.substring((tag + length + value).length());
		return str;
	}
	/**
	 * 移除TAG 8B中的2F06
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String remove2F06In8B(String tag,String str,List<String > list) throws Exception{
		String length = str.substring(2, 4);//16
		int tempLen = (Util.atoi(length))*2;//10
		String value = str.substring(4, 4 + tempLen);
		String str2F06 = value.substring(0, 4);
		String resultValue = StringUtils.remove(value, str2F06);
		String comTlv = Util.toTLV(tag, resultValue);
		System.out.println("after remove2F06 In 8B:" +comTlv);
		list.add(comTlv);
		str = str.substring((tag + length + value).length());
		return str;
	}
	/**
	 * 移除TAG C6中不需要的数据
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String orgC6Tag(String tag,String str,List<String > list) throws Exception{
		String lenOfC6 = str.substring(2, 4);//16
		int totalLen = (Util.atoi(lenOfC6))*2;//10
		String valueOfC6 = str.substring(4, 4 + totalLen);
		String Value0f83 = "";
		if(valueOfC6.indexOf("83") != -1){  
	        String[] str1 = valueOfC6.split("83"); 
	        for (int i = 1; i< str1.length; i++) {
				System.out.println("C6TLV中每个tag83后的length和value:" + str1[i]);
				String length = str1[i].substring(0, 2);//16
				int tempLen = (Util.atoi(length))*2;//10
				String value = str1[i].substring(2, 2 + tempLen);
				Value0f83 +=  value;
			}
	        String reOrgStrC6 = Util.toTLV(tag, Value0f83);
			list.add(reOrgStrC6);
			str = str.substring((tag + lenOfC6 + valueOfC6).length());
	    }else {
			throw new RuntimeException("did not get the tag 83!");
		} 
		return str;
	}
	/*
	private static String tlvConstru(String str) throws Exception{
		List<Object> list = new ArrayList<Object>();
		int position = 0;
		while (position != StringUtils.length(str)) {  
			String hexTag = Util.getTag(str, position); 
			if (StringUtils.equals(hexTag, "8A")) {
				position += hexTag.length();  
				PositionBean l_position = Util.getLengthAndPosition(str, position);  
				String lenString = l_position.getValueLength();  
				int length = Util.atoi(lenString);
				position = l_position.getCurrPosition();  
				String value = StringUtils.substring(str, position, position + length * 2);
				position = position + value.length();

			}else if (StringUtils.equals(hexTag, "8B")) {
				position += hexTag.length();  

				PositionBean l_position = Util.getLengthAndPosition(str, position);  
				String lenString = l_position.getValueLength();  
				int length = Util.atoi(lenString);
				position = l_position.getCurrPosition();  
				String value = StringUtils.substring(str, position, position + length * 2);
				String str2F06 = value.substring(0, 4);
				String resultValue = StringUtils.remove(value, str2F06);
				lenString = Util.itoa(resultValue.length()/2);
				list.add(new TlvBean(hexTag, lenString, resultValue)); 
				position = position + value.length();  


			}else {
				position += hexTag.length();  

				PositionBean l_position = Util.getLengthAndPosition(str, position);  
				String lenString = l_position.getValueLength();  
				int length = Util.atoi(lenString);
				position = l_position.getCurrPosition();  
				String value = StringUtils.substring(str, position, position + length * 2);  
				position = position + value.length();  
				list.add(new TlvBean(hexTag, lenString, value));  

			}

		}
//		String total = totalStr(list);
//		String totalString = Util.toTLV("62", total);
//		System.out.println(totalString);
//		return totalString;
		return null;
	}
*/
  
}
