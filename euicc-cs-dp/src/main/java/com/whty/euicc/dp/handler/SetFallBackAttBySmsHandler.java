package com.whty.euicc.dp.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.SetFallBackAttrBySmsReqBody;
import com.whty.euicc.packets.message.request.SetFallBackByDpAndSmsReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;

@Service("setFallBackAttrByDpAndSms")
public class SetFallBackAttBySmsHandler implements HttpHandler {
	Logger logger = LoggerFactory.getLogger(SetFallBackAttBySmsHandler.class);

	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		SetFallBackByDpAndSmsReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (SetFallBackByDpAndSmsReqBody) reqMsgObject.getBody();
			
			// 设置回滚属性
			SetFallBackAttrByDp(reqBody);
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

	private void SetFallBackAttrByDp(SetFallBackByDpAndSmsReqBody reqBody) throws Exception {
		MsgHeader header = new MsgHeader("setFallBackAttrBySms");
		SetFallBackAttrBySmsReqBody requestBody = new SetFallBackAttrBySmsReqBody();
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
