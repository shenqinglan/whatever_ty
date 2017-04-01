/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-8
 * Id: ITraceInfoService.java,v 1.0 2017-3-8 上午9:05:41 Administrator
 */
package com.whty.oauth.platform.service;

import com.whty.oauth.platform.pojo.InterfaceInfo;

/**
 * @ClassName ITraceInfoService
 * @author Administrator
 * @date 2017-3-8 上午9:05:41
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IInterfaceInfoService {

	void save(InterfaceInfo traceInfo);

	InterfaceInfo findByMsisdnAndInterfaceTypeAndTransactionId(String src,
			String type, String transactionID);

	InterfaceInfo getByMsisdnAndTransactionId(String phoneNo,
			String transactionId);

}
