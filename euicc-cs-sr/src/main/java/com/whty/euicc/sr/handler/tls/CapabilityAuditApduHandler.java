package com.whty.euicc.sr.handler.tls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.profile.util.Tool;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;
/**
 * 卡片审计APDU指令
 * @author Administrator
 *
 */
@Service("capabilityAuditApdu")
public class CapabilityAuditApduHandler extends AbstractHandler {
	private final String CLA = "80";
	private final String INS = "CA";
	private final String P1 = "BF";
	private final String P2 = "30";
	private String Lc = "";
	private final String data = "5C0166";
	private final String Le = "00";
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	@Override
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);	
		SmsTrigger eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
		String getDataApdu = getAuditApdu();  
		System.out.println("apdu:" + getDataApdu);
		return httpPostResponseNoEnc(getDataApdu);
	}
	@Override
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		try {
			String eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);	
		    eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
		} catch (Exception e) {
			e.printStackTrace();
			processResult = false;
		}finally{
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}
		return processResult;
	}
	private String getAuditApdu() {
		Lc =  Tool.toHex(String.valueOf(data.length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(Lc).append(data).append(Le);
		return ToTLV.toTLV("22", apdu.toString());
		
	}

}
