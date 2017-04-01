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
import com.thinkgem.jeesite.modules.person.entity.GaPersonInfo;
import com.thinkgem.jeesite.modules.person.service.GaPersonInfoService;

/**
 * 个人信息Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/person/gaPersonInfo")
public class GaPersonInfoController extends BaseController {

	@Autowired
	private GaPersonInfoService gaPersonInfoService;
	
	@ModelAttribute
	public GaPersonInfo get(@RequestParam(required=false) String id) {
		GaPersonInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaPersonInfoService.get(id);
		}
		if (entity == null){
			entity = new GaPersonInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("person:gaPersonInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaPersonInfo gaPersonInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaPersonInfo> page = gaPersonInfoService.findPage(new Page<GaPersonInfo>(request, response), gaPersonInfo); 
		model.addAttribute("page", page);
		return "modules/person/gaPersonInfoList";
	}

	@RequiresPermissions("person:gaPersonInfo:view")
	@RequestMapping(value = "form")
	public String form(GaPersonInfo gaPersonInfo, Model model) {
		model.addAttribute("gaPersonInfo", gaPersonInfo);
		return "modules/person/gaPersonInfoForm";
	}

	@RequiresPermissions("person:gaPersonInfo:edit")
	@RequestMapping(value = "save")
	public String save(GaPersonInfo gaPersonInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaPersonInfo)){
			return form(gaPersonInfo, model);
		}
		gaPersonInfoService.save(gaPersonInfo);
		addMessage(redirectAttributes, "保存个人信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaPersonInfo/?repage";
	}
	
	@RequiresPermissions("person:gaPersonInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GaPersonInfo gaPersonInfo, RedirectAttributes redirectAttributes) {
		gaPersonInfoService.delete(gaPersonInfo);
		addMessage(redirectAttributes, "删除个人信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaPersonInfo/?repage";
	}

}