package com.whty.euicc.sr.handler.tls;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.profile.util.Tool;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * Https下状态查询的apdu指令
 * @author Administrator
 *
 */
@Service("getStatusApdu")
public class GetStatusApduHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(GetStatusApduHandler.class);
	
	//启用ProfileAPDU参数
	private final String CLA = "80";
	private final String INS = "F2";
	private final String P1 = "40";
	private final String P2 = "02";
	private String Lc;
	private final String Le = "";
	@Autowired
	private EuiccProfileService profileService;		
	/**
	 * 下发获取ISD-P状态指令
	 */
	@Override
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);	
		SmsTrigger eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
		String getStatusApdu = getStatusApdu(eventTrigger.getIsdPAid());  //检验目标Profile的当前状态的操作已经在校验请求参数里面已经执行过了
		return httpPostResponseNoEnc(getStatusApdu);
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
		    eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
		    eventTrigger.setState(checkResp(requestStr,eventTrigger.getIsdPAid()));
		    System.out.println("eventTrigger.setState: " + eventTrigger.getState());
		    updateStatus(eventTrigger);
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
	
	private String getStatusApdu(String isdPAid) {
		StringBuilder data =  new StringBuilder().append("4F").append(ToTLV.toTLV(isdPAid)).append("5C").append("034F9F70");
		Lc = Tool.toHex(String.valueOf(data.toString().length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(Lc).append(data).append(Le);
		return ToTLV.toTLV("22", apdu.toString());
	}

	/**
	 * 人为抛异常为检查不合格
	 */
	private String checkResp(String requestStr ,String isdpAid) {
		//todo
		logger.info("card resp:{}",requestStr);
		int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
		System.out.println(endOfDbl0D0A);
		String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
		logger.info("apdu:{}",requestStr);
		String state = paserCardContent(strData ,isdpAid);
		if(StringUtils.equals(state,"3F")){   //相应返回值为‘3F’，则卡的状态为启用状态
			state = ProfileState.ENABLE;
		}else if(StringUtils.equals(state,"1F")){  //相应返回值为‘1F’，则卡的状态为禁用状态
			state = ProfileState.DIS_ENABLE;
		}else if(StringUtils.equals(state,"6A88")){  //相应返回值为‘6A88’，则卡的状态为删除状态
			state = ProfileState.DELETE;
		}else{
			state = "99";
		}
		return state;
	}
	
	private String paserCardContent(String responseData , String IsdPAid){
		String IsdPStatus = "";
		String dataStr = "";
		if(!responseData.substring(10, 18).equals("23026A88")){
			dataStr = responseData.substring(16, 78);
			if(!dataStr.substring(0, 46).equals("E3164F10" + IsdPAid +"9F7001")){
				logger.info("paserCardContent:{}","The state of queried Profile has exception");
				throw new EuiccBusiException("9010","The state of queried Profile has exception");
			}
			IsdPStatus = dataStr.substring(46, 48);
		}
		else{
			IsdPStatus = responseData.substring(14, 18);
		}
			
		return IsdPStatus;
	}
	
	private void updateStatus(SmsTrigger eventTrigger) {
		EuiccProfileWithBLOBs profile = new EuiccProfileWithBLOBs();
		profile.setEid(eventTrigger.getEid());
		profile.setIccid(eventTrigger.getIccid());
		profile.setIsdPAid(eventTrigger.getIsdPAid());
		profile.setState(eventTrigger.getState());
		if(StringUtils.equals(eventTrigger.getState(), ProfileState.DELETE)){
			profileService.deleteProfile(profile);
		}else{
			profileService.updateStatus(profile);
		}
	}
}
