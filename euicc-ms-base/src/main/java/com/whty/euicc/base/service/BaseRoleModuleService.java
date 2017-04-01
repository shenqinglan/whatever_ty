package com.whty.euicc.base.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.BaseRoleModuleExample;
import com.whty.euicc.base.pojo.BaseRoleModuleKey;

public interface BaseRoleModuleService {
	
	/**
	 * 保存角色菜单信息
	 * @param baseRoleModule
	 * @return
	 */
	int insertSelective(BaseRoleModuleKey baseRoleModule);
	
	/**
	 * 根据条件查询角色菜单信息
	 * @param example 条件参数map
	 * @return
	 */
	PageList<BaseRoleModuleKey> selectByExample(BaseRoleModuleExample example, PageBounds pageBounds);
	
	/**
	 * 根据条件修改传入的角色菜单信息
	 * @param record 
	 * @param example
	 * @return
	 */
	int updateSelectiveByRecord(BaseRoleModuleKey record,
                                BaseRoleModuleExample example);
	
	/**
	 * 根据主键删除角色菜单信息
	 * @param userId
	 * @return 0：删除失败 ， 1：删除成功
	 */
	int deleteByPrimaryKey(BaseRoleModuleKey key);
	
	/**
	 * 根据条件删除角色菜单信息
	 * @param example
	 * @return
	 */
	int deleteByExample(BaseRoleModuleExample example);
	
}
