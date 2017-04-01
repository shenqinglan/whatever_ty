package com.whty.euicc.rsp.handler.tls;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.rsp.beans.EuiccInfo1;
import com.whty.euicc.rsp.cache.CacheBean;
import com.whty.euicc.rsp.cache.JvmCacheUtil;
import com.whty.euicc.rsp.constant.EccProperties;
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.InitiateAuthenticationReq;
import com.whty.euicc.rsp.packets.message.response.InitiateAuthenticationResp;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.util.RandomUtil;
import com.whty.euicc.rsp.util.ToTLV;
import com.whty.security.ecc.ECCUtils;

/**
 * ES9+.initiateAuthentication()接口，双向认证接口，DP+提供数据以便卡验证DP+
 * 入参：euiccChallenge，euiccInfo1，smdpAddress
 * 出参：transactionId，serverSigned1，serverSignature1，euiccCiPKIdTobeUsed，serverCertificate
 * @author Administrator
 *
 */
@Service("/gsma/rsp2/es9plus/initiateAuthentication")
public class InitiateAuthenticationHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(InitiateAuthenticationHandler.class);
	
	private final static String TAG = " *** InitiateAuthenticationHandler *** ";
	
	@Override
	public byte[] handle(String requestStr) {
		try {
			logger.info("initiateAuthentication begin");
			EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/initiateAuthentication");
			InitiateAuthenticationReq reqBody = (InitiateAuthenticationReq) reqMsgObject.getBody();
			String euiccChallenge = reqBody.getEuiccChallenge();
			String euiccInfo1 = reqBody.getEuiccInfo1();
			String smdpAddress = reqBody.getSmdpAddress();
			logger.info("euiccChallenge:{},euiccInfo1:{},smdpAddress:{}",euiccChallenge,euiccInfo1,smdpAddress);
			
			//检查euiccInfo1,抽出euiccCiPKIdToBeUsed,euiccCiPKIdListForVerification,euiccCiPKIdListForSigning
			EuiccInfo1 info1 = checkEuiccInfo1(euiccInfo1);
			String svn = info1.getSvn();
			//根据优先级在euiccCiPKIdListForSigning选择euiccCiPKIdToBeUsed
			String euiccCiPKIdToBeUsed =info1.getEuiccCiPKIdListForSigning().substring(0, 44);
			String CERT_DPauth_ECDSA = EccProperties.CERT_DPauth_ECDSA;
			//检查smdp地址
			if(!checkSmdpAddress(smdpAddress)){
				throw new RuntimeException("LPAd返回的DP+地址与本地DP+地址不匹配");
			}
			//生成TransactionID,脚本中8字节
			String transactionId = generateTransactionID();
			transactionId = "1122334455667700";//测试用
			String transactionIdTLV = ToTLV.toTLV("80", transactionId);
			//生成smdpChallenge，脚本中16字节
			String smdpChallenge = generateSmdpChallenge();
			smdpChallenge = "39B32D12074D4DC8644366BB428B26A6";//测试用
			String smdpChallengeTLV = ToTLV.toTLV("84", smdpChallenge);
			//构建smdpSigned1(TLV格式)
			String smdpSigned1 = buildSmdpSigned1(transactionIdTLV,euiccChallenge,smdpAddress,smdpChallengeTLV);
			System.out.println("smdpSigned1 **************: "+smdpSigned1);
			//计算smdpSignature1(TLV格式)
			String smdpSignature1 = calSmdpSignature1(smdpSigned1);
			System.out.println("smdpSignature1 ************: "+smdpSignature1);
			
			//save cache(value格式)
			CacheBean cacheBean = new CacheBean.CacheBeanBuilder(transactionIdTLV)
			                      .setSmdpChallenge(smdpChallenge).setSvn(svn).build();
			new JvmCacheUtil().put(transactionIdTLV, MessageHelper.gs.toJson(cacheBean));
			logger.info("InitiateAuthenticate cacheBean : {} ",new JvmCacheUtil().getString(transactionIdTLV));
			
			logger.info("initiateAuthentication end");
			return httpPostResponseByRsp("application/json",respBodyByBean(transactionIdTLV,smdpSigned1,smdpSignature1,euiccCiPKIdToBeUsed,CERT_DPauth_ECDSA));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(TAG + e.getMessage());
			return httpPostResponseByRsp("application/json", respBodyByBeanError(e));
		}
		
	}
	
	/*
	 * 生成smdpChallenge
	 */
	private String generateSmdpChallenge() {
		return RandomUtil.createId();
	}

	/*
	 * 计算smdpSignature1
	 */
	private String calSmdpSignature1(String smdpSigned1) {
		String P = EccProperties.P;
		String A = EccProperties.A;
		String B = EccProperties.B;
		String Gx = EccProperties.Gx;
		String Gy = EccProperties.Gy;
		String N = EccProperties.N;
		String H = EccProperties.H;
		String SK_DPauth_ECDSA = EccProperties.SK_DPauth_ECDSA;   
		String smdpSignature1 = ECCUtils.eccECKASign(P, A, B, Gx, Gy, N, H, smdpSigned1, SK_DPauth_ECDSA);
		String smdpSignature1TLV = ToTLV.toTLV("5F37", smdpSignature1);
		return smdpSignature1TLV;
	}

	/*
	 * 构建smdpSigned1
	 */
	private String buildSmdpSigned1(String transactionId, String euiccChallenge, String checkedSmdpAddress, String smdpChallenge) {
		StringBuilder smdpSigned1 = new StringBuilder().append(transactionId).append(euiccChallenge)
				                    .append(checkedSmdpAddress).append(smdpChallenge);
		return ToTLV.toTLV("30", smdpSigned1.toString());
	}

	/*
	 * 生成TransactionID
	 */
	private String generateTransactionID() {
		return RandomUtil.createId().substring(0, 16);
	}

	/*
	 * 检查smdp地址
	 */
	private boolean checkSmdpAddress(String smdpAddress) {
		String localDpPlusAddr = SpringPropertyPlaceholderConfigurer.getStringProperty("dpPlus_address");
		String smdpAddressValue = smdpAddress.substring(4);
		return StringUtils.equals(smdpAddressValue, localDpPlusAddr);
	}

	/*
	 * 检查euiccInfo1,抽出euiccCiPKIdToBeUsed,euiccCiPKIdListForVerification,euiccCiPKIdListForSigning
	 */
	private EuiccInfo1 checkEuiccInfo1(String euiccInfo1) {
		EuiccInfo1 info1 = new EuiccInfo1();
		String svn = ToTLV.determineTLV(euiccInfo1, "82", "A9");
		info1.setSvn(svn);
		logger.info("checkEuiccInfo1 ——— svn : {}", svn);
		
		String euiccCiPKIdListForVerification = ToTLV.determineTLV(euiccInfo1,"A9", "AA");
		logger.info("checkEuiccInfo1 ——— euiccCiPKIdListForVerification : {}", euiccCiPKIdListForVerification);
		
		String euiccCiPKIdListForSigningLV = StringUtils.substringAfter(euiccInfo1, "AA");
		String euiccCiPKIdListForSigning = euiccCiPKIdListForSigningLV.substring(2);
		logger.info("checkEuiccInfo1 ——— euiccCiPKIdListForSigning : {}", euiccCiPKIdListForSigning);
		
		info1.setEuiccCiPKIdListForSigning(euiccCiPKIdListForSigning); 
		return info1;
	}

	private String respBodyByBean(String transactionId, String smdpSigned1, String smdpSignature1, String euiccCiPKIdToBeUsed, String cERT_DPauth_Certificate){
		StatusCodeData statusCodeData = new StatusCodeData("00");
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Success", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		InitiateAuthenticationResp resp = new InitiateAuthenticationResp();
		resp.setHeader(head);
		resp.setTransactionId(transactionId);
		resp.setServerSigned1(smdpSigned1);
		resp.setServerSignature1(smdpSignature1);
		resp.setEuiccCiPKIdTobeUsed(euiccCiPKIdToBeUsed);
		resp.setServerCertificate(cERT_DPauth_Certificate);
		return MessageHelper.gs.toJson(resp);
	}
	
	private String respBodyByBeanError(Exception e){
		StatusCodeData statusCodeData = new StatusCodeData("01", TAG + e.getMessage());
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Failure", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		InitiateAuthenticationResp resp = new InitiateAuthenticationResp();
		resp.setHeader(head);
		return MessageHelper.gs.toJson(resp);
	}


}
