/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-7
 * Id: SetFallBackAttrApduHandler.java,v 1.0 2016-11-7 下午2:20:47 Administrator
 */
package com.whty.euicc.sr.handler.tls;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.constant.ProfileFallBack;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccIsdPService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.profile.util.Tool;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * @ClassName SetFallBackAttrApduHandler
 * @author Administrator
 * @date 2016-11-7 下午2:20:47
 * @Description TODO(SR设置回调属性)
 */
@Service("setFallBackAttrApdu")
public class SetFallBackAttrApduHandler extends AbstractHandler {

	private Logger logger = LoggerFactory.getLogger(SetFallBackAttrApduHandler.class);

	// 启用ProfileAPDU参数
	private final String CLA = "80";
	private final String INS = "E2";
	private final String P1 = "88";
	private final String P2 = "00";
	private String Lc;
	private final String Le = "";
	
	@Autowired
	private EuiccProfileService profileService;
	

	/**
	 * 下发获取ISD-P回退属性指令
	 */
	@Override
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);
		SmsTrigger eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
		String setFallBackAttrApdu = setFallBackAttrApdu(eventTrigger.getIsdPAid());
		logger.info("fallBackApdu>>>"+setFallBackAttrApdu);
		return httpPostResponseNoEnc(setFallBackAttrApdu);
	}

	/**
	 * 检查返回结果,通知前端调用者，查询Profile状态
	 */
	@Override
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		try {
			String eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);
			eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
			checkResp(requestStr);
			updateProfile(eventTrigger);
		} catch (Exception e) {
			logger.error("checkProcessResp error:{}", e.getMessage());
			eventTrigger.setErrorMsg(e.getMessage());
			e.printStackTrace();
			processResult = false;
		} finally {
			SmsTriggerUtil.notifyProcessResult(eventTrigger, processResult);
		}
		return processResult;
	}
	/**
	 * 认为抛异常检查不合格
	 * @param requestStr
	 */
	private void checkResp(String requestStr) {
		logger.info("card resp:{}",requestStr);
		int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
		System.out.println(endOfDbl0D0A);
		String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
		logger.info("apdu:{}",requestStr);
		String dataCheck = paserCardContent(strData);
		String errorNote = "";
		if(StringUtils.equals(dataCheck,"9000")){   //相应返回值为‘9000’，则命令执行成功
			errorNote = "Command execution success";
		}else if(StringUtils.equals(dataCheck,"6A88")){  //相应返回值为‘6A88’，则引用数据无法找到
			errorNote = "Referenced data not found";
		}else if(StringUtils.equals(dataCheck,"6A84")){  //相应返回值为‘6A84’，则内存空间不够
			errorNote = "Not enough memory space";
		}else if(StringUtils.equals(dataCheck,"6A80")){  //相应返回值为‘6A80’，则APDU指令data域参数不正确
			errorNote = "Incorrect parameters in data field";
		}else{
			errorNote = dataCheck + "The returned value of currently Enabled Profile has other error";
		}
		logger.info("card check:{}",errorNote);	
		if(!StringUtils.equals(dataCheck,"9000")){
			throw new EuiccBusiException("8010",errorNote);
		}
		
	}

	private String setFallBackAttrApdu(String isdPAid) {
		String len = Tool.toHex(String.valueOf(isdPAid.length()/2));
		StringBuilder isdPId = new StringBuilder().append("4F").append(len).append(isdPAid);
		String data = ToTLV.toTLV("3A05" ,isdPId.toString());
		Lc = Tool.toHex(String.valueOf(data.toString().length() / 2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS)
				.append(P1).append(P2).append(Lc).append(data).append(Le).append("00");
		return ToTLV.toTLV("22", apdu.toString());
	}

	private void updateProfile(SmsTrigger eventTrigger) {
		EuiccProfileWithBLOBs profile = new EuiccProfileWithBLOBs();
		profile.setEid(eventTrigger.getEid());
		profile.setIsdPAid(eventTrigger.getIsdPAid());
		profile.setIccid(eventTrigger.getIccid());
				
		// 查找并更新profile的fallBack属性数据
		EuiccProfileWithBLOBs retProfile = profileService.selectByEidAndIsdPAid(profile);
		if (retProfile == null) {
			logger.info("查询profile 不存在!  "+"  eid>>"+profile.getEid()+"  isdpAid>>"+profile.getIsdPAid());
			throw new EuiccBusiException("0002", "当前eUICC卡中没有具有给定的profile");
		}
		if (!StringUtils.equals(retProfile.getFallbackAttribute(), ProfileFallBack.FALLBACK_YES)) {
			
			// 如果查找到的profile 不是 回滚属性为yes，那么修改该值
			profile.setFallbackAttribute(ProfileFallBack.FALLBACK_YES);
			int ret = profileService.updateFallBackAttr(profile);
			if(ret == 0){
				throw new EuiccBusiException("0002", "更新设置FallBack属性的profile异常");
			}
		}
		
		// 将另外一个回滚属性置为false
		EuiccProfileWithBLOBs profilePar = new EuiccProfileWithBLOBs();
		profilePar.setEid(eventTrigger.getEid());
		profilePar.setFallbackAttribute(ProfileFallBack.FALLBACK_YES);
		List<EuiccProfileWithBLOBs> listProfile = profileService.selectByEidAndFallBack(profilePar);
		if (listProfile != null) {
			for (int index=0;index<listProfile.size();index++) {
				EuiccProfileWithBLOBs profileFallBack = listProfile.get(index);
				
				// 如果不是设置的这个profile,全部的属性设置为空 fallBack_no
				if (!StringUtils.equals(profile.getIsdPAid(), profileFallBack.getIsdPAid())) {
					
					// 更新回滚属性
					profileFallBack.setFallbackAttribute(ProfileFallBack.FALLBACK_NO);
					int ret = profileService.updateFallBackAttr(profileFallBack);
					if(ret == 0){
						throw new EuiccBusiException("0002", "更新取消FallBack属性的profile异常 >>> "+"isdpAid>>>"+profileFallBack.getIsdPAid());
					}
				}
			}
		}
	}
	
	/**
	 * 截取返回码
	 * @param responseData
	 * @return
	 */
	private String paserCardContent(String responseData){
		String retCheck = "1111";
		if(!responseData.substring(2, 12).equals("0D0AAF8023")){
			return "9999";
		}
		int offSize = responseData.indexOf("00000D0A30");
		retCheck = responseData.substring(offSize-4, offSize);
		logger.info("processing result code：" + retCheck);
		return retCheck;
	}
}
