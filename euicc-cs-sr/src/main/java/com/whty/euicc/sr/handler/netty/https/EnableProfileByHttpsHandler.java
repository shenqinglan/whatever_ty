package com.whty.euicc.sr.handler.netty.https;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.PorNoticeType;
import com.whty.euicc.common.constant.ProfileFallBack;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccPol2Service;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.DeleteProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.EnableProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.GetStatusByHttpsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.sr.handler.netty.notify.ES4Notification;
import com.whty.euicc.trigger.SmsTriggerUtil;


/**
 * Https下SM-SR的启用Profile服务业务
 * @author Administrator
 *
 */
@Service("enableProfileByHttps")
public class EnableProfileByHttpsHandler implements HttpHandler{
	
	private Logger logger = LoggerFactory.getLogger(EnableProfileByHttpsHandler.class);
	
	@Autowired
	private EuiccProfileService profileService;
	
	@Autowired
	private EuiccCardService cardService;
	
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Autowired
	private EuiccPol2Service euiccPol2;
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		EnableProfileByHttpsReqBody reqBody = (EnableProfileByHttpsReqBody) reqMsgObject.getBody();
		//获取启用Profile请求参数
		EuiccProfileWithBLOBs targetProfile = getProfile(reqBody.getIccid());
		//获取当前Profile参数，从数据库中获取
		EuiccProfileWithBLOBs currentProfile = profileService.selectEuiccProfileByStateAndEid(ProfileState.ENABLE,targetProfile.getEid());
		//更新本业务相关的两个Profile状态
		if(StringUtils.equals(ProfileState.ENABLE,targetProfile.getState()) 
				||  StringUtils.equals(ProfileState.DIS_ENABLE,targetProfile.getState())
				||  StringUtils.equals(ProfileState.DELETE,targetProfile.getState())
				){
			getStatusByHttps(targetProfile);
			getStatusByHttps(currentProfile);
			targetProfile = getProfile(reqBody.getIccid());//再次获取卡profile状态，通过getState命令可能更新过数据库卡状态
			currentProfile = getProfile(currentProfile.getIccid());
		}
		checkInitialConditions(targetProfile, currentProfile);
		reqBody.setIsdPAid(targetProfile.getIsdPAid());
		reqBody.setIccid(reqBody.getIccid());
		reqBody.setNoticeType(PorNoticeType.ENABLE);
		
