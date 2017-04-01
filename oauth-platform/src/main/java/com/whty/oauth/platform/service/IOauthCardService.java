/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-7
 * Id: IQuickOauthService.java,v 1.0 2017-3-7 下午3:57:59 Administrator
 */
package com.whty.oauth.platform.service;

import com.whty.oauth.platform.pojo.OauthCard;

/**
 * @ClassName IQuickOauthService
 * @author Administrator
 * @date 2017-3-7 下午3:57:59
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IOauthCardService {

	OauthCard findEuiccCardByPhoneNo(String phoneNo);

	void save(OauthCard card);

	OauthCard findEuiccCardByEid(String eid);

	OauthCard findByMsisdn(String msisdn);

	OauthCard findByMsisdnAndCardManufacturerIdAndIccid(String msisdn,
			String cardManufactureID, String iCCID);

}
