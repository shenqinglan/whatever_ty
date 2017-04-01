package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccRetrievalTransformType;
import com.whty.efs.data.pojo.EuiccRetrievalTransformTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccRetrievalTransformTypeMapper {
    int countByExample(EuiccRetrievalTransformTypeExample example);

    int deleteByExample(EuiccRetrievalTransformTypeExample example);

    int deleteByPrimaryKey(String transformId);
    
    int deleteByRetrievalId(String retrievalId);

    int insert(EuiccRetrievalTransformType record);

    int insertSelective(EuiccRetrievalTransformType record);

    List<EuiccRetrievalTransformType> selectByExample(EuiccRetrievalTransformTypeExample example);

    EuiccRetrievalTransformType selectByPrimaryKey(String transformId);

    int updateByExampleSelective(@Param("record") EuiccRetrievalTransformType record, @Param("example") EuiccRetrievalTransformTypeExample example);

    int updateByExample(@Param("record") EuiccRetrievalTransformType record, @Param("example") EuiccRetrievalTransformTypeExample example);

    int updateByPrimaryKeySelective(EuiccRetrievalTransformType record);

    int updateByPrimaryKey(EuiccRetrievalTransformType record);
}