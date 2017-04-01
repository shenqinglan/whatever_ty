package com.whty.euicc.data.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.gson.Gson;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.base.DataTableQuery;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.common.utils.CheckCallType;
import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.DateUtil;
import com.whty.euicc.data.constant.ProfileFallBack;
import com.whty.euicc.data.constant.ProfileState;
import com.whty.euicc.data.message.EuiccMsg;
import com.whty.euicc.data.message.MsgHeader;
import com.whty.euicc.data.message.request.DeleteProfileByDpReqBody;
import com.whty.euicc.data.message.request.DeleteProfileByDpSmsReqBody;
import com.whty.euicc.data.message.request.DisableProfileByDpReqBody;
import com.whty.euicc.data.message.request.DisableProfileByDpSmsReqBody;
import com.whty.euicc.data.message.request.EnableProfileByDpReqBody;
import com.whty.euicc.data.message.request.EnableProfileByDpSmsReqBody;
import com.whty.euicc.data.message.request.MasterDeleteByDpReqBody;
import com.whty.euicc.data.message.request.SetFallBackAttrByDpReqBody;
import com.whty.euicc.data.message.request.SetFallBackByDpAndSmsReqBody;
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileExample;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccPol2Service;
import com.whty.euicc.data.service.EuiccProfileService;


