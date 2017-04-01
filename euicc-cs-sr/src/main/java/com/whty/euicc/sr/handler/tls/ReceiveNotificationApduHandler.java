package com.whty.euicc.sr.handler.tls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.dao.EuiccCardMapper;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;

//待完善
@Deprecated
@Service("receiveNotificationApdu")
public class ReceiveNotificationApduHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(ReceiveNotificationApduHandler.class);
	
	@Autowired
	private EuiccCardMapper euiccCardMapper;
	@Autowired
	private EuiccProfileService profileService;
	
	@Override
	public byte[] handle(String requestStr) {
		logger.info("https进入接收默认通知流程");
//		String eid = TlsMessageUtils.getEid(requestStr);
//		String isdpAid = TlsMessageUtils.getAid(requestStr);
		//根据data解析出seqnum
		int offsize =requestStr.indexOf("4C")+4;
		String eidString = requestStr.substring(offsize, offsize + 32);
		offsize =requestStr.indexOf("4D")+4;
		String noticeType =  requestStr.substring(offsize, offsize + 2);
		offsize =requestStr.indexOf("4E")+4;
		String seqNumber =  requestStr.substring(offsize, offsize + 4);	
		offsize =requestStr.indexOf("4F")+4;
		String isdpAid =  requestStr.substring(offsize, offsize + 32);
		
		boolean flag = checkInitialConditions(eidString);
		
		if (!flag) {
			throw new EuiccBusiException("8002","checkInitialConditions error");
		}
		
		flag = processingData(eidString,noticeType,isdpAid);
		if (!flag) {
			throw new EuiccBusiException("8003","processingData error");
		}
		
		//拼通知确认指令
		String data = "HTTP/1.1 200 \r\n"
					+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
					+ "\r\n";

		String notificationConfirmation = "80E28900073A08044E02" + seqNumber + "00";
		data += "AE80"+ notificationConfirmation + "0000";
		return data.getBytes();
	}
	
	@Override
	public boolean checkProcessResp(String requestStr) {
		String data = "HTTP/1.1 204 \r\n"
			+ "X-Admin-Protocol: globalplatform-remote-admin/1.0\r\n"
			+ "\r\n";
		
		return true;
	}
	
	private boolean checkInitialConditions(String requestStr){	
		EuiccCard card = euiccCardMapper.selectByPrimaryKey(requestStr);
		if (card == null) {
			return false;
		}
		return true;
	}

	private boolean processingData(String eid, String type, String isdpAid){	
		//根据type做判断
		EuiccProfileWithBLOBs  euiccProfile  = new EuiccProfileWithBLOBs();
		euiccProfile.setIsdPAid(isdpAid);
		euiccProfile.setEid(eid);

		int count = profileService.checkCount(euiccProfile);
		if (count == 0) {
			return false;
		}
		
		if ("01".equals(type)) {	
			//First network attachment
			//“设备第一次登网”通知在eUICC的生命周期中只发生一次
			String tmpVal = CacheUtil.getString(eid);		
			if (tmpVal.isEmpty()) {
				profileService.enableProfile(euiccProfile);
			}
		} else if ("02".equals(type)){
			profileService.enableProfile(euiccProfile);
		} else if ("03".equals(type)) {
			//Profile change failed and Roll-back 
			//新的Profile登网失败后，先前处于启用状态的profile或具备Fall-back属性的Profile被启用
			//不需要做任何处理，先前处于启用状态的profile仍然是启用状态
		} else if ("05".equals(type)) {
			//Profile change after Fall-back
			//查询fall-back状态，如果是激活状态，启用，不是激活，报错	
			profileService.fallBackEnableProfile(euiccProfile);
		}
		
		return true;
	}
	
	//待完善，https服务器代码还有修改
	private boolean  notifyProcessResult(String eid){
		boolean processResult = true;	
		SmsTrigger eventTrigger = null;	
		String sms = CacheUtil.getString(eid);	
		eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
		SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		return processResult;
	}
}
