/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: GetBoundProfilePackageHandler.java,v 1.0 2016-12-6 下午3:51:35 Administrator
 */
package com.whty.euicc.rsp.handler.tls.es9plus;

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
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.es9plus.GetBoundProfilePackageReq;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.message.response.es9plus.GetBoundProfilePackageResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.profile.ProfileUtil;
import com.whty.security.ecc.ECCUtils;

/**
 * @ClassName GetBoundProfilePackageHandler
 * @author Administrator
 * @date 2016-12-6 下午3:51:35
 * @Description TODO(函数用来请求绑定Profile Package到eUICC，函数的执行依赖于ES9+.AuthenticateClient)
 */
@Service("/gsma/rsp2/es9plus/smdp/getBoundProfilePackage")
public class GetBoundProfilePackageHandler extends AbstractHandler implements ResponseMsgBodyImpl{

	private Logger logger = LoggerFactory.getLogger(GetBoundProfilePackageHandler.class);
	
	private final String P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
	private final String A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
	private final String B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
	private final String Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
	private final String Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
	private final String N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
	private final String H="1";
	private final String Q_ECASD_ECKA="3F157A6242EC54888EB50967BD84D1A885E51D66B2F6CD135354724C91D790EDB48B015F6B272DF50E49EAB2E1383BF0EEB0E9848543B971397D274D88E8EA77";
	private final String Qx_ECASD_ECKA=Q_ECASD_ECKA.substring(0, 64);
	private final String Qy_ECASD_ECKA=Q_ECASD_ECKA.substring(64);
	private final int Keylen=64;
	private final String share="108810";

	/** 
	 * @author Administrator
	 * @date 2016-12-6
	 * @param requestStr
	 * @return
	 * @Description TODO(处理函数，把输入的数据传到Profile Package Binding)
	 * @see com.whty.euicc.handler.base.AbstractHandler#handle(java.lang.String)
	 */
	@Override
	public byte[] handle(String requestStr) {
		logger.info("GetBoundProfilePackage begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/smdp/getBoundProfilePackage");
		GetBoundProfilePackageReq reqBody = (GetBoundProfilePackageReq) reqMsgObject.getBody();
		String transactionId = reqBody.getTransactionId();
		String euiccSigned2 = reqBody.getEuiccSigned2();
		String euiccSignature2 = reqBody.getEuiccSignature2();
		Boolean checkFlag = true;
		logger.info("transactionId:{},euiccSigned2:{},euiccSignature2:{}",transactionId,euiccSigned2,euiccSignature2);
		
		/**
		 * 校验 transactionId
		 */
		checkFlag = checkTransactionId(transactionId);
		
		/**
		 * 校验 euiccSignature2
		 */
		checkFlag = checkEuiccSignature2(euiccSignature2);
		
		/**
		 * 校验euiccSigned2
		 */
		checkFlag = checkEuiccSigned2(euiccSigned2, transactionId);
		
		/**
		 * 反复多次校验确认码 confirmationCode，如果在es2.confirmCode，生成了校验码的
		 */
		checkFlag = checkConfirmationCode();
		
		/**
		 * 生成一次性的ECKA key pair 包括 otPK.DP.ECKA 和  otSK.DP.ECKA
		 */
		String eCKAKeyPair = generateECKAKeyPair();
		
		/**
		 * 生成sessionKeys(S-ENC and S-MAC)，如何生成otPK.EUICC.ECKA和前面生成的otSK.DP.ECKA
		 */
		String otSK_DP_ECKA = eCKAKeyPair.substring(0, 64);
		String sessionKey = generateSessionKeys(otSK_DP_ECKA);
		String initialMacChaining = sessionKey.substring(0,32);
		String S_ENC=sessionKey.substring(32,64);
		String S_MAC=sessionKey.substring(64,96);

		/**
		 * 生成 Bound Profile Package
		 */
		String boundProfilePackage = generateBoundProfilePackage("00","12345678",initialMacChaining,S_ENC,S_MAC);
		
		checkFlag = true;
		if (checkFlag) {
			GetBoundProfilePackageResp resp = new GetBoundProfilePackageResp();
			resp.setTransactionId("123456");
			resp.setBoundProfilePackage(boundProfilePackage);
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeSuccess(),new ResponseStatusSuccess()), resp));
		} else {
			StatusCodeData scd = new StatusCodeData("8.2.5", "3.7", "No more Profile");
			return httpPostResponseByRsp("application/json",responseDataJson(
					new ResponseBodyHeader(new ResponseStatusCodeFailed(scd),new ResponseStatusFailed()), new GetBoundProfilePackageResp()));
		}
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @param transactionId
	 * @return
	 * @Description TODO(校验 transactionId)
	 */
	private Boolean checkTransactionId(String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @param euiccSignature2
	 * @return
	 * @Description TODO(校验 euiccSignature2)
	 */
	private Boolean checkEuiccSignature2(String euiccSignature2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @author Administrator
	 * @date 2016-12-14
	 * @param euiccSigned2
	 * @return
	 * @Description TODO(校验euiccSigned2)
	 */
	private Boolean checkEuiccSigned2(String euiccSigned2, String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(反复多次校验确认码 confirmationCode，如果在es2.confirmCode，生成了校验码的话，如何校验)
	 */
	private Boolean checkConfirmationCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(生成一次性的ECKA key pair，如何生成)
	 */
	private String generateECKAKeyPair() {
		String keyPair = ECCUtils.createECCKeyPair(P, A, B, Gx, Gy, N, H);
		if(StringUtils.isBlank(keyPair)){
			throw new RuntimeException("ECC生成keyPair算法出错");
		}
		return keyPair;
	}

	/**
	 * 
	 * @author Administrator
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(生成sessionKeys(S-ENC and S-MAC)，如何生成otPK.EUICC.ECKA和前面生成的otSK.DP.ECKA)
	 */
	private String generateSessionKeys(String otSK_DP_ECKA) {
		String sessionKey = ECCUtils.eccKeyAgreement(P, A, B, Gx, Gy, N, H, otSK_DP_ECKA, Qx_ECASD_ECKA, Qy_ECASD_ECKA, share, Keylen);
		if(StringUtils.isBlank(sessionKey)){
			throw new RuntimeException("ECC生成shs算法出错");
		}
		return sessionKey;
	}

	/**
	 * @author Administrator
	 * @param s_MAC 
	 * @param s_ENC 
	 * @param initialMacChaining 
	 * @param string2 
	 * @param string 
	 * @date 2016-12-14
	 * @return
	 * @Description TODO(生成 Bound Profile Package)
	 */
	private String generateBoundProfilePackage(String iccid, String UPP, String initialMacChaining, String s_ENC, String s_MAC) {
		// TODO Auto-generated method stub
		return new ProfileUtil().generateBPP(iccid, UPP, initialMacChaining, s_ENC, s_MAC);
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
		GetBoundProfilePackageResp resp = (GetBoundProfilePackageResp) responseBody;
		HeaderResp header = bodyHeader.getResponseBodyHeader();
		resp.setHeader(header);
		return MessageHelper.gs.toJson(resp);
	}
}
