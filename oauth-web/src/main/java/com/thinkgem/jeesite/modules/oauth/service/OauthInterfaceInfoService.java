/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oauth.entity.OauthInterfaceInfo;
import com.thinkgem.jeesite.modules.oauth.dao.OauthInterfaceInfoDao;

/**
 * 状态信息Service
 * @author liuwsh
 * @version 2017-03-08
 */
@Service
@Transactional(readOnly = true)
public class OauthInterfaceInfoService extends CrudService<OauthInterfaceInfoDao, OauthInterfaceInfo> {

	public OauthInterfaceInfo get(String id) {
		return super.get(id);
	}
	
	public List<OauthInterfaceInfo> findList(OauthInterfaceInfo oauthInterfaceInfo) {
		return super.findList(oauthInterfaceInfo);
	}
	
	public Page<OauthInterfaceInfo> findPage(Page<OauthInterfaceInfo> page, OauthInterfaceInfo oauthInterfaceInfo) {
		return super.findPage(page, oauthInterfaceInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(OauthInterfaceInfo oauthInterfaceInfo) {
		super.save(oauthInterfaceInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(OauthInterfaceInfo oauthInterfaceInfo) {
		super.delete(oauthInterfaceInfo);
	}
	
}