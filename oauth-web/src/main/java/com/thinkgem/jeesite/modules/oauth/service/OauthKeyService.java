/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oauth.entity.OauthCard;
import com.thinkgem.jeesite.modules.oauth.entity.OauthKey;
import com.thinkgem.jeesite.modules.oauth.dao.OauthKeyDao;

/**
 * 密钥信息Service
 * @author liuwsh
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class OauthKeyService extends CrudService<OauthKeyDao, OauthKey> {

	public OauthKey get(String id) {
		return super.get(id);
	}

	public List<OauthKey> findList(OauthKey oauthKey) {
		return super.findList(oauthKey);
	}

	public Page<OauthKey> findPage(Page<OauthKey> page, OauthKey oauthKey) {
		return super.findPage(page, oauthKey);
	}

	@Transactional(readOnly = false)
	public void save(OauthKey oauthKey) {

		OauthKey search = new OauthKey();
		search.setEid(oauthKey.getEid());
		search.setIccid(oauthKey.getIccid());
		search.setIndex(oauthKey.getIndex());
		search.setVersion(oauthKey.getVersion());
		search.setCurrentUser(oauthKey.getCurrentUser());
		if (findList(search).size() > 0) {
			for (OauthKey ok : findList(search)) {
				delete(ok);
			}
			
		}

		super.save(oauthKey);
	}

	@Transactional(readOnly = false)
	public void delete(OauthKey oauthKey) {
		super.delete(oauthKey);
	}

}