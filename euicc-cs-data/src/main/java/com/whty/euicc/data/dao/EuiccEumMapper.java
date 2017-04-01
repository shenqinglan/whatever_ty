package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccEum;

public interface EuiccEumMapper {
    int deleteByPrimaryKey(String eumId);

    int insert(EuiccEum record);

    int insertSelective(EuiccEum record);

    EuiccEum selectByPrimaryKey(String eumId);

    int updateByPrimaryKeySelective(EuiccEum record);

    int updateByPrimaryKeyWithBLOBs(EuiccEum record);

    int updateByPrimaryKey(EuiccEum record);
}