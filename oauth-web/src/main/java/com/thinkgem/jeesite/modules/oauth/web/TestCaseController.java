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
@RequestMapping(value = "${adminPath}/oauth/testCase")
public class TestCaseController extends BaseController {
	@Autowired
	OauthInterfaceInfoService s;

	@RequiresPermissions("oauth:testCase:view")
	@RequestMapping(value = {"list", ""})
	public String list(InputEntity inputEntity, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("inputEntity", inputEntity);
		return "modules/oauth/testCase";
	}

	@RequiresPermissions("oauth:testCase:view")
	@RequestMapping(value = "form")
	public String form(InputEntity inputEntity, Model model) {
		if (!beanValidator(model, inputEntity)){
			return form(inputEntity, model);
		}
		String ur = "http://10.8.40.116:8090/oauth/v1/platform/down/";
		Map<String, String> map = new HashMap<String, String>();
		map.put("01", ur + "triggerRegister?phoneNo=" + inputEntity.getPhoneNo());
		map.put("02", ur + "baseOauth?phoneNo=" + inputEntity.getPhoneNo() + "&format=" + inputEntity.getFormat() + "&authType=" + inputEntity.getAuthType() + "&authData=" + inputEntity.getAuthData());
		map.put("03", ur + "validCodeOauth?phoneNo=" + inputEntity.getPhoneNo() + "&format=" + inputEntity.getFormat() + "&authType=" + inputEntity.getAuthType() + "&authData=" + inputEntity.getAuthData());
		map.put("04", ur + "changePwd?phoneNo=" + inputEntity.getPhoneNo());
		map.put("05", ur + "resetPwd?phoneNo=" + inputEntity.getPhoneNo());
		map.put("06", ur + "getRegisterStatus?phoneNo=" + inputEntity.getPhoneNo());
		map.put("07", ur + "clearRegister?phoneNo=" + inputEntity.getPhoneNo());

		List<OutputEntity> list = new ArrayList<OutputEntity>();
		OutputEntity oe = new OutputEntity();
		oe = getURLContent(map.get(inputEntity.getType()));
		
		oe.setMsisdn(inputEntity.getPhoneNo());
		oe.setInterfaceType(DictUtils.getDictLabel(inputEntity.getType(), "oauth_test_type", ""));
		if (oe != null && oe.getRespcode() != null && "0000".equals(oe.getRespcode())) {
			OauthInterfaceInfo oauthInterfaceInfo = new OauthInterfaceInfo();
			oauthInterfaceInfo.setCurrentUser(inputEntity.getCurrentUser());
			oauthInterfaceInfo.setTransactionId(oe.getTransactionId());
			List<OauthInterfaceInfo> lo = s.findList(oauthInterfaceInfo);
			if (lo != null && lo.size() == 1 && lo.get(0) != null) {
				OauthInterfaceInfo oii = lo.get(0);
				oe.setTransactionId(oii.getTransactionId());
				oe.setEid(oii.getEid());
				oe.setOperatorTime(oii.getOperatorTime().toString());
				oe.setInterfaceType(DictUtils.getDictLabel(oii.getInterfaceType(), "oauth_test_type", ""));
				oe.setFinishTime(oii.getFinishTime());
				oe.setRetResult("处理中");
			}
		}
		list.add(oe);

		model.addAttribute("userrID", inputEntity.getCurrentUser().getId());
		model.addAttribute("list", list);
		return "modules/oauth/resultList";
	}

	/**   
	 * 程序中访问http数据接口   
	 */    
	public OutputEntity getURLContent(String urlStr) {               
		/** 网络的url地址 */        
		URL url = null;              
		/** http连接 */    
		HttpURLConnection httpConn = null;            
		/**//** 输入流 */   
		BufferedReader in = null;   
		StringBuffer sb = new StringBuffer();   
		try{     
			url = new URL(urlStr);     
			in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );   
			String str = null;    
			while((str = in.readLine()) != null) {    
				sb.append( str );     
			}     
		} catch (Exception ex) {   
			ex.printStackTrace();
		} finally{    
			try{             
				if(in!=null) {  
					in.close();     
				}     
			}catch(IOException ex) {      
			}     
		}     
		String result =sb.toString();     
		System.out.println(result);     
		JsonMapper jsm = new JsonMapper();

		OutputEntity m = jsm.fromJson(result, OutputEntity.class);
		return m;    
	}    

	@ResponseBody
	@RequestMapping(value = "refreshList")
	public String refreshList(String msisdn, String respcode, String respdesc, String accepttime, String transactionId, String userrID) {
		List<OutputEntity> list = new ArrayList<OutputEntity>();
		OutputEntity oe = new OutputEntity();
		oe.setMsisdn(msisdn);
		oe.setRespcode(respcode);
		oe.setRespdesc(respdesc);
		oe.setAccepttime(accepttime);
		oe.setTransactionId(transactionId);

		OauthInterfaceInfo oauthInterfaceInfo = new OauthInterfaceInfo();
		oauthInterfaceInfo.setTransactionId(oe.getTransactionId());
		oauthInterfaceInfo.setCurrentUser(UserUtils.get(userrID));
		if (oe.getTransactionId() != null && !"".equals(oe.getTransactionId())) {
			List<OauthInterfaceInfo> lo = s.findList(oauthInterfaceInfo);
			if (lo != null && lo.size() == 1 && lo.get(0) != null) {
				OauthInterfaceInfo oii = lo.get(0);
				oe.setTransactionId(oii.getTransactionId());
				if (oii.getEid() != null) {
					oe.setEid(oii.getEid());
				} else {
					oe.setEid("");
				}
				oe.setOperatorTime(oii.getOperatorTime().toString());
				oe.setInterfaceType(DictUtils.getDictLabel(oii.getInterfaceType(), "oauth_test_type", ""));
				if (oii.getFinishTime() != null) {
					oe.setFinishTime(oii.getFinishTime());
				} else {
					oe.setFinishTime("");
				}
				if (oii.getRetResult() != null && !"".equals(oii.getRetResult())) {
					oe.setRetResult(DictUtils.getDictLabel(oii.getRetResult(), "oauth_interface_status", ""));
				} else {
					oe.setRetResult("处理中");
				}
			}
		}
		list.add(oe);
		String res= JsonMapper.toJsonString(list);
		return res;
	}

}