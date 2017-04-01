package com.whty.euicc.sr.handler.netty;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.ProfileDownloadCompletedReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * 下载Profile完成后，更新数据库
 * @author Administrator
 *
 */
@Service("profileDownloadCompleted")
public class ProfileDownloadCompletedHandler implements HttpHandler{
	private Logger logger = LoggerFactory.getLogger(ProfileDownloadCompletedHandler.class);
	@Autowired
	private EuiccCardService cardService;
	
	@Autowired
	private EuiccProfileService profileService;
	
	@Override
	public byte[] handle(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		ProfileDownloadCompletedReqBody reqBody = (ProfileDownloadCompletedReqBody) reqMsgObject.getBody();
		updateEIS(reqBody);
		String respMsg = new Gson().toJson(new RespMessage(ErrorCode.SUCCESS,"success"));
		logger.info("--------------profileDownloadCompleted返回结果:{}",respMsg);
		return respMsg.getBytes();
	}
	
	private void updateEIS(ProfileDownloadCompletedReqBody reqBody) {
		EuiccProfileWithBLOBs profileWiBloBs = getProfile(reqBody);
		String isdPAid = profileWiBloBs.getIsdPAid();
		EuiccCard card = cardService.selectByPrimaryKey(reqBody.getEid());
		card.setAvailablememoryforprofiles("1000");
		
	
		EuiccProfileWithBLOBs profile = new EuiccProfileWithBLOBs();
		profile.setIccid(reqBody.getIccid());
		profile.setIsdPAid(isdPAid);
		profile.setState(ProfileState.INSTALL_ISD_P_STATE_SUCCESS);
		
		EuiccIsdP isdP = new EuiccIsdP();
		isdP.setEid(reqBody.getEid());
		isdP.setIsdPAid(isdPAid);
		isdP.setIsdPState(ProfileState.INSTALL_ISD_P_STATE_SUCCESS);
		isdP.setUpdateDate(new Date());
		
		cardService.updateEIS(card, profile,isdP);
	}
	private EuiccProfileWithBLOBs getProfile(ProfileDownloadCompletedReqBody reqBody){
		return profileService.selectByPrimaryKey(reqBody.getIccid());
	}

}
