package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccIsdR;

public interface EuiccIsdRMapper {
    int deleteByPrimaryKey(String rId);

    int insert(EuiccIsdR record);

    int insertSelective(EuiccIsdR record);

    EuiccIsdR selectByPrimaryKey(String rId);

    int updateByPrimaryKeySelective(EuiccIsdR record);

    int updateByPrimaryKey(EuiccIsdR record);
    
    EuiccIsdR selectByEid(String eid);
    
    int deleteByEid(String eid);
    
    EuiccIsdR selectByIsdRAid(String isdRAid);
}