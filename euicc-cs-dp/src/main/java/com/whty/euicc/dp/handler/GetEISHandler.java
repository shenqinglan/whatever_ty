package com.whty.euicc.dp.handler;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.GetEISByDpReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.RetrieveEISReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;

@Service("getEISByDp")
public class GetEISHandler implements HttpHandler{

	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		byte[] EIS =null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			GetEISByDpReqBody reqBody = (GetEISByDpReqBody) reqMsgObject.getBody();
			EIS = GetEIS(reqBody.getEid());
		} catch (Exception e) {
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
		}
		return EIS;
	}

	private byte[] GetEIS(String eid) throws Exception {
		MsgHeader header = new MsgHeader("retrieveEISBySr");
        RetrieveEISReqBody requestBody = new RetrieveEISReqBody();
		requestBody.setEid(eid);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		return msgBype;
	}

}
