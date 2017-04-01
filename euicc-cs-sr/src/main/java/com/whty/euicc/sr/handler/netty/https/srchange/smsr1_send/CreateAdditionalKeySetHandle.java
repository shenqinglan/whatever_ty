package com.whty.euicc.sr.handler.netty.https.srchange.smsr1_send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.CreateAdditionalKeySetReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

@Service("createAdditionalKeySet")
public class CreateAdditionalKeySetHandle implements HttpHandler {
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Autowired
	private EuiccCardService euiccCardService;
	
	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		CreateAdditionalKeySetReqBody reqBody = (CreateAdditionalKeySetReqBody) reqMsgObject.getBody();
		if(!checkInitialConditions()){
			throw new EuiccBusiException("0101", "检查初始化状态有误");
		}
		
		String eid = reqBody.getEid();
		EuiccCard euiccCard = euiccCardService.selectByPrimaryKey(eid);
		if(euiccCard == null){
			throw new EuiccBusiException("0102", "EID Unknown");
		}
		
		smsTriggerUtil.sendTriggerSms(reqBody,"createAdditionalKeySetApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			RespMessage respMessage = new RespMessage(ErrorCode.FAILURE,"error");
			return new Gson().toJson(respMessage).getBytes();
		}
		
		String key = "change-recept-" + reqBody.getEid();
		String receipt = CacheUtil.getString(key);
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success", receipt);
		return new Gson().toJson(respMessage).getBytes();
	}
	
	private boolean checkInitialConditions() {
		return true;
	}

}
