package com.whty.euicc.dp.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.DeleteProfileByDpReqBody;
import com.whty.euicc.packets.message.request.DeleteProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.common.https.BaseHttp;
/**
 * Https下SM-DP的删除Profile服务业务
 * @author Administrator
 *
 */
@Service("profileDeletionByDp")
public class ProfileDeletionHandler implements HttpHandler {

	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		DeleteProfileByDpReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (DeleteProfileByDpReqBody) reqMsgObject.getBody();
			profileDeleteByHttps(reqBody);
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
	
	/**
	 * 删除isd-p和profile
	 * @param reqBody
	 * @throws Exception 
	 */
	public void profileDeleteByHttps(DeleteProfileByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("deleteProfileByHttps");
        DeleteProfileByHttpsReqBody requestBody = new DeleteProfileByHttpsReqBody();
        requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setErrorDeal(false);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
}
