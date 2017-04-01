package com.whty.euicc.base.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.BaseRoles;
import com.whty.euicc.base.pojo.BaseRolesExample;

public interface BaseRolesService {
	
	/**
	 * 保存角色信息
	 * @param baseRoles
	 * @return
	 */
	int insertSelective(BaseRoles baseRoles);
	
	/**
	 * 根据主键查询角色信息
	 * @param roleId 角色表主键
	 * @return
	 */
	BaseRoles selectByPrimaryKey(String roleId);
	
	/**
	 * 根据条件查询角色信息
	 * @param example 条件参数map
	 * @return
	 */
	PageList<BaseRoles> selectByExample(BaseRolesExample example, PageBounds pageBounds);
	
	/**
	 * 根据主键修改传入的角色信息
	 * @param BaseRoles
	 * @return 0：失败 ， 1：成功
	 */
	int updateSelectiveByPrimaryKey(BaseRoles baseRoles);
	
	/**
	 * 根据条件修改传入的角色信息
	 * @param record 
	 * @param example
	 * @return
	 */
	int updateSelectiveByRecord(BaseRoles record,
                                BaseRolesExample example);
	
	/**
	 * 根据主键删除角色信息
	 * @param roleId
	 * @return 0：删除失败 ， 1：删除成功
	 */
	int deleteByPrimaryKey(String roleId);
	
	/**
	 * 根据条件删除角色信息
	 * @param example
	 * @return
	 */
	int deleteByExample(BaseRolesExample example);
	
}
