package com.whty.euicc.sr.handler.tls.change.smsr2_receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.sr.handler.tls.change.smsr2_receive.service.FinaliseIsdrHandoverApdu;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * step 25
 * sr2与卡交互
 * @author zyj
 *
 */
@Service("deleteKeysApdu")
public class FinaliseIsdrHandoverApduHandler extends AbstractHandler {
	private Logger logger = LoggerFactory.getLogger(FinaliseIsdrHandoverApduHandler.class);
	
	@Autowired
	private FinaliseIsdrHandoverApdu finalise;
	
	@Override
	public byte[] handle(String requestStr) {
		byte[] apdu = null;
		if(!checkInitialConditions()){
			throw new EuiccBusiException("0101", "检查初始化状态返回错误");
		}
		apdu = finalise.deleteCommand();
		System.out.println("apdu3 >>> "+new String(apdu));
		
		return httpPostResponseNoEnc(new String(apdu));
		
	}
	
	@Override
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		try {
			String eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);	
			eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
			
			System.out.println("**********************3**************************");
			finalise.checkDeleteCommandResp(eid, requestStr);
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}catch (Exception e) {
			logger.error("checkProcessResp error:{}",e.getMessage());
			e.printStackTrace();
			processResult = false;
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}
		return processResult;
	}

	private boolean checkInitialConditions() {
		return true;
	}

}
