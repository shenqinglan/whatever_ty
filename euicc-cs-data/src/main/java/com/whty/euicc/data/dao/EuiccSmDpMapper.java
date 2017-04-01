package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccSmDp;

public interface EuiccSmDpMapper {
    int deleteByPrimaryKey(String smdpId);

    int insert(EuiccSmDp record);

    int insertSelective(EuiccSmDp record);

    EuiccSmDp selectByPrimaryKey(String smdpId);

    int updateByPrimaryKeySelective(EuiccSmDp record);

    int updateByPrimaryKeyWithBLOBs(EuiccSmDp record);

    int updateByPrimaryKey(EuiccSmDp record);
}