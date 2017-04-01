package com.whty.euicc.sr.handler.netty;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.CreateISDPReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * 创建isd-p服务业务，供DP调用
 * @author Administrator
 *
 */
@Service("createISDP")
public class CreateISDPHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(CreateISDPHandler.class);
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Autowired
	private EuiccProfileService profileService;
	@Autowired
	private EuiccCardService cardService;

	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		CreateISDPReqBody reqBody = (CreateISDPReqBody) reqMsgObject.getBody();
		checkInitialConditions(reqBody);
		
		smsTriggerUtil.sendTriggerSms(reqBody,"createISDPApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			RespMessage respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
			return new Gson().toJson(respMessage).getBytes();
		}
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success",smsTrigger.getIsdPAid());
		return new Gson().toJson(respMessage).getBytes();
	}

	

	

	private void checkInitialConditions(CreateISDPReqBody reqBody) {
		// 查询数据库（card），检查是否包含该eid
		EuiccCard record = cardService.selectByPrimaryKey(reqBody.getEid());
		if (record == null) {
			logger.info(" this eid :"+ reqBody.getEid() +" is not exists in EIS");
			throw new EuiccBusiException("8.1.1", "SM-SR is not responsible for the euicc card!");
		}
		//查询profile表，表内iccid标识的isd-p-aid没有被其他profile占用
		EuiccProfileWithBLOBs profile = profileService.selectByPrimaryKey(reqBody.getIccid());
		String isdpAid = profile.getIsdPAid();
		logger.info("search from database and the isdpaid is:" + isdpAid);
		if (StringUtils.isNotBlank(isdpAid)) {
			logger.info("this iccid:"+ reqBody.getIccid() +" is already been assigned!");
			throw new EuiccBusiException("8.2.1", "the ICCID is already allocated to another Profile");
		}
		//todo 内存（allocated-memory）满足创建isd-p 
	}

	

}
