package com.whty.euicc.dp.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.data.service.EuiccPol2Service;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.UpdatePolilcyRulesByDPReqBody;
import com.whty.euicc.packets.message.request.UpdatePolilcyRulesReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.common.https.BaseHttp;

/**
 * 更新POL2策略规则
 * 
 * @author Administrator
 * 
 */
@Service("updatePolicyRulesByDP")
public class UpdatePolicyRulesHandler implements HttpHandler {
	private Logger logger = LoggerFactory
			.getLogger(UpdatePolicyRulesHandler.class);

	@Override
	public byte[] handle(String requestStr) {
		logger.info("pol2策略更新");
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS, "success");
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse()
					.toEuiccMsg(requestStr.getBytes());
			UpdatePolilcyRulesByDPReqBody reqBody = (UpdatePolilcyRulesByDPReqBody) reqMsgObject
					.getBody();
			updatePolicyRules(reqBody);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			if (e instanceof EuiccBusiException) {
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(), eb.getMessage());
			} else {
				respMessage = new RespMessage("9999", e.getMessage());
			}
		}
		return new Gson().toJson(respMessage).getBytes();
	}

	private void updatePolicyRules(UpdatePolilcyRulesByDPReqBody reqBody) throws Exception {
		MsgHeader header = new MsgHeader("updatePolicyRules");
		UpdatePolilcyRulesReqBody requestBody = new UpdatePolilcyRulesReqBody();
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setSmSrId(reqBody.getSmSrId());
		requestBody.setPol2Rules(reqBody.getPol2Rules());
		logger.info("action:"+ reqBody.getPol2Rules().getAction());
		logger.info("subject:"+reqBody.getPol2Rules().getSubject());
		logger.info("qualification:" +reqBody.getPol2Rules().getQualification());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}

}
