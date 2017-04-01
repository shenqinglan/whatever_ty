package com.whty.efs.webservice.service;

import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.webservice.es.message.ES1RegisterEISRequest;
import com.whty.efs.webservice.es.message.ES2DisableProfileRequest;

/**
 * 标准客户端实现类 所有的客户端均需要实现该抽象类 内含初始化函数
 */
@SuppressWarnings("deprecation")
public interface EsWsClient {
	
	String es1RegisterEIS(EuiccEntity<ES1RegisterEISRequest> Entity) throws Exception;
	String es2DisableProfile(EuiccEntity<ES2DisableProfileRequest> Entity) throws Exception;

}
