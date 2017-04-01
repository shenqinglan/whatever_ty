/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.device.entity.GaDeviceAp;
import com.thinkgem.jeesite.modules.device.dao.GaDeviceApDao;

/**
 * 基站设备Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaDeviceApService extends CrudService<GaDeviceApDao, GaDeviceAp> {

	public GaDeviceAp get(String id) {
		return super.get(id);
	}
	
	public List<GaDeviceAp> findList(GaDeviceAp gaDeviceAp) {
		return super.findList(gaDeviceAp);
	}
	
	public Page<GaDeviceAp> findPage(Page<GaDeviceAp> page, GaDeviceAp gaDeviceAp) {
		return super.findPage(page, gaDeviceAp);
	}
	
	@Transactional(readOnly = false)
	public void save(GaDeviceAp gaDeviceAp) {
		super.save(gaDeviceAp);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaDeviceAp gaDeviceAp) {
		super.delete(gaDeviceAp);
	}
	
}