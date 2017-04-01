/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.person.entity.GaHouseInfo;
import com.thinkgem.jeesite.modules.person.service.GaHouseInfoService;

/**
 * 房屋信息Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/person/gaHouseInfo")
public class GaHouseInfoController extends BaseController {

	@Autowired
	private GaHouseInfoService gaHouseInfoService;
	
	@ModelAttribute
	public GaHouseInfo get(@RequestParam(required=false) String id) {
		GaHouseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaHouseInfoService.get(id);
		}
		if (entity == null){
			entity = new GaHouseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("person:gaHouseInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaHouseInfo gaHouseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaHouseInfo> page = gaHouseInfoService.findPage(new Page<GaHouseInfo>(request, response), gaHouseInfo); 
		model.addAttribute("page", page);
		return "modules/person/gaHouseInfoList";
	}

	@RequiresPermissions("person:gaHouseInfo:view")
	@RequestMapping(value = "form")
	public String form(GaHouseInfo gaHouseInfo, Model model) {
		model.addAttribute("gaHouseInfo", gaHouseInfo);
		return "modules/person/gaHouseInfoForm";
	}

	@RequiresPermissions("person:gaHouseInfo:edit")
	@RequestMapping(value = "save")
	public String save(GaHouseInfo gaHouseInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaHouseInfo)){
			return form(gaHouseInfo, model);
		}
		gaHouseInfoService.save(gaHouseInfo);
		addMessage(redirectAttributes, "保存房屋信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaHouseInfo/?repage";
	}
	
	@RequiresPermissions("person:gaHouseInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GaHouseInfo gaHouseInfo, RedirectAttributes redirectAttributes) {
		gaHouseInfoService.delete(gaHouseInfo);
		addMessage(redirectAttributes, "删除房屋信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaHouseInfo/?repage";
	}

}