package com.whty.euicc.data.service;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileExample;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;

public interface EuiccProfileService {
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	EuiccProfileWithBLOBs selectByPrimaryKey(String id);
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	PageList<EuiccProfile> selectByExample(EuiccProfile profile,EuiccProfileExample example,PageBounds pageBounds);
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	PageList<EuiccProfileWithBLOBs> selectByExampleWithBLOBs(EuiccProfileExample example,PageBounds pageBounds);
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	List<EuiccProfile> selectByExample(EuiccProfileExample example);
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @param example
	 * @return
	 */
	int deleteByExample(EuiccProfileExample example);
	
	int updateByPrimaryKeySelective(EuiccProfileWithBLOBs record);
	
	int updatePol2ByPrimaryKey(EuiccProfile record);
	
	int insertSelective(EuiccProfileWithBLOBs record);
	
	int insertProfileSelective(EuiccProfile record);
	
	void deleteProfile(EuiccProfileWithBLOBs profile);
	
	void enableProfile(EuiccProfileWithBLOBs profile);
	
	void disableProfile(EuiccProfileWithBLOBs profile);
	void getStatus(EuiccProfileWithBLOBs profile);
	void createIsdP(EuiccProfileWithBLOBs profile);
	void updateConnectParas(EuiccProfileWithBLOBs profile);
	
	EuiccProfileWithBLOBs selectEuiccProfileByStateAndEid(String state ,String eid);
	
}
