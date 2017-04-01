package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccSmSr;

public interface EuiccSmSrMapper {
    int deleteByPrimaryKey(String smsrId);

    int insert(EuiccSmSr record);

    int insertSelective(EuiccSmSr record);

    EuiccSmSr selectByPrimaryKey(String smsrId);

    int updateByPrimaryKeySelective(EuiccSmSr record);

    int updateByPrimaryKeyWithBLOBs(EuiccSmSr record);

    int updateByPrimaryKey(EuiccSmSr record);
}