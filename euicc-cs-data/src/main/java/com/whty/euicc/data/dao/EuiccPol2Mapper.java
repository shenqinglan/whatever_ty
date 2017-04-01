package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccPol2;

public interface EuiccPol2Mapper {
    int deleteByPrimaryKey(String pol2Id);

    int insert(EuiccPol2 record);

    int insertSelective(EuiccPol2 record);

    EuiccPol2 selectByPrimaryKey(String pol2Id);

    int updateByPrimaryKeySelective(EuiccPol2 record);

    int updateByPrimaryKey(EuiccPol2 record);
    
    EuiccPol2 selectByActionAndQualification(EuiccPol2 record);
}