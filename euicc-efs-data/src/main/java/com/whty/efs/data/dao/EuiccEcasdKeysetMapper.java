package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccEcasdKeyset;
import com.whty.efs.data.pojo.EuiccEcasdKeysetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccEcasdKeysetMapper {
    int countByExample(EuiccEcasdKeysetExample example);

    int deleteByExample(EuiccEcasdKeysetExample example);

    int deleteByPrimaryKey(String keysetId);
    
    int deleteByEcasdId(String ecasdId);

    int insert(EuiccEcasdKeyset record);

    int insertSelective(EuiccEcasdKeyset record);

    List<EuiccEcasdKeyset> selectByExampleWithBLOBs(EuiccEcasdKeysetExample example);

    List<EuiccEcasdKeyset> selectByExample(EuiccEcasdKeysetExample example);

    EuiccEcasdKeyset selectByPrimaryKey(String keysetId);

    int updateByExampleSelective(@Param("record") EuiccEcasdKeyset record, @Param("example") EuiccEcasdKeysetExample example);

    int updateByExampleWithBLOBs(@Param("record") EuiccEcasdKeyset record, @Param("example") EuiccEcasdKeysetExample example);

    int updateByExample(@Param("record") EuiccEcasdKeyset record, @Param("example") EuiccEcasdKeysetExample example);

    int updateByPrimaryKeySelective(EuiccEcasdKeyset record);

    int updateByPrimaryKeyWithBLOBs(EuiccEcasdKeyset record);

    int updateByPrimaryKey(EuiccEcasdKeyset record);
}