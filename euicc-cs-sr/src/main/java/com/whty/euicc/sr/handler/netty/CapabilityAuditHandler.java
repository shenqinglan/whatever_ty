package com.whty.euicc.sr.handler.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.CapabilityAuditReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * 卡片能力审计之getData
 * 见SGP.02 4.1.1.5
 * @author Administrator
 *
 */
@Service("capabilityAudit")
public class CapabilityAuditHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(CapabilityAuditHandler.class);

	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		CapabilityAuditReqBody reqBody = (CapabilityAuditReqBody)reqMsgObject.getBody();
		
		smsTriggerUtil.sendTriggerSms(reqBody,"capabilityAuditApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			RespMessage respMessage = new RespMessage(ErrorCode.FAILURE,"error");
			return new Gson().toJson(respMessage).getBytes();
		}
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success",smsTrigger.getIsdPAid());
		return new Gson().toJson(respMessage).getBytes();
	}

}
