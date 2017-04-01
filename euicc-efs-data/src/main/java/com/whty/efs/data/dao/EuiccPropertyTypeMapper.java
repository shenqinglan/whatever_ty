package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccPropertyType;
import com.whty.efs.data.pojo.EuiccPropertyTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccPropertyTypeMapper {
    int countByExample(EuiccPropertyTypeExample example);

    int deleteByExample(EuiccPropertyTypeExample example);

    int deleteByPrimaryKey(String propertyId);
    
    int deleteByEid(String eid);

    int insert(EuiccPropertyType record);

    int insertSelective(EuiccPropertyType record);

    List<EuiccPropertyType> selectByExample(EuiccPropertyTypeExample example);

    EuiccPropertyType selectByPrimaryKey(String propertyId);

    int updateByExampleSelective(@Param("record") EuiccPropertyType record, @Param("example") EuiccPropertyTypeExample example);

    int updateByExample(@Param("record") EuiccPropertyType record, @Param("example") EuiccPropertyTypeExample example);

    int updateByPrimaryKeySelective(EuiccPropertyType record);

    int updateByPrimaryKey(EuiccPropertyType record);
}