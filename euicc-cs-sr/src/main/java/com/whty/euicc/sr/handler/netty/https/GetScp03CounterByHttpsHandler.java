package com.whty.euicc.sr.handler.netty.https;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.GetScp03CounterByHttpsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;
/**
 * https 下SM-SR获取scp03计数器的服务业务
 * @author Administrator
 *
 */
@Service("getScp03CounterByHttps")
public class GetScp03CounterByHttpsHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(GetScp03CounterByHttpsHandler.class);

	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Autowired
	private EuiccProfileService profileService;
	
	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		GetScp03CounterByHttpsReqBody reqBody = (GetScp03CounterByHttpsReqBody)reqMsgObject.getBody();
		EuiccProfileWithBLOBs targetProfile = getProfile(reqBody.getIccid());
		reqBody.setIsdPAID(targetProfile.getIsdPAid());
		smsTriggerUtil.sendTriggerSms(reqBody, "getScp03CounterApdu");
        SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		
        if(!smsTrigger.isProcessResult()){
        	String error = smsTrigger.getErrorMsg();
			RespMessage	respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
			return new Gson().toJson(respMessage).getBytes();
		}
		String key = "scp03-recept-"+reqBody.getEid();
		String recept = CacheUtil.getString(key);
		CacheUtil.remove(key);
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success",recept);
		return new Gson().toJson(respMessage).getBytes();
		
	}
	
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}

}
