/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-1
 * Id: EuiccEcasdInfoServiceImpl.java,v 1.0 2016-11-1 下午2:37:15 Administrator
 */
package com.whty.efs.data.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.efs.data.dao.EuiccProfileMapper;
import com.whty.efs.data.pojo.EuiccProfile;
import com.whty.efs.data.pojo.EuiccProfileWithBLOBs;
import com.whty.efs.data.service.IEuiccProfileInfoService;

/**
 * @ClassName EuiccEcasdInfoServiceImpl
 * @author Administrator
 * @date 2016-11-1 下午2:37:15
 * @Description TODO(查询手机端智能卡的profile信息服务层)
 */
@Transactional
@Service("euiccProfileInfoService")
public class EuiccProfileInfoServiceImpl implements IEuiccProfileInfoService {
	
	@Autowired
	@Resource
	EuiccProfileMapper euiccProfileMapper;
	
	/**
	 * @author Administrator
	 * @date 2016-11-2
	 * @param eid
	 * @return
	 * @Description TODO(查询管理卡的profile信息)
	 * @see com.whty.efs.data.service.IEuiccProfileInfoService#findMgrProfile(java.lang.String)
	 */
	@Override
	public List<EuiccProfile> findMgrProfile(String eid) {
		// TODO Auto-generated method stub
		return euiccProfileMapper.findMgrProfile(eid);
	}

	/**
	 * @author Administrator
	 * @date 2016-11-2
	 * @param eid
	 * @return
	 * @Description TODO(查询安装卡的profile信息)
	 * @see com.whty.efs.data.service.IEuiccProfileInfoService#findInstallProfile(java.lang.String)
	 */
	@Override
	public List<EuiccProfile> findInstallProfile(String eid) {
		// TODO Auto-generated method stub
		return euiccProfileMapper.findInstallProfile(eid);
	}
	
	/**
	 * @author Administrator
	 * @date 2016-11-24
	 * @param iccid
	 * @return
	 * @Description TODO(查询安装卡的profile信息)
	 * @see com.whty.efs.data.service.IEuiccProfileInfoService#findInstallProfile(java.lang.String)
	 */
	@Override
	public EuiccProfile selectByIccid(String iccid) {
		// TODO Auto-generated method stub
		return euiccProfileMapper.selectByIccid(iccid);
	}

	@Override
	public List<EuiccProfileWithBLOBs> selectByEid(String eid) {
		// TODO Auto-generated method stub
		return euiccProfileMapper.selectByEid(eid);
	}

}
