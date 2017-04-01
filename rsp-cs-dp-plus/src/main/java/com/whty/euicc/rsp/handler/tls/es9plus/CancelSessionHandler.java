/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: CancelSessionHandler.java,v 1.0 2016-12-6 下午4:33:05 Administrator
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
import com.whty.euicc.rsp.packets.message.request.es9plus.CancelSessionReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es9plus.CancelSessionResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;

/**
 * @ClassName CancelSessionHandler
 * @author Administrator
 * @date 2016-12-6 下午4:33:05
 * @Description TODO(取消会话，在执行完ES9+.AuthenticateClient之后，sm-dp+获得transactionId)
 */
@Service("/gsma/rsp2/es9plus/smdp/cancelSession")
public class CancelSessionHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(CancelSessionHandler.class);

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
		logger.info("CancelSession begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/smdp/cancelSession");
		CancelSessionReq reqBody = (CancelSessionReq) reqMsgObject.getBody();
		boolean checkFlag = false;
		
		String transactionId = reqBody.getTransactionId();
		String euiccCancelSessionSigned = reqBody.getEuiccCancelSessionSigned();
		String euiccCancelSessionSignature = reqBody.getEuiccCancelSessionSignature();
		logger.info("transactionId:{},euiccCancelSessionSigned:{},euiccCancelSessionSignature:{}",
				     transactionId,euiccCancelSessionSigned,euiccCancelSessionSignature);
		
		/**
		 * 校验 transactionId
		 */
		checkFlag = verifyTransactionId(transactionId);
		
		/**
		 * 检查euiccCancelSessionSigned{transactionId,smdpOid,reason}，获取原因
		 */
		String reason = getReason(euiccCancelSessionSigned);
		
		/**
		 * 使用 PK.EUICC.ECDSA 校验 euiccCancelSessionSignature
		 */
		checkFlag = verifyEuiccCancelSessionSignature(euiccCancelSessionSignature);
		
		/**
		 * 校验reason状态，如果为End user postponed或者Timeout，SM-DP+返回 Executed-Success
		 */
		if ((reason.equals("timeout")||reason.equals("End user postponed"))&& checkFlag) {
			// 返回成功
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), new CancelSessionResp()));
			
		} else if (reason.equals("endUserRejection")) {
			/**
			 * 执行事件删除，暂不完成
			 */
			deleteEvent();
			
			// 返回错误
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), new CancelSessionResp()));
		} else {
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), new CancelSessionResp()));
		}
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @param transactionId
	 * @return
	 * @Description TODO(校验 transactionId)
	 */
	private boolean verifyTransactionId(String transactionId) {
		// TODO Auto-generated method stub
		//从缓存中取transactionId
		
		//若transactionId未知，返回失败码，LPAd可能用不同的transactionId再试
		
		return true;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param euiccCancelSessionSigned
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private String getReason(String euiccCancelSessionSigned) {
		// TODO Auto-generated method stub
		//截取reason(暂时不知道tag)
		
		//修改profile状态,todo
		setProfileState("00",ProfileStates.PROFILE_RELEASED);
		
		return "timeout";
	}
	
	/**
	 * 
	 * @author Administrator
	 * @date 2016-12-21
	 * @param string
	 * @param profileReleased
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private void setProfileState(String iccid, String profileReleased) {
		// TODO
		
	}

	/**
	 * @author Administrator
	 * @param euiccCancelSessionSignature 
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(使用 PK.EUICC.ECDSA 校验 euiccCancelSessionSignature)
	 */
	private boolean verifyEuiccCancelSessionSignature(String euiccCancelSessionSignature) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @Description TODO(执行事件删除)
	 */
	private void deleteEvent() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @param bodyHeader
	 * @param responseBody
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.euicc.rsp.common.resp.ResponseMsgBodyImpl#responseDataJson(com.whty.euicc.rsp.common.resp.ResponseBodyHeaderImpl, com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody)
	 */
	@Override
	public String responseDataJson(ResponseBodyHeaderImpl bodyHeader,
			ResponseMsgBody responseBody) {
		CancelSessionResp resp = (CancelSessionResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
}
