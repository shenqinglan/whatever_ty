package com.whty.euicc.rsp.handler.tls;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.rsp.cache.CacheBean;
import com.whty.euicc.rsp.cache.JvmCacheUtil;
import com.whty.euicc.rsp.constant.EccProperties;
import com.whty.euicc.rsp.constant.ProfileStates;
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.CancelSessionReq;
import com.whty.euicc.rsp.packets.message.response.CancelSessionResp;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;
import com.whty.security.ecc.ECCUtils;

/**
 * ES9+.CancelSession()接口
 * 出参：transactionId,cancelSessionResponse
 * 入参：无
 * @author 11
 *
 */
@Service("/gsma/rsp2/es9plus/cancelSession")
public class CancelSessionHandler extends AbstractHandler {
	private Logger logger = LoggerFactory.getLogger(CancelSessionHandler.class);
	
	private final static String TAG = "*** CancelSessionHandler ***";
	
	@Autowired
	private RspProfileService rspProfileService;
	
	private final String P = EccProperties.P;
	private final String A = EccProperties.A;
	private final String B = EccProperties.B;
	private final String Gx = EccProperties.Gx;
	private final String Gy = EccProperties.Gy;
	private final String N = EccProperties.N;
	private final String H = EccProperties.H;
	private String spax;
	private String spay;
	private String sM;
	private String sR;
	private String sS;
	
	@Override
	public byte[] handle(String requestStr) {
		try {
			logger.info("cancelSession begin");
			EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/cancelSession");
			CancelSessionReq reqBody = (CancelSessionReq) reqMsgObject.getBody();
			String transactionId = reqBody.getTransactionId();
			String euiccCancelSessionSigned = reqBody.getEuiccCancelSessionSigned();
			String euiccCancelSessionSignature = reqBody.getEuiccCancelSessionSignature();
			logger.info("transactionId:{},euiccCancelSessionSigned:{},euiccCancelSessionSignature:{}",
					     transactionId,euiccCancelSessionSigned,euiccCancelSessionSignature);
			
			//验transactionId
			if(!checkTransactionId(transactionId)){
				throw new RuntimeException("transactionId不一致");
			}
			
			//检查euiccCancelSessionSigned{transactionId,smdpOid,reason}，获取原因
			getReason(transactionId, euiccCancelSessionSigned);
			//验签
			boolean result = checkEuiccCancelSessionSignature(euiccCancelSessionSigned, euiccCancelSessionSignature, transactionId);
			if(!result){
				throw new RuntimeException("Error >> checkEuiccCancelSessionSignature");
			}
			
			//检查cancelSessionResponse(euiccCancelSessionSigned{transactionId,smdpOid,reason},euiccCancelSessionSignature)
			return httpPostResponseByRsp("application/json",respBodyByBean());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return httpPostResponseByRsp("application/json", respBodyByBeanError(e));
		}
		
	}
	
	/*
	 * 用PK.EUICC.ECDSA验签
	 */
	private boolean checkEuiccCancelSessionSignature(String euiccCancelSessionSigned, String euiccCancelSessionSignature, String transactionId) {
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString(transactionId), CacheBean.class);
		String PkEuiccEcdsa = cacheBean.getPkEuiccEcdsa();
		String euiccCancelSessionSignatureVal = euiccCancelSessionSignature.substring(6);
		spax = PkEuiccEcdsa.substring(0, 64);
		spay = PkEuiccEcdsa.substring(64);
		sM = euiccCancelSessionSigned;
		sR = euiccCancelSessionSignatureVal.substring(0, 64);
		sS = euiccCancelSessionSignatureVal.substring(64);
		return ECCUtils.eccECKAVerify(P, A, B, Gx, Gy, N, H, sM, spax, spay, sR, sS);
	}

	/*
	 * 判断reason，改profile状态，若为'End user postponed'/ 'Timeout'，则"Released";若"End user rejection"，则"Error"
	 */
	private void getReason(String transactionId, String euiccCancelSessionSigned) {
		// TODO，不同原因不同数据库处理
		//截取reason,tag为82
		String reason = euiccCancelSessionSigned.substring(euiccCancelSessionSigned.indexOf("8201")+"8201".length());
		String profileState = "";
		if(StringUtils.equals(reason, "01") || StringUtils.equals(reason, "02")){
			profileState = ProfileStates.PROFILE_RELEASED;
		}else{
			profileState =  ProfileStates.PROFILE_ERROR;
		}
		modifyProfile(transactionId, profileState);
		
	}

	/**
	 * 修改profile状态
	 * @param transactionId
	 * @param profileState 
	 */
	private void modifyProfile(String transactionId, String profileState) {
		
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString(transactionId), CacheBean.class);
		String matchingId = cacheBean.getMatchingId();
		setProfileState(matchingId, profileState);
		
	}

	/*
	 * 验transactionId
	 */
	private boolean checkTransactionId(String transactionId) {
		String cache = new JvmCacheUtil().getString(transactionId);
		return StringUtils.isBlank(cache) ? false : true;
	}

	private void setProfileState(String matchingId, String profileState) {
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByMatchingId(matchingId);
		rspProfileWithBLOBs.setProfileState(profileState);
		rspProfileService.updateByPrimaryKeySelective(rspProfileWithBLOBs);
	}

	private String respBodyByBean() {
		StatusCodeData statusCodeData = new StatusCodeData("00");
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Success", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		CancelSessionResp resp = new CancelSessionResp();
		resp.setHeader(head);
		return MessageHelper.gs.toJson(resp);
	}
	
	private String respBodyByBeanError(Exception e){
		StatusCodeData statusCodeData = new StatusCodeData("01", TAG + e.getMessage());
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Failure", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		CancelSessionResp resp = new CancelSessionResp();
		resp.setHeader(head);
		return MessageHelper.gs.toJson(resp);
	}

}
