/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: DownloadOrder.java,v 1.0 2016-12-6 下午1:52:05 Administrator
 */
package com.whty.euicc.rsp.handler.tls.es2plus;

import org.apache.commons.lang3.StringUtils;
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
import com.whty.euicc.rsp.packets.message.request.es2plus.DownloadOrderReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es2plus.DownloadOrderResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;

/**
 * @ClassName DownloadOrderHandler
 * @author Administrator
 * @date 2016-12-6 下午1:52:05
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service("/gsma/rsp2/es2plus/smdp/downloadOrder")
public class DownloadOrderHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(DownloadOrderHandler.class);
	
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
		logger.info("DownloadOrder begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es2plus/smdp/downloadOrder");
		DownloadOrderReq reqBody = (DownloadOrderReq) reqMsgObject.getBody();
		boolean checkFlag = true;
		String eid = reqBody.getEid();
		String iccid = reqBody.getIccid();
		String profileType = reqBody.getProfileType();
		logger.info("eid:{},iccid:{},profileType:{}",eid,iccid,profileType);
		
		String profileState = StringUtils.isBlank(eid) ? ProfileStates.PROFILE_ALLOCATED : ProfileStates.PROFILE_LINKED;
		logger.info("profileState:{}",profileState);
		
		// 根据iccid或者profileType从数据库中选择profile，并更新数据库profile的信息
		if (StringUtils.isNotBlank(reqBody.getIccid())) {
			String state = selectProfileByIccid(reqBody.getIccid());
			
			// 校验profileState的状态信息
			checkFlag = checkProfileState(state, profileState);
			
			// 更新profile的状态
			updateProfileState(iccid, profileState);
			
		} else if (StringUtils.isNotBlank(reqBody.getProfileType())){
			iccid = selectProfileByType(eid, reqBody.getProfileType());
			
			// 更新profile的状态
			updateProfileState(iccid, profileState);
		} else {
			checkFlag = false;
		}
		
		// 返回数据
		checkFlag = false;
		if (checkFlag) {
			DownloadOrderResp responseBody =  new DownloadOrderResp(iccid);
			responseBody.setIccid(iccid);
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), responseBody));
		} else {
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), new DownloadOrderResp()));
		}
		
		
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param state
	 * @param profileState
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private boolean checkProfileState(String state, String profileState) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-19
	 * @param iccid
	 * @param profileState
	 * @Description TODO(更新profile的状态信息)
	 */
	private void updateProfileState(String iccid, String profileState) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author Administrator
	 * @date 2016-12-13
	 * @param eid
	 * @param profileType
	 * @return
	 * @Description TODO(根据profileType获取iccid信息，更新数据库)
	 */
	private String selectProfileByType(String eid, String profileType) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-13
	 * @param eid
	 * @param iccid
	 * @return
	 * @Description TODO(根据iccid获取eid信息，更新数据库)
	 */
	private String selectProfileByIccid(String iccid) {
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
	public String responseDataJson(ResponseBodyHeaderImpl bodyHeader, ResponseMsgBody responseBody) {
		DownloadOrderResp resp = (DownloadOrderResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
	
}
