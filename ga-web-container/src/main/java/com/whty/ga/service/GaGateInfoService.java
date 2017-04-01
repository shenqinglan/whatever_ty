/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.whty.ga.dao.GaGateInfoDao;
import com.whty.ga.entity.GaGateInfo;

/**
 * 门禁信息Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaGateInfoService extends CrudService<GaGateInfoDao, GaGateInfo> {

	public GaGateInfo get(String id) {
		return super.get(id);
	}
	
	public List<GaGateInfo> findList(GaGateInfo gaGateInfo) {
		return super.findList(gaGateInfo);
	}
	
	public Page<GaGateInfo> findPage(Page<GaGateInfo> page, GaGateInfo gaGateInfo) {
		return super.findPage(page, gaGateInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(GaGateInfo gaGateInfo) {
		super.save(gaGateInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaGateInfo gaGateInfo) {
		super.delete(gaGateInfo);
	}
	
}