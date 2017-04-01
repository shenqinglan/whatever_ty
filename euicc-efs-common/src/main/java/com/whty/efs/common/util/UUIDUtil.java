package com.whty.efs.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * 获取32位唯一的uuid
 * @ClassName: UUIDUtil  
 * @author liyang
 * @date 2015-4-17
 */
public class UUIDUtil {
	
	/**
	 * 返回32位唯一UUID字符串
	 * @return
	 */
	public static String getUuidString(){
		return new String(UUID.randomUUID().toString().replace("-", ""));
	}
	
	/** 
	 * 获取随机字母数字组合 
	 *  
	 * @param length 
	 *            字符串长度 
	 * @return 
	 */  
	public static String getRandomCharAndNumr(Integer length) {  
	    String str = "";  
	    Random random = new Random();  
	    for (int i = 0; i < length; i++) {  
	        boolean b = random.nextBoolean();  
	        if (b) { // 字符串  
	            // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
	            str += (char) (65 + random.nextInt(26));// 取得大写字母  
	        } else { // 数字  
	            str += String.valueOf(random.nextInt(10));  
	        }  
	    }  
	    return str;  
	} 
}
