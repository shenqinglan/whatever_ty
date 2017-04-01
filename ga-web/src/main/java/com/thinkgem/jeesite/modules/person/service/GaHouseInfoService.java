/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.person.entity.GaGateInfo;
import com.thinkgem.jeesite.modules.person.entity.GaHouseInfo;
import com.thinkgem.jeesite.modules.person.dao.GaHouseInfoDao;

/**
 * 房屋信息Service
 * @author liuwsh
 * @version 2017-02-28
 */
@Service
@Transactional(readOnly = true)
public class GaHouseInfoService extends CrudService<GaHouseInfoDao, GaHouseInfo> {
	
	@Autowired
	public GaGateInfoService gaGateInfoService;

	public GaHouseInfo get(String id) {
		return super.get(id);
	}
	
	public List<GaHouseInfo> findList(GaHouseInfo gaHouseInfo) {
		return super.findList(gaHouseInfo);
	}
	
	public Page<GaHouseInfo> findPage(Page<GaHouseInfo> page, GaHouseInfo gaHouseInfo) {
		return super.findPage(page, gaHouseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(GaHouseInfo gaHouseInfo) {
		if (gaHouseInfo != null && gaHouseInfo.getGateId() != null && !"".equals(gaHouseInfo.getGateId())) {
			GaGateInfo g = gaGateInfoService.get(gaHouseInfo.getGateId());
			gaHouseInfo.setBuildingNo(g.getBuildingNo());
			gaHouseInfo.setUnitNo(g.getUnitNo());
		}
		super.save(gaHouseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GaHouseInfo gaHouseInfo) {
		super.delete(gaHouseInfo);
	}
	
}