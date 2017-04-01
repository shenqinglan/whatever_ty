package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccEcasd;
import com.whty.efs.data.pojo.EuiccEcasdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccEcasdMapper {
    int countByExample(EuiccEcasdExample example);

    int deleteByExample(EuiccEcasdExample example);

    int deleteByPrimaryKey(String ecasdId);

    int insert(EuiccEcasd record);

    int insertSelective(EuiccEcasd record);

    List<EuiccEcasd> selectByExampleWithBLOBs(EuiccEcasdExample example);

    List<EuiccEcasd> selectByExample(EuiccEcasdExample example);

    EuiccEcasd selectByPrimaryKey(String ecasdId);

    int updateByExampleSelective(@Param("record") EuiccEcasd record, @Param("example") EuiccEcasdExample example);

    int updateByExampleWithBLOBs(@Param("record") EuiccEcasd record, @Param("example") EuiccEcasdExample example);

    int updateByExample(@Param("record") EuiccEcasd record, @Param("example") EuiccEcasdExample example);

    int updateByPrimaryKeySelective(EuiccEcasd record);

    int updateByPrimaryKeyWithBLOBs(EuiccEcasd record);

    int updateByPrimaryKey(EuiccEcasd record);
}