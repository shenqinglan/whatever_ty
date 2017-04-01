package com.whty.euicc.base.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dto.LogsUser;
import com.whty.euicc.base.pojo.BaseLogs;
import com.whty.euicc.base.pojo.BaseLogsExample;

public interface BaseLogsService {
	
	/**
	 * 保存系统日志
	 * @param baseLogs
	 * @return
	 */
	int insertSelective(BaseLogs baseLogs);
	
	/**
	 * 根据主键查询系统日志
	 * @param id 系统日志表主键
	 * @return
	 */
	BaseLogs selectByPrimaryKey(String id);
	
	/**
	 * 根据条件查询系统日志
	 * @param example 条件参数map
	 * @return
	 */
	PageList<BaseLogs> selectByExample(BaseLogsExample example, PageBounds pageBounds);
	
	/**
	 * 根据主键修改传入的系统日志
	 * @param baseLogs
	 * @return 0：失败 ， 1：成功
	 */
	int updateSelectiveByPrimaryKey(BaseLogs baseLogs);
	
	/**
	 * 根据条件修改传入的系统日志
	 * @param record 
	 * @param example
	 * @return
	 */
	int updateSelectiveByRecord(BaseLogs record,
                                BaseLogsExample example);
	
	/**
	 * 根据主键删除系统日志
	 * @param id
	 * @return 0：删除失败 ， 1：删除成功
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 根据条件删除系统日志
	 * @param example
	 * @return
	 */
	int deleteByExample(BaseLogsExample example);
	
	
	PageList<LogsUser> selectSystemLog(BaseLogsExample example, PageBounds pageBounds);
}
