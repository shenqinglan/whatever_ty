/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oauth.entity.OauthCard;
import com.thinkgem.jeesite.modules.oauth.service.OauthCardService;

/**
 * 卡信息Controller
 * @author liuwsh
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/oauth/oauthCard")
public class OauthCardController extends BaseController {

	@Autowired
	private OauthCardService oauthCardService;
	
	@ModelAttribute
	public OauthCard get(@RequestParam(required=false) String id) {
		OauthCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oauthCardService.get(id);
		}
		if (entity == null){
			entity = new OauthCard();
		}
		return entity;
	}
	
	@RequiresPermissions("oauth:oauthCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(OauthCard oauthCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OauthCard> page = oauthCardService.findPage(new Page<OauthCard>(request, response), oauthCard); 
		model.addAttribute("page", page);
		return "modules/oauth/oauthCardList";
	}

	@RequiresPermissions("oauth:oauthCard:view")
	@RequestMapping(value = "form")
	public String form(OauthCard oauthCard, Model model) {
		oauthCard.setCardmanufacturerid(oauthCard.getCurrentUser().getRoleNames());
		model.addAttribute("oauthCard", oauthCard);
		return "modules/oauth/oauthCardForm";
	}

	@RequiresPermissions("oauth:oauthCard:edit")
	@RequestMapping(value = "save")
	public String save(OauthCard oauthCard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oauthCard)){
			return form(oauthCard, model);
		}
		
		if (oauthCard.getId() == null || "".equals(oauthCard.getId())) {
			OauthCard search = new OauthCard();
			search.setEid(oauthCard.getEid());
			search.setIccid(oauthCard.getIccid());
			search.setCurrentUser(oauthCard.getCurrentUser());
			if (oauthCardService.findList(search).size() != 0) {
				model.addAttribute("message", "卡已存在。");
				model.addAttribute("type", "error");
				return form(oauthCard, model);
			}
		} else {
			OauthCard search = new OauthCard();
			search.setEid(oauthCard.getEid());
			search.setIccid(oauthCard.getIccid());
			search.setCurrentUser(oauthCard.getCurrentUser());
			List<OauthCard> l = oauthCardService.findList(search);
			if (l.size() != 0) {
				for (OauthCard oa : l) {
					if (!oa.getId().equals(oauthCard.getId())) {
						model.addAttribute("message", "卡已存在。");
						model.addAttribute("type", "error");
						return form(oauthCard, model);
					}
				}
				
			}
		}
		
		if (oauthCard.getCount() == null || "".equals(oauthCard.getCount())) {
			oauthCard.setCount("0");
		}
		
		oauthCardService.save(oauthCard);
		addMessage(redirectAttributes, "保存卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/oauth/oauthCard/?repage";
	}
	
	@RequiresPermissions("oauth:oauthCard:edit")
	@RequestMapping(value = "delete")
	public String delete(OauthCard oauthCard, RedirectAttributes redirectAttributes) {
		oauthCardService.delete(oauthCard);
		addMessage(redirectAttributes, "删除卡信息成功");
		return "redirect:"+Global.getAdminPath()+"/oauth/oauthCard/?repage";
	}

}