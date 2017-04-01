/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: AuthenticateClientHandler.java,v 1.0 2016-12-6 下午4:13:42 Administrator
 */
package com.whty.euicc.rsp.handler.tls.es9plus;

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
import com.whty.euicc.rsp.packets.message.request.es9plus.AuthenticateClientReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es9plus.AuthenticateClientResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.profile.ProfileUtil;
import com.whty.euicc.rsp.util.ToTLV;

/**
 * @ClassName AuthenticateClientHandler
 * @author Administrator
 * @date 2016-12-6 下午4:13:42
 * authenticateServerResponse()
 * @Description TODO(函数是LPA请求SM-DP+与卡进行认证)
 */
@Service("/gsma/rsp2/es9plus/smdp/authenticateClient")
public class AuthenticateClientHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(AuthenticateClientHandler.class);

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
		logger.info("authenticateClient begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/smdp/authenticateClient");
		AuthenticateClientReq reqBody = (AuthenticateClientReq) reqMsgObject.getBody();
		Boolean checkFlag = false;
		
		String transactionId = reqBody.getTransactionId();
		String euiccSigned1 = reqBody.getEuiccSigned1();
		String euiccSignature1 = reqBody.getEuiccSignature1();
		String euiccCertificate = reqBody.getEuiccCertificate();
		String eumCertificate = reqBody.getEumCertificate();
		logger.info("transactionId:{},euiccSigned1:{},euiccSignature1:{},euiccCertificate:{},eumCertificate:{}",
				     transactionId,euiccSigned1,euiccSignature1,euiccCertificate,eumCertificate);
		
		/**
		 * 使用公钥PK.CI.ECDSA，校验CERT.EUM.ECDSA
		 */
		checkFlag = verifyCertEumEcdsa();
		
		/**
		 * 使用公钥PK.EUM.ECDSA，校验CERT.EUICC.ECDSA
		 */
		checkFlag = verifyPKEumEcdsa(transactionId);
		
		/**
		 * 使用PK.EUICC.ECDSA，校验euiccSignature1 
		 */
		checkFlag = verifyEuiccSignature1(euiccSignature1);
		
		checkFlag = checkEuiccSigned1(euiccSigned1);
		
		/**
		 * 校验transactionId，和正在通话的RSP会话关联
		 */
		checkFlag = verifyTransactionId(transactionId);
		
		/**
		 * 校验serverChallenge
		 */
		checkFlag = verifyServerChallenge();
		
		//通过MatchingId(euiccSigned1中的ctxParams1中有)找未决订单,检查其中profile的状态是否为“released”,检查若此订单绑定了eid，其是否与认证的eUICC的eid匹配
		findPendindOrder();
		//基于Device Info(euiccSigned1中的ctxParams1中有)和/或eUICCInfo2(euiccSigned1中有)做合格检查
		eligibilityChecks();
		//判断是否需要确认码
        String ccRequiredFlag = checkNeedCC(); 		
		//检查是否重试下载，若之前下载过，则保留了bppEuiccOtpk
		String bppEuiccOtpk = checkIfRetryDownload();
		
		/**
		 * 生成 profileMetadata
		 */
		String profileMetadata = generateProfileMetadata();
		
		/**
		 * 关联 PK.EUICC.ECDSA 到RSP会话，如何关联
		 */
		attachPKEuiccEcdsa();
		
		/**
		 * 生成 smdpSigned2
		 */
		String smdpSigned2 = generateSmdpSigned2(transactionId,ccRequiredFlag,bppEuiccOtpk);
		
		/**
		 * 计算 smdpSignature2
		 */
		String smdpSignature2 = generateSmdpSignature2(smdpSigned2);
		
		/**
		 * 生成输出的 smdpCertificate，CERT.DPpb.ECDSA
		 */
		String smdpCertificate = buildCert_DPpb_ECDSA();;
		
		logger.info("authenticateClient end");
		
		checkFlag = true;
		if (checkFlag) {
			AuthenticateClientResp resp = new AuthenticateClientResp();
			resp.setTransactionId(reqBody.getTransactionId());
			resp.setProfileMetadata(profileMetadata);
			resp.setSmdpSigned2(smdpSigned2);
			resp.setSmdpSignature2(smdpSignature2);
			resp.setSmdpCertificate(smdpCertificate);
			
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), resp));
		} else {
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), new AuthenticateClientResp()));
		}
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(使用公钥PK.CI.ECDSA，校验CERT.EUM.ECDSA)
	 */
	private Boolean verifyCertEumEcdsa() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(使用公钥PK.EUM.ECDSA，校验CERT.EUICC.ECDSA)
	 */
	private Boolean verifyPKEumEcdsa(String transactionId) {
		//从证书中解析出eid,todo
		String eid = "8900112233445566778899AABBCCDDEE";
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString(transactionId),CacheBean.class);
		CacheBean cacheBean2 = new CacheBean.CacheBeanBuilder(transactionId).setSvn(cacheBean.getSvn())
				.setSmdpChallenge(cacheBean.getSmdpChallenge()).build();
		new JvmCacheUtil().put(eid, MessageHelper.gs.toJson(cacheBean2));
		logger.info("AuthenticateClient cacheBean : {} ",new JvmCacheUtil().getString(eid));
		
		return true;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(使用PK.EUICC.ECDSA，校验euiccSignature1 )
	 */
	private Boolean verifyEuiccSignature1(String euiccSignature1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @param euiccSigned1
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private Boolean checkEuiccSigned1(String euiccSigned1) {
		//验euiccSigned1中的smdpChallenge与之前匹配(拆解后和缓存中的作比较)
		/*String smdpChallengeLV = StringUtils.substringBetween(euiccSigned1, "84", "BF22");
		String smdpChallenge = smdpChallengeLV.substring(2);*/
		String smdpChallenge = ToTLV.determineTLV(euiccSigned1, "84", "BF22");
		System.out.println("smdpChallenge >> "+smdpChallenge);
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString("eid"),CacheBean.class);
		String expectSmdpChallenge = cacheBean.getSmdpChallenge();
		System.out.println("expectSmdpChallenge >> "+expectSmdpChallenge);
		
		//检查euiccSigned1中的euiccInfo2中的svn是否和euiccInfo1中的svn一致
		String euiccInfo2 = StringUtils.substringAfter(euiccSigned1, "BF22");
		/*String svnLV = StringUtils.substringBetween(euiccInfo2, "82", "83"); 
		String svn =  svnLV.substring(2);*/
		String svn = ToTLV.determineTLV(euiccInfo2, "82", "83");
		String expectSvn = cacheBean.getSvn();
		System.out.println("svn >> "+svn);
		System.out.println("expectSvn >> "+expectSvn);
		
		return StringUtils.equals(smdpChallenge, expectSmdpChallenge) && StringUtils.equals(svn,expectSvn);
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(校验transactionId，和正在通话的RSP会话关联)
	 */
	private Boolean verifyTransactionId(String transactionId) {
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString("eid"),CacheBean.class);
		String expectTransactionId = cacheBean.getTransactionId();
		System.out.println("expectTransactionId >>> "+expectTransactionId);
		return StringUtils.equals(transactionId, expectTransactionId);
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(校验serverChallenge)
	 */
	private Boolean verifyServerChallenge() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @Description TODO(通过MatchingId找未决订单,检查其中profile的状态是否为“released”,检查若此订单绑定了eid，其是否与认证的eUICC的eid匹配)
	 */
	private void findPendindOrder() {
		// TODO Auto-generated method stub
		//matchingId与iccid绑定了，通过ctxParams1中的matchingId找到相应iccid，从而找到profile
		
		//查数据库，看profile状态是否为"released"
		
		//若订单绑定了eid，与从卡证书中抽出的eid是否匹配
		
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @Description TODO(基于Device Info(euiccSigned1中的ctxParams1中有)和/或eUICCInfo2(euiccSigned1中有)做合格检查)
	 */
	private void eligibilityChecks() {
		// TODO
		//若失败，设置未决Profile下载订单中的Profile为"Error"状态
		
		//否则，从之前smdpSigned2数据结构中获取的otPK.EUICC.ECKA
		
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @return
	 * @Description TODO(检查是否需要确认码（CC）)
	 */
	private String checkNeedCC() {
		// TODO 查数据库中cc是否有值
		//根据激活码中cc需要标志，01为true
		return "01";            
	}

	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @return
	 * @Description TODO(检查是否重试下载，若之前下载过，则保留了bppEuiccOtpk)
	 */
	private String checkIfRetryDownload() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(生成 profileMetadata，传入参iccid)
	 */
	private String generateProfileMetadata() {
		// TODO Auto-generated method stub
		return new ProfileUtil().storeMetadata("00");
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @Description TODO(关联 PK.EUICC.ECDSA 到RSP会话，暂时不用)
	 */
	private void attachPKEuiccEcdsa() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @author Administrator
	 * @param bppEuiccOtpk 
	 * @param ccRequiredFlag 
	 * @param transactionId 
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(生成 smdpSigned2)
	 */
	private String generateSmdpSigned2(String transactionId, String ccRequiredFlag, String bppEuiccOtpk) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @param smdpSigned2 
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(计算 smdpSignature2)
	 */
	private String generateSmdpSignature2(String smdpSigned2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @author Administrator
	 * @date 2016-12-21
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	private String buildCert_DPpb_ECDSA() {
		// TODO Auto-generated method stub
		return null;
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
		AuthenticateClientResp resp = (AuthenticateClientResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
}
