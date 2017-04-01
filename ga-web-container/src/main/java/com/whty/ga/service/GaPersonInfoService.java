/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.whty.ga.dao.GaPersonInfoDao;
import com.whty.ga.entity.GaPersonInfo;

/**
 * 个人信息Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaPersonInfoService extends CrudService<GaPersonInfoDao, GaPersonInfo> {

	public GaPersonInfo get(String id) {
		return super.get(id);
	}
	
	public List<GaPersonInfo> findList(GaPersonInfo gaPersonInfo) {
		return super.findList(gaPersonInfo);
	}
	
	public Page<GaPersonInfo> findPage(Page<GaPersonInfo> page, GaPersonInfo gaPersonInfo) {
		return super.findPage(page, gaPersonInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(GaPersonInfo gaPersonInfo) {
		super.save(gaPersonInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaPersonInfo gaPersonInfo) {
		super.delete(gaPersonInfo);
	}
	
}