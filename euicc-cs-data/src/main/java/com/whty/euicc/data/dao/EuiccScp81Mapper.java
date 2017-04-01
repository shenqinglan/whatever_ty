package com.whty.euicc.data.dao;

import java.util.List;

import com.whty.euicc.data.pojo.EuiccScp81;

public interface EuiccScp81Mapper {
    int deleteByPrimaryKey(String scp81Id);

    int insert(EuiccScp81 record);

    int insertSelective(EuiccScp81 record);

    EuiccScp81 selectByPrimaryKey(String scp81Id);
    
    String selectById(String id);

    int updateByPrimaryKeySelective(EuiccScp81 record);

    int updateByPrimaryKey(EuiccScp81 record);
    
    List<EuiccScp81> selectLists();
    
    EuiccScp81 selectByEid(String eid);
    
    int deleteByEid(String eid);
}