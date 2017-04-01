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
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccPol2Service;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.POL2Type;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.UpdatePolilcyRulesReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * 更新POL2策略规则
 * @author Administrator
 *
 */
@Service("updatePolicyRules")
public class UpdatePolicyRulesHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(UpdatePolicyRulesHandler.class);
	@Autowired
	private EuiccProfileService profileService;
	
	@Autowired
	private EuiccPol2Service euiccPol2Service;

	@Override
	public byte[] handle(String requestStr) {
		logger.info("pol2策略更新");
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			UpdatePolilcyRulesReqBody reqBody = (UpdatePolilcyRulesReqBody) reqMsgObject.getBody();
			updatePol2(reqBody);
		} catch (Exception e) {
			e.printStackTrace();
			//返回失败信息
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
		}
		return new Gson().toJson(respMessage).getBytes();
	}

	private void updatePol2(UpdatePolilcyRulesReqBody reqBody) {
		EuiccProfileWithBLOBs targetProfile = getProfile(reqBody.getIccid());
		POL2Type pol2Rules = reqBody.getPol2Rules();
		if (pol2Rules == null) {
			targetProfile.setPol2Id("");
			targetProfile.setIccid(reqBody.getIccid());
			profileService.updateByPrimaryKeySelective(targetProfile);
			return;
		}
		String action = pol2Rules.getAction();
		String qualification = pol2Rules.getQualification();
		
		logger.info("action and qualification:" + action +qualification);
		//获得pol2 id
		EuiccPol2 euiccPol2 = new EuiccPol2();
		euiccPol2.setAction(action);
		euiccPol2.setQualification(qualification);
		EuiccPol2 Pol2Rules = euiccPol2Service.selectByActionAndQualification(euiccPol2);
		String pol2Id = Pol2Rules.getPol2Id();
		
		
		targetProfile.setPol2Id(pol2Id);
		targetProfile.setIccid(reqBody.getIccid());
		profileService.updateByPrimaryKeySelective(targetProfile);
		
	}
	
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}

	

}
