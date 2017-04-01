package com.whty.ga.util;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName EncodingUtil
 * @author Administrator
 * @date 2017-3-3 上午9:55:28
 * @Description TODO(编码转换工具类)
 */
public class EncodingUtil {
	public static String encodeStr(String str) {  
        try {  
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;
        }  
    }  
}
