/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-7
 * Id: QuickOauthServiceImpl.java,v 1.0 2017-3-7 下午3:58:30 Administrator
 */
package com.whty.oauth.platform.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.whty.oauth.platform.pojo.OauthCard;
import com.whty.oauth.platform.repository.OauthCardRepo;
import com.whty.oauth.platform.service.IOauthCardService;

/**
 * @ClassName QuickOauthServiceImpl
 * @author Administrator
 * @date 2017-3-7 下午3:58:30
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Transactional
@Service("oauthCardServiceImpl")
public class OauthCardServiceImpl implements IOauthCardService {
	
	@Resource
	private OauthCardRepo euiccCardRepo;

	@Override
	public OauthCard findEuiccCardByPhoneNo(String phoneNo) {
		return euiccCardRepo.findByMsisdn(phoneNo);
	}

	@Override
	public OauthCard findEuiccCardByEid(String eid) {
		// TODO Auto-generated method stub
		return euiccCardRepo.findByEid(eid);
	}

	@Override
	public void save(OauthCard card) {
		euiccCardRepo.save(card);
	}

	@Override
	public OauthCard findByMsisdn(String msisdn) {
		// TODO Auto-generated method stub
		return euiccCardRepo.findByMsisdn(msisdn);
	}

	@Override
	public OauthCard findByMsisdnAndCardManufacturerIdAndIccid(String msisdn,
			String cardManufactureID, String iCCID) {
		// TODO Auto-generated method stub
		return euiccCardRepo.findByMsisdnAndCardManufacturerIdAndIccid(msisdn,cardManufactureID,iCCID);
	}
}
