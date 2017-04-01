package com.whty.euicc.sr.handler.netty;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.PersonalISDPReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * 个人化触发与卡交互短信
 * @author zyj,lw
 *
 */
@Service("personalISDP")
public class PersonalHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(PersonalHandler.class);
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	@Autowired
	private EuiccProfileService profileService;
	
	@Autowired
	private EuiccCardService cardService;

	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		PersonalISDPReqBody reqBody = (PersonalISDPReqBody) reqMsgObject.getBody();
		
		EuiccProfileWithBLOBs profileBean = getProfile(reqBody.getIccid());
		reqBody.setIsdPAid(profileBean.getIsdPAid());
		checkInitialConditions(reqBody);
		
		smsTriggerUtil.sendTriggerSms(reqBody,"personalApduHandler");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			RespMessage respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
			return new Gson().toJson(respMessage).getBytes();
		}
		String key = "personal-recept-"+reqBody.getEid();
		String recept = CacheUtil.getString(key);
		CacheUtil.remove(key);
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success",recept);
		return new Gson().toJson(respMessage).getBytes();
	}
	
	

	private void checkInitialConditions(PersonalISDPReqBody reqBody) {
		//查询数据库（card），检查是否包含该eid
		EuiccCard record = cardService.selectByPrimaryKey(reqBody.getEid());
		if (record == null) {
			logger.info(" this eid :"+ reqBody.getEid() +" is not exists in EIS");
			throw new EuiccBusiException("8.1.1", "SM-SR is not responsible for the euicc card!");
		}
		//检查目标isd-p是否已经创建成功
		EuiccProfileWithBLOBs profile = new EuiccProfileWithBLOBs();
		profile.setEid(record.getEid());
		profile.setIsdPAid(reqBody.getIsdPAid());
		EuiccProfileWithBLOBs ret = profileService.selectByEidAndIsdPAid(profile);
		String state = ret.getState();
		logger.info("从数据库获取的state:" + state);
		if (!StringUtils.equals(ProfileState.CREATE_ISD_P_STATE_SUCCESS, state)) {
			logger.info("the isd-p: " +  reqBody.getIsdPAid() + " is not created");
			throw new EuiccBusiException("0831", "the target isd-p is not created on the euicc！");
		}
		
	}
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}
}
