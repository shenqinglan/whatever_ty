package com.whty.euicc.common.utils;

import java.util.UUID;
/**
 * 生产不重复主键工具类
 * @author Administrator
 *
 */
public class UuidUtil {
	public static String createId(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
