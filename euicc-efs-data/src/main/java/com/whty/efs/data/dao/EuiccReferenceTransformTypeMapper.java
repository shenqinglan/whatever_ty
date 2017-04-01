package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccReferenceTransformType;
import com.whty.efs.data.pojo.EuiccReferenceTransformTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccReferenceTransformTypeMapper {
    int countByExample(EuiccReferenceTransformTypeExample example);

    int deleteByExample(EuiccReferenceTransformTypeExample example);

    int deleteByPrimaryKey(String transformId);
    
    int deleteByReferenceId(String referenceId);

    int insert(EuiccReferenceTransformType record);

    int insertSelective(EuiccReferenceTransformType record);

    List<EuiccReferenceTransformType> selectByExample(EuiccReferenceTransformTypeExample example);

    EuiccReferenceTransformType selectByPrimaryKey(String transformId);

    int updateByExampleSelective(@Param("record") EuiccReferenceTransformType record, @Param("example") EuiccReferenceTransformTypeExample example);

    int updateByExample(@Param("record") EuiccReferenceTransformType record, @Param("example") EuiccReferenceTransformTypeExample example);

    int updateByPrimaryKeySelective(EuiccReferenceTransformType record);

    int updateByPrimaryKey(EuiccReferenceTransformType record);
}