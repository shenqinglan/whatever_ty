/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-10-31
 * Id: CardInfoController.java,v 1.0 2016-10-31 下午3:32:50 Administrator
 */
package com.whty.efs.data.controller;

import java.io.IOException;
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

import com.whty.efs.data.pojo.EuiccCard;
import com.whty.efs.data.service.IEuiccCardInfoService;

/**
 * @ClassName CardInfoController
 * @author Administrator
 * @date 2016-10-31 下午3:32:50
 * @Description TODO(手机端智能卡控制器)
 */
@Controller
@RequestMapping("/euiccCardInfo")
public class EuiccCardInfoController  {
	
	@Autowired
	@Qualifier("euiccCardInfoService")
	private IEuiccCardInfoService euiccCardInfoService;
	
	/**
	 * 
	 * @author Administrator
	 * @date 2016-10-31
	 * @param request
	 * @param response
	 * @param euiccCard
	 * @throws IOException
	 * @Description TODO(查询卡信息)
	 */
	@RequestMapping(value = "/findEuiccCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findEuiccCard(@RequestParam(value="eid", required=false) String eid) {
		List<EuiccCard> result = euiccCardInfoService.find(eid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
}
