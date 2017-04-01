package com.whty.oauth.platform.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 把只包含TV格式的转成TLV格式,如长度超过0x7F，将会根据需要表示为T 81 LV 的格式;如果长度超过0xFF，将根据需要表示为T 82 LV;如果长度超过0xFFFF，将根据需要表示为T 83 LV。
 * 如果想将只含有V的数据转换为LV格式，也可使用该函数，将Tag设为空即可
 * @author Administrator
 *
 */
public class ToTLV {
	
	public static String toTLVExtend(String tag,String input,String extend){
		if(StringUtils.isBlank(tag))return toTLVExtend(input,extend);
		return tag+toTLVExtend(input,extend);
	}
	
	public static String toTLVExtend(String input,String extend) {
    	int inputLen = new StringBuilder().append(input).append(extend).length()/2;
    	StringBuilder sb = new StringBuilder();
    	String strInputLenString = toHex(String.valueOf(inputLen));
    	if (inputLen > 65535) {
    		sb.append("83").append(strInputLenString).append(input);
		} else if (inputLen > 255) {
			sb.append("82").append(strInputLenString).append(input);
		} else if(inputLen > 127) {
			sb.append("81").append(strInputLenString).append(input);
		} else {
			sb.append(strInputLenString).append(input);
		}

    	return sb.toString();
    }
	
	public static String toTLV(String tag,String input){
		if(StringUtils.isBlank(tag))return toTLV(input);
		return tag+toTLV(input);
	}
	
	
    public static String toTLV(String input) {
    	int inputLen = input.length()/2;
    	StringBuilder sb = new StringBuilder();
    	String strInputLenString = toHex(String.valueOf(inputLen));
    	if (inputLen > 65535) {
    		sb.append("83").append(strInputLenString).append(input);
		} else if (inputLen > 255) {
			sb.append("82").append(strInputLenString).append(input);
		} else if(inputLen > 127) {
			sb.append("81").append(strInputLenString).append(input);
		} else {
			sb.append(strInputLenString).append(input);
		}

    	return sb.toString();
    }

	private static String toHex(String num) {
		String hex = Integer.toHexString(Integer.valueOf(num));
		if (hex.length() % 2 != 0) {
			hex = new StringBuilder().append("0").append(hex).toString();
		}
		return hex.toUpperCase();
	}
	
	
	public static String toEccTLV(String tag,String input) {
		if(StringUtils.isBlank(tag))return toEccLV(input);
		return new StringBuilder().append(tag).append(toEccLV(input)).toString();
    } 
	
	public static String toEccLV(String input) {
		//没有体
		if (input == null) {
			return "00";
		}
    	int inputLen = input.length()/2;
    	String strInputLenString = toHex(String.valueOf(inputLen));
    	return new StringBuilder().append(strInputLenString).append(input).toString();
    }
	
	public static String appends(String ...args){
		StringBuilder sb = new StringBuilder();
		int num = args.length;
		for(int i = 0;i < num;i++){
			sb.append(args[i]);
		}
		return sb.toString();
	}
	
	public static String intToHex(int num) {
		String hex = Integer.toHexString(num);
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex.toLowerCase();
	}
}
