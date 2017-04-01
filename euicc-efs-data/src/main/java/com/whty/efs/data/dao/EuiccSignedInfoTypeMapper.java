package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccSignedInfoType;
import com.whty.efs.data.pojo.EuiccSignedInfoTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccSignedInfoTypeMapper {
    int countByExample(EuiccSignedInfoTypeExample example);

    int deleteByExample(EuiccSignedInfoTypeExample example);

    int deleteByPrimaryKey(String signedInfoId);

    int insert(EuiccSignedInfoType record);

    int insertSelective(EuiccSignedInfoType record);

    List<EuiccSignedInfoType> selectByExample(EuiccSignedInfoTypeExample example);

    EuiccSignedInfoType selectByPrimaryKey(String signedInfoId);

    int updateByExampleSelective(@Param("record") EuiccSignedInfoType record, @Param("example") EuiccSignedInfoTypeExample example);

    int updateByExample(@Param("record") EuiccSignedInfoType record, @Param("example") EuiccSignedInfoTypeExample example);

    int updateByPrimaryKeySelective(EuiccSignedInfoType record);

    int updateByPrimaryKey(EuiccSignedInfoType record);
}