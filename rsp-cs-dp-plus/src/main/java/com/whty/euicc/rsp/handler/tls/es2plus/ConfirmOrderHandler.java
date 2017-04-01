/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: ConfirmOrderHandler.java,v 1.0 2016-12-6 下午2:11:09 Administrator
 */
package com.whty.euicc.rsp.handler.tls.es2plus;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.rsp.cache.CacheBean;
import com.whty.euicc.rsp.cache.JvmCacheUtil;
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
import com.whty.euicc.rsp.packets.message.request.es2plus.ConfirmOrderReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es2plus.ConfirmOrderResp;
import com.whty.euicc.rsp.packets.message.response.es2plus.DownloadOrderResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.util.RandomUtil;

/**
 * @ClassName ConfirmOrderHandler
 * @author Administrator
 * @date 2016-12-6 下午2:11:09
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service("/gsma/rsp2/es2plus/smdp/confirmOrder")
public class ConfirmOrderHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(ConfirmOrderHandler.class);
	
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
		logger.info("ConfirmOrder begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es2plus/smdp/confirmOrder");
		ConfirmOrderReq reqBody = (ConfirmOrderReq) reqMsgObject.getBody();
		boolean checkFlag = false;
		String iccid = reqBody.getIccid();
		String eid = reqBody.getEid();
		String matchingId = reqBody.getMatchingId();
		String confirmationCode = reqBody.getConfirmationCode();
		String smdsAddress = reqBody.getSmdsAddress();
		boolean releaseFlag = reqBody.getReleaseFlag();
		logger.info("iccid:{},eid:{},matchingId:{},confirmationCode:{},smdsAddress:{},releaseFlag:{}"
				,iccid,eid,matchingId,confirmationCode,smdsAddress,releaseFlag);

		// 生成 matchingId ，matchingId和iccid相关联
		matchingId = generateMatchingId(matchingId);
		
		// 用SHA256计算确认码哈希值
		if(StringUtils.isNotBlank(confirmationCode)) {
			sha256ConfirmationCode(confirmationCode);
		}
		
		// 如果参数包含SM-DS地址，sm-dp+向 sm-ds执行事件注册，返回一个smdp地址
		if (StringUtils.isNotBlank(smdsAddress)) {
			String eventId = generateEventId();
			registEvent(reqBody.getEid(), reqBody.getSmdsAddress(), eventId);
		}
		
		// 获取到smdpAddress的值,可能返回，操作者将使用这个值生成激活码，否则将使用默认的SM-DP+地址
		String smdpAddress = getSmdpAddress();
		
		// 更新profile的状态信息
		String profileState = releaseFlag ? ProfileStates.PROFILE_RELEASED : ProfileStates.PROFILE_CONFIRMED;
		updateProfileState(iccid, profileState);
		
		// 数据放入缓存库
		CacheBean cacheBean = new CacheBean.CacheBeanBuilder(eid).setMatchingId(matchingId).setConfirmationCode(confirmationCode).build();
		new JvmCacheUtil().put(eid, MessageHelper.gs.toJson(cacheBean));
		
		logger.info("ConfirmOrder end");
		
		// 返回数据
		checkFlag = false;
		if (checkFlag) {
			ConfirmOrderResp responseBody = new ConfirmOrderResp(eid, matchingId, smdpAddress);
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), responseBody));
		} else {
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), new ConfirmOrderResp()));
		}
		
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param iccid
	 * @param profileState
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private void updateProfileState(String iccid, String profileState) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @return
	 * @Description TODO(从配置文件中查找smdpAddress的地址)
	 */
	private String getSmdpAddress() {
		// TODO Auto-generated method stub
		return "smdp.gsma.com";
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @return
	 * @Description TODO(生成随机数，eventId)
	 */
	private String generateEventId() {
		// TODO Auto-generated method stub
		return RandomUtil.genarateMatchingId();
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param confirmationCode
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private void sha256ConfirmationCode(String confirmationCode) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @author Administrator
	 * @date 2016-12-13
	 * @return
	 * @Description TODO(生成 matchingId ，matchingId和iccid相关联)
	 */
	private String generateMatchingId(String matchingId) {
		if (StringUtils.isNotBlank(matchingId)) {
			return matchingId;
		} else {
			return RandomUtil.genarateMatchingId();
		}
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-13
	 * @param eid
	 * @param smdsAddress
	 * @param eventId
	 * @Description TODO(如果参数包含SM-DS地址，sm-dp+向 sm-ds执行事件注册，目前暂时不实现)
	 */
	private void registEvent(String eid, String smdsAddress, String eventId) {
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
		ConfirmOrderResp resp = (ConfirmOrderResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
}
