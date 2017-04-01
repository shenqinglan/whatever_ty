package com.whty.euicc.dp.handler.sms;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.DisableProfileByDpSmsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;

/**
 * 
 * <p>Title: DisableProfileBySmsHandler</p>
 * <p>@author yex</p>
 * <p>@description: dp短信短信方式启用Profile</p>
 * <p>@date: 2017-1-23 下午1:26:52</p>
 * 
 */
@Service("disableProfileByDpSms")
public class DisableProfileBySmsHandler implements HttpHandler {

	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		DisableProfileByDpSmsReqBody reqBody = null;
		
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (DisableProfileByDpSmsReqBody) reqMsgObject.getBody();
			disableProfile(reqBody);
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
		return new Gson().toJson(respMessage).getBytes() ;
	}

	/**
	 * 调用sr，通过短信方式启用Profile
	 * @param reqBody
	 * @throws Exception
	 */
	private void disableProfile(DisableProfileByDpSmsReqBody reqBody) throws Exception {
		MsgHeader header = new MsgHeader("disableProfileBySms");
		EuiccMsg<DisableProfileByDpSmsReqBody> euiccMsg = new EuiccMsg<DisableProfileByDpSmsReqBody>(header, reqBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
      if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
      	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
      }
	}

}
