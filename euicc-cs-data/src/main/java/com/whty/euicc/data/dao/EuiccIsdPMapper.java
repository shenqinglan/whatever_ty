package com.whty.euicc.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whty.euicc.data.pojo.EuiccIsdP;

public interface EuiccIsdPMapper {
    int deleteByPrimaryKey(String pId);

    int insert(EuiccIsdP record);

    int insertSelective(EuiccIsdP record);

    EuiccIsdP selectByPrimaryKey(String pId);
	EuiccIsdP selectFirstByEid(String eid);
    
    EuiccIsdP selectByEidAndIsdPAid(@Param("eid")String eid,@Param("isdPAid")String isdPAid);
    
    List<EuiccIsdP> selectListByEid(String eid);

    int updateByPrimaryKeySelective(EuiccIsdP record);

    int updateByPrimaryKey(EuiccIsdP record);
    
    int updateByEidAndIsdPAid(EuiccIsdP record);
    
    int deleteByEid(String eid);
    
    int deleteByEidAndIsdPAid(EuiccIsdP record);
    
    int insertIsdps(List<EuiccIsdP> isdPs);
    
}