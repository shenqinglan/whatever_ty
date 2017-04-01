package com.whty.euicc.data.service;

import com.whty.euicc.data.pojo.EuiccScp81;

public interface EuiccScp81Service {
	int deleteByPrimaryKey(String scp81Id);

    int insert(EuiccScp81 record);

    int insertSelective(EuiccScp81 record);

    EuiccScp81 selectByPrimaryKey(String scp81Id);
    
    /**
     * 根据id查找值
     * @param id
     * @return
     */
    String selectById(String id);

    int updateByPrimaryKeySelective(EuiccScp81 record);

    int updateByPrimaryKey(EuiccScp81 record);
    
    EuiccScp81 selectByEid(String eid);
    
    int deleteByEid(String eid);

}
