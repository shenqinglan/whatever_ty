/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-13
 * Id: IOauthKeyService.java,v 1.0 2017-3-13 下午4:37:34 Administrator
 */
package com.whty.oauth.platform.service;

import java.util.List;

import com.whty.oauth.platform.pojo.OauthKey;

/**
 * @ClassName IOauthKeyService
 * @author Administrator
 * @date 2017-3-13 下午4:37:34
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IOauthKeyService {

	List<OauthKey> findCardKey(String eid, String iccid, String versionKic,
			String versionKid);

	List<OauthKey> findCardKey(String msisdn, String versionKic, String versionKid);

}
