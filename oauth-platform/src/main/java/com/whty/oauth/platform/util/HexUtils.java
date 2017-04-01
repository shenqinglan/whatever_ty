package com.whty.oauth.platform.util;

import java.io.UnsupportedEncodingException;

/**
 * 中文汉字及字符转为十六进制
 * @param targetStr
 * @return
 */
public class HexUtils {
   private static String hexString = "0123456789ABCDEF";  
   
    public static String encodeCN(String data) {  
        byte[] bytes;  
        try {  
            bytes = data.getBytes("gbk");  
            StringBuilder sb = new StringBuilder(bytes.length * 2);  
  
            for (int i = 0; i < bytes.length; i++) {  
                sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));  
                sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));  
            }  
            return sb.toString();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    public static String encodeStr(String data) {  
        String result = "";  
        byte[] bytes;  
        try {  
            bytes = data.getBytes("gbk");  
            for (int i = 0; i < bytes.length; i++) {  
                result += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);  
            }  
            return result;  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 判定是否为中文汉字 
     * @param data 
     * @return 
     */  
    public static boolean isCN(String data) {  
        boolean flag = false;  
        String regex = "^[\u4e00-\u9fa5]*$";  
        if (data.matches(regex)) {  
            flag = true;  
        }  
        return flag;  
    }  
 
    
    /**
     * 中文汉字及字符转为十六进制
     * @param targetStr
     * @return
     */
    public static String getHexResult(String targetStr) {  
        StringBuilder hexStr = new StringBuilder();  
        int len = targetStr.length();  
        if (len > 0) {  
            for (int i = 0; i < len; i++) {  
                char tempStr = targetStr.charAt(i);  
                String data = String.valueOf(tempStr);  
                if (isCN(data)) {  
                    hexStr.append(encodeCN(data));  
                } else {  
                    hexStr.append(encodeStr(data));  
                }  
            }  
        }  
        return hexStr.toString();  
    }  
    
     
	/** 
	* 将字符串转成unicode 
	* @param str 待转字符串 
	* @return unicode字符串 
	*/ 
	public static String convert(String str) 
	{ 
		str = (str == null ? "" : str); 
		String tmp; 
		StringBuffer sb = new StringBuffer(1000); 
		char c; 
		int i, j; 
		sb.setLength(0); 
		for (i = 0; i < str.length(); i++) 
		{ 
			c = str.charAt(i); 
			//sb.append("\\u"); 
			j = (c >>>8); //取出高8位 
			tmp = Integer.toHexString(j); 
			if (tmp.length() == 1) 
				sb.append("0"); 
				sb.append(tmp); 
				j = (c & 0xFF); //取出低8位 
				tmp = Integer.toHexString(j); 
			if (tmp.length() == 1) 
				sb.append("0"); 
			sb.append(tmp); 
		} 
		return (new String(sb)); 
	} 
	
    /**
     * @param hex
     *            将16进制的ascii
     * @return
     */
    public static String hexToAscii(String hex) {
        byte[] buffer = new byte[hex.length() / 2];
        String strByte;

        for (int i = 0; i < buffer.length; i++) {
            strByte = hex.substring(i * 2, i * 2 + 2);
            buffer[i] = (byte) Integer.parseInt(strByte, 16);
        }

        return new String(buffer);
    }
}
