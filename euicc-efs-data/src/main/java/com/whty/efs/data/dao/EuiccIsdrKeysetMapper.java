package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccIsdrKeyset;
import com.whty.efs.data.pojo.EuiccIsdrKeysetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccIsdrKeysetMapper {
    int countByExample(EuiccIsdrKeysetExample example);

    int deleteByExample(EuiccIsdrKeysetExample example);

    int deleteByPrimaryKey(String keysetId);
    
    int deleteByRid(String rId);

    int insert(EuiccIsdrKeyset record);

    int insertSelective(EuiccIsdrKeyset record);

    List<EuiccIsdrKeyset> selectByExampleWithBLOBs(EuiccIsdrKeysetExample example);

    List<EuiccIsdrKeyset> selectByExample(EuiccIsdrKeysetExample example);

    EuiccIsdrKeyset selectByPrimaryKey(String keysetId);

    int updateByExampleSelective(@Param("record") EuiccIsdrKeyset record, @Param("example") EuiccIsdrKeysetExample example);

    int updateByExampleWithBLOBs(@Param("record") EuiccIsdrKeyset record, @Param("example") EuiccIsdrKeysetExample example);

    int updateByExample(@Param("record") EuiccIsdrKeyset record, @Param("example") EuiccIsdrKeysetExample example);

    int updateByPrimaryKeySelective(EuiccIsdrKeyset record);

    int updateByPrimaryKeyWithBLOBs(EuiccIsdrKeyset record);

    int updateByPrimaryKey(EuiccIsdrKeyset record);
}