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
import com.whty.euicc.rsp.packets.message.request.HandleNotificationReq;
import com.whty.euicc.rsp.packets.message.response.HandleNotificationResp;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.util.ToTLV;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;
import com.whty.security.ecc.ECCUtils;

/**
 * ES9+.HandleNotification()接口，LPAd调用返回profile安装结果
 * 入参：pendingNotification
 * 出参：无
 * @author 11
 *
 */
@Service("/gsma/rsp2/es9plus/handleNotification")
public class HandleNotificationHandler extends AbstractHandler {
	private Logger logger = LoggerFactory.getLogger(HandleNotificationHandler.class);
	
	private final static String TAG = " *** HandleNotificationHandler *** ";
	
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
			logger.info("handleNotification begin");
			EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es9plus/handleNotification");
			HandleNotificationReq reqBody = (HandleNotificationReq) reqMsgObject.getBody();
			String profileInstallationResultData = reqBody.getProfileInstallationResultData();
			String euiccSignPIR = reqBody.getEuiccSignPIR();
			logger.info("profileInstallationResultData:{},euiccSignPIR:{}",profileInstallationResultData,euiccSignPIR);
			
			
			
			String matchingId = checkProfileInstallationResultData(profileInstallationResultData);
			boolean result = checkEuiccSignPIR(euiccSignPIR, profileInstallationResultData);
			if(!result){
				throw new RuntimeException("Error >> checkEuiccSignPIR");
			}
			//修改profile状态
			setProfileStates(matchingId, ProfileStates.PROFILE_INSTALLED);
			
			logger.info("handleNotification end");
			return httpPostResponseByRsp("application/json",respBodyByBean());
			
		} catch (Exception e) {
			e.printStackTrace();
			return httpPostResponseByRsp("application/json", respBodyByBeanError(e));
		}
		
	}
	
	//验签名,本地测试数据中的transactionId会影响结果
	private boolean checkEuiccSignPIR(String euiccSignPIR, String profileInstallationResultData) {
		String transactionId = ToTLV.toTLV("80", ToTLV.determineTLV(profileInstallationResultData, "80", "BF2F"));
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString(transactionId), CacheBean.class);
		String PkEuiccEcdsa = cacheBean.getPkEuiccEcdsa();
		String euiccSignPIRVal = euiccSignPIR.substring(6);
		spax = PkEuiccEcdsa.substring(0, 64);
		System.out.println("spax ************** "+spax);
		spay = PkEuiccEcdsa.substring(64);
		System.out.println("spay ************** "+spay);
		sM = profileInstallationResultData;
		System.out.println("sM ************** "+sM);
		sR = euiccSignPIRVal.substring(0, 64);
		System.out.println("sR ************** "+sR);
		sS = euiccSignPIRVal.substring(64);
		System.out.println("sS ************** "+sS);
		return ECCUtils.eccECKAVerify(P, A, B, Gx, Gy, N, H, sM, spax, spay, sR, sS);
		
	}

	/*
	 * 找为transactionId标识的未决订单，验transactionId与缓存中的是否一样
	 */
	private String checkProfileInstallationResultData(String profileInstallationResultData) {
		String transactionId = ToTLV.determineTLV(profileInstallationResultData, "80", "BF2F");
		//找缓存中的transactionId,若transactionId一致，设置profile状态为"Installed",若不一致，设置为"Error"
		CacheBean cacheBean = MessageHelper.gs.fromJson(new JvmCacheUtil().getString(ToTLV.toTLV("80", transactionId)), CacheBean.class);
		if(cacheBean == null){
			throw new RuntimeException("transactionId不一致");
		}else{
			String matchingId = cacheBean.getMatchingId(); 
			return matchingId;
		}
	}

	/*
	 * 修改profile状态为installed
	 */
	private void setProfileStates(String matchingId,String profileState) {
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByMatchingId(matchingId);
		String stateOfProfile = rspProfileWithBLOBs.getProfileState();
		if(StringUtils.equals(stateOfProfile, ProfileStates.PROFILE_DOWNLOADED)){
			rspProfileWithBLOBs.setProfileState(profileState);
			rspProfileService.updateByPrimaryKeySelective(rspProfileWithBLOBs);
		}else{
			throw new RuntimeException("当前profile状态不符合条件");
		}
	}

	private String respBodyByBean(){
		StatusCodeData statusCodeData = new StatusCodeData("00");
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Success", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		HandleNotificationResp resp = new HandleNotificationResp();
		resp.setHeader(head);
		return MessageHelper.gs.toJson(resp);
	}
	
	private String respBodyByBeanError(Exception e){
		StatusCodeData statusCodeData = new StatusCodeData("01", TAG + e.getMessage());
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Failure", statusCodeData);
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		HandleNotificationResp resp = new HandleNotificationResp();
		resp.setHeader(head);
		return MessageHelper.gs.toJson(resp);
	}
}
