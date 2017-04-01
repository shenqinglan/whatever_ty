/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.person.entity.GaAreaInfo;
import com.thinkgem.jeesite.modules.person.dao.GaAreaInfoDao;

/**
 * 小区信息Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaAreaInfoService extends CrudService<GaAreaInfoDao, GaAreaInfo> {

	public GaAreaInfo get(String id) {
		return super.get(id);
	}
	
	public List<GaAreaInfo> findList(GaAreaInfo gaAreaInfo) {
		return super.findList(gaAreaInfo);
	}
	
	public Page<GaAreaInfo> findPage(Page<GaAreaInfo> page, GaAreaInfo gaAreaInfo) {
		return super.findPage(page, gaAreaInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(GaAreaInfo gaAreaInfo) {
		super.save(gaAreaInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaAreaInfo gaAreaInfo) {
		super.delete(gaAreaInfo);
	}
	
}