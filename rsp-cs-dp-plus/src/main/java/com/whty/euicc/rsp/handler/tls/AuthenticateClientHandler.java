package com.whty.euicc.rsp.handler.tls;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whty.euicc.common.utils.SecurityUtil;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.rsp.beans.EuiccCertificate;
import com.whty.euicc.rsp.cache.CacheBean;
import com.whty.euicc.rsp.cache.JvmCacheUtil;
import com.whty.euicc.rsp.constant.EccProperties;
import com.whty.euicc.rsp.constant.ProfileStates;
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.AuthenticateClientReq;
import com.whty.euicc.rsp.packets.message.response.AuthenticateClientResp;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.profile.ProfileUtil;
import com.whty.euicc.rsp.util.ToTLV;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;
import com.whty.security.ecc.ECCUtils;

/**
 * ES9+.AuthenticateClient()接口，双向认证接口，DP+认证卡
 * 入参：transactionId,authenticateServerResponse
 * 出参：transactionID,(opt.) --> profileMetadata,smdpSigned2,smdpSignature2,smdpCertificate
 * @author 11
 *
 */
@Service("/gsma/rsp2/es9plus/authenticateClient")
public class AuthenticateClientHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(AuthenticateClientHandler.class);
	
	private final static String TAG = " *** AuthenticateClientHandler *** ";
	
	@Autowired
	private RspProfileService rspProfileService;
	
	@Autowired
	private ProfileUtil profileUtil;
	
	private static String P = EccProperties.P;
	private static String A = EccProperties.A;
	private static String B = EccProperties.B;
	private static String Gx = EccProperties.Gx;   // 待确定
	private static String Gy = EccProperties.Gy;   // 待确定
	private static String N = EccProperties.N;
	private static String H = EccProperties.H;
	private static String SK_DPpb_ECDSA = EccProperties.SK_DPpb_ECDSA;
	private String spax;
	private String spay;
	private String sM;
	private String sR;
	private String sS;
	
	@Override
	public byte[] handle(String requestStr) {
		try {
			logger.info("authenticateClient begin");
			EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/authenticateClient");
			AuthenticateClientReq reqBody = (AuthenticateClientReq) reqMsgObject.getBody();
			String transactionId = reqBody.getTransactionId();
			String euiccSigned1 = reqBody.getEuiccSigned1();
			String euiccSignature1 = reqBody.getEuiccSignature1();
			String euiccCertificate = reqBody.getEuiccCertificate();
			String eumCertificate = reqBody.getEumCertificate();
			logger.info("transactionId:{},euiccSigned1:{},euiccSignature1:{},euiccCertificate:{},eumCertificate:{}",
					     transactionId,euiccSigned1,euiccSignature1,euiccCertificate,eumCertificate);
			
			//检查入参中的transactionId是否和缓存中的一致
			if(!checkTransactionId(transactionId)){
				throw new RuntimeException("transactionId不一致");
			}
			
			boolean result = checkEuiccSigned1(euiccSigned1, transactionId);
			if(!result){
				throw new RuntimeException("Error >> checkEuiccSigned1");
			}
			EuiccCertificate euiccCert = checkEuiccCertificate(euiccCertificate);
			String pkEuiccEcdsa = euiccCert.getPkEuiccEcdsa();
			String eidInCert = euiccCert.getEid();
			boolean result2 = checkEuiccSignature1(euiccSignature1, pkEuiccEcdsa, euiccSigned1);
			if(!result2){
				throw new RuntimeException("Error >> checkEuiccSignature1");
			}
			
			checkEumCertificate(eumCertificate);
			//通过MatchingId(euiccSigned1中的ctxParams1中有)找未决订单,检查其中profile的状态是否为"released",检查若此订单绑定了eid，其是否与认证的eUICC的eid匹配
			String matchingId = findPendindOrder(euiccSigned1, eidInCert);
			//获取数据库中的哈希确认码
			String hashCc = getHashCc(rspProfileService.selectByMatchingId(matchingId));
			//基于Device Info(euiccSigned1中的ctxParams1中有)和/或eUICCInfo2(euiccSigned1中有)做合格检查
			eligibilityChecks();
			//判断是否需要确认码
	        String ccRequiredFlag = checkNeedCC(hashCc); 		
			//检查是否重试下载，若之前下载过，则保留了bppEuiccOtpk
			String bppEuiccOtpkFlag = checkIfRetryDownload();
			//构建profile元数据,与ES8+.storeMetadata()相关
			String profileMetadata = buildProfileMetadata(matchingId);
			//构建smdpSigned2
			String smdpSigned2 = buildSmdpSigned2(transactionId,ccRequiredFlag,bppEuiccOtpkFlag);
			//计算smdpSignature2
			String smdpSignature2 = calSmdpSignature2(smdpSigned2, euiccSignature1);
			
			//拼CERT.DPpb.ECDSA
			String CERT_DPpb_ECDSA = buildCert_DPpb_ECDSA();
			
			//save cache
			CacheBean cache = new CacheBean.CacheBeanBuilder(transactionId).setEid(eidInCert).setPkEuiccEcdsa(pkEuiccEcdsa).setSmdpSignature2(smdpSignature2).setMatchingId(matchingId).build();
			new JvmCacheUtil().put(transactionId, MessageHelper.gs.toJson(cache));
			
			logger.info("AuthenticateClient cacheBean : {} ",new JvmCacheUtil().getString(transactionId));
			
			logger.info("authenticateClient end");
			return httpPostResponseByRsp("application/json",respBodyByBean(transactionId,profileMetadata,smdpSigned2,smdpSignature2,CERT_DPpb_ECDSA));
		} catch (Exception e) {
			e.printStackTrace();
			return httpPostResponseByRsp("application/json", respBodyByBeanError(e));
		}
		
	}
	
	/*
	 * 拼证书数据
	 */
	private String buildCert_DPpb_ECDSA() {
		return EccProperties.CERT_DPpb_ECDSA;
	}

	/*
	 * 用PK.CI.ECDSA验证CERT.EUM.ECDSA有效性
	 */
	private void checkEumCertificate(String eumCertificate) {
		
	}

	/*
	 * 用PK.EUM.ECDSA验证CERT.EUICC.ECDSA有效性
	 */
	private EuiccCertificate checkEuiccCertificate(String euiccCertificate) {
		EuiccCertificate euiccCert = new EuiccCertificate(); 
		//从CERT.EUICC.ECDSA中抽出PK.EUICC.ECDSA，并返回(查脚本)
		//脚本：1ed227965ed396101f84b3e34ab3c183866edc978841e0b0974818663a29d961b3ba47a977376ea25f649b1bf361544269e818108d6479acb1c0198f5c28a7c0
		String pkEuiccEcdsa = ToTLV.determineTLV(euiccCertificate, "03", "A3").substring(4);
		euiccCert.setPkEuiccEcdsa(pkEuiccEcdsa);
		//用PK.EUM.ECDSA验证CERT.EUICC.ECDSA有效性
		
		//从证书中解析出eid
//		String eid = "8900112233445566778899AABBCCDDEE";
		int indexOfEid = euiccCertificate.indexOf("3839");
		String eidHex = euiccCertificate.substring(indexOfEid, indexOfEid+64);
		String eid = SecurityUtil.hexStringToString(eidHex);
		logger.info("checkEuiccCertificate -- eid : {}", eid);
		euiccCert.setEid(eid);
		
		return euiccCert;
	}

	/*
	 * 用PK.EUICC.ECDSA验证euiccSignature1
	 */
	private boolean checkEuiccSignature1(String euiccSignature1, String pkEuiccEcdsa, String euiccSigned1) {
		String euiccSignature1Value = euiccSignature1.substring(6);
		spax = pkEuiccEcdsa.substring(0, 64);
		System.out.println("spax ******************* : "+spax);
		spay = pkEuiccEcdsa.substring(64);
		System.out.println("spay ******************* : "+spay);
		sM = euiccSigned1;
		System.out.println("sM ******************* : "+sM);
		sR = euiccSignature1Value.substring(0, 64);
		System.out.println("sR ****************** : "+sR);
		sS = euiccSignature1Value.substring(64);
		System.out.println("sS ****************** : "+sS);
		return ECCUtils.eccECKAVerify(P, A, B, Gx, Gy, N, H, sM, spax, spay, sR, sS);
	}

	/*
	 * 验euiccSigned1
	 */
	private boolean checkEuiccSigned1(String euiccSigned1, String transactionId) {
		//验euiccSigned1中的smdpChallenge与之前匹配(拆解后和缓存中的作比较)
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString(transactionId), CacheBean.class);
		String smdpChallenge = ToTLV.determineTLV(euiccSigned1, "84", "BF22");
		logger.info("checkEuiccSigned1 -- smdpChallenge : {}", smdpChallenge);
		String cacheSmdpChallenge = cacheBean.getSmdpChallenge();
		logger.info("cacheSmdpChallenge : {}", cacheSmdpChallenge);
		
		//检查euiccSigned1中的euiccInfo2中的svn是否和euiccInfo1中的svn一致
		String euiccInfo2 = StringUtils.substringAfter(euiccSigned1, "BF22");
		
		String svn = ToTLV.determineTLV(euiccInfo2, "82", "83");
		String cacheSvn = cacheBean.getSvn();
		logger.info("checkEuiccSigned1 -- svn : {}", svn);
		logger.info("cacheSvn : {}", cacheSvn);
		
		return StringUtils.equals(smdpChallenge, cacheSmdpChallenge) && StringUtils.equals(svn,cacheSvn);
	}

	private boolean checkTransactionId(String transactionId) {
		String cache = new JvmCacheUtil().getString(transactionId);
		return StringUtils.isBlank(cache) ? false : true;
	}

	/*
	 * 检查是否需要确认码（CC）
	 */
	private String checkNeedCC(String hashCc) {
		//根据激活码中cc需要标志，01为true
		return StringUtils.isNotBlank(hashCc) ? ToTLV.toTLV("01", "01") : "";
	}

	/*
	 * 检查是否重试下载，若之前下载过，则保留了bppEuiccOtpk
	 */
	private String checkIfRetryDownload() {
		// %sOtpkFlag为0，表示preparedownload不带bppEuiccOtpk；%sOtpkFlag为1，表示带bppEuiccOtpk
		return "00";
	}

	/*
	 * 计算smdpSignature2 根据脚本，data_sign为：smdpSigned2 + euiccSignature1
	 */
	private String calSmdpSignature2(String smdpSigned2, String euiccSignature1) {
		String data_sign = new StringBuilder().append(smdpSigned2).append(euiccSignature1).toString();
		String SmdpSignature2=ECCUtils.eccECKASign(P, A, B, Gx, Gy, N, H, data_sign, SK_DPpb_ECDSA);
		return ToTLV.toTLV("5F37", SmdpSignature2);
	}

	/*
	 * 构建smdpSigned2(transactionID,ccRequiredFlag,bppEuiccOtpk(opt.))
	 */
	private String buildSmdpSigned2(String transactionID, String ccRequiredFlag, String bppEuiccOtpkFlag) {
		String bppEuiccOtpk = StringUtils.equals(bppEuiccOtpkFlag, "00") ? "" : ToTLV.toTLV("5F49", EccProperties.EUICC_OTPK);
		StringBuilder smdpSigned2 = new StringBuilder().append(transactionID).append(ccRequiredFlag).append(bppEuiccOtpk);
		return ToTLV.toTLV("30", smdpSigned2.toString());
	}

	/*
	 * 构建profile元数据
	 */
	private String buildProfileMetadata(String matchingId) {
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByMatchingId(matchingId);
		//传入参iccid
		return profileUtil.storeMetadata(rspProfileWithBLOBs.getIccid());
	}

	/*
	 * 基于Device Info(euiccSigned1中的ctxParams1中有)和/或eUICCInfo2(euiccSigned1中有)做合格检查
	 */
	private void eligibilityChecks() {
		//若失败，设置未决Profile下载订单中的Profile为"Error"状态
		
		//否则，从之前smdpSigned2数据结构中获取的otPK.EUICC.ECKA
		
	}

	/*
	 * 通过MatchingId找未决订单,检查其中profile的状态是否为"released",检查若此订单绑定了eid，其是否与认证的eUICC的eid匹配
	 */
	private String findPendindOrder(String euiccSigned1, String eidInCert) {
		//matchingId与iccid绑定了，通过截取ctxParams1中的matchingId找到相应iccid，从而找到profile
		String ctxParams1TLV = euiccSigned1.substring(euiccSigned1.indexOf("A00E"));    //待确认，结构可能会有改动
		String matchingId = ToTLV.determineTLV(ctxParams1TLV, "80", "A1");
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByMatchingId(matchingId);
		if(rspProfileWithBLOBs == null){
			throw new RuntimeException("数据库中未找到相应记录");
		}
		//查数据库，看profile状态是否为"released"
		String stateOfProfile = rspProfileWithBLOBs.getProfileState();
		if(!StringUtils.equalsIgnoreCase(stateOfProfile, ProfileStates.PROFILE_RELEASED)){
			throw new RuntimeException("当前profile状态不符合条件");
		}
		//若订单绑定了eid，与从卡证书中抽出的eid是否匹配
		String eid = rspProfileWithBLOBs.getEid();
		if(StringUtils.isNotBlank(eid) && !StringUtils.equals(eid, eidInCert)){
			throw new RuntimeException("profile订单中的eid与卡证书中的eid不匹配");
		}
		return matchingId;
	}

	private String getHashCc(RspProfileWithBLOBs rspProfileWithBLOBs) {
		String hashCc = rspProfileWithBLOBs.getHashCc();
		if(StringUtils.isNotBlank(hashCc)){
			return hashCc;
		}
		return null;
	}

	private String respBodyByBean(String transactionId, String profileMetadata, String smdpSigned2, String smdpSignature2, String cERT_DPpb_ECDSA){
		StatusCodeData statusCodeData = new StatusCodeData("00");
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Success", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		AuthenticateClientResp resp = new AuthenticateClientResp();
		resp.setHeader(head);
		resp.setTransactionID(transactionId);
		
		resp.setProfileMetadata(profileMetadata);
		resp.setSmdpSigned2(smdpSigned2);
		resp.setSmdpSignature2(smdpSignature2);
		resp.setSmdpCertificate(cERT_DPpb_ECDSA);
		return MessageHelper.gs.toJson(resp);
	}
	
	private String respBodyByBeanError(Exception e){
		StatusCodeData statusCodeData = new StatusCodeData("01", TAG + e.getMessage());
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Failure", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		AuthenticateClientResp resp = new AuthenticateClientResp();
		resp.setHeader(head);
		return MessageHelper.gs.toJson(resp);
	}
	
}
