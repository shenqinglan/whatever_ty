/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.web;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.oauth.entity.OauthCard;
import com.thinkgem.jeesite.modules.oauth.entity.OauthKey;
import com.thinkgem.jeesite.modules.oauth.service.OauthCardService;
import com.thinkgem.jeesite.modules.oauth.service.OauthKeyService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 密钥信息Controller
 * @author liuwsh
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/oauth/oauthKey")
public class OauthKeyController extends BaseController {

	@Autowired
	private OauthKeyService oauthKeyService;

	@Autowired
	OauthCardService oauthCardService;

	@ModelAttribute
	public OauthKey get(@RequestParam(required=false) String id) {
		OauthKey entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oauthKeyService.get(id);
		}
		if (entity == null){
			entity = new OauthKey();
		}
		return entity;
	}

	@RequiresPermissions("oauth:oauthKey:view")
	@RequestMapping(value = {"list", ""})
	public String list(OauthKey oauthKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OauthKey> page = oauthKeyService.findPage(new Page<OauthKey>(request, response), oauthKey); 
		model.addAttribute("page", page);
		return "modules/oauth/oauthKeyList";
	}

	@RequiresPermissions("oauth:oauthKey:view")
	@RequestMapping(value = "form")
	public String form(OauthKey oauthKey, Model model) {
		model.addAttribute("oauthKey", oauthKey);
		return "modules/oauth/oauthKeyForm";
	}

	private String convert(String before) {
		String regex="^((\\d+.?\\d+)[Ee]{1}(\\d+))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(before);
		if (m.matches()) {
			DecimalFormat df = new DecimalFormat("#");
			return df.format(Double.parseDouble(before));
		}
		return before;
	}

	private boolean checkCard(OauthKey oauthKey) {
		oauthKey.setEid(convert(oauthKey.getEid()));
		oauthKey.setIccid(convert(oauthKey.getIccid()));
		oauthKey.setIndex(convert(oauthKey.getIndex()));
		oauthKey.setVersion(convert(oauthKey.getVersion()));
		oauthKey.setMacKey(convert(oauthKey.getMacKey()));
		
		
		OauthCard o = new OauthCard();
		o.setEid(oauthKey.getEid());
		o.setIccid(oauthKey.getIccid());
		
		o.setCurrentUser(UserUtils.getUser());
		List<OauthCard> r = oauthCardService.findList(o);
		if (r == null || r.size() != 1) {
			return false;
		}
		oauthKey.setMsisdn(r.get(0).getMsisdn());
		return true;
	}

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oauth:oauthKey:edit")
	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OauthKey> list = ei.getDataList(OauthKey.class);
			for (OauthKey oauthKey : list){
				try{
					BeanValidators.validateWithException(validator, oauthKey);
					if (checkCard(oauthKey)){
						oauthKeyService.save(oauthKey);
						successNum++;
					}else{
						failureMsg.append("<br/>EID:" + oauthKey.getEid() + ", ICCID:" + oauthKey.getIccid() + "的卡不存在或存在多张卡. ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>EID:" + oauthKey.getEid() + ", ICCID:" + oauthKey.getIccid() + "的卡密钥导入失败. ");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
					}
					failureNum++;
				}catch (Exception ex) {
					failureMsg.append("<br/>EID:" + oauthKey.getEid() + ", ICCID:" + oauthKey.getIccid() + "的卡密钥导入失败. ");
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条密钥"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/oauth/oauthKey/?repage";
	}

	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oauth:oauthKey:edit")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "密钥导入模板.xlsx";
			List<OauthKey> list = Lists.newArrayList(); 
			new ExportExcel("密钥数据", OauthKey.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/oauth/oauthKey/?repage";
	}

	@RequiresPermissions("oauth:oauthKey:edit")
	@RequestMapping(value = "save")
	public String save(OauthKey oauthKey, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oauthKey)){
			return form(oauthKey, model);
		}
		OauthCard o = new OauthCard();
		o.setIccid(oauthKey.getIccid());
		List<OauthCard> r = oauthCardService.findList(o);
		if (r == null || r.size() == 0) {
			model.addAttribute("message", "卡不存在。");
			model.addAttribute("type", "error");
			return form(oauthKey, model);
		}
		oauthKey.setEid(r.get(0).getEid());
		oauthKey.setMsisdn(r.get(0).getMsisdn());
		
		oauthKeyService.save(oauthKey);
		addMessage(redirectAttributes, "保存密钥信息成功");
		return "redirect:"+Global.getAdminPath()+"/oauth/oauthKey/?repage";
	}

	@RequiresPermissions("oauth:oauthKey:edit")
	@RequestMapping(value = "delete")
	public String delete(OauthKey oauthKey, RedirectAttributes redirectAttributes) {
		oauthKeyService.delete(oauthKey);
		addMessage(redirectAttributes, "删除密钥信息成功");
		return "redirect:"+Global.getAdminPath()+"/oauth/oauthKey/?repage";
	}

}