		smsTriggerUtil.sendTriggerSms(reqBody,"enableProfileApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
			return new Gson().toJson(respMessage).getBytes();
		}
		try {
			profileService.enableProfile(targetProfile);
		} catch (Exception e) {
			String msg = "";
			e.printStackTrace();
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
			respMessage = new RespMessage(ErrorCode.FAILURE,msg);
			return new Gson().toJson(respMessage).getBytes();
		}
		//更新状态以免euicc中Profile回滚或者已删除（本应通知确认时判断场景）
		//getStatusByHttps(targetProfile);
		//getStatusByHttps(currentProfile);
		//targetProfile = getProfile(targetProfile.getIccid());
		currentProfile = getProfile(currentProfile.getIccid());
		/*if(StringUtils.equals(ProfileState.DIS_ENABLE,targetProfile.getState()) && 
				StringUtils.equals(ProfileState.ENABLE,currentProfile.getState())){
			throw new EuiccBusiException("8010","The target Profile has rolled back");
		}*/
		//检查POL2
		if((checkPol2(currentProfile)) && 
				!(StringUtils.equals(ProfileState.DELETE,currentProfile.getState()))){
			currentProfile = profileService.selectByPrimaryKey(currentProfile.getIccid());
 			DeleteProfileByHttpsReqBody reqBody2 = new DeleteProfileByHttpsReqBody();
			if(!StringUtils.equals(ProfileState.DIS_ENABLE,currentProfile.getState())){
				throw new EuiccBusiException("8010","The current Profile is not in the disabled state");
			}
			if(StringUtils.equals(ProfileFallBack.FALLBACK_YES,currentProfile.getFallbackAttribute())){
				throw new EuiccBusiException("8010","The current Profile has the Fall-back Attribute set");
			}
			reqBody2.setEid(currentProfile.getEid());
			reqBody2.setIsdPAid(currentProfile.getIsdPAid());
			reqBody2.setIccid(currentProfile.getIccid());
			reqBody2.setNoticeType(PorNoticeType.DELETE);
		
			smsTriggerUtil.sendTriggerSms(reqBody2,"deleteProfileApdu");
			smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
			if(!smsTrigger.isProcessResult()){
				String error = smsTrigger.getErrorMsg();
				respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
				return new Gson().toJson(respMessage).getBytes();
			}
		}
		//通知原MNO
		try {
			ES4Notification notify = new ES4Notification();
			System.out.println("currentProfile.getEid()" + currentProfile.getEid());
			System.out.println("currentProfile.getIccid()" + currentProfile.getIccid());
			notify.handleNotifyInEnableProfile(currentProfile.getEid(), currentProfile.getIccid(), currentProfile.getState());
		} catch (Exception e) {
			String msg = "";
			e.printStackTrace();
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
			respMessage = new RespMessage(ErrorCode.FAILURE,msg);
			return new Gson().toJson(respMessage).getBytes();
		}
		return new Gson().toJson(respMessage).getBytes();
	}
	
	/**
	 * 由ICCID确认的目标Profile在eUICC上已加载，检查Profile状态
	 * @param targetProfile
	 */
	public void getStatusByHttps(EuiccProfileWithBLOBs targetProfile){
		GetStatusByHttpsReqBody reqBody = new GetStatusByHttpsReqBody();
		reqBody.setEid(targetProfile.getEid());
		reqBody.setIsdPAid(targetProfile.getIsdPAid());
		reqBody.setIccid(targetProfile.getIccid());
		smsTriggerUtil.sendTriggerSms(reqBody,"getStatusApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			throw new EuiccBusiException("8010","The state of queried Profile has exception");
		}
	}
	
	/**
	 * 检查初始条件                    
	 */
	private void checkInitialConditions(EuiccProfileWithBLOBs targetProfile, EuiccProfileWithBLOBs currentProfile){
		//查询数据库（card），检查是否包含该eid
		EuiccCard record = cardService.selectByPrimaryKey(targetProfile.getEid());
		if (record == null) {
			logger.info("This eid :"+ targetProfile.getEid() +"is not exists");
			throw new EuiccBusiException("8010", "SM-SR is not responsible for the euicc card!");
		}
		//目标Profile属于请求发起方MNO(检查mno-id?)
		
		//目标Profile的状态为禁用状态
		if(!(StringUtils.equals(ProfileState.DIS_ENABLE,targetProfile.getState()) 
				|| StringUtils.equals(ProfileState.INSTALL_ISD_P_STATE_SUCCESS,targetProfile.getState()))){
			throw new EuiccBusiException("8010","The target Profile is not in the disabled state");
		}
		//根据给定的eid若检查POL2策略为不允许禁用，则不启用
		String pol2Id = currentProfile.getPol2Id();
		if(StringUtils.isBlank(pol2Id))return;
		EuiccPol2 pol2 = euiccPol2.selectByPrimaryKey(pol2Id);
		String action = pol2.getAction();
		String qualification=pol2.getQualification();
		//判断是否为“不允许禁用”
		if(StringUtils.equals(action, "DISABLE")&&StringUtils.equals(qualification, "Not allowed")){
			throw new EuiccBusiException("8010","The current Profile POL2 Policy does not allowed disable");
		}
	}
		
	
	private boolean checkPol2(EuiccProfileWithBLOBs profile) {
		//根据给定的eid若检查POL2策略为禁用即删除，则执行删除动作
		String pol2Id = profile.getPol2Id();
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

	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}
	
}
