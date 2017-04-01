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
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.GetStatusByHttpsReqBody;
import com.whty.euicc.packets.message.request.MasterDeleteByHttpsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * Https下SM-SR的主删除服务业务
 * @author Administrator
 *
 */
@Service("masterDeleteByHttps")
public class MasterDeleteByHttpsHandler implements HttpHandler{
	private Logger logger = LoggerFactory.getLogger(MasterDeleteByHttpsHandler.class);

	@Autowired
	private EuiccProfileService profileService;
	
	@Autowired
	private EuiccCardService cardService;
	
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		MasterDeleteByHttpsReqBody reqBody = (MasterDeleteByHttpsReqBody) reqMsgObject.getBody();
		//获取启用Profile请求参数
		EuiccProfileWithBLOBs targetProfile = getProfile(reqBody.getIccid());
		getStatusByHttps(targetProfile);
		targetProfile = getProfile(reqBody.getIccid());//再次获取卡profile状态，通过getState命令可能更新过数据库卡状态
		checkInitialConditions(targetProfile);
		reqBody.setIsdPAid(targetProfile.getIsdPAid());
		reqBody.setIccid(targetProfile.getIccid());
		reqBody.setNoticeType(PorNoticeType.DELETE);
		smsTriggerUtil.sendTriggerSms(reqBody,"masterDeleteApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
			return new Gson().toJson(respMessage).getBytes();
		}
		return new Gson().toJson(respMessage).getBytes();
	}
	
	/**
	 * 检查初始条件                    
	 */
	private void checkInitialConditions(EuiccProfileWithBLOBs profile){
		//查询数据库（card），检查是否包含该eid
		EuiccCard record = cardService.selectByPrimaryKey(profile.getEid());
		if (record == null) {
			logger.info("This eid :"+ profile.getEid() +"is not exists");
			throw new EuiccBusiException("8010", "SM-SR is not responsible for the euicc card!");
		}
		//目标Profile属于请求发起方MNO(检查mno-id?)
		
		//目标Profile的状态为禁用状态
		if((!StringUtils.equals(ProfileState.DIS_ENABLE,profile.getState()))&&(!StringUtils.equals(ProfileState.INSTALL_ISD_P_STATE_SUCCESS,profile.getState()))){
			throw new EuiccBusiException("8010","The target Profile is not in the disabled state");
		}
		//目标Profile不是具有Fall-back属性的Profile
		if(StringUtils.equals(ProfileFallBack.FALLBACK_YES,profile.getFallbackAttribute())){
			throw new EuiccBusiException("8010","The target Profile has the Fall-back Attribute set");
		}
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
	
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}
	
}
