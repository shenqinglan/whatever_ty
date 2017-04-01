/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.device.entity.GaDeviceMote;
import com.thinkgem.jeesite.modules.device.dao.GaDeviceMoteDao;

/**
 * 中继设备Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaDeviceMoteService extends CrudService<GaDeviceMoteDao, GaDeviceMote> {

	public GaDeviceMote get(String id) {
		return super.get(id);
	}
	
	public List<GaDeviceMote> findList(GaDeviceMote gaDeviceMote) {
		return super.findList(gaDeviceMote);
	}
	
	public Page<GaDeviceMote> findPage(Page<GaDeviceMote> page, GaDeviceMote gaDeviceMote) {
		return super.findPage(page, gaDeviceMote);
	}
	
	@Transactional(readOnly = false)
	public void save(GaDeviceMote gaDeviceMote) {
		super.save(gaDeviceMote);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaDeviceMote gaDeviceMote) {
		super.delete(gaDeviceMote);
	}
	
}