package com.whty.euicc.sr.handler.netty.https;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.dao.EuiccIsdRMapper;
import com.whty.euicc.data.pojo.EuiccIsdR;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.UpdateSrAddressParaByHttpsReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * Https下SM-SR的更新SMSR地址参数服务业务
 * @author Administrator
 *
 */
@Service("updateSrAddressParaByHttps")
public class UpdateSrAddressParaByHttpsHandler implements HttpHandler{
	private Logger logger = LoggerFactory.getLogger(UpdateSrAddressParaByHttpsHandler.class);

	@Autowired
	private EuiccIsdRMapper isdrMapper;
	
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		UpdateSrAddressParaByHttpsReqBody reqBody = (UpdateSrAddressParaByHttpsReqBody) reqMsgObject.getBody();
		EuiccIsdR target = getIsdR(reqBody.getIsdRAid());
		if(StringUtils.equals(target.getrId(),"")){
			throw new EuiccBusiException("8010","The target isdR is not existence");
		}
		reqBody.setrId(target.getrId());
		reqBody.setEid(target.getEid());
		smsTriggerUtil.sendTriggerSms(reqBody,"updateSrAddressParaApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
			return new Gson().toJson(respMessage).getBytes();
		}
		return new Gson().toJson(respMessage).getBytes();
	}
	private EuiccIsdR getIsdR(String isdRAid) {
		return isdrMapper.selectByIsdRAid(isdRAid);
	}
}
