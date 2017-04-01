/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-7
 * Id: SetFallBackAttribute.java,v 1.0 2016-11-7 上午9:47:36 Administrator
 */
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
import com.whty.euicc.packets.message.request.SetFallBackAttrByDpReqBody;
import com.whty.euicc.packets.message.request.SetFallBackAttrByHttpsReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;

/**
 * @ClassName SetFallBackAttribute
 * @author Administrator
 * @date 2016-11-7 上午9:47:36
 * @Description TODO(DP模块设置回滚属性)
 */
@Service("setFallBackAttrByDp")
public class SetFallBackAttrHandle implements HttpHandler {
	
	Logger logger = LoggerFactory.getLogger(SetFallBackAttrHandle.class);

	/** 
	 * @author Administrator
	 * @date 2016-11-7
	 * @param requestStr
	 * @return
	 * @Description TODO(DP设置回滚属性)
	 * @see com.whty.euicc.handler.base.HttpHandler#handle(java.lang.String)
	 */
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		SetFallBackAttrByDpReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (SetFallBackAttrByDpReqBody) reqMsgObject.getBody();
			
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
	
	public void SetFallBackAttrByDp(SetFallBackAttrByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("setFallBackAttrByHttps");
        SetFallBackAttrByHttpsReqBody requestBody = new SetFallBackAttrByHttpsReqBody();
        
        // 封装 isdpAid 参数和  eid参数
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		
		logger.info("封装请求参数： "+"eid>>"+reqBody.getEid()+" isdpAid>>"+reqBody.getIsdPAid());
		
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
}
