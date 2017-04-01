package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccIsdPExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccIsdPMapper {
    int countByExample(EuiccIsdPExample example);

    int deleteByExample(EuiccIsdPExample example);

    int deleteByPrimaryKey(String pId);

    int insert(EuiccIsdP record);

    int insertSelective(EuiccIsdP record);

    List<EuiccIsdP> selectByExample(EuiccIsdPExample example);

    EuiccIsdP selectByPrimaryKey(String pId);

    int updateByExampleSelective(@Param("record") EuiccIsdP record, @Param("example") EuiccIsdPExample example);

    int updateByExample(@Param("record") EuiccIsdP record, @Param("example") EuiccIsdPExample example);

    int updateByPrimaryKeySelective(EuiccIsdP record);

    int updateByPrimaryKey(EuiccIsdP record);
    
    int updateByEidAndIsdPAid(EuiccIsdP record);
    
    List<EuiccIsdP> selectListByEid(String eid);
}