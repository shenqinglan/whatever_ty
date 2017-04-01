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
import com.whty.euicc.packets.message.request.ConnectParaUpdateBySmsAndDpReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.UpdateConnectivityParametersReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * 使用scp03更新连接参数类
 * @author Administrator
 *
 */
@Service("connectParaUpdateBySmsAndDp")
public class ConnectParaUpdateBySmsHandler implements HttpHandler {
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
			try {
				EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
				ConnectParaUpdateBySmsAndDpReqBody reqBody = (ConnectParaUpdateBySmsAndDpReqBody) reqMsgObject.getBody();
				
				// 设更新连接参数
				updateConnParaByDp(reqBody);
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

	private void updateConnParaByDp(ConnectParaUpdateBySmsAndDpReqBody reqBody) throws Exception {
		MsgHeader header = new MsgHeader("updateConnectivityParametersBySms");
		UpdateConnectivityParametersReqBody requestBody = new UpdateConnectivityParametersReqBody();
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setSmsCenterNo(reqBody.getSmsCenterNo());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
		if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
			throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
		}

}

			

}
