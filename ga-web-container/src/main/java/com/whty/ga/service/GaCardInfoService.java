/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.whty.ga.dao.GaCardInfoDao;
import com.whty.ga.entity.GaCardInfo;

/**
 * 卡信息Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaCardInfoService extends CrudService<GaCardInfoDao, GaCardInfo> {

	public GaCardInfo get(String id) {
		return super.get(id);
	}
	
	public List<GaCardInfo> findList(GaCardInfo gaCardInfo) {
		return super.findList(gaCardInfo);
	}
	
	public Page<GaCardInfo> findPage(Page<GaCardInfo> page, GaCardInfo gaCardInfo) {
		return super.findPage(page, gaCardInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(GaCardInfo gaCardInfo) {
		super.save(gaCardInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaCardInfo gaCardInfo) {
		super.delete(gaCardInfo);
	}
	
}