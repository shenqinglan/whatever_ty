/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-13
 * Id: OauthKeyServiceImpl.java,v 1.0 2017-3-13 下午4:37:58 Administrator
 */
package com.whty.oauth.platform.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.whty.oauth.platform.pojo.OauthKey;
import com.whty.oauth.platform.repository.OauthKeyRepo;
import com.whty.oauth.platform.service.IOauthKeyService;

/**
 * @ClassName OauthKeyServiceImpl
 * @author Administrator
 * @date 2017-3-13 下午4:37:58
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Transactional
@Service("oauthKeyServiceImpl")
public class OauthKeyServiceImpl implements IOauthKeyService {
	
	@Resource
	private OauthKeyRepo keyRepo;

	@Override
	public List<OauthKey> findCardKey(String eid, String iccid,
			String versionKic, String versionKid) {
		// TODO Auto-generated method stub
		return keyRepo.findCardKey(eid, iccid, versionKic, versionKid);
	}

	@Override
	public List<OauthKey> findCardKey(String msisdn, String versionKic,
			String versionKid) {
		// TODO Auto-generated method stub
		return keyRepo.findCardKey(msisdn, versionKic, versionKid);
	}

}
