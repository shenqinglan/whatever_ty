package com.whty.euicc.sr.handler.tls;

import static com.whty.euicc.common.utils.StringUtils.appends;
import static com.whty.euicc.common.apdu.ToTLV.toTLV;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;
import com.whty.security.aes.AesCmac;
/**
 * Https下主删除的apdu指令
 * @author Administrator
 *
 */
@Service("masterDeleteApdu")
public class MasterDeleteApduHandler extends AbstractHandler{
private Logger logger = LoggerFactory.getLogger(MasterDeleteApduHandler.class);
	
	private final String CLA = "80";
	private final String INS = "E4";
	private final String P1 = "00";
	private final String P2 = "40";
	private final String Le = "00";
	@Autowired
	private EuiccProfileService profileService;
	/**
	 * 删除profile的apdu指令
	 */
	@Override
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);	
		SmsTrigger eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
		String deleteApdu = masterDeleteApdu(eid ,eventTrigger.getIsdPAid());  //检验目标Profile的当前状态的操作已经在校验请求参数里面已经执行过了
		return httpPostResponseNoEnc(deleteApdu);
	}
	
	/**
	 * 检查返回结果,通知前端调用者，更新EIS
	 */
	@Override
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		try {
			String eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);	
		    eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
			checkResp(requestStr);
			updateEIS(eventTrigger);
			
		} catch (Exception e) {
			logger.error("checkProcessResp error:{}",e.getMessage());
			eventTrigger.setErrorMsg(e.getMessage());
			e.printStackTrace();
			processResult = false;
		}finally{
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}
		return processResult;
	}
	
	private String masterDeleteApdu(String eid ,String isdPAid) {
		//从euicc_profile表中获取这些信息
		EuiccProfileWithBLOBs profile = getProfile(eid, isdPAid);
		
		String tokenKey = profile.getDeleteTokenKey();
		String isdPId = toTLV("42", profile.getIsdPIdNo());
		String isdPImage = toTLV("45", profile.getIsdPImageNo());
		String appProvId = toTLV("5F20", profile.getApplicationProviderNo());
		String tokenId = toTLV("93", profile.getTokenIdNo());
		String str = appends(isdPId,isdPImage,appProvId,tokenId);
		String contrRefer = toTLV("B6" ,str);
		String aid =  toTLV("4F" ,isdPAid);
		String inputData = toTLV(P1+P2,aid+contrRefer);
		String deleteTokenMac = toTLV("9E" ,deleteToken(inputData, tokenKey));
		String apdu = toTLV(appends(CLA,INS,P1,P2), appends(aid,contrRefer,deleteTokenMac)) + Le;
		return toTLV("22", apdu);
	}

	/**
	 * 人为抛异常为检查不合格
	 */
	private void checkResp(String requestStr) {
		//todo
				logger.info("card resp:{}",requestStr);
				int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
				System.out.println(endOfDbl0D0A);
				String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
				logger.info("apdu:{}",requestStr);
				String dataCheck = paserCardContent(strData);
				String errorNote = "";
				if(StringUtils.equals(dataCheck,"9000")){   //相应返回值为‘9000’，则命令执行成功
					errorNote = "Command execution success";
				}else if(StringUtils.equals(dataCheck,"6581")){  //相应返回值为‘6A80’，则命令数据的值不对
					errorNote = "Memory failure";
				}else if(StringUtils.equals(dataCheck,"6A88")){  //相应返回值为‘6A84’，则存储空间不足
					errorNote = "Referenced data not found";
				}else if(StringUtils.equals(dataCheck,"6A82")){  //相应返回值为‘6A88’，则引用数据无法找到
					errorNote = "Application not found";
				}else if(StringUtils.equals(dataCheck,"6A80")){  //相应返回值为‘6A88’，则引用数据无法找到
					errorNote = "Incorrect values in command data";
				}else if(StringUtils.equals(dataCheck,"6985")){  //相应返回值为‘6985’，则Profile处于启用状态或包含Fallback属性
					errorNote = "Profile is not in the Disabled state or Profile has the Fall-back Attribute";
				}else{
					errorNote = dataCheck + "The returned value of currently  Master Deletion Profile has other error";
				}
				logger.info("card check:{}",errorNote);	
				if(!StringUtils.equals(dataCheck,"9000")){
					throw new EuiccBusiException("8010",errorNote);
				}
	}
	private String paserCardContent(String responseData){
		String IsdPCheck = "1111";
		if(!responseData.substring(2, 12).equals("0D0AAF8023")){
			return "9999";
		}
		if(responseData.indexOf("00000D0A30") == 18){
			IsdPCheck = responseData.substring(14, 18);
		}else if(responseData.indexOf("00000D0A30") == 20){
			IsdPCheck = responseData.substring(16, 20);
		}
		return IsdPCheck;
	}
	private void updateEIS(SmsTrigger eventTrigger) {
		EuiccProfileWithBLOBs profile = new EuiccProfileWithBLOBs();
		profile.setEid(eventTrigger.getEid());
		profile.setIccid(eventTrigger.getIccid());
		profile.setIsdPAid(eventTrigger.getIsdPAid());
		profileService.deleteProfile(profile);
	}
	
	private String deleteToken(String data ,String tokenKey){
		AesCmac mac = null;
		try {
			mac = new AesCmac();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String deleteToken = null;
		try {
			deleteToken = mac.calcuCmac1(data ,tokenKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteToken;
	}
	
	private EuiccProfileWithBLOBs getProfile(String eid ,String isdPAid) {
		return profileService.selectByEidAndIsdPAid(eid ,isdPAid);
	}
}
