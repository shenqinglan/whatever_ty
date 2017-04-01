package com.whty.euicc.common.properties;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;

/**
 * 环境配置，实网环境或者测试环境
 * @author Administrator
 *
 */
public class EnvProperty {
	
	private final static String testEnv = "0";
	private final static String productionEnv = "1";
	

	/**
	 * @return true:生产环境  false:测试环境
	 */
	public static boolean isProduction() {
		String smsConnectType = SpringPropertyPlaceholderConfigurer.getStringProperty("sms_connectType");
		return StringUtils.equals(productionEnv, smsConnectType);
	}

	
}
