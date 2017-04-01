/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: CancelOrderHandler.java,v 1.0 2016-12-6 下午2:56:14 Administrator
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
import com.whty.euicc.rsp.packets.message.request.es2plus.CancelOrderReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es2plus.CancelOrderResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;

/**
 * @ClassName CancelOrderHandler
 * @author Administrator
 * @date 2016-12-6 下午2:56:14
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service("/gsma/rsp2/es2plus/smdp/cancelOrder")
public class CancelOrderHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(CancelOrderHandler.class);

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
		logger.info("CancelOrder begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es2plus/smdp/cancelOrder");
		CancelOrderReq reqBody = (CancelOrderReq) reqMsgObject.getBody();
		Boolean checkFlag = false;
		
		// 确认profile的状态是 allocated 并且没有 被下载
		checkFlag = checkProfileStatus(reqBody.getIccid());
		
		// 如果matchingId和iccid绑定，确认绑定的matchingId的
		checkFlag = checkMatchingId(reqBody.getMatchingId());
		
		// 如果eid和iccid绑定，确认绑定的eid
		checkFlag = checkEid(reqBody.getEid());
		
		// 根据最终标识更新iccid的状态信息
		updateIccid(reqBody.getFinalProfileStatusIndicator());
		
		// 如果最开始签约关联了事件注册，执行事件删除流程
		String eventId = null;
		deleteEvent(reqBody.getEid(), eventId);
		
		CancelOrderResp responseBody = new CancelOrderResp();
		checkFlag = false;
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
	 * @param eid
	 * @param eventId
	 * @Description TODO(目前暂时不实现该功能)
	 */
	private void deleteEvent(String eid, String eventId) {
		// TODO Auto-generated method stub
		
	}

	private void updateIccid(String finalProfileStatusIndicator) {
		// TODO Auto-generated method stub
		
	}

	private Boolean checkEid(String eid) {
		// TODO Auto-generated method stub
		return null;
	}

	private Boolean checkMatchingId(String matchingId) {
		// TODO Auto-generated method stub
		return null;
	}

	private Boolean checkProfileStatus(String iccid) {
		// TODO Auto-generated method stub
		return null;
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
		CancelOrderResp resp = (CancelOrderResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
}
