package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccKeyInfoType;
import com.whty.efs.data.pojo.EuiccKeyInfoTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccKeyInfoTypeMapper {
    int countByExample(EuiccKeyInfoTypeExample example);

    int deleteByExample(EuiccKeyInfoTypeExample example);

    int deleteByPrimaryKey(String keyInfoId);

    int insert(EuiccKeyInfoType record);

    int insertSelective(EuiccKeyInfoType record);

    List<EuiccKeyInfoType> selectByExample(EuiccKeyInfoTypeExample example);

    EuiccKeyInfoType selectByPrimaryKey(String keyInfoId);

    int updateByExampleSelective(@Param("record") EuiccKeyInfoType record, @Param("example") EuiccKeyInfoTypeExample example);

    int updateByExample(@Param("record") EuiccKeyInfoType record, @Param("example") EuiccKeyInfoTypeExample example);

    int updateByPrimaryKeySelective(EuiccKeyInfoType record);

    int updateByPrimaryKey(EuiccKeyInfoType record);
}