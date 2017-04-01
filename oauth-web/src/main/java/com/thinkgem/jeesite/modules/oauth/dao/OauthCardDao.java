/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oauth.entity.OauthCard;

/**
 * 卡信息DAO接口
 * @author liuwsh
 * @version 2017-03-07
 */
@MyBatisDao
public interface OauthCardDao extends CrudDao<OauthCard> {
	
}