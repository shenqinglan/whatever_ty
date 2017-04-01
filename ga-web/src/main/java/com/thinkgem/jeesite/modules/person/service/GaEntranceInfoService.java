/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.person.entity.GaEntranceInfo;
import com.thinkgem.jeesite.modules.person.dao.GaEntranceInfoDao;

/**
 * 出入信息Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaEntranceInfoService extends CrudService<GaEntranceInfoDao, GaEntranceInfo> {

	public GaEntranceInfo get(String id) {
		return super.get(id);
	}
	
	public List<GaEntranceInfo> findList(GaEntranceInfo gaEntranceInfo) {
		return super.findList(gaEntranceInfo);
	}
	
	public Page<GaEntranceInfo> findPage(Page<GaEntranceInfo> page, GaEntranceInfo gaEntranceInfo) {
		return super.findPage(page, gaEntranceInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(GaEntranceInfo gaEntranceInfo) {
		super.save(gaEntranceInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaEntranceInfo gaEntranceInfo) {
		super.delete(gaEntranceInfo);
	}
	
}