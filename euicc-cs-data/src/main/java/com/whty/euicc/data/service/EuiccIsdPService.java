package com.whty.euicc.data.service;


import java.util.List;

import com.whty.euicc.data.pojo.EuiccIsdP;


public interface EuiccIsdPService {
	/**
	 * 保存信息
	 * @param isdP
	 * @return
	 */
	int insertSelective(EuiccIsdP isdP);
	
	/**
	 * 根据主键查询信息
	 * @param userId 用户表主键
	 * @return
	 */
	EuiccIsdP selectByPrimaryKey(String isdPAid);
	
	/**
	 * 根据主键修改传入的信息
	 * @param isdP
	 * @return 0：失败 ， 1：成功
	 */
	int updateSelectiveByPrimaryKey(EuiccIsdP isdP);
	
	/**
	 * 根据eid查询一条信息
	 * @param eid
	 * @return
	 */
	EuiccIsdP selectFirstByEid(String eid);
	
	/**
	 * 根据eid/isdPAid查询信息
	 * @param eid,isdPAid
	 * @return
	 */
	EuiccIsdP selectByEidAndIsdPAid(String eid,String isdPAid);
	
	/**
	 * 根据eid查询信息
	 * @param eid
	 * @return
	 */
	String getIsdPAid(String eid);
	
	int updateByEidAndIsdPAid(EuiccIsdP record);
	
	int deleteByEid(String eid);
	
	int deleteByEidAndIsdPAid(EuiccIsdP record);
	
	int insertIsdps(List<EuiccIsdP> isdPs);
	
	List<EuiccIsdP> selectListByEid(String eid);
}
