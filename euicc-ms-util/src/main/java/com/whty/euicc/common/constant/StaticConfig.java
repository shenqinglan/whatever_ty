package com.whty.euicc.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class StaticConfig {
	/**
	 * 操作方式（本地/调用服务）
	 */
	private static String callType;
	
	private static String dpUrl;
	
	private static String srUrl;

	public static String getSrUrl() {
		return srUrl;
	}

	@Value("${sr_url}")
	public void setSrUrl(String srUrl) {
		StaticConfig.srUrl = srUrl;
	}

	public static String getCallType() {
		return callType;
	}
	
	@Value("${is_call_server}")
	public void setCallType(String callType) {
		StaticConfig.callType = callType;
	}

	public static String getDpUrl() {
		return dpUrl;
	}

	@Value("${dp_url}")
	public void setDpUrl(String dpUrl) {
		StaticConfig.dpUrl = dpUrl;
	}
	
	
	
	

}
