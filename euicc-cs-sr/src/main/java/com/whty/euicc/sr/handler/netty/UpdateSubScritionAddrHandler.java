package com.whty.euicc.sr.handler.netty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.RetrieveEISReqBody;
import com.whty.euicc.packets.message.request.UpdateSubscriAddrReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * 查询EIS
 * @author Administrator
 *
 */
@Service("updateSubscriAddrBySr")
public class UpdateSubScritionAddrHandler implements HttpHandler{
	@Autowired
	private EuiccProfileService profileService;
	
	@Override
	public byte[] handle(String requestStr) {
		System.out.println("进入更新订阅地址.................");
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		int ret = updateSubscriAddr(requestStr);
		if(ret == 0){
			respMessage = new RespMessage(ErrorCode.FAILURE,"error");
			return new Gson().toJson(respMessage).getBytes();
		}
		return new Gson().toJson(respMessage).getBytes();
		
	}
	
	private int updateSubscriAddr(String requestStr) {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		UpdateSubscriAddrReqBody reqBody = (UpdateSubscriAddrReqBody) reqMsgObject.getBody();
		EuiccProfileWithBLOBs profileWithBLOBs = new EuiccProfileWithBLOBs();
		EuiccProfileWithBLOBs targetProfile = getProfile(reqBody.getIccid());
		if (targetProfile == null) throw new EuiccBusiException("82139", "Unknown ICCID");
		if (!StringUtils.equals(targetProfile.getEid(), reqBody.getEid())) throw new EuiccBusiException("82134", "Invalid destination");
		
		profileWithBLOBs.setIccid(reqBody.getIccid());
		profileWithBLOBs.setImsi(reqBody.getImsi());
		profileWithBLOBs.setMsisdn(reqBody.getMsisdn());
		int ret =  profileService.updateByPrimaryKeySelective(profileWithBLOBs);
		
		return ret;
	}
	
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}
}
