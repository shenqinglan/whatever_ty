/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.web;

import java.text.SimpleDateFormat;
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
 * 出入信息Controller
 * @author liuwsh
 * @version 2017-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/person/gaEntranceInfo")
public class GaEntranceInfoController extends BaseController {

	@Autowired
	private GaEntranceInfoService gaEntranceInfoService;

	@Autowired
	private GaGateInfoService gateInfoService;

	@ModelAttribute
	public GaEntranceInfo get(@RequestParam(required=false) String id) {
		GaEntranceInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaEntranceInfoService.get(id);
		}
		if (entity == null){
			entity = new GaEntranceInfo();
		}
		return entity;
	}

	@RequiresPermissions("person:gaEntranceInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GaEntranceInfo gaEntranceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GaEntranceInfo> page = gaEntranceInfoService.findPage(new Page<GaEntranceInfo>(request, response), gaEntranceInfo); 
		model.addAttribute("page", page);
		return "modules/person/gaEntranceInfoList";
	}

	@RequiresPermissions("person:gaEntranceInfo:view")
	@RequestMapping(value = "form")
	public String form(GaEntranceInfo gaEntranceInfo, Model model) {
		model.addAttribute("gaEntranceInfo", gaEntranceInfo);
		return "modules/person/gaEntranceInfoForm";
	}

	@RequiresPermissions("person:gaEntranceInfo:edit")
	@RequestMapping(value = "save")
	public String save(GaEntranceInfo gaEntranceInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gaEntranceInfo)){
			return form(gaEntranceInfo, model);
		}
		gaEntranceInfoService.save(gaEntranceInfo);
		addMessage(redirectAttributes, "保存出入信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaEntranceInfo/?repage";
	}

	@RequiresPermissions("person:gaEntranceInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GaEntranceInfo gaEntranceInfo, RedirectAttributes redirectAttributes) {
		gaEntranceInfoService.delete(gaEntranceInfo);
		addMessage(redirectAttributes, "删除出入信息成功");
		return "redirect:"+Global.getAdminPath()+"/person/gaEntranceInfo/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "getDoorByAreaId")
	public String getDoorByAreaId(String areaId) {
		GaGateInfo gateInfo = new GaGateInfo();
		gateInfo.setAreaId(areaId);
		List<GaGateInfo> ls = gateInfoService.findList(gateInfo);
		String res= JsonMapper.toJsonString(ls);
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "refreshList")
	public String refreshList(String areaId, String gateId, String startTime, String endTime, String pageNo, String pageSize) {
		Page p = new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		GaEntranceInfo gaEntranceInfo = new GaEntranceInfo();
		if (areaId != null && !"".equals(areaId)) {
			gaEntranceInfo.setAreaId(areaId);
		}
		if (gateId != null && !"".equals(gateId)) {
			gaEntranceInfo.setGateId(gateId);
		}
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (startTime != null && !"".equals(startTime)) {
				gaEntranceInfo.setStartTime(sdf.parse(startTime));
			}
			if (endTime != null && !"".equals(endTime)) {
				gaEntranceInfo.setEndTime(sdf.parse(endTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Page<GaEntranceInfo> page = gaEntranceInfoService.findPage(p, gaEntranceInfo);
		List<GaEntranceInfo> ls = page.getList();
		String res= JsonMapper.toJsonString(ls);
		return res;
	}

}