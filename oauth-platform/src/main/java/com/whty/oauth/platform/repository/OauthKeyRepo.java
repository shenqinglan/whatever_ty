/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-13
 * Id: OauthKeyRepo.java,v 1.0 2017-3-13 下午4:39:30 Administrator
 */
package com.whty.oauth.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whty.oauth.platform.common.Constants;
import com.whty.oauth.platform.pojo.OauthKey;

/**
 * @ClassName OauthKeyRepo
 * @author Administrator
 * @date 2017-3-13 下午4:39:30
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Repository
public interface OauthKeyRepo extends JpaRepository<OauthKey, String> {

	@Query("select key from OauthKey key where key.eid=?1 and key.iccid=?2 and " +
			"(key.version=?3 and key.keyIndex = "+Constants.SCP80_ENCRYP_KIC_INDEX+" or " +
			"key.version=?4 and key.keyIndex = "+Constants.SCP80_ENCRYP_KID_INDEX+")")
	List<OauthKey> findCardKey(String eid, String iccid, String versionKic,
			String versionKid);

	@Query("select key from OauthKey key where key.msisdn=?1 and " +
			"(key.version=?2 and key.keyIndex = "+Constants.SCP80_ENCRYP_KIC_INDEX+" or " +
			"key.version=?3 and key.keyIndex = "+Constants.SCP80_ENCRYP_KID_INDEX+")")
	List<OauthKey> findCardKey(String msisdn, String versionKic,
			String versionKid);
}
