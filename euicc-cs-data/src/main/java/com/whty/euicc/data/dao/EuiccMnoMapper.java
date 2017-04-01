package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccMno;

public interface EuiccMnoMapper {
    int deleteByPrimaryKey(String mnoId);

    int insert(EuiccMno record);

    int insertSelective(EuiccMno record);

    EuiccMno selectByPrimaryKey(String mnoId);

    int updateByPrimaryKeySelective(EuiccMno record);

    int updateByPrimaryKey(EuiccMno record);
}