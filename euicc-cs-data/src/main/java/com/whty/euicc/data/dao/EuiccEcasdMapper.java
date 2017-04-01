package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccEcasd;

public interface EuiccEcasdMapper {
    int deleteByPrimaryKey(String ecasdId);

    int insert(EuiccEcasd record);

    int insertSelective(EuiccEcasd record);

    EuiccEcasd selectByPrimaryKey(String ecasdId);

    int updateByPrimaryKeySelective(EuiccEcasd record);

    int updateByPrimaryKeyWithBLOBs(EuiccEcasd record);
}