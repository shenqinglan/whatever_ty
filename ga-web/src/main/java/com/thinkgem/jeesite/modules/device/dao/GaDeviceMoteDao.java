/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.device.entity.GaDeviceMote;

/**
 * 中继设备DAO接口
 * @author liuwsh
 * @version 2017-02-28
 */
@MyBatisDao
public interface GaDeviceMoteDao extends CrudDao<GaDeviceMote> {
	
}