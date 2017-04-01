package com.whty.euicc.data.service;

import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;


public interface EuiccCardService {
	/**
	 * 保存信息
	 * @param card
	 * @return
	 */
	int insertSelective(EuiccCard card);
	
	/**
	 * 根据主键查询信息
	 * @param userId 用户表主键
	 * @return
	 */
	EuiccCard selectByPrimaryKey(String eid);
	
	/**
	 * 根据主键修改传入的信息
	 * @param EuiccCard
	 * @return 0：失败 ， 1：成功
	 */
	int updateSelectiveByPrimaryKey(EuiccCard eid);
	
    void updateEIS(EuiccCard card,EuiccProfileWithBLOBs profile,EuiccIsdP isdP);
    
    void deleteEIS(String eid, String iccid);
    
    int deleteByPrimaryKey(String eid);
}
