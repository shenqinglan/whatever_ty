package com.whty.euicc.sr.handler.netty.https;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.ConnectParaUpdateByHttpsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * https下SM-SR更新连接参数的服务业务
 * 
 * @author Administrator
 *
 */
@Service("connectParaUpdateByHttps")
public class ConnectParaUpdateByHttpsHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(ConnectParaUpdateByHttpsHandler.class);

	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Autowired
	private EuiccProfileService profileService;
	
	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		ConnectParaUpdateByHttpsReqBody reqBody = (ConnectParaUpdateByHttpsReqBody)reqMsgObject.getBody();
		EuiccProfileWithBLOBs targetProfile = getProfile(reqBody.getIccid());
		reqBody.setIsdPAID(targetProfile.getIsdPAid());
		smsTriggerUtil.sendTriggerSms(reqBody, "connectParaUpdateByHttpsApdu");
        SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
		}
		String respMsg = new Gson().toJson(respMessage);
		logger.info("--------------update connectivity Parameters返回结果:{}",respMsg);
		return respMsg.getBytes();
	}
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}

}
