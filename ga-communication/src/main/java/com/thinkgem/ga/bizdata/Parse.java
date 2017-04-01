package com.thinkgem.ga.bizdata;


/**
 * 数据解析类
 * @author liuwsh
 * @version 2017-02-17
 */
public class Parse {
	public static String[] parse(String data) throws Exception {
		if (data == null) {
			throw new Exception();
		}
		String[] sa1 = convert(data, 1);
		data = data.substring(Integer.valueOf(sa1[1]));
		String[] sa2 = convert(data, 2);
		data = data.substring(Integer.valueOf(sa2[1]));
		String[] sa3 = convert(data, 3);
		String[] result = new String[3];
		result[0] = sa1[0];
		result[1] = sa2[0];
		result[2] = sa3[0];
		return result;
	}
	
	private static String[] convert(String data, int step) throws Exception {
		if (!data.startsWith("00") && !data.startsWith("01") && !data.startsWith("02")) {
			throw new Exception();
		}
		if (data.length() < 4) {
			throw new Exception();
		}
		String firstLengthStr = data.substring(2, 4);
		int firstLength = Integer.parseInt(firstLengthStr,16);
		data = data.substring(4);
		if (data.length() < firstLength * 2) {
			throw new Exception();
		}
		if (step == 1) {
			String[] r = new String[2];
			r[0] = data.substring(0, firstLength * 2);
			r[1] = String.valueOf((firstLength * 2) + 4);
			return  r;
		} else if (step == 2) {
			String[] r = new String[2];
			r[0] = data.substring(0, firstLength * 2);
			r[1] = String.valueOf((firstLength * 2) + 4);
			return  r;
		} else if (step == 3) {
			String[] r = new String[2];
			r[0] = convertDate(data);
			r[1] = "0";
			return r;
		}
		throw new Exception();
	}

	private static String convertDate(String date) throws Exception {
		if (date.length() != 14) {
			throw new Exception();
		}
		String year = String.valueOf(Integer.parseInt(date.substring(0, 4),16));
		String month = String.valueOf(Integer.parseInt(date.substring(4, 6),16));
		String day = String.valueOf(Integer.parseInt(date.substring(6, 8),16));
		String hour = String.valueOf(Integer.parseInt(date.substring(8, 10),16));
		String minute = String.valueOf(Integer.parseInt(date.substring(10, 12),16));
		String second = String.valueOf(Integer.parseInt(date.substring(12, 14),16));
		return addZero(year) + addZero(month) + addZero(day) + addZero(hour) + addZero(minute) + addZero(second);
	}
	
	private static String addZero(String s) {
		if (s.length() == 1) {
			return "0" + s;
		} else {
			return s;
		}
	}
	
	/** 
	 * @Title:hexString2String 
	 * @Description:16进制字符串转字符串 
	 * @param src 
	 *            16进制字符串 
	 * @return 字节数组 
	 * @throws 
	 */  
	private static String hexString2String(String src) {  
		String temp = "";  
		for (int i = 0; i < src.length() / 2; i++) {  
			temp = temp  
					+ (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),  
							16).byteValue();  
		}  
		return temp;  
	}  
	
	public static void main(String args[]) {
		try {
			String[] r = Parse.parse("00044f00480001056202097800020707e103030b1b0f");
			System.out.println(r[0]);
			System.out.println(r[1]);
			System.out.println(r[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
