package com.whty.euicc.rsp.util;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.rsp.beans.TLVBean;

/**
 * 把只包含TV格式的转成TLV格式,如长度超过0x7F，将会根据需要表示为T 81 LV 的格式;如果长度超过0xFF，将根据需要表示为T 82 LV;如果长度超过0xFFFF，将根据需要表示为T 83 LV。
 * 如果想将只含有V的数据转换为LV格式，也可使用该函数，将Tag设为空即可
 * @author Administrator
 *
 */
public class ToTLV {
	public static String toTLV(String tag,String input){
		if(StringUtils.isBlank(tag))return toTLV(input);
		return tag+toTLV(input);
	}
	
    public static String toTLV(String input) {
    	int inputLen = input.length()/2;
    	String strInputLenString = toHex(String.valueOf(inputLen));
    	if (inputLen > 65535) {
			input = "83" + strInputLenString + input;
		} else if (inputLen > 255) {
			input = "82" + strInputLenString + input;
		} else if(inputLen > 127) {
			input = "81" + strInputLenString + input;
		} else {
			input = strInputLenString + input;
		}

    	return input;
    }

	private static String toHex(String num) {
		String hex = Integer.toHexString(Integer.valueOf(num));
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex.toUpperCase();
	}
	
	
	public static String toEccTLV(String tag,String input) {
		if(StringUtils.isBlank(tag))return toEccLV(input);
		return tag+toEccLV(input);
    } 
	
	public static String toEccLV(String input) {
    	int inputLen = input.length()/2;
    	String strInputLenString = toHex(String.valueOf(inputLen));
		return strInputLenString + input;
    }
	
	/*
	 * 判断符合TLV结构，抽出两个tag间的value值
	 */
	public static String determineTLV(String input, String tag1, String tag2){
		int indexOfTag1 = input.indexOf(tag1);
		int indexOfTag2 = input.indexOf(tag2);
		while(indexOfTag1<=input.length() && indexOfTag1 >= 0 && indexOfTag2 >= 0){
			if(indexOfTag1 < indexOfTag2){
				if(indexOfTag1%2 == 0){
					while(indexOfTag2 <= input.length() && indexOfTag2 >= 0){
						if(indexOfTag2%2 == 0){
							String LV = input.substring(indexOfTag1+tag1.length(), indexOfTag2);
							if(StringUtils.isBlank(LV)){
								indexOfTag2 = input.indexOf(tag2, indexOfTag2+1);
								LV = input.substring(indexOfTag1+2, indexOfTag2);
							}
							String hexLen = LV.substring(0, 2);
							int len = Integer.parseInt(hexLen, 16);
							if(LV.substring(2).length()/2==len){
//								System.out.println("valueOfTlv >> "+LV.substring(2));
								return LV.substring(2);
							}else{
								indexOfTag2 = input.indexOf(tag2, indexOfTag2+1);
							}
						}else{
							indexOfTag2 = input.indexOf(tag2, indexOfTag2+1);
						}
						
					}
					indexOfTag1 = input.indexOf(tag1, indexOfTag1+1);
					indexOfTag2 = input.indexOf(tag2);
				}else{
					indexOfTag1 = input.indexOf(tag1, indexOfTag1+1);
				}
			}else{
				indexOfTag2 = input.indexOf(tag2, indexOfTag2+1);
			}
			
		}
		throw new RuntimeException("未找到合适的value");
	}
	
	/**
	 * 从数据中取出tag为给定值的TLV结构（后一个TLV结构的tag已知）
	 * @param inputData
	 * @param beginTag
	 * @param endTag
	 * @return
	 */
	public static TLVBean selectTlv(String inputData ,String beginTag ,String endTag) {
		int beginOfSeg = inputData.indexOf(beginTag);
		String s = inputData.substring(beginOfSeg + beginTag.length()); 
		if(s.substring(0, 2).equals("83")){
			int num83 = Integer.parseInt("83", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 8), 16)*2 + 8;
			if((num83 <= s.length())&&(s.substring(num83).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"83",s.substring(2, num83));
			}else if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 8),s.substring(8, num));
			}
		}else if(s.substring(0, 2).equals("82")){
			int num82 = Integer.parseInt("82", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 6), 16)*2 + 6;
			if((num82 <= s.length())&&(s.substring(num82).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"82",s.substring(2, num82));
			}else if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 6),s.substring(6, num));
			}
		}else if(s.substring(0, 2).equals("81")){
			int num81 = Integer.parseInt("81", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 4), 16)*2 + 4;
			if((num81 <= s.length())&&(s.substring(num81).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"81",s.substring(2, num81));
			}else if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 4),s.substring(4, num));
			}
		}else{
			int num = Integer.parseInt(s.substring(0, 2), 16)*2 + 2;
			if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 2),s.substring(2, num));
			}
		}
		return null;
	}
	
}
