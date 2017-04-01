package com.whty.euicc.rsp.handler.tls;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.rsp.constant.ProfileStates;
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.ReleaseProfileReq;
import com.whty.euicc.rsp.packets.message.response.ReleaseProfileResp;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;

/**
 * ES2+.ReleaseProfile()接口
 * 入参：iccid
 * 出参：无
 * @author 11
 *
 */
@Service("/gsma/rsp2/es2plus/releaseProfile")
public class ReleaseProfileHandler extends AbstractHandler {
	private Logger logger = LoggerFactory.getLogger(ReleaseProfileHandler.class);
	
	@Autowired
	private RspProfileService rspProfileService;
	
	@Override
	public byte[] handle(String requestStr) {
		logger.info("releaseProfile begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es2plus/releaseProfile");
		ReleaseProfileReq reqBody = (ReleaseProfileReq) reqMsgObject.getBody();
		String Iccid =  reqBody.getIccid();
		//（1）确认被提供的ICCID标识的Profile执行了"ES2+.DownloadOrder" and "ES2+.ConfirmOrder"，但还没"释放";（2）设置Profile状态为“Released”以允许下载。
		releaseProfile(Iccid);
		
		logger.info("releaseProfile end");
		return httpPostResponseByRsp("application/json",respBodyByBean());
	}

	/*
	 * 确认此时iccid对应的profile状态为Confirmed，改状态为Released，以备下载安装
	 */
	private void releaseProfile(String iccid) {
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByPrimaryKey(iccid);
		String stateOfProfile = rspProfileWithBLOBs.getProfileState();
		if(StringUtils.equalsIgnoreCase(stateOfProfile, ProfileStates.PROFILE_CONFIRMED)){
			rspProfileWithBLOBs.setProfileState(ProfileStates.PROFILE_RELEASED);
			rspProfileService.updateByPrimaryKeySelective(rspProfileWithBLOBs);
		}else{
			throw new RuntimeException("当前profile状态不符合条件");
		}
		
	}

	private String respBodyByBean() {
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Success");
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		ReleaseProfileResp resp = new ReleaseProfileResp();
		resp.setHeader(head);
		return MessageHelper.gs.toJson(resp);
	}

}
