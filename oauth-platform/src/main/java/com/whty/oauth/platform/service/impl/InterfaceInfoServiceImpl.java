/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-8
 * Id: TraceInfoServiceImpl.java,v 1.0 2017-3-8 上午9:05:59 Administrator
 */
package com.whty.oauth.platform.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.whty.oauth.platform.pojo.InterfaceInfo;
import com.whty.oauth.platform.repository.InterfaceInfoRepo;
import com.whty.oauth.platform.service.IInterfaceInfoService;

/**
 * @ClassName TraceInfoServiceImpl
 * @author Administrator
 * @date 2017-3-8 上午9:05:59
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Transactional
@Service("interfaceInfoServiceImpl")
public class InterfaceInfoServiceImpl implements IInterfaceInfoService {

	@Resource
	private InterfaceInfoRepo traceInfoRepo;
	
	@Override
	public void save(InterfaceInfo traceInfo) {
		traceInfoRepo.save(traceInfo);
	}

	@Override
	public InterfaceInfo findByMsisdnAndInterfaceTypeAndTransactionId(
			String src, String type, String transactionID) {
		return traceInfoRepo.findByMsisdnAndInterfaceTypeAndTransactionId(src,type,transactionID);
	}

	@Override
	public InterfaceInfo getByMsisdnAndTransactionId(String phoneNo,
			String transactionId) {
		// TODO Auto-generated method stub
		return traceInfoRepo.findByMsisdnAndTransactionId(phoneNo, transactionId);
	}

}
