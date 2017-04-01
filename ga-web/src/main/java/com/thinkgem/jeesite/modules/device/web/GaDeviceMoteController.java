/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.web;

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
import com.thinkgem.jeesite.modules.device.entity.GaDeviceMote;
import com.thinkgem.jeesite.modules.device.service.GaDeviceMoteService;

/**
 * 中继设备Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/device/gaDeviceMote")
public class GaDeviceMoteController extends BaseController {

	@Autowired
	private GaDeviceMoteService gaDeviceMoteService;
	
	@ModelAttribute
	public GaDeviceMote get(@RequestParam(required=false) String id) {
		GaDeviceMote entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaDeviceMoteService.get(id);
		}
		if (entity == null){
			entity = new GaDeviceMote();
		}
		return entity;
	}
	
	@RequiresPermissions("device:gaDeviceMote:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaDeviceMote gaDeviceMote, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaDeviceMote> page = gaDeviceMoteService.findPage(new Page<GaDeviceMote>(request, response), gaDeviceMote); 
		model.addAttribute("page", page);
		return "modules/device/gaDeviceMoteList";
	}

	@RequiresPermissions("device:gaDeviceMote:view")
	@RequestMapping(value = "form")
	public String form(GaDeviceMote gaDeviceMote, Model model) {
		model.addAttribute("gaDeviceMote", gaDeviceMote);
		return "modules/device/gaDeviceMoteForm";
	}

	@RequiresPermissions("device:gaDeviceMote:edit")
	@RequestMapping(value = "save")
	public String save(GaDeviceMote gaDeviceMote, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaDeviceMote)){
			return form(gaDeviceMote, model);
		}
		gaDeviceMoteService.save(gaDeviceMote);
		addMessage(redirectAttributes, "保存中继设备成功");
		return "redirect:"+Global.getAdminPath()+"/device/gaDeviceMote/?repage";
	}
	
	@RequiresPermissions("device:gaDeviceMote:edit")
	@RequestMapping(value = "delete")
	public String delete(GaDeviceMote gaDeviceMote, RedirectAttributes redirectAttributes) {
		gaDeviceMoteService.delete(gaDeviceMote);
		addMessage(redirectAttributes, "删除中继设备成功");
		return "redirect:"+Global.getAdminPath()+"/device/gaDeviceMote/?repage";
	}

}