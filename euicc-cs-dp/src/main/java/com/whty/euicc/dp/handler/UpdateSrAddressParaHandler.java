package com.whty.euicc.dp.handler;

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
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.UpdateSrAddressParaByDPReqBody;
import com.whty.euicc.packets.message.request.UpdateSrAddressParaByHttpsReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;

/**
 * Https下SM-DP的更新SMSR地址参数服务业务
 * @author Administrator
 *
 */
@Service("updateSrAddressParaByDP")
public class UpdateSrAddressParaHandler implements HttpHandler {
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		UpdateSrAddressParaByDPReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (UpdateSrAddressParaByDPReqBody) reqMsgObject.getBody();
			updateSrAddressParaByHttps(reqBody);
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
	public void updateSrAddressParaByHttps(UpdateSrAddressParaByDPReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("updateSrAddressParaByHttps");
        UpdateSrAddressParaByHttpsReqBody requestBody = new UpdateSrAddressParaByHttpsReqBody();
        requestBody.setIsdRAid(reqBody.getIsdRAid());
		requestBody.setSrAddressPara(reqBody.getSrAddressPara());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
}
