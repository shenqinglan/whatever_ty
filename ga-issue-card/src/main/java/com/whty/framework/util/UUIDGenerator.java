package com.whty.framework.util;

import java.util.UUID;

/**
 * @ClassName UUIDGenerator
 * @author Administrator
 * @date 2017-3-3 上午9:49:59
 * @Description TODO(生成uuid)
 */
public class UUIDGenerator {

	 public static String getUUID(){ 
	        String s = UUID.randomUUID().toString(); 
	        //去掉“-”符号 
	        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	} 
}
