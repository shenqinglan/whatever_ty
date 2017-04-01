package com.whty.euicc.data.dao;

import java.util.List;

import com.whty.euicc.data.pojo.EuiccScp03;

public interface EuiccScp03Mapper {
    int deleteByPrimaryKey(String scp03Id);
    
    int deleteByEid(String eid);
    
    int deleteByEidAndIsdPAid(EuiccScp03 record);
    
    int insert(EuiccScp03 record);

    int insertSelective(EuiccScp03 record);

    EuiccScp03 selectByPrimaryKey(String scp03Id);

    int updateByPrimaryKeySelective(EuiccScp03 record);

    int updateByPrimaryKey(EuiccScp03 record);
    
    EuiccScp03 selectByScp03(EuiccScp03 record);
    
    List<EuiccScp03> selectListByEid(String eid);
    
    int insertListScp03(List<EuiccScp03> scp03s);
    
}