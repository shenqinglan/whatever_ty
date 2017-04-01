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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.whty.ga.entity.GaAreaInfo;
import com.whty.ga.service.GaAreaInfoService;

/**
 * 小区信息Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/person/gaAreaInfo")
public class GaAreaInfoController extends BaseController {

	@Autowired
	private GaAreaInfoService gaAreaInfoService;
	
	@ModelAttribute
	public GaAreaInfo get(@RequestParam(required=false) String id) {
		GaAreaInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaAreaInfoService.get(id);
		}
		if (entity == null){
			entity = new GaAreaInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("person:gaAreaInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaAreaInfo gaAreaInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaAreaInfo> page = gaAreaInfoService.findPage(new Page<GaAreaInfo>(request, response), gaAreaInfo); 
		model.addAttribute("page", page);
		return "modules/person/gaAreaInfoList";
	}

	@RequiresPermissions("person:gaAreaInfo:view")
	@RequestMapping(value = "form")
	public String form(GaAreaInfo gaAreaInfo, Model model) {
		model.addAttribute("gaAreaInfo", gaAreaInfo);
		return "modules/person/gaAreaInfoForm";
	}

	@RequiresPermissions("person:gaAreaInfo:edit")
	@RequestMapping(value = "save")
	public String save(GaAreaInfo gaAreaInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaAreaInfo)){
			return form(gaAreaInfo, model);
		}
		gaAreaInfoService.save(gaAreaInfo);
		addMessage(redirectAttributes, "保存小区信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaAreaInfo/?repage";
	}
	
	@RequiresPermissions("person:gaAreaInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GaAreaInfo gaAreaInfo, RedirectAttributes redirectAttributes) {
		gaAreaInfoService.delete(gaAreaInfo);
		addMessage(redirectAttributes, "删除小区信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaAreaInfo/?repage";
	}

}