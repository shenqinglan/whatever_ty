package com.whty.euicc.sr.handler.tls.change.smsr1_send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * step 8 - step 12
 * sr1与卡交互,检查卡生成返回的RC
 * @author lw
 *
 */
@Service("authenticateSMSRApdu")
public class AuthenticateSMSRApduHandler extends AbstractHandler {
private Logger logger = LoggerFactory.getLogger(AuthenticateSMSRApduHandler.class);

	private String ISDR = "A0000005591010FFFFFFFF8900000100";//todo
	
	@Override
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);	
		SmsTrigger eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);

		byte[] apdu = null;
		if(!checkInitialConditions()){
			throw new EuiccBusiException("0101", "检查初始化状态错误");
		}
		
		apdu = eventTrigger.getCertSrEcdsa().getBytes();
		System.out.println("apdu1 >>>" + new String(apdu));
		
		return httpPostResponseNoEnc(new String(apdu),ISDR);
	}

	@Override
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		
		try {
			String eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);	
			eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
			
			System.out.println("**********************1**************************");
			checkFirstStoreDataResp(eid,requestStr);
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}catch (Exception e) {
			logger.error("checkProcessResp error:{}",e.getMessage());
			eventTrigger.setErrorMsg(e.getMessage());
			e.printStackTrace();
			processResult = false;
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}
		return processResult;
	}

	private boolean checkFirstStoreDataResp(String eid,String requestStr) {
		String RC = null;
		try {
			int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
			System.out.println(endOfDbl0D0A);
			String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
			logger.info("apdu:{}",strData);
			if(strData.indexOf("9000")==-1){
				throw new EuiccBusiException("0101","Card response is not 9000");
			}else{
				int endOf85=requestStr.indexOf("8510")+"8510".length();
				RC=requestStr.substring(endOf85,endOf85+32);
				System.out.println("RC checking >>>"+RC);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			if(e instanceof EuiccBusiException){
				throw new EuiccBusiException("0101", "Card response is not 9000");
			}
		}
		CacheUtil.put("change-RC-"+eid, RC);
		return true;
	}
	
	private boolean checkInitialConditions() {
		return true;
	}
}
