package com.whty.euicc.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.dao.ProfileMgrMapper;
import com.whty.euicc.data.pojo.ProfileMgr;
import com.whty.euicc.data.pojo.ProfileMgrExample;
import com.whty.euicc.data.service.ProfileMgrService;
/**
 * @author dzmsoft
 * @date 2016-08-03 11:27
 *
 * @version 1.0
 */
@Service
@Transactional
public class ProfileMgrServiceImpl implements ProfileMgrService{

	@Autowired
	private ProfileMgrMapper profileMgrMapper;

	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	public int insertSelective(ProfileMgr record){
		return profileMgrMapper.insertSelective(record);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	public ProfileMgr selectByPrimaryKey(String id){
		return profileMgrMapper.selectByPrimaryKey(id);
	}
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	public PageList<ProfileMgr> selectByExample(ProfileMgrExample example,PageBounds pageBounds){
		return profileMgrMapper.selectByExample(example, pageBounds);
	}
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	public List<ProfileMgr> selectByExample(ProfileMgrExample example){
		return profileMgrMapper.selectByExample(example);
	}
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	public int updateByPrimaryKeySelective(ProfileMgr record){
		return profileMgrMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	public int updateByExampleSelective(ProfileMgr record,
			ProfileMgrExample example){
		return profileMgrMapper.updateByExampleSelective(record, example);
	}
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-08-03 11:27
     */
	public int deleteByPrimaryKey(String id){
		return profileMgrMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @param example
	 * @return
	 */
	public int deleteByExample(ProfileMgrExample example){
		return profileMgrMapper.deleteByExample(example);
	}
}
