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
import com.thinkgem.jeesite.modules.device.entity.GaDeviceMs;
import com.thinkgem.jeesite.modules.device.service.GaDeviceMsService;

/**
 * 终端设备Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/device/gaDeviceMs")
public class GaDeviceMsController extends BaseController {

	@Autowired
	private GaDeviceMsService gaDeviceMsService;
	
	@ModelAttribute
	public GaDeviceMs get(@RequestParam(required=false) String id) {
		GaDeviceMs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaDeviceMsService.get(id);
		}
		if (entity == null){
			entity = new GaDeviceMs();
		}
		return entity;
	}
	
	@RequiresPermissions("device:gaDeviceMs:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaDeviceMs gaDeviceMs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaDeviceMs> page = gaDeviceMsService.findPage(new Page<GaDeviceMs>(request, response), gaDeviceMs); 
		model.addAttribute("page", page);
		return "modules/device/gaDeviceMsList";
	}

	@RequiresPermissions("device:gaDeviceMs:view")
	@RequestMapping(value = "form")
	public String form(GaDeviceMs gaDeviceMs, Model model) {
		model.addAttribute("gaDeviceMs", gaDeviceMs);
		return "modules/device/gaDeviceMsForm";
	}

	@RequiresPermissions("device:gaDeviceMs:edit")
	@RequestMapping(value = "save")
	public String save(GaDeviceMs gaDeviceMs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaDeviceMs)){
			return form(gaDeviceMs, model);
		}
		gaDeviceMsService.save(gaDeviceMs);
		addMessage(redirectAttributes, "保存终端设备成功");
		return "redirect:"+Global.getAdminPath()+"/device/gaDeviceMs/?repage";
	}
	
	@RequiresPermissions("device:gaDeviceMs:edit")
	@RequestMapping(value = "delete")
	public String delete(GaDeviceMs gaDeviceMs, RedirectAttributes redirectAttributes) {
		gaDeviceMsService.delete(gaDeviceMs);
		addMessage(redirectAttributes, "删除终端设备成功");
		return "redirect:"+Global.getAdminPath()+"/device/gaDeviceMs/?repage";
	}

}