/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oauth.entity.InputEntity;
import com.thinkgem.jeesite.modules.oauth.entity.OauthInterfaceInfo;
import com.thinkgem.jeesite.modules.oauth.entity.OauthKey;
import com.thinkgem.jeesite.modules.oauth.entity.OutputEntity;
import com.thinkgem.jeesite.modules.oauth.service.OauthInterfaceInfoService;
import com.thinkgem.jeesite.modules.oauth.service.OauthKeyService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;


/**
 * Controller
 * @author liuwsh
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/oauth/query")
public class QueryController extends BaseController {
	@Autowired
	OauthInterfaceInfoService s;

	@RequiresPermissions("oauth:query:view")
	@RequestMapping(value = {"list", ""})
	public String list(OauthInterfaceInfo oauthInterfaceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OauthInterfaceInfo> lo = s.findList(oauthInterfaceInfo);
		model.addAttribute("userrID", oauthInterfaceInfo.getCurrentUser().getId());
		for (OauthInterfaceInfo o : lo) {
			o.setInterfaceType(DictUtils.getDictLabel(o.getInterfaceType(), "oauth_test_type", ""));
			if (o.getFinishTime() != null) {
				o.setFinishTime(o.getFinishTime());
			} else {
				o.setFinishTime("");
			}
			if (o.getRetResult() != null && !"".equals(o.getRetResult())) {
				o.setRetResult(DictUtils.getDictLabel(o.getRetResult(), "oauth_interface_status", ""));
			} else {
				o.setRetResult("处理中");
			}
		}
		model.addAttribute("list", lo);
		return "modules/oauth/query";
	}


	@ResponseBody
	@RequestMapping(value = "refreshList")
	public String refreshList(String userrID) {
		OauthInterfaceInfo oauthInterfaceInfo = new OauthInterfaceInfo();
		oauthInterfaceInfo.setCurrentUser(UserUtils.get(userrID));
		
		List<OauthInterfaceInfo> lo = s.findList(oauthInterfaceInfo);
		for (OauthInterfaceInfo o : lo) {
			o.setInterfaceType(DictUtils.getDictLabel(o.getInterfaceType(), "oauth_test_type", ""));
			if (o.getFinishTime() != null) {
				o.setFinishTime(o.getFinishTime());
			} else {
				o.setFinishTime("");
			}
			if (o.getRetResult() != null && !"".equals(o.getRetResult())) {
				o.setRetResult(DictUtils.getDictLabel(o.getRetResult(), "oauth_interface_status", ""));
			} else {
				o.setRetResult("处理中");
			}
		}
		String res= JsonMapper.toJsonString(lo);
		return res;
	}

}