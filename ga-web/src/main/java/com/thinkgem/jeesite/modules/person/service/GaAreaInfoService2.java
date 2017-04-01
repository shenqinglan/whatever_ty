/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.person.entity.GaAreaInfo2;
import com.thinkgem.jeesite.modules.person.dao.GaAreaInfoDao2;

/**
 * 小区信息Service
 * @author liuwsh
 * @version 2017-02-17
 */
@Service
@Transactional(readOnly = true)
public class GaAreaInfoService2 extends CrudService<GaAreaInfoDao2, GaAreaInfo2> {
	
	public Page<GaAreaInfo2> findPage(Page<GaAreaInfo2> page, GaAreaInfo2 areaInfo) {
		return super.findPage(page, areaInfo);
	}
	
}