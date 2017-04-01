package com.whty.euicc.common.utils;

/**
 * 解析EIS工具类
 * @author Administrator
 *
 */
public class TlsMessageUtils {
	public static String getEid(String requestStr){
		try {
			int indexOfEid=requestStr.indexOf("eid/");
			int endOfEid=requestStr.indexOf(";");
			String eid=requestStr.substring(indexOfEid+4, endOfEid);
			System.out.println("eid>>"+eid);
			return eid;
		} catch (Exception e) {
			e.printStackTrace();
			return "89001012012341234012345678901224";
		}
		
	 }
	
	public static String getPath(String requestStr){
		try {
			String path = org.apache.commons.lang3.StringUtils.substringBetween(requestStr, "HTTP POST", "HTTP/1.1").trim();
			System.out.println("path>>"+path);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	 }
}