/**
 * @author profile启用、禁用、删除、主删除
 * @date 2016-08-03 16:15
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/euiccProfile")
public class EuiccProfileController extends BaseController{
	
	@Autowired
	private BaseLogsService baseLogsService;

	@Autowired
	private EuiccProfileService euiccProfileService;
	
	@Autowired
	private EuiccPol2Service euiccPol2;
	
	@RequestMapping(value = "/showProfiles", method = RequestMethod.POST)
	public void showScp80(HttpServletRequest request, HttpServletResponse response,
			String eid) throws IOException {
		writeJSONResult(eid, response);
	}
	
	/**
	 * 查询
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @return
	 */
	@RequestMapping(value = "/findProfiles")
	public void find(HttpServletRequest request,HttpServletResponse response,EuiccProfile profile)
			throws IOException {
		DataTableQuery dt = new DataTableQuery(request);
		Map<String,Object> result = buildTableData(dt, profile);
		writeJSONResult(result, response,DateUtil.yyyy_MM_dd_HH_mm_EN);
	}
	
	/**
	 * 构建数据树
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws IOException
	 */
	private Map<String,Object> buildTableData(DataTableQuery dt,EuiccProfile profile) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		EuiccProfileExample example = buildExample(dt, profile);
		PageList<EuiccProfile> euiccProfiles = euiccProfileService.selectByExample(profile,example, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", euiccProfiles);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", euiccProfiles.getPaginator().getTotalCount());
		records.put("recordsFiltered", euiccProfiles.getPaginator().getTotalCount());
		return records;
	}
	
	/**
	 * 构建列表查询条件
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @param 
	 * @return
	 */
	private EuiccProfileExample buildExample(DataTableQuery dt,EuiccProfile profile) {
		EuiccProfileExample euiccProfileExample = new EuiccProfileExample();
		EuiccProfileExample.Criteria criteria = euiccProfileExample.createCriteria();
		// 查询条件
		String eid = profile.getCardEid();
		
		if (!StringUtils.equalsIgnoreCase(profile.getOptType(), "install") && CheckEmpty.isNotEmpty(eid)) {
			criteria.andEidEqualTo(eid);
		}
		
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			euiccProfileExample.setOrderByClause(orderByClause.toString());
		}
		return euiccProfileExample;
	}
	/**
	 * 主删除profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/masterDeleteProfile", method = RequestMethod.POST)
	public void masterDelete(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		
		// 删除
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(StringUtils.equals(ProfileState.ENABLE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "请先禁用profile");
					writeJSONResult(baseResponseDto, response);
				}
				if(StringUtils.equals(ProfileState.DELETE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已删除");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.deleteProfile(profile);
			}else{
				masterDeleteByHttps(profile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "主删除成功")
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "主删除profile", "主删除"));
		writeJSONResult(baseResponseDto, response);
	}
	
	public void masterDeleteByHttps(EuiccProfileWithBLOBs profile)throws Exception{
        MsgHeader header = new MsgHeader("masterDeleteByDp");
        MasterDeleteByDpReqBody requestBody = new MasterDeleteByDpReqBody();
        requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	/**
	 * 设置回滚属性
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/setFallBackAttr", method = RequestMethod.POST)
	public void setFallBackAttr(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		
		// 设置回滚属性
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(StringUtils.equals(ProfileFallBack.FALLBACK_YES, profile.getFallbackAttribute())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已设置回滚属性");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.updateByPrimaryKeySelective(profile);
			}else{
				setFallBackAttrByHttps(profile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "设置回滚属性成功")
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "profile设置回滚属性", "回滚属性"));
		writeJSONResult(baseResponseDto, response);
	}
	
	public void setFallBackAttrByHttps(EuiccProfileWithBLOBs profile)throws Exception{
        MsgHeader header = new MsgHeader("setFallBackAttrByDp");
        SetFallBackAttrByDpReqBody requestBody = new SetFallBackAttrByDpReqBody();
        requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	/**
	 * 删除profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/deleteProfile", method = RequestMethod.POST)
	public void delete(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		// 删除
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(StringUtils.equals(ProfileState.DELETE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已删除");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.deleteProfile(profile);
			}else{
				profileDeleteByHttps(profile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "删除成功")
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "删除profile", "删除"));
		writeJSONResult(baseResponseDto, response);
	}
	public void profileDeleteByHttps(EuiccProfileWithBLOBs profile)throws Exception{
        MsgHeader header = new MsgHeader("profileDeletionByDp");
        DeleteProfileByDpReqBody requestBody = new DeleteProfileByDpReqBody();
        requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	/**
	 * 启用profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/enableProfile", method = RequestMethod.POST)
	public void enableProfile(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		EuiccProfileWithBLOBs currentProfile = euiccProfileService.selectEuiccProfileByStateAndEid(ProfileState.ENABLE, profile.getEid());
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if((!StringUtils.equals(ProfileState.INSTALL_ISD_P_STATE_SUCCESS, profile.getState())&&(!StringUtils.equals(ProfileState.DIS_ENABLE, profile.getState())))){
					baseResponseDto = new BaseResponseDto(false, "请先下载或禁用profile");
					writeJSONResult(baseResponseDto, response);
				}
				if(StringUtils.equals(ProfileState.DELETE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已删除");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.enableProfile(profile);
			}else{
				enableProfileByHttps(profile);
				if(isAutoDelete(currentProfile.getIccid())){
					msg = ": 原Profile禁用即删除成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag? new BaseResponseDto(true, "启用成功")
				: new BaseResponseDto(false, "启用失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "启用profile", "启用"));
		writeJSONResult(baseResponseDto, response);
	}
	
	public void enableProfileByHttps(EuiccProfileWithBLOBs profile)throws Exception{
        MsgHeader header = new MsgHeader("enableProfileByDp");
        EnableProfileByDpReqBody requestBody = new EnableProfileByDpReqBody();
		requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	/**
	 * 禁用profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/disableProfile", method = RequestMethod.POST)
	public void disableProfile(HttpServletRequest request,
			HttpServletResponse response, String iccid) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(iccid);
		BaseResponseDto baseResponseDto = null;
		
		// 删除
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(!StringUtils.equals(ProfileState.ENABLE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "请先启用profile");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.disableProfile(profile);
			}else{
				disableProfileByHttps(profile);
				if(isAutoDelete(iccid)){
					msg = ": 目标Profile禁用即删除成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "禁用成功")
				: new BaseResponseDto(false, "禁用失败" );
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "禁用profile", "禁用"));
		writeJSONResult(baseResponseDto, response);
	}
	
	public void disableProfileByHttps(EuiccProfileWithBLOBs profile)throws Exception{
        MsgHeader header = new MsgHeader("disableProfileByDp");
        DisableProfileByDpReqBody requestBody = new DisableProfileByDpReqBody();
        requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }

	}
	/**
	 * 查询profile的POL2是否为禁用即删除
	 * @param request
	 * @param response
	 * @param iccid
	 * @return
	 */
	@RequestMapping(value = "/getPOL2",method = RequestMethod.POST)
	public void getPOL2(HttpServletRequest request,
			HttpServletResponse response,String iccid){
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(iccid);
		String pol2Id = profile.getPol2Id();
		if(StringUtils.isBlank(pol2Id)){
			writeJSONResult(false,response);
		}else{
			EuiccPol2 pol2 = euiccPol2.selectByPrimaryKey(pol2Id);
			String action = pol2.getAction();
			String qualification=pol2.getQualification();
			if(StringUtils.equals(action, "DISABLE")&&StringUtils.equals(qualification, "Auto-delete")){
				writeJSONResult(true,response);
			}
			else{
				writeJSONResult(false,response);
			}
		}
	}
	/**
	 * 查询Profile状态
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/getStatus", method = RequestMethod.POST)
	public void getStatus(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		boolean flag = true;
		String msg = "";
		try {
			if(!CheckCallType.isLocalOperate() && 
					(StringUtils.equals(ProfileState.ENABLE,profile.getState()) 
							|| StringUtils.equals(ProfileState.DIS_ENABLE,profile.getState())
							|| StringUtils.equals(ProfileState.DELETE,profile.getState()) 
							|| StringUtils.equals(ProfileState.INSTALL_ISD_P_STATE_SUCCESS,profile.getState()))){
				getStatusByHttps(profile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
				msg = "连接服务异常";
			}else{
				msg = e.getMessage();
			}
		}
		EuiccProfileWithBLOBs record = euiccProfileService.selectByPrimaryKey(id);
		String status = record.getState();
		baseResponseDto = flag ? new BaseResponseDto(true, "状态查询成功,当前状态为： " + status)
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "查询profile状态", "状态查询"));
		writeJSONResult(baseResponseDto, response);
	}
	public void getStatusByHttps(EuiccProfileWithBLOBs profile)throws Exception{
        MsgHeader header = new MsgHeader("getStatusByDp");
        DisableProfileByDpReqBody requestBody = new DisableProfileByDpReqBody();
        requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	/**
	 * 短信方式启用profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/enableProfileBySms", method = RequestMethod.POST)
	public void enableProfileBySms(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		EuiccProfileWithBLOBs currentProfile = euiccProfileService.selectEuiccProfileByStateAndEid(ProfileState.ENABLE, profile.getEid());
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if((!StringUtils.equals(ProfileState.INSTALL_ISD_P_STATE_SUCCESS, profile.getState())&&(!StringUtils.equals(ProfileState.DIS_ENABLE, profile.getState())))){
					baseResponseDto = new BaseResponseDto(false, "请先下载或禁用profile");
					writeJSONResult(baseResponseDto, response);
				}
				if(StringUtils.equals(ProfileState.DELETE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已删除");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.enableProfile(profile);
			}else{
				enableProfileBySms(profile);
				if(isAutoDelete(currentProfile.getIccid())){
					msg = ": 原Profile禁用即删除成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag? new BaseResponseDto(true, "启用成功" + msg)
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "启用profile", "启用"));
		writeJSONResult(baseResponseDto, response);
	}
	
	
	private void enableProfileBySms(EuiccProfileWithBLOBs profile) throws Exception {
		MsgHeader header = new MsgHeader("enableProfileByDpSms");
        EnableProfileByDpSmsReqBody requestBody = new EnableProfileByDpSmsReqBody();
		requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	/**
	 * 短信方式禁用profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/disableProfileBySms", method = RequestMethod.POST)
	public void disableProfileBySms(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		
		// 删除
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(!StringUtils.equals(ProfileState.ENABLE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "请先启用profile");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.disableProfile(profile);
			}else{
				disableProfileBySms(profile);
				if(isAutoDelete(id)){
					msg = ": 目标Profile禁用即删除成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "禁用成功" + msg)
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "禁用profile", "禁用"));
		writeJSONResult(baseResponseDto, response);
	}

	private void disableProfileBySms(EuiccProfileWithBLOBs profile) throws Exception {
		MsgHeader header = new MsgHeader("disableProfileByDpSms");
        DisableProfileByDpSmsReqBody requestBody = new DisableProfileByDpSmsReqBody();
		requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	/**
	 * 短信方式删除profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/deleteProfileBySms", method = RequestMethod.POST)
	public void deleteProfileBySms(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		// 删除
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(StringUtils.equals(ProfileState.ENABLE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "请先禁用profile");
					writeJSONResult(baseResponseDto, response);
				}
				if(StringUtils.equals(ProfileState.DELETE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已删除");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.deleteProfile(profile);
			}else{
				profileDeleteBySms(profile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "删除成功")
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "删除profile", "删除"));
		writeJSONResult(baseResponseDto, response);
	}

	private void profileDeleteBySms(EuiccProfileWithBLOBs profile) throws Exception {
		MsgHeader header = new MsgHeader("deleteProfileByDpSms");
        DeleteProfileByDpSmsReqBody requestBody = new DeleteProfileByDpSmsReqBody();
		requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
		
	}
	
	/**
	 * 短信方式主删除profile
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/masterDeleteProfileBySms", method = RequestMethod.POST)
	public void masterDeleteBySms(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		
		// 删除
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(StringUtils.equals(ProfileState.DELETE, profile.getState())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已删除");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.deleteProfile(profile);
			}else{
				masterDeleteBySms(profile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "主删除成功")
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "主删除profile", "主删除"));
		writeJSONResult(baseResponseDto, response);
	}

	private void masterDeleteBySms(EuiccProfileWithBLOBs profile) throws Exception {
		MsgHeader header = new MsgHeader("masterDeleteByDpSms");
        MasterDeleteByDpReqBody requestBody = new MasterDeleteByDpReqBody();
		requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
		
	}
	
	/**
	 * 短信方式设置回滚属性
	 * 
	 * @param request
	 * @param response
	 * @param moduleId
	 *            模块id
	 * @throws java.io.IOException
	 */
	@RequestMapping(value = "/setFallBackAttrBySms", method = RequestMethod.POST)
	public void setFallBackAttrBySms(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(id);
		BaseResponseDto baseResponseDto = null;
		
		// 设置回滚属性
		boolean flag = true;
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				if(StringUtils.equals(ProfileFallBack.FALLBACK_YES, profile.getFallbackAttribute())){
					baseResponseDto = new BaseResponseDto(false, "当前profile已设置回滚属性");
					writeJSONResult(baseResponseDto, response);
				}
				euiccProfileService.updateByPrimaryKeySelective(profile);
			}else{
				setFallBackAttrBySms(profile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "设置回滚属性成功")
				: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(profile), "", "profile设置回滚属性", "回滚属性"));
		writeJSONResult(baseResponseDto, response);
	}

	private void setFallBackAttrBySms(EuiccProfileWithBLOBs profile) throws Exception {
		MsgHeader header = new MsgHeader("setFallBackAttrByDpAndSms");
        SetFallBackByDpAndSmsReqBody requestBody = new SetFallBackByDpAndSmsReqBody();
		requestBody.setEid(profile.getEid());
		requestBody.setIccid(profile.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
		
	}
	
	private String getObjectData(EuiccProfile profile) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("EuiccProfile[");
		sBuffer.append("id=");
		sBuffer.append(profile.getEid());
		sBuffer.append(", ");
		sBuffer.append("iccid=");
		sBuffer.append(profile.getIccid());
		sBuffer.append("]");
		return sBuffer.toString();
	}
	private boolean isAutoDelete(String iccid){
		EuiccProfileWithBLOBs record = euiccProfileService.selectByPrimaryKey(iccid);
		if(!StringUtils.equals(record.getState() ,ProfileState.DELETE)){
			return false;
		}
		//根据给定的eid若检查POL2策略为禁用即删除，则执行删除动作
		String pol2Id = record.getPol2Id();
		if(StringUtils.isBlank(pol2Id))return false;
		EuiccPol2 pol2 = euiccPol2.selectByPrimaryKey(pol2Id);
		String action = pol2.getAction();
		String qualification=pol2.getQualification();
		//判断是否为“禁用即删除”
		if(StringUtils.equals(action, "DISABLE")&&StringUtils.equals(qualification, "Auto-delete")){
			return true;
		}
		return false;
	}
}
