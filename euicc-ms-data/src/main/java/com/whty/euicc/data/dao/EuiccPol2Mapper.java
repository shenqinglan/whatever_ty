package com.whty.euicc.data.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.pojo.EuiccPol2Example;

public interface EuiccPol2Mapper {
    int deleteByPrimaryKey(String pol2Id);

    int insert(EuiccPol2 record);

    int insertSelective(EuiccPol2 record);

    EuiccPol2 selectByPrimaryKey(String pol2Id);
    
    PageList<EuiccPol2> selectByExample(EuiccPol2Example example,PageBounds pageBounds);

    int updateByPrimaryKeySelective(EuiccPol2 record);

    int updateByPrimaryKey(EuiccPol2 record);
}