/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-7
 * Id: SetFallBackAttrByHttpsHandler.java,v 1.0 2016-11-7 上午10:40:27 Administrator
 */
package com.whty.euicc.sr.handler.netty.https;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.SetFallBackAttrByHttpsReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * @ClassName SetFallBackAttrByHttpsHandler
 * @author Administrator
 * @date 2016-11-7 上午10:40:27
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service("setFallBackAttrByHttps")
public class SetFallBackAttrByHttpsHandler implements HttpHandler {
	
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Autowired
	private EuiccProfileService profileService;

	/** 
	 * @author Administrator
	 * @date 2016-11-7
	 * @param requestStr
	 * @return
	 * @Description TODO(SR处理DP发送过来的设置回调属性消息)
	 * @see com.whty.euicc.handler.base.HttpHandler#handle(java.lang.String)
	 */
	@Override
	public byte[] handle(String requestStr) {
		
		//解析请求参数数据
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		SetFallBackAttrByHttpsReqBody reqBody = (SetFallBackAttrByHttpsReqBody) reqMsgObject.getBody();
		EuiccProfileWithBLOBs profileWithBLOBs = getProfile(reqBody.getIccid());
		if (profileWithBLOBs == null) {
			RespMessage respMessage = new RespMessage(ErrorCode.FAILURE,"error");
			return new Gson().toJson(respMessage).getBytes();
		}
		
		reqBody.setIsdPAid(profileWithBLOBs.getIsdPAid());
		// 更新Apdu信息
		smsTriggerUtil.sendTriggerSms(reqBody,"setFallBackAttrApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			RespMessage respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
			return new Gson().toJson(respMessage).getBytes();
		}
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success",smsTrigger.getIsdPAid());
		return new Gson().toJson(respMessage).getBytes();
	}
	
	/**
	 * 获取profile信息
	 * @param iccid
	 * @return
	 */
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}
	
}
