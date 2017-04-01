/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oauth.entity.OauthCard;
import com.thinkgem.jeesite.modules.oauth.dao.OauthCardDao;

/**
 * 卡信息Service
 * @author liuwsh
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class OauthCardService extends CrudService<OauthCardDao, OauthCard> {

	public OauthCard get(String id) {
		return super.get(id);
	}
	
	public List<OauthCard> findList(OauthCard oauthCard) {
		return super.findList(oauthCard);
	}
	
	public Page<OauthCard> findPage(Page<OauthCard> page, OauthCard oauthCard) {
		return super.findPage(page, oauthCard);
	}
	
	@Transactional(readOnly = false)
	public void save(OauthCard oauthCard) {
		super.save(oauthCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(OauthCard oauthCard) {
		super.delete(oauthCard);
	}
	
}