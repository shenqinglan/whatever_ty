package com.whty.euicc.sr.handler.netty;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
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
import com.whty.euicc.packets.message.request.InstallProfileReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;


/**
 * SM-SR的下载Profile服务业务
 * @author Administrator
 *
 */
@Service("installProfile")
public class InstallProfileHandler implements HttpHandler{
	private Logger logger = LoggerFactory.getLogger(InstallProfileHandler.class);
	
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	@Autowired
	private EuiccProfileService profileService;
	
	@Autowired
	private EuiccCardService cardService;
	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		InstallProfileReqBody reqBody = (InstallProfileReqBody) reqMsgObject.getBody();//得到eid,iccid,profile
		checkInitCondition(reqBody);
		smsTriggerUtil.sendTriggerSms(reqBody,"downloadProfileApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(reqBody);
		
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		if(!smsTrigger.isProcessResult()){
			String error = smsTrigger.getErrorMsg();
			respMessage = new RespMessage(ErrorCode.FAILURE,StringUtils.defaultIfBlank(error, "error"));
		}
		String respMsg = new Gson().toJson(respMessage);
		logger.info("--------------installProfile返回结果:{}",respMsg);
		return respMsg.getBytes();
	}
	
	/**
	 * 检查卡能力                    
	 */
	private void checkInitCondition(InstallProfileReqBody reqBody) {
		//查询数据库（card），检查是否包含该eid
				EuiccCard record = cardService.selectByPrimaryKey(reqBody.getEid());
				if (record == null) {
					logger.info("This eid :"+ reqBody.getEid() +"is not exists");
					throw new EuiccBusiException("8.1.1", "SM-SR is not responsible for the euicc card!");
				}
				//检查目标isd-p是否已经创建成功
				EuiccProfileWithBLOBs profile = new EuiccProfileWithBLOBs();
				profile.setEid(record.getEid());
				profile.setIsdPAid(reqBody.getIsdPAid());
				EuiccProfileWithBLOBs ret = profileService.selectByEidAndIsdPAid(profile);
				String state = ret.getState();
				if (!StringUtils.equals(ProfileState.PERSONAL_ISD_P_STATE_SUCCESS, state)) {
					logger.info("the isd-p" + reqBody.getIsdPAid()+"is not be personal");
					throw new EuiccBusiException("8.3.1", "the target isd-p did not operate ket set！ ");
				}
	}
	
}
