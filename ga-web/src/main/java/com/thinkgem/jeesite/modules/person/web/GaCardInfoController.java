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
import com.thinkgem.jeesite.modules.person.entity.GaCardInfo;
import com.thinkgem.jeesite.modules.person.service.GaCardInfoService;

/**
 * 卡信息Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/person/gaCardInfo")
public class GaCardInfoController extends BaseController {

	@Autowired
	private GaCardInfoService gaCardInfoService;
	
	@ModelAttribute
	public GaCardInfo get(@RequestParam(required=false) String id) {
		GaCardInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaCardInfoService.get(id);
		}
		if (entity == null){
			entity = new GaCardInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("person:gaCardInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaCardInfo gaCardInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaCardInfo> page = gaCardInfoService.findPage(new Page<GaCardInfo>(request, response), gaCardInfo); 
		model.addAttribute("page", page);
		return "modules/person/gaCardInfoList";
	}

	@RequiresPermissions("person:gaCardInfo:view")
	@RequestMapping(value = "form")
	public String form(GaCardInfo gaCardInfo, Model model) {
		model.addAttribute("gaCardInfo", gaCardInfo);
		return "modules/person/gaCardInfoForm";
	}

	@RequiresPermissions("person:gaCardInfo:edit")
	@RequestMapping(value = "save")
	public String save(GaCardInfo gaCardInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaCardInfo)){
			return form(gaCardInfo, model);
		}
		gaCardInfoService.save(gaCardInfo);
		addMessage(redirectAttributes, "保存卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaCardInfo/?repage";
	}
	
	@RequiresPermissions("person:gaCardInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GaCardInfo gaCardInfo, RedirectAttributes redirectAttributes) {
		gaCardInfoService.delete(gaCardInfo);
		addMessage(redirectAttributes, "删除卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaCardInfo/?repage";
	}

}