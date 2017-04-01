package com.whty.euicc.sr.handler.tls;



import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * 删除profile的apdu指令
 * @author Administrator
 *
 */
@Service("deleteProfileApdu")
public class DeleteProfileApduHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(DeleteProfileApduHandler.class);
	
	private final String CLA = "80";
	private final String INS = "E4";
	private final String P1 = "00";
	private final String P2 = "40";
	private String Lc="12";
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
		String deleteApdu = profileDeletionApdu(eventTrigger.getIsdPAid());  //检验目标Profile的当前状态的操作已经在校验请求参数里面已经执行过了
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
	
	private String profileDeletionApdu(String isdPAid) {
		StringBuilder data =  new StringBuilder().append("4F").append("10").append(isdPAid);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(Lc).append(data).append(Le);
		return ToTLV.toTLV("22", apdu.toString());
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
					errorNote = "Profile is in Enable State or Profile has the Fall-back Attribute";
				}else if(StringUtils.equals(dataCheck,"69E1")){  //相应返回值为‘69E1’，则当前启用Profile的POL1阻止了删除操作
					errorNote = "POL1 of the Profile prevents deletion";
				}else{
					errorNote = dataCheck + "The returned value of currently Deletion Profile has other error";
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
}
