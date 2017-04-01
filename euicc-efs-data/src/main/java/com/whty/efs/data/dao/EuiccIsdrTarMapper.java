package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccIsdrTar;
import com.whty.efs.data.pojo.EuiccIsdrTarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccIsdrTarMapper {
    int countByExample(EuiccIsdrTarExample example);

    int deleteByExample(EuiccIsdrTarExample example);

    int deleteByPrimaryKey(String tarId);
    
    int deleteByRid(String rId);

    int insert(EuiccIsdrTar record);

    int insertSelective(EuiccIsdrTar record);

    List<EuiccIsdrTar> selectByExample(EuiccIsdrTarExample example);

    EuiccIsdrTar selectByPrimaryKey(String tarId);

    int updateByExampleSelective(@Param("record") EuiccIsdrTar record, @Param("example") EuiccIsdrTarExample example);

    int updateByExample(@Param("record") EuiccIsdrTar record, @Param("example") EuiccIsdrTarExample example);

    int updateByPrimaryKeySelective(EuiccIsdrTar record);

    int updateByPrimaryKey(EuiccIsdrTar record);
}