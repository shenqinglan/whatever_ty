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
 * step 16-21
 * sr1与卡交互，检查卡返回生成的receipt
 * @author 11
 *
 */
@Service("createAdditionalKeySetApdu")
public class CreateAdditionalKeySetApduHandler extends AbstractHandler{
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
		
		apdu = eventTrigger.getEpkSrEcka().getBytes();
		System.out.println("apdu2 >>>" + new String(apdu));
		
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
			
			System.out.println("**********************2**************************");
			checkSecondStoreDataResp(eid,requestStr);
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
			
		} catch (Exception e) {
			logger.error("checkProcessResp error:{}",e.getMessage());
			e.printStackTrace();
			processResult = false;
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}
		return processResult;
	}

	private boolean checkSecondStoreDataResp(String eid,String requestStr) {
		//;这里需要分离出卡片返回内容(0d0a0d0a后面的内容且排除最后的32字节MAC及补齐)赋值给变量
		//;返回receipt，结构为86的TLV
		String receipt = null;
		try{
			int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
			System.out.println(endOfDbl0D0A);
			String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
			logger.info("apdu:{}",strData);
			if(strData.indexOf("9000")==-1){
				throw new EuiccBusiException("0101","卡片返回值不为9000");
			}else{
//				int endOf86=requestStr.indexOf("8608")+"8608".length();
				int endOf86=requestStr.indexOf("8610")+"8610".length();     //协商scp80所需
//				receipt=requestStr.substring(endOf86,endOf86+16);
				receipt=requestStr.substring(endOf86,endOf86+32);           //协商scp80所需
				System.out.println("Receipt checking >>>"+receipt);
			}
		} catch(Exception e) {
			e.printStackTrace();
			if(e instanceof EuiccBusiException){
				throw new EuiccBusiException("0101","卡片返回值不为9000");
			}
			return false;
		}
		CacheUtil.put("change-recept-"+eid, receipt);
		return true;
	}

	private boolean checkInitialConditions() {
		return true;
	}

}
