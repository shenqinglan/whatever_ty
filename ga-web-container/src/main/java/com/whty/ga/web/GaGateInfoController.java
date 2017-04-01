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
import com.whty.ga.entity.GaGateInfo;
import com.whty.ga.service.GaGateInfoService;

/**
 * 门禁信息Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/person/gaGateInfo")
public class GaGateInfoController extends BaseController {

	@Autowired
	private GaGateInfoService gaGateInfoService;
	
	@ModelAttribute
	public GaGateInfo get(@RequestParam(required=false) String id) {
		GaGateInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaGateInfoService.get(id);
		}
		if (entity == null){
			entity = new GaGateInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("person:gaGateInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaGateInfo gaGateInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaGateInfo> page = gaGateInfoService.findPage(new Page<GaGateInfo>(request, response), gaGateInfo); 
		model.addAttribute("page", page);
		return "modules/person/gaGateInfoList";
	}

	@RequiresPermissions("person:gaGateInfo:view")
	@RequestMapping(value = "form")
	public String form(GaGateInfo gaGateInfo, Model model) {
		model.addAttribute("gaGateInfo", gaGateInfo);
		return "modules/person/gaGateInfoForm";
	}

	@RequiresPermissions("person:gaGateInfo:edit")
	@RequestMapping(value = "save")
	public String save(GaGateInfo gaGateInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaGateInfo)){
			return form(gaGateInfo, model);
		}
		gaGateInfoService.save(gaGateInfo);
		addMessage(redirectAttributes, "保存门禁信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaGateInfo/?repage";
	}
	
	@RequiresPermissions("person:gaGateInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GaGateInfo gaGateInfo, RedirectAttributes redirectAttributes) {
		gaGateInfoService.delete(gaGateInfo);
		addMessage(redirectAttributes, "删除门禁信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaGateInfo/?repage";
	}

}