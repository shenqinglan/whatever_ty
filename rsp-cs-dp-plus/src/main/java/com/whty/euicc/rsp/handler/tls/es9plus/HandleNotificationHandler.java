/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: HandleNotificationHandler.java,v 1.0 2016-12-6 下午4:26:08 Administrator
 */
package com.whty.euicc.rsp.handler.tls.es9plus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.rsp.common.resp.ResponseBodyHeaderImpl;
import com.whty.euicc.rsp.common.resp.ResponseMsgBodyImpl;
import com.whty.euicc.rsp.common.resp.impl.ResponseBodyHeader;
import com.whty.euicc.rsp.common.resp.impl.ResponseStatusCodeFailed;
import com.whty.euicc.rsp.common.resp.impl.ResponseStatusCodeSuccess;
import com.whty.euicc.rsp.common.resp.impl.ResponseStatusFailed;
import com.whty.euicc.rsp.common.resp.impl.ResponseStatusSuccess;
import com.whty.euicc.rsp.constant.ProfileStates;
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.es9plus.HandleNotificationReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es9plus.HandleNotificationResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.util.ToTLV;

/**
 * @ClassName HandleNotificationHandler
 * @author Administrator
 * @date 2016-12-6 下午4:26:08
 * @Description TODO(LPA通知SM-DP+，profile管理已经安装到eUICC上面)
 */
@Service("/gsma/rsp2/es9plus/smdp/handleNotification")
public class HandleNotificationHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(HandleNotificationHandler.class);

	/** 
	 * @author Administrator
	 * @date 2016-12-6
	 * @param requestStr
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.euicc.handler.base.AbstractHandler#handle(java.lang.String)
	 */
	@Override
	public byte[] handle(String requestStr) {
		logger.info("handleNotification begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/smdp/handleNotification");
		HandleNotificationReq reqBody = (HandleNotificationReq) reqMsgObject.getBody();
		boolean checkFlag = true;
		
		String profileInstallationResultData = reqBody.getProfileInstallationResultData();
		String euiccSignPIR = reqBody.getEuiccSignPIR();
		logger.info("profileInstallationResultData:{},euiccSignPIR:{}",profileInstallationResultData,euiccSignPIR);
		
		checkFlag = checkProfileInstallationResultData(profileInstallationResultData);
		checkFlag = checkEuiccSignPIR(euiccSignPIR);
		
		logger.info("handleNotification end");
		checkFlag = true;
		if (checkFlag) {
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), new HandleNotificationResp()));
		} else {
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), new HandleNotificationResp()));
		}
	}
	
	/**
	 * 
	 * @author Administrator
	 * @date 2016-12-21
	 * @param euiccSignPIR
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private boolean checkEuiccSignPIR(String euiccSignPIR) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param profileInstallationResultData
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private boolean checkProfileInstallationResultData(
			String profileInstallationResultData) {
		String transactionId = ToTLV.determineTLV(profileInstallationResultData, "80", "BF2F");
		//找缓存中的transactionId
		
		//若transactionId一致，设置profile状态为"Installed",若不一致，设置profile状态为"Error"
		setProfileStates("00",ProfileStates.PROFILE_INSTALLED);
		
		return true;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param iccid
	 * @param profileState
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private void setProfileStates(String iccid,String profileState) {
		// TODO
		
	}

	@Override
	public String responseDataJson(ResponseBodyHeaderImpl bodyHeader,
			ResponseMsgBody responseBody) {
		HandleNotificationResp resp = (HandleNotificationResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
	
}
