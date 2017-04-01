package com.whty.euicc.data.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.CheckLogs;
import com.whty.euicc.data.pojo.CheckLogsExample;
import com.whty.euicc.data.pojo.LogsUser;

/**
 * @author dzmsoft
 * @date 2015-07-15 15:35
 *
 * @version 1.0
 */
public interface CheckLogsService {
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	int insertSelective(CheckLogs record);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	CheckLogs selectByPrimaryKey(String id);
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	PageList<CheckLogs> selectByExample(CheckLogsExample example,PageBounds pageBounds);
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	List<CheckLogs> selectByExample(CheckLogsExample example);
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	int updateByPrimaryKeySelective(CheckLogs record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	int updateByExampleSelective(CheckLogs record,
			CheckLogsExample example);
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2015-07-15 15:35
	 * @param example
	 * @return
	 */
	int deleteByExample(CheckLogsExample example);
	
	PageList<LogsUser> selectByObjectId(CheckLogsExample example,PageBounds pageBounds);
	
	public  PageList<LogsUser> selectApByObjectId(CheckLogsExample example,PageBounds pageBounds); 
}
