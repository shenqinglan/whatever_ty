/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-8
 * Id: EuiccCardRepo.java,v 1.0 2017-3-8 上午9:26:54 Administrator
 */
package com.whty.oauth.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whty.oauth.platform.pojo.OauthCard;

/**
 * @ClassName EuiccCardRepo
 * @author Administrator
 * @date 2017-3-8 上午9:26:54
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Repository
public interface OauthCardRepo extends JpaRepository<OauthCard, String> {

	OauthCard findByMsisdn(String phoneNo);

	OauthCard findByEid(String eid);

	OauthCard findByMsisdnAndCardManufacturerIdAndIccid(String msisdn,
			String cardManufactureID, String iCCID);

}
