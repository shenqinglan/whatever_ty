package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccReferenceType;
import com.whty.efs.data.pojo.EuiccReferenceTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccReferenceTypeMapper {
    int countByExample(EuiccReferenceTypeExample example);

    int deleteByExample(EuiccReferenceTypeExample example);

    int deleteByPrimaryKey(String referenceId);
    
    int deleteBySingedInfoId(String singedInfoId);

    int insert(EuiccReferenceType record);

    int insertSelective(EuiccReferenceType record);

    List<EuiccReferenceType> selectByExample(EuiccReferenceTypeExample example);

    EuiccReferenceType selectByPrimaryKey(String referenceId);

    int updateByExampleSelective(@Param("record") EuiccReferenceType record, @Param("example") EuiccReferenceTypeExample example);

    int updateByExample(@Param("record") EuiccReferenceType record, @Param("example") EuiccReferenceTypeExample example);

    int updateByPrimaryKeySelective(EuiccReferenceType record);

    int updateByPrimaryKey(EuiccReferenceType record);
}