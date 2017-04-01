/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.device.entity.GaDeviceMs;
import com.thinkgem.jeesite.modules.device.dao.GaDeviceMsDao;

/**
 * 终端设备Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaDeviceMsService extends CrudService<GaDeviceMsDao, GaDeviceMs> {

	public GaDeviceMs get(String id) {
		return super.get(id);
	}
	
	public List<GaDeviceMs> findList(GaDeviceMs gaDeviceMs) {
		return super.findList(gaDeviceMs);
	}
	
	public Page<GaDeviceMs> findPage(Page<GaDeviceMs> page, GaDeviceMs gaDeviceMs) {
		return super.findPage(page, gaDeviceMs);
	}
	
	@Transactional(readOnly = false)
	public void save(GaDeviceMs gaDeviceMs) {
		super.save(gaDeviceMs);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaDeviceMs gaDeviceMs) {
		super.delete(gaDeviceMs);
	}
	
}