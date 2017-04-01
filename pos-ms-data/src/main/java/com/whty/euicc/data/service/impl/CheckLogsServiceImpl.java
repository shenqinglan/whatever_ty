package com.whty.euicc.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.dao.CheckLogsMapper;
import com.whty.euicc.data.pojo.CheckLogs;
import com.whty.euicc.data.pojo.CheckLogsExample;
import com.whty.euicc.data.pojo.LogsUser;
import com.whty.euicc.data.service.CheckLogsService;
/**
 * @author dzmsoft
 * @date 2015-07-15 15:35
 *
 * @version 1.0
 */
@Service
@Transactional
public class CheckLogsServiceImpl implements CheckLogsService{

	@Autowired
	private CheckLogsMapper checkLogsMapper;

	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	public int insertSelective(CheckLogs record){
		return checkLogsMapper.insertSelective(record);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	public CheckLogs selectByPrimaryKey(String id){
		return checkLogsMapper.selectByPrimaryKey(id);
	}
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	public PageList<CheckLogs> selectByExample(CheckLogsExample example,PageBounds pageBounds){
		return checkLogsMapper.selectByExample(example, pageBounds);
	}
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	public List<CheckLogs> selectByExample(CheckLogsExample example){
		return checkLogsMapper.selectByExample(example);
	}
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	public int updateByPrimaryKeySelective(CheckLogs record){
		return checkLogsMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	public int updateByExampleSelective(CheckLogs record,
			CheckLogsExample example){
		return checkLogsMapper.updateByExampleSelective(record, example);
	}
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2015-07-15 15:35
     */
	public int deleteByPrimaryKey(String id){
		return checkLogsMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2015-07-15 15:35
	 * @param example
	 * @return
	 */
	public int deleteByExample(CheckLogsExample example){
		return checkLogsMapper.deleteByExample(example);
	}

	public PageList<LogsUser> selectByObjectId(CheckLogsExample example,
			PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return checkLogsMapper.selectByObjectId(example, pageBounds);
	}

	public PageList<LogsUser> selectApByObjectId(CheckLogsExample example, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return checkLogsMapper.selectApByObjectId(example, pageBounds);
	}
}
