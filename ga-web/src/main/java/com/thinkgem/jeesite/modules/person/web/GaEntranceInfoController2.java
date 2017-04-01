/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.person.entity.GaEntranceInfo;
import com.thinkgem.jeesite.modules.person.entity.GaGateInfo;
import com.thinkgem.jeesite.modules.person.service.GaEntranceInfoService;
import com.thinkgem.jeesite.modules.person.service.GaGateInfoService;



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