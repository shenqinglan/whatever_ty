package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccObjectType;
import com.whty.efs.data.pojo.EuiccObjectTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccObjectTypeMapper {
    int countByExample(EuiccObjectTypeExample example);

    int deleteByExample(EuiccObjectTypeExample example);

    int deleteByPrimaryKey(String typeId);
    
    int deleteBySignatureId(String signatureId);

    int insert(EuiccObjectType record);

    int insertSelective(EuiccObjectType record);

    List<EuiccObjectType> selectByExample(EuiccObjectTypeExample example);

    EuiccObjectType selectByPrimaryKey(String typeId);

    int updateByExampleSelective(@Param("record") EuiccObjectType record, @Param("example") EuiccObjectTypeExample example);

    int updateByExample(@Param("record") EuiccObjectType record, @Param("example") EuiccObjectTypeExample example);

    int updateByPrimaryKeySelective(EuiccObjectType record);

    int updateByPrimaryKey(EuiccObjectType record);
}