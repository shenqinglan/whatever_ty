package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccCanonicalizationMethodType;
import com.whty.efs.data.pojo.EuiccCanonicalizationMethodTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccCanonicalizationMethodTypeMapper {
    int countByExample(EuiccCanonicalizationMethodTypeExample example);

    int deleteByExample(EuiccCanonicalizationMethodTypeExample example);

    int deleteByPrimaryKey(String typeId);
    
    int deleteBySingedInfoId(String singedInfoId);

    int insert(EuiccCanonicalizationMethodType record);

    int insertSelective(EuiccCanonicalizationMethodType record);

    List<EuiccCanonicalizationMethodType> selectByExample(EuiccCanonicalizationMethodTypeExample example);

    EuiccCanonicalizationMethodType selectByPrimaryKey(String typeId);

    int updateByExampleSelective(@Param("record") EuiccCanonicalizationMethodType record, @Param("example") EuiccCanonicalizationMethodTypeExample example);

    int updateByExample(@Param("record") EuiccCanonicalizationMethodType record, @Param("example") EuiccCanonicalizationMethodTypeExample example);

    int updateByPrimaryKeySelective(EuiccCanonicalizationMethodType record);

    int updateByPrimaryKey(EuiccCanonicalizationMethodType record);
}