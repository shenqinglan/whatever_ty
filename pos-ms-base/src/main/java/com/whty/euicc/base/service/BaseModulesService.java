package com.whty.euicc.base.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.BaseModules;
import com.whty.euicc.base.pojo.BaseModulesExample;

public interface BaseModulesService {
	
	/**
	 * 保存菜单信息
	 * @param baseModules
	 * @return
	 */
	int insertSelective(BaseModules baseModules);
	
	/**
	 * 根据主键查询菜单信息
	 * @param moduleId 菜单信息表主键
	 * @return
	 */
	BaseModules selectByPrimaryKey(String moduleId);
	
	/**
	 * 根据条件查询菜单信息
	 * @param example 条件参数map
	 * @return
	 */
	PageList<BaseModules> selectByExample(BaseModulesExample example, PageBounds pageBounds);
	
	/**
	 * 根据主键修改传入的菜单信息
	 * @param BaseModules
	 * @return 0：失败 ， 1：成功
	 */
	int updateSelectiveByPrimaryKey(BaseModules baseModules);
	
	/**
	 * 根据条件修改传入的菜单信息
	 * @param record 
	 * @param example
	 * @return
	 */
	int updateSelectiveByRecord(BaseModules record,
                                BaseModulesExample example);
	
	/**
	 * 根据主键删除菜单信息
	 * @param moduleId
	 * @return 0：删除失败 ， 1：删除成功
	 */
	int deleteByPrimaryKey(String moduleId);
	
	/**
	 * 根据条件删除菜单信息
	 * @param example
	 * @return
	 */
	int deleteByExample(BaseModulesExample example);
	
	
	List<BaseModules> selectMyModules(String userId);
}
