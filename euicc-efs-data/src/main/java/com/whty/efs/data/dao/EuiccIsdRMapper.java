package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccIsdR;
import com.whty.efs.data.pojo.EuiccIsdRExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccIsdRMapper {
    int countByExample(EuiccIsdRExample example);

    int deleteByExample(EuiccIsdRExample example);

    int deleteByPrimaryKey(String rId);
    
    int deleteByEid(String eid);

    int insert(EuiccIsdR record);

    int insertSelective(EuiccIsdR record);

    List<EuiccIsdR> selectByExample(EuiccIsdRExample example);

    EuiccIsdR selectByPrimaryKey(String rId);

    int updateByExampleSelective(@Param("record") EuiccIsdR record, @Param("example") EuiccIsdRExample example);

    int updateByExample(@Param("record") EuiccIsdR record, @Param("example") EuiccIsdRExample example);

    int updateByPrimaryKeySelective(EuiccIsdR record);

    int updateByPrimaryKey(EuiccIsdR record);
}