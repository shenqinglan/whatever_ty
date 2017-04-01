package com.whty.euicc.data.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.common.utils.CheckCallType;
import com.whty.euicc.data.constant.CertConstant;
import com.whty.euicc.data.constant.ProfileState;
import com.whty.euicc.data.dao.EuiccIsdRMapper;
import com.whty.euicc.data.message.EuiccMsg;
import com.whty.euicc.data.message.MsgHeader;
import com.whty.euicc.data.message.request.ConnectParaUpdateByDpReqBody;
import com.whty.euicc.data.message.request.CreateISDPReqBody;
import com.whty.euicc.data.message.request.PersonalISDPReqBody;
import com.whty.euicc.data.message.request.UpdateSrAddressParaByDPReqBody;
import com.whty.euicc.data.pojo.EuiccIsdR;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.data.message.request.InstallProfileReqBody;
/**
 * profile下载安装
 * @author Administrator
 *
 */
@Controller
@RequestMapping("installProfile")
public class InstallProfileController  extends BaseController{
	
	@Autowired
	private BaseLogsService baseLogsService;

	@Autowired
	private EuiccProfileService euiccProfileService;
	
	@Autowired
	private EuiccIsdRMapper isdrMapper;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/install/installProfileUI";
	}
	
	/**
	 * 安装profile
	 * @param request
	 * @param response
	 * @param id
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/install", method = RequestMethod.POST)
	public void install(HttpServletRequest request,
			HttpServletResponse response, String iccid,String eid) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfile profile = euiccProfileService.selectByPrimaryKey(iccid);
		BaseResponseDto baseResponseDto = null;
		
		
		// 安装
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setIccid(profile.getIccid());
		record.setEid(eid);
		record.setState(ProfileState.PROCESS_WAITING);
		int flag = euiccProfileService.updateByPrimaryKeySelective(record);
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "安装处理中")
				: new BaseResponseDto(false, "安装失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(record), "", "安装profile", "安装"));
		writeJSONResult(baseResponseDto, response);
	}
	
	/**
	 * 创建ISD-P
	 * @param request
	 * @param response
	 * @param id
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/createIsdP", method = RequestMethod.POST)
	public void createIsdP(HttpServletRequest request,
			HttpServletResponse response, String iccid,String eid) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfile profile = euiccProfileService.selectByPrimaryKey(iccid);
		BaseResponseDto baseResponseDto = null;
		
		
		// 安装
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setIccid(profile.getIccid());
		record.setEid(eid);
		record.setState(ProfileState.CREATE_ISD_P_STATE_SUCCESS);
		
		boolean flag = true;
		String msg = "";
		System.out.println("CheckCallType.isLocalOperate(): " + CheckCallType.isLocalOperate());
		System.out.println("eid: " + eid);
		try {
			if(CheckCallType.isLocalOperate()){
				euiccProfileService.createIsdP(record);//db
			}else{
				createISDP(record);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "创建ISD-P成功")
		: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(record), "", "创建ISD-P", "创建ISD-P"));
		writeJSONResult(baseResponseDto, response);
	}
	
	public void createISDP(EuiccProfileWithBLOBs record)throws Exception{
		System.out.println("eid222: " + record.getEid());
        MsgHeader header = new MsgHeader("createISDPByDp");
        CreateISDPReqBody requestBody = new CreateISDPReqBody();
		requestBody.setEid(record.getEid());
		requestBody.setIccid(record.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	/**
	 * 双向认证
	 * @param request
	 * @param response
	 * @param id
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/personal", method = RequestMethod.POST)
	public void personal(HttpServletRequest request,
			HttpServletResponse response, String iccid,String eid) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfile profile = euiccProfileService.selectByPrimaryKey(iccid);
		BaseResponseDto baseResponseDto = null;
		
		
		// 安装
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setIccid(profile.getIccid());
		record.setEid(eid);
		//record.setState(ProfileState.PERSONAL_ISD_P_STATE_SUCCESS);
		int flag = euiccProfileService.updateByPrimaryKeySelective(record);

		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				//euiccProfileService.personalAllIsdP(record);
			}else{
				personalAllIsdP(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
		}
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "双向认证成功")
				: new BaseResponseDto(false, "双向认证失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(record), "", "双向认证", "双向认证"));
		writeJSONResult(baseResponseDto, response);
	}

	public void personalAllIsdP(EuiccProfileWithBLOBs record) throws Exception {
		String certDpEcdsa = CertConstant.DP_CERT;
		MsgHeader header = new MsgHeader("personalAllISDP");
		PersonalISDPReqBody requestBody = new PersonalISDPReqBody();
		requestBody.setEid(record.getEid());
		requestBody.setIccid(record.getIccid());
		requestBody.setCertDpEcdsa(certDpEcdsa);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	/**
	 * profile下载安装 
	 * @param request
	 * @param response
	 * @param id
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadProfile", method = RequestMethod.POST)
	public void downloadProfile(HttpServletRequest request,
			HttpServletResponse response, String iccid,String eid) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(iccid);
		BaseResponseDto baseResponseDto = null;
		// 安装
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setIccid(profile.getIccid());
		record.setEid(eid);
		record.setIsdPAid(profile.getIsdPAid());
		record.setState(ProfileState.INSTALL_ISD_P_STATE_SUCCESS);
		record.setDerFile(profile.getDerFile());
		int flag = 1; 
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
				flag = euiccProfileService.updateByPrimaryKeySelective(record);
			}else{
				installProfile(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
		}
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "下载安装成功 ")
				: new BaseResponseDto(false, "安装失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(record), "", "安装profile", "安装"));
		writeJSONResult(baseResponseDto, response);
	}
	public void installProfile(EuiccProfileWithBLOBs record)throws Exception{
		MsgHeader header = new MsgHeader("installProfileByDp");
		InstallProfileReqBody requestBody = new InstallProfileReqBody();
		requestBody.setEid(record.getEid());
		requestBody.setIccid(record.getIccid());
		requestBody.setIsdPAid(record.getIsdPAid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	
	}
	/**
	 * 查询当前profile的短信中心号码
	 * @param request
	 * @param response
	 * @param iccid
	 * @return
	 */
	@RequestMapping(value = "/getCurrSmsCenterNo",method = RequestMethod.POST)
	public void getCurrSmsCenterNo(HttpServletRequest request,
			HttpServletResponse response,String iccid){
		EuiccProfileWithBLOBs profile = euiccProfileService.selectByPrimaryKey(iccid);
		String smsCenterNo = profile.getSmscenterNo();
		writeJSONResult(smsCenterNo,response);
	}
	/**
	 * 更新连接参数
	 * @param request
	 * @param response
	 * @param iccid
	 * @param eid
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateconnetivityPara", method = RequestMethod.POST)
	public void updateconnParas(HttpServletRequest request,
			HttpServletResponse response,String eid,String iccid,String smsCenterNo) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		BaseResponseDto baseResponseDto = null;

		// 安装
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setEid(eid);
		record.setIccid(iccid);
		record.setSmscenterNo(smsCenterNo);
		int flag = 1; 
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
			    euiccProfileService.updateConnectParas(record);
			}else{
				connectParaUpdate(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
		}
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "SCP03更新连接参数成功 ")
				: new BaseResponseDto(false, "SCP03更新连接参数失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(record), "", "SCP03更新连接参数", "更新"));
		writeJSONResult(baseResponseDto, response);
	}
	
	public void connectParaUpdate(EuiccProfileWithBLOBs record) throws Exception{
		MsgHeader header = new MsgHeader("connectParaUpdateByDp");
		ConnectParaUpdateByDpReqBody requestBody = new ConnectParaUpdateByDpReqBody();
		requestBody.setEid(record.getEid());
		requestBody.setIccid(record.getIccid());
		requestBody.setSmsCenterNo(record.getSmscenterNo());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
		
	}

	/**
	 * 更新SMSR地址参数
	 * @param request
	 * @param response
	 * @param isdPAid
	 * @param srAddressPara
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateSrAddress", method = RequestMethod.POST)
	public void updateSrAddress(HttpServletRequest request,
			HttpServletResponse response,/* String iccid,*/String eid,String srAddress) throws IOException {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccIsdR isdR = isdrMapper.selectByEid(eid);
		if(StringUtils.equals(isdR.getrId(),"")){
			throw new EuiccBusiException("8010","The target isdR is not existence");
		}
		BaseResponseDto baseResponseDto = null;
		// 安装
		EuiccIsdR record = new EuiccIsdR();
		record.setrId(isdR.getrId());
		record.setIsdRAid(isdR.getIsdRAid());
		record.setSrAddressPara(srAddress);
		System.out.println("isdR.getrId():"+isdR.getrId());
		System.out.println("isdR.getIsdRAid():"+isdR.getIsdRAid());
		System.out.println("srAddress from input:"+srAddress);
		
		//将新的短信目的地址放进数据库
		String srAddrNumber = isdR.getSrAddressPara();
		if (!StringUtils.equals(srAddress, srAddrNumber)) {
			isdrMapper.updateByPrimaryKeySelective(record);
		}
				
		int flag = 1; 
		String msg = "";
		try {
			if(CheckCallType.isLocalOperate()){
			}else{
				updateSrAddressParaByDP(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = 0;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
		}
		baseResponseDto = flag > 0 ? new BaseResponseDto(true, "更新成功 ")
				: new BaseResponseDto(false, "更新失败");
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
				"2", Constant.EUICC_BASE_TYPE, "EUICC_PROFILE",
				getObjectData(record), "", "更新SMSR地址参数", "更新"));
		writeJSONResult(baseResponseDto, response);
	}
	public void updateSrAddressParaByDP(EuiccIsdR record)throws Exception{
		
		MsgHeader header = new MsgHeader("updateSrAddressParaByDP");
		UpdateSrAddressParaByDPReqBody requestBody = new UpdateSrAddressParaByDPReqBody();
		requestBody.setIsdRAid(record.getIsdRAid());
		requestBody.setSrAddressPara(record.getSrAddressPara());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	
	}
	/**
	 * 查询数据库中SMSR目的地址
	 * @param request
	 * @param response
	 * @param iccid
	 */
	@RequestMapping(value = "/getCurrSrAddressNo",method = RequestMethod.POST)
	public void getCurrSrAddressNo(HttpServletRequest request,
			HttpServletResponse response,String eid){
		EuiccIsdR isdR = isdrMapper.selectByEid(eid);
		String srAddress = isdR.getSrAddressPara();
		writeJSONResult(srAddress,response);
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
	
	private String getObjectData(EuiccIsdR isdR) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("EuiccIsdR[");
		sBuffer.append("id=");
		sBuffer.append(isdR.getrId());
		sBuffer.append(", ");
		sBuffer.append("isdRAid=");
		sBuffer.append(isdR.getIsdRAid());
		sBuffer.append("]");
		return sBuffer.toString();
	}
}
