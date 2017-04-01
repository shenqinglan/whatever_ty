/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.person.entity.GaGateInfo;

/**
 * 门禁信息DAO接口
 * @author liuwsh
 * @version 2017-02-28
 */
@MyBatisDao
public interface GaGateInfoDao extends CrudDao<GaGateInfo> {
	
}