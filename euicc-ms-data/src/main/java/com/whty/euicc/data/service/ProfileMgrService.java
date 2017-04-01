package com.whty.euicc.data.service;

import java.util.List;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.ProfileMgr;
import com.whty.euicc.data.pojo.ProfileMgrExample;

/**
 * @author dzmsoft
 * @date 2016-08-03 11:27
 *
 * @version 1.0
 */
public interface ProfileMgrService {
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	int insertSelective(ProfileMgr record);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	ProfileMgr selectByPrimaryKey(String id);
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	PageList<ProfileMgr> selectByExample(ProfileMgrExample example,PageBounds pageBounds);
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	List<ProfileMgr> selectByExample(ProfileMgrExample example);
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	int updateByPrimaryKeySelective(ProfileMgr record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	int updateByExampleSelective(ProfileMgr record,
			ProfileMgrExample example);
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @param example
	 * @return
	 */
	int deleteByExample(ProfileMgrExample example);
	
	
}
