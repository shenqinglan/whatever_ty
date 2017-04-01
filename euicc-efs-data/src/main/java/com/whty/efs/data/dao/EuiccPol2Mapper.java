package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccPol2;
import com.whty.efs.data.pojo.EuiccPol2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccPol2Mapper {
    int countByExample(EuiccPol2Example example);

    int deleteByExample(EuiccPol2Example example);

    int deleteByPrimaryKey(String pol2Id);

    int insert(EuiccPol2 record);

    int insertSelective(EuiccPol2 record);

    List<EuiccPol2> selectByExample(EuiccPol2Example example);

    EuiccPol2 selectByPrimaryKey(String pol2Id);

    int updateByExampleSelective(@Param("record") EuiccPol2 record, @Param("example") EuiccPol2Example example);

    int updateByExample(@Param("record") EuiccPol2 record, @Param("example") EuiccPol2Example example);

    int updateByPrimaryKeySelective(EuiccPol2 record);

    int updateByPrimaryKey(EuiccPol2 record);
}