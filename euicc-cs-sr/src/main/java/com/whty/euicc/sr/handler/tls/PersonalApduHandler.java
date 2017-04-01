package com.whty.euicc.sr.handler.tls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.sr.handler.tls.personal.PersonalizeService;
import com.whty.euicc.sr.handler.tls.personal.SecondStoreDataForISDPApdu;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * 个人化与卡交互流程
 * @author zyj,lw
 *
 */
@Service("personalApduHandler")
public class PersonalApduHandler extends AbstractHandler {
private Logger logger = LoggerFactory.getLogger(PersonalApduHandler.class);

	private String ISDR = "A0000005591010FFFFFFFF8900000100";//todo

	@Autowired
	private PersonalizeService service;
	
	@Autowired
	private SecondStoreDataForISDPApdu second;
	
	@Override
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);	
		SmsTrigger eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);

		byte[] apdu = null;
		if(!checkInitialConditions()){
			return null;
		}
		
		if (eventTrigger.getStep() == 0) {
			apdu = service.installISDPApdu(eventTrigger);
			System.out.println("apdu >>>" + new String(apdu));
		} else if(eventTrigger.getStep() == 1){
			apdu = eventTrigger.getCertDpEcdsa().getBytes();
			System.out.println("apdu1 >>>" + new String(apdu));
		} else{
			apdu = getEpkDpEcka(eid,eventTrigger);
			System.out.println("apdu2 >>>" + new String(apdu));
		}
		return httpPostResponseNoEnc(new String(apdu),ISDR);
	}



	public byte[] getEpkDpEcka(String eid,SmsTrigger eventTrigger) {
		String key = "personal-RC-"+eid;
		String rc = CacheUtil.getString(key);
		String eSK_DP=eventTrigger.getEskDp();
		System.out.println("eSK_DP_ECKA>>"+eSK_DP);
		String ePK_DP=eventTrigger.getEpkDp();
		System.out.println("ePK_DP_ECKA>>"+ePK_DP);
//		rc = "000102030405060708090A0B0C0D0E0F";
		byte[] ePK_DP_ECKA=second.secondStoreDataForISDPApdu(rc,ePK_DP,eSK_DP);
		return ePK_DP_ECKA;
	}
	
	

	@Override
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		
		try {
			String eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);	
			eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
			if (eventTrigger.getStep() == 0) {
				System.out.println("**********************1**************************");
				service.checkInstallResp(eid,requestStr);
				updateTrigger(eventTrigger);
			} else if (eventTrigger.getStep() == 1) {
				System.out.println("**********************2**************************");
				service.checkFirstStoreDataResp(eid,requestStr);
				updateTrigger(eventTrigger);
			} else{
				System.out.println("**********************3**************************");
				service.checkSecondStoreDataResp(eid,requestStr);
				SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
			}
		} catch (Exception e) {
			logger.error("checkProcessResp error:{}",e.getMessage());
			eventTrigger.setErrorMsg(e.getMessage());
			e.printStackTrace();
			processResult = false;
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}
		return processResult;
	}


	private void updateTrigger(SmsTrigger eventTrigger) {
		eventTrigger.setStep(eventTrigger.getStep()+1);
		eventTrigger.setAllStep(2);
		CacheUtil.put(eventTrigger.getEid(), new Gson().toJson(eventTrigger));		
	}
	
	private boolean checkInitialConditions() {
		return true;
	}
}
