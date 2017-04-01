package com.whty.euicc.common.utils;


import com.whty.euicc.common.constant.StaticConfig;


public class CheckCallType {
	
	private static final String IS_LOCAL_OPERATE = "0";
	
	/**
	 * 
	 * @return true:本地操作 false:调用服务
	 */
	public static boolean isLocalOperate(){
		return IS_LOCAL_OPERATE.equals(StaticConfig.getCallType());
	}

}
