package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccEcasdTar;
import com.whty.efs.data.pojo.EuiccEcasdTarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccEcasdTarMapper {
    int countByExample(EuiccEcasdTarExample example);

    int deleteByExample(EuiccEcasdTarExample example);

    int deleteByPrimaryKey(String tarId);
    
    int deleteByEcasdId(String ecasdId);

    int insert(EuiccEcasdTar record);

    int insertSelective(EuiccEcasdTar record);

    List<EuiccEcasdTar> selectByExample(EuiccEcasdTarExample example);

    EuiccEcasdTar selectByPrimaryKey(String tarId);

    int updateByExampleSelective(@Param("record") EuiccEcasdTar record, @Param("example") EuiccEcasdTarExample example);

    int updateByExample(@Param("record") EuiccEcasdTar record, @Param("example") EuiccEcasdTarExample example);

    int updateByPrimaryKeySelective(EuiccEcasdTar record);

    int updateByPrimaryKey(EuiccEcasdTar record);
}