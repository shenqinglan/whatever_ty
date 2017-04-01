package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccCardMapper {
    int countByExample(EuiccCardExample example);

    int deleteByExample(EuiccCardExample example);

    int deleteByPrimaryKey(String eid);

    int insert(EuiccCard record);

    int insertSelective(EuiccCard record);

    List<EuiccCard> selectByExample(EuiccCardExample example);

    EuiccCard selectByPrimaryKey(String eid);

    int updateByExampleSelective(@Param("record") EuiccCard record, @Param("example") EuiccCardExample example);

    int updateByExample(@Param("record") EuiccCard record, @Param("example") EuiccCardExample example);

    int updateByPrimaryKeySelective(EuiccCard record);

    int updateByPrimaryKey(EuiccCard record);
}