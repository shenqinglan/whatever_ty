package com.whty.euicc.sr.handler.tls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;
import com.whty.security.scp03forupdate.Scp03ForUpdate;
/**
 * https下获取scp03连接参数的apdu指令
 * @author Administrator
 *
 */
@Service("getScp03CounterApdu")
public class GetScp03CounterApduHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(GetScp03CounterApduHandler.class);
	private String kerV = "00";
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);
		SmsTrigger eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
		String isdPAID = eventTrigger.getIsdPAid();
		String apdu = getScp03SequenceApdu();
//		updateTrigger(eid);
		return httpPostResponseNoEnc(apdu,isdPAID);
	}
	
	private String getScp03SequenceApdu() {
		String iniString = Scp03ForUpdate.initializeUpdate(kerV).getApdu();
		String commandString = ToTLV.toTLV("22", iniString);
		System.out.println("sequence 指令：" + commandString);
		return commandString;
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
			checkResp(requestStr,eid);
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
	
	/**
	 * 人为抛异常为检查不合格
	 */
	private boolean checkResp(String requestStr,String eid) {
		String receipt = "";
		logger.info("card resp:{}",requestStr);
		
		try{
			int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
			
			System.out.println("endOfDbl0D0A 66666544444:" + endOfDbl0D0A);
			String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
			logger.info("apdu:{}",strData);
			int endofstrData =strData.indexOf("9000");
			int beginString = strData.length() - 20;
			int endString = strData.length() - 14;
			if(endofstrData ==-1){
				throw new RuntimeException("卡片返回值不为9000");
			}else{
				logger.info("卡片返回值（在checkResp中）：" + strData);
				//int endOf86=strData.indexOf("8610")+"8610".length();
				//receipt=strData.substring(endOf86,endOf86+32);
				receipt = strData.substring(beginString, endString);
				System.out.println("Receipt checking >>>"+receipt);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		CacheUtil.put("scp03-recept-" + eid, receipt);
		return true;
		
	}
		
	
	private void updateTrigger(SmsTrigger eventTrigger) {
		eventTrigger.setStep(eventTrigger.getStep() + 1);
		eventTrigger.setAllStep(4);
		CacheUtil.put(eventTrigger.getEid(), new Gson().toJson(eventTrigger));
	}

}
