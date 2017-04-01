package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccPol1;

public interface EuiccPol1Mapper {
    int deleteByPrimaryKey(String pol1Id);

    int insert(EuiccPol1 record);

    int insertSelective(EuiccPol1 record);
}