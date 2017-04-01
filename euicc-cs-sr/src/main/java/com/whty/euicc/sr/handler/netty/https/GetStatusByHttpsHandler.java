package com.whty.euicc.sr.handler.netty.https;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
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
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;


/**
 * Https下SM-SR的状态查询服务业务
 * @author Administrator
 *
 */
@Service("getStatusByHttps")
public class GetStatusByHttpsHandler implements HttpHandler{
	private Logger logger = LoggerFactory.getLogger(GetStatusByHttpsHandler.class);
	@Autowired
	private EuiccCardService cardService;
	
	@Autowired
	private EuiccProfileService profileService;

	
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		GetStatusByHttpsReqBody reqBody = (GetStatusByHttpsReqBody) reqMsgObject.getBody();
		SmsTrigger smsTrigger = null;
		if (StringUtils.isNotBlank(reqBody.getIccid())) {
			EuiccProfileWithBLOBs targetProfile = getProfile(reqBody.getIccid());
			reqBody.setIsdPAid(targetProfile.getIsdPAid());
			reqBody.setIccid(targetProfile.getIccid());
			
			smsTriggerUtil.sendTriggerSms(reqBody,"getStatusApdu");
			smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
			
			if(!smsTrigger.isProcessResult()){
				String error = smsTrigger.getErrorMsg();
				respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
				return new Gson().toJson(respMessage).getBytes();
			}
			String respMsg = new Gson().toJson(respMessage);
			logger.info("--------------getStatus返回结果:{}",respMsg);
			return respMsg.getBytes();
			
		}else { //批量查询profile状态
			for(String iccid : reqBody.getIccidList()){
				EuiccProfileWithBLOBs targetProfile = getProfile(iccid);
				reqBody.setIsdPAid(targetProfile.getIsdPAid());
				reqBody.setIccid(targetProfile.getIccid());
				
				smsTriggerUtil.sendTriggerSms(reqBody,"getStatusApdu");
				smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
				
				if(!smsTrigger.isProcessResult()){
					String error = smsTrigger.getErrorMsg();
					respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
					return new Gson().toJson(respMessage).getBytes();
				}
			}
			//获取EIS
			EuiccCard card =  cardService.selectByPrimaryKey(reqBody.getEid());
			String eis = new Gson().toJson(card);
			return eis.getBytes();
		}
	}
	
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}
}
