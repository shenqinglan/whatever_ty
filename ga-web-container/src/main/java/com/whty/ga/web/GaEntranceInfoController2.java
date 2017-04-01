/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.whty.ga.entity.GaEntranceInfo;
import com.whty.ga.service.GaEntranceInfoService;



/**
 * 出入记录Controller
 * @author liuwsh
 * @version 2017-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/person/gaEntranceInfo2")
public class GaEntranceInfoController2 extends BaseController {

	@Autowired
	private GaEntranceInfoService entranceInfoService;
	
	@RequiresPermissions("person:gaEntranceInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaEntranceInfo entranceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaEntranceInfo> page = entranceInfoService.findPage(new Page<GaEntranceInfo>(request, response), entranceInfo); 
		model.addAttribute("page", page);
		return "modules/person/gaEntranceInfoList2";
	}

}