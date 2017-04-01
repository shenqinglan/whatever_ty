/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-1
 * Id: EuiccProfileInfoController.java,v 1.0 2016-11-1 下午2:28:14 Administrator
 */
package com.whty.efs.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whty.efs.data.pojo.EuiccProfile;
import com.whty.efs.data.pojo.EuiccProfileWithBLOBs;
import com.whty.efs.data.service.IEuiccProfileInfoService;

/**
 * @ClassName EuiccProfileInfoController
 * @author Administrator
 * @date 2016-11-1 下午2:47:27
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Controller
@RequestMapping("/euiccProfileInfo")
public class EuiccProfileInfoController {
	
	@Autowired
	@Qualifier("euiccProfileInfoService")
	private IEuiccProfileInfoService euiccProfileInfoService;

	/**
	 * @author Administrator
	 * @date 2016-11-2
	 * @param eid
	 * @return
	 * @Description TODO(通过eid查询管理卡profile信息,profile的eid必须不为空)
	 */
	@RequestMapping(value = "/findMgrProfile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findMgrProfile(@RequestParam(value="eid", required=false) String eid) {                 
		List<EuiccProfile> result = euiccProfileInfoService.findMgrProfile(eid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-11-2
	 * @param eid
	 * @return
	 * @Description TODO(通过eid查询能够安装卡profile信息,一张卡可以有多个profile)
	 */
	@RequestMapping(value = "/findInstallProfile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findInstallProfile(@RequestParam(value="eid", required=false) String eid) {
		List<EuiccProfile> result = euiccProfileInfoService.findInstallProfile(eid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-11-24
	 * @param iccid
	 * @return
	 * @Description TODO(通过eid查询能够安装卡profile信息,一张卡可以有多个profile)
	 */
	@RequestMapping(value = "/findProfileByPrimaryKey", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findProfileByPrimaryKey(@RequestParam(value="iccid", required=false) String iccid) {
		List<EuiccProfile> result = new ArrayList<EuiccProfile>();
		EuiccProfile euiccProfile = euiccProfileInfoService.selectByIccid(iccid);
		result.add(euiccProfile);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
}
