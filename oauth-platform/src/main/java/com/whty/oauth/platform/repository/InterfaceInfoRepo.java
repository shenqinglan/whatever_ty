/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-8
 * Id: TraceInfoRepo.java,v 1.0 2017-3-8 上午9:18:52 Administrator
 */
package com.whty.oauth.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whty.oauth.platform.pojo.InterfaceInfo;

/**
 * @ClassName TraceInfoRepo
 * @author Administrator
 * @date 2017-3-8 上午9:18:52
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Repository
public interface InterfaceInfoRepo extends JpaRepository<InterfaceInfo, String> {

	InterfaceInfo findByMsisdnAndInterfaceTypeAndTransactionId(String src,
			String type, String transactionID);

	InterfaceInfo findByMsisdnAndTransactionId(String phoneNo,
			String transactionId);
}
