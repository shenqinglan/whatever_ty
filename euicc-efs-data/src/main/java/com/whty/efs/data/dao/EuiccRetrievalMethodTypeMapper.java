package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccRetrievalMethodType;
import com.whty.efs.data.pojo.EuiccRetrievalMethodTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccRetrievalMethodTypeMapper {
    int countByExample(EuiccRetrievalMethodTypeExample example);

    int deleteByExample(EuiccRetrievalMethodTypeExample example);

    int deleteByPrimaryKey(String retrievalId);

    int insert(EuiccRetrievalMethodType record);

    int insertSelective(EuiccRetrievalMethodType record);

    List<EuiccRetrievalMethodType> selectByExample(EuiccRetrievalMethodTypeExample example);

    EuiccRetrievalMethodType selectByPrimaryKey(String retrievalId);

    int updateByExampleSelective(@Param("record") EuiccRetrievalMethodType record, @Param("example") EuiccRetrievalMethodTypeExample example);

    int updateByExample(@Param("record") EuiccRetrievalMethodType record, @Param("example") EuiccRetrievalMethodTypeExample example);

    int updateByPrimaryKeySelective(EuiccRetrievalMethodType record);

    int updateByPrimaryKey(EuiccRetrievalMethodType record);
}