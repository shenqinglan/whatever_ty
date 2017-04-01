/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-1
 * Id: IEuiccEcasdInfoService.java,v 1.0 2016-11-1 下午2:36:40 Administrator
 */
package com.whty.efs.data.service;

import java.util.List;

import com.whty.efs.data.pojo.EuiccProfile;
import com.whty.efs.data.pojo.EuiccProfileWithBLOBs;

/**
 * @ClassName IEuiccEcasdInfoService
 * @author Administrator
 * @date 2016-11-1 下午2:36:40
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IEuiccProfileInfoService {

	List<EuiccProfile> findMgrProfile(String eid);

	List<EuiccProfile> findInstallProfile(String eid);
	
	EuiccProfile selectByIccid(String iccid);
	
	List<EuiccProfileWithBLOBs> selectByEid(String eid);

}
