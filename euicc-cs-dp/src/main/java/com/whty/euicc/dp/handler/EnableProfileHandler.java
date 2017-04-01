package com.whty.euicc.dp.handler;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.dp.handler.notify.ES2Notification;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.EnableProfileByDpReqBody;
import com.whty.euicc.packets.message.request.EnableProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * Https下SM-DP的启用Profile服务业务
 * @author Administrator
 *
 */
@Service("enableProfileByDp")
public class EnableProfileHandler implements HttpHandler{

	@Autowired
	private EuiccProfileService profileService;
	
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		EnableProfileByDpReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (EnableProfileByDpReqBody) reqMsgObject.getBody();
			EuiccProfileWithBLOBs currentProfile = profileService.selectEuiccProfileByStateAndEid(ProfileState.ENABLE, reqBody.getEid());
			enableProfileByHttps(reqBody);
			currentProfile = profileService.selectByPrimaryKey(currentProfile.getIccid());
			ES2Notification notify = new ES2Notification();
			notify.handleNotifyInEnableProfile(currentProfile.getEid(), currentProfile.getIccid(), currentProfile.getState());
		} catch (Exception e) {
			//返回失败信息
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
			return new Gson().toJson(respMessage).getBytes();
		}
		
		return new Gson().toJson(respMessage).getBytes();

	
	}

	
	public void enableProfileByHttps(EnableProfileByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("enableProfileByHttps");
        EnableProfileByHttpsReqBody requestBody = new EnableProfileByHttpsReqBody();
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
}
