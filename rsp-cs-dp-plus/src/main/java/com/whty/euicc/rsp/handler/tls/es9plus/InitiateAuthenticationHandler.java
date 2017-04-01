/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-14
 * Id: InitiateAuthenticationHandler.java,v 1.0 2016-12-14 上午9:34:51 Administrator
 */
package com.whty.euicc.rsp.handler.tls.es9plus;

import java.util.HashMap;
import java.util.Map;

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
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.es9plus.InitiateAuthenticationReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es9plus.InitiateAuthenticationResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.util.RandomUtil;
import com.whty.euicc.rsp.util.ToTLV;
import com.whty.security.ecc.ECCUtils;

/**
 * @ClassName InitiateAuthenticationHandler
 * @author Administrator
 * @date 2016-12-14 上午9:34:51
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service("/gsma/rsp2/es9plus/smdp/initiateAuthentication")
public class InitiateAuthenticationHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(InitiateAuthenticationHandler.class);

	/** 
	 * @author Administrator
	 * @date 2016-12-14
	 * @param requestStr
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.euicc.handler.base.AbstractHandler#handle(java.lang.String)
	 */
	@Override
	public byte[] handle(String requestStr) {
		logger.info("initiateAuthentication begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/smdp/initiateAuthentication");
		InitiateAuthenticationReq reqBody = (InitiateAuthenticationReq) reqMsgObject.getBody();
		Boolean checkFlag = false;
		String euiccChallenge = reqBody.getEuiccChallenge();
		String euiccInfo1 = reqBody.getEuiccInfo1();
		String smdpAddress = reqBody.getSmdpAddress();
		logger.info("euiccChallenge:{},euiccInfo1:{},smdpAddress:{}",euiccChallenge,euiccInfo1,smdpAddress);

		/**
		 * 校验通过GSMA的CI公钥认证的eUICC的签名，选择加密算法
		 */
		Map<String,String> map = checkEuiccInfo1(euiccInfo1); 
		String svn = map.get("svn");
		String euiccCiPKIdToBeUsed = map.get("euiccCiPKIdToBeUsed");
		String CERT_DPauth_Certificate = null;
		
		/**
		 * 校验传入的参数SM-DP+地址是否和自己的SM-DP+地址匹配
		 */
		checkFlag = checkSmdpAddress(reqBody.getSmdpAddress());

		/**
		 * 生成 SM-DP+ TransactionID
		 */
		String transactionId = generateTransactionId();
		String transactionIdTLV = new StringBuilder().append(ToTLV.toTLV("80", transactionId)).toString();
		
		/**
		 * 生成 SM-DP+ smdpChallenge
		 */
		String smdpChallenge = generateSmdpChallenge();
		
		/**
		 * 生成 serverSigned1
		 */
		String serverSigned1 = generateServerSigned1(transactionId, euiccChallenge,smdpChallenge,smdpAddress);
		
		/**
		 * 生成 serverSignature1
		 */
		String serverSignature1 = generateServerSignature1(serverSigned1);
		
		/**
		 * 缓存数据
		 */
		CacheBean cacheBean = new CacheBean.CacheBeanBuilder("transactionId")
					.setSmdpChallenge(smdpChallenge).setSvn(svn).build();
		new JvmCacheUtil().put(transactionId, MessageHelper.gs.toJson(cacheBean));
		logger.info("InitiateAuthenticate cacheBean : {} ",new JvmCacheUtil().getString("eid"));
		logger.info("initiateAuthentication end");
		
		checkFlag = false;
		if (checkFlag) {
			InitiateAuthenticationResp resp = new InitiateAuthenticationResp();
			resp.setEuiccCiPKIdToBeUsed(euiccCiPKIdToBeUsed);
			resp.setServerCertificate(CERT_DPauth_Certificate);
			resp.setServerSignature1(serverSignature1);
			resp.setServerSigned1(serverSigned1);
			resp.setTransactionId(transactionIdTLV);
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), resp));
		} else {
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), new InitiateAuthenticationResp()));
		}
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param smdpAddress
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private Boolean checkSmdpAddress(String smdpAddress) {
		// TODO Auto-generated method stub
		return StringUtils.equals(smdpAddress, "smdp.gsma.com");
	}


	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param euiccInfo1
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private Map<String, String> checkEuiccInfo1(String euiccInfo1) {
		Map<String,String> map = new HashMap<String, String>();
		String svn = ToTLV.determineTLV(euiccInfo1, "82", "A9");
		map.put("svn", svn);
		System.out.println("svn >>> "+svn);
		
		String euiccCiPKIdListForVerification = ToTLV.determineTLV(euiccInfo1,"A9", "AA");
		System.out.println("euiccCiPKIdListForVerification >>>"+euiccCiPKIdListForVerification);
		
		//抽出CERT.XXauth.ECDSA
		
		String euiccCiPKIdListForSigningLV = StringUtils.substringAfter(euiccInfo1, "AA");
		String euiccCiPKIdListForSigning = euiccCiPKIdListForSigningLV.substring(2);
		System.out.println("euiccCiPKIdListForSigning >>> "+euiccCiPKIdListForSigning);
		
		//根据优先级在euiccCiPKIdListForSigning选择euiccCiPKIdToBeUsed
		map.put("euiccCiPKIdToBeUsed", "");    //todo
		return map;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private String generateTransactionId() {
		String transactionId = RandomUtil.createId();
		return transactionId;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private String generateSmdpChallenge() {
		String smdpChallenge =  RandomUtil.createId();
		return smdpChallenge;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param transactionId
	 * @param euiccChallenge
	 * @param smdpChallenge
	 * @param smdpAddress
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private String generateServerSigned1(String transactionId,
			String euiccChallenge, String smdpChallenge, String smdpAddress) {
		String transactionIdTLV = ToTLV.toTLV("80", transactionId);
		String euiccChallengeTLV = ToTLV.toTLV("81",euiccChallenge);
		String smdpAddressTLV = ToTLV.toTLV("83",smdpAddress);
		String smdpChallengeTLV = ToTLV.toTLV("84",smdpChallenge);
		StringBuilder smdpSigned1 = new StringBuilder().append(transactionIdTLV).append(euiccChallengeTLV)
				                    .append(smdpAddressTLV).append(smdpChallengeTLV);
		return smdpSigned1.toString();
	}

	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param serverSigned1
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private String generateServerSignature1(String serverSigned1) {
		String P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
		String A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
		String B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
		String Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
		String Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
		String N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
		String H="1";
		String D_CI_ECDSA="CCF97608A5081B8F478FBAB00CFE6F5A50B1C23C4B42E95EFFDDFB2DD1AD6676";
		String SmdpSignature1=ECCUtils.eccECKASign(P, A, B, Gx, Gy, N, H, serverSigned1, D_CI_ECDSA);
		StringBuilder smdpSignature1TLV = new StringBuilder().append("5F37").append(ToTLV.toTLV(SmdpSignature1));
		return smdpSignature1TLV.toString();
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
		InitiateAuthenticationResp resp = (InitiateAuthenticationResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
	
}
