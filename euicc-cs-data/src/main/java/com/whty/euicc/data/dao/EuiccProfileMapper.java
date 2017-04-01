package com.whty.euicc.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;

public interface EuiccProfileMapper {
    int deleteByPrimaryKey(String iccid);

    int insert(EuiccProfileWithBLOBs record);

    int insertSelective(EuiccProfileWithBLOBs record);

    EuiccProfileWithBLOBs selectByPrimaryKey(String iccid);

    int updateByPrimaryKeySelective(EuiccProfileWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(EuiccProfileWithBLOBs record);

    int updateByPrimaryKey(EuiccProfile record);

	int updataProfileState(EuiccProfileWithBLOBs euiccProfile);
	
	int updateProfileStateByEidAndIsdPAid(EuiccProfileWithBLOBs euiccProfile);
	
	int countByPrimaryKey(EuiccProfileWithBLOBs euiccProfile);
	
    EuiccProfileWithBLOBs selectByEidAndIsdPAid(EuiccProfileWithBLOBs euiccProfile);
   
    EuiccProfileWithBLOBs selectByEidAndIsdPAid(@Param("eid")String eid,@Param("isdPAid")String isdPAid);

    
    List<EuiccProfileWithBLOBs> selectByEidAndFallback(EuiccProfileWithBLOBs record);

    
    EuiccProfileWithBLOBs selectEuiccProfileByStateAndEid(EuiccProfileWithBLOBs euiccProfile);
    
    int updateByEid(EuiccProfile record);
    
    List<EuiccProfileWithBLOBs> selectByEid(String eid);
    
    int deleteByEid(String eid);
    
    int insertProfiles(List<EuiccProfileWithBLOBs> profiles);

}