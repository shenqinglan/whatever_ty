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
import com.whty.euicc.packets.message.request.CreateISDPByDpReqBody;
import com.whty.euicc.packets.message.request.CreateISDPReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.RetrieveEISReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.common.https.BaseHttp;
/**
 * SM-DP的创建ISD-P服务业务
 * @author Administrator
 *
 */
@Service("createISDPByDp")
public class CreateISDPHandler implements HttpHandler {

	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			CreateISDPByDpReqBody reqBody = (CreateISDPByDpReqBody) reqMsgObject.getBody();
			String eis = getEIS(reqBody.getEid());
			boolean euiccElig = checkEuiccEligibillity(eis);
			if(!euiccElig){
				//返回失败信息
				respMessage = new RespMessage("0001","card check eligibillity error");
				return new Gson().toJson(respMessage).getBytes();
			}
			createISDP(reqBody);
		} catch (Exception e) {
			e.printStackTrace();
			//返回失败信息
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
		}
		
		return new Gson().toJson(respMessage).getBytes();
	}
	
	
	public String getEIS(String eid)throws Exception{
        MsgHeader header = new MsgHeader("retrieveEISBySr");
        RetrieveEISReqBody requestBody = new RetrieveEISReqBody();
		requestBody.setEid(eid);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		return new String(msgBype);
	}

	private boolean checkEuiccEligibillity(String eis) {
		checkProfileType();
		checkEuiccMemory();
		checkECASD();
		return true;
	}
	
	
	
	public void createISDP(CreateISDPByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("createISDP");
        CreateISDPReqBody requestBody = new CreateISDPReqBody();
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

	/**
	 * eUICC是否被认证；如果是非认证的eUICC，SM-DP应该停止流程
	 */
	private void checkECASD() {
		// TODO Auto-generated method stub
	}

	/**
	 * eUICC卡上有无足够的空间
	 */
	private void checkEuiccMemory() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 目标Profile是否兼容该类型的eUICC卡; SM-DP能否能为该类型的eUICC卡生成Profile
	 */
	private void checkProfileType() {
		// TODO Auto-generated method stub
		
	}

	

}
