package com.whty.euicc.data.service;

import java.util.List;

import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;

public interface EuiccProfileService {
	/**
	 * 根据主键查询信息
	 * @param iccid profile表主键
	 * @return
	 */
	EuiccProfileWithBLOBs selectByPrimaryKey(String iccid);
	
	void updateEIS(EuiccProfileWithBLOBs profile,EuiccIsdP isdP);

	int checkCount(EuiccProfileWithBLOBs euiccProfile);
	
	/**
	 * 根据主键查询信息
	 * @param 
	 * @return
	 */
	EuiccProfileWithBLOBs selectByEidAndIsdPAid(EuiccProfileWithBLOBs euiccProfile);
	EuiccProfileWithBLOBs selectByEidAndIsdPAid(String eid,String isdPAid);
	void enableProfile(EuiccProfileWithBLOBs record);
	void disableProfile(EuiccProfileWithBLOBs record);
	void deleteProfile(EuiccProfileWithBLOBs record);
	void updateStatus(EuiccProfileWithBLOBs record);
	boolean fallBackEnableProfile(EuiccProfileWithBLOBs profile);
	int updateByPrimaryKeySelective(EuiccProfileWithBLOBs record);
	
	EuiccProfileWithBLOBs selectEuiccProfileByStateAndEid(String state ,String eid);
	
	List<EuiccProfileWithBLOBs> selectByEid(String eid);
	
	int deleteByEid(String eid);
	
	int insertProfiles(List<EuiccProfileWithBLOBs> profiles);

	int updateFallBackAttr(EuiccProfileWithBLOBs profile);

	/**
	 * @author Administrator
	 * @date 2016-11-10
	 * @param profile
	 * @return
	 * @Description TODO(根据Eid和FallBack查询)
	 */
	List<EuiccProfileWithBLOBs> selectByEidAndFallBack(EuiccProfileWithBLOBs profile);
}
