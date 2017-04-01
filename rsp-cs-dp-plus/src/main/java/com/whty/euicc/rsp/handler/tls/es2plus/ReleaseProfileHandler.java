/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: ReleaseProfileHandler.java,v 1.0 2016-12-6 下午2:24:49 Administrator
 */
package com.whty.euicc.rsp.handler.tls.es2plus;

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
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.es2plus.ReleaseProfileReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es2plus.ReleaseProfileResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;

/**
 * @ClassName ReleaseProfileHandler
 * @author Administrator
 * @date 2016-12-6 下午2:24:49
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service("/gsma/rsp2/es2plus/smdp/releaseProfile")
public class ReleaseProfileHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(ReleaseProfileHandler.class);

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
		logger.info("ReleaseProfile begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es2plus/smdp/releaseProfile");
		ReleaseProfileReq reqBody = (ReleaseProfileReq) reqMsgObject.getBody();
		Boolean checkFlag = true;
		
		// 确认profile的标识经过了下载签约和确认签约的过程
		checkFlag = checkProfileStateByIccid(reqBody.getIccid());
		
		// 设置profile的状态为released
		updateProfileState();
		
		logger.info("ReleaseProfile end");
		ReleaseProfileResp responseBody = new ReleaseProfileResp();
		checkFlag = true;
		if (checkFlag) {
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), responseBody));
		} else {
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), responseBody));
		}
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param iccid
	 * @return
	 * @Description TODO(校验profile的状态是否为release)
	 */
	private Boolean checkProfileStateByIccid(String iccid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @Description TODO(更新数据库的状态，设置profile的状态为released)
	 */
	private void updateProfileState() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author Administrator
	 * @date 2016-12-13
	 * @param bodyHeader
	 * @param responseBody
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.euicc.rsp.common.resp.ResponseMsgBodyImpl#responseDataJson(com.whty.euicc.rsp.common.resp.ResponseBodyHeaderImpl, com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody)
	 */
	@Override
	public String responseDataJson(ResponseBodyHeaderImpl bodyHeader,
			ResponseMsgBody responseBody) {
		ReleaseProfileResp resp = (ReleaseProfileResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
}
