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
import com.whty.euicc.rsp.packets.message.request.DownloadOrderReq;
import com.whty.euicc.rsp.packets.message.response.DownloadOrderResp;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;

/**
 * ES2+.DownloadOrder()接口,下载订单
 * 入参：eid,iccid 
 * or eid,profileType 
 * or iccid 
 * or profileType
 * 出参：iccid
 * 期望状态：DP+保留iccid并作为出参传给operator
 * @author 11
 *
 */
@Service("/gsma/rsp2/es2plus/downloadOrder")
public class DownloadOrderHandler extends AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(DownloadOrderHandler.class);
    
    @Autowired
    private RspProfileService rspProfileService;
    
    @Override
	public byte[] handle(String requestStr) {
		logger.info("downloadOrder begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es2plus/downloadOrder");
		DownloadOrderReq reqBody = (DownloadOrderReq) reqMsgObject.getBody();
		String eid = reqBody.getEid();
		String iccid = reqBody.getIccid();
		String profileType = reqBody.getProfileType();
		String msisdn = reqBody.getMsisdn();
		logger.info("eid:{},iccid:{},profileType:{},msisdn:{}",eid,iccid,profileType,msisdn);
		
		String profileState = StringUtils.isBlank(eid) ? ProfileStates.PROFILE_ALLOCATED : ProfileStates.PROFILE_LINKED;
		logger.info("profileState:{}",profileState);
		
		iccid = StringUtils.defaultIfBlank(iccid, findIccidAndUpdateProfileState(msisdn, profileState));
		System.out.println("iccid >> " + iccid);
		
		logger.info("downloadOrder end");
		return httpPostResponseByRsp("application/json",respBodyByBean(iccid));
	}

    /*
     * 若入参传入profile type，则在数据库中找符合profile type的profile的iccid值返回。
     * 暂时将profile type改为msisdn手机号
     */
	private String findIccidAndUpdateProfileState(String msisdn, String profileState) {
		//从DB中选个武汉Iccid
		String iccid = selectIccid(msisdn);
		System.out.println("ICCID >> "+iccid);
		//锁定当前profile状态
		updateProfileState(iccid,profileState);
		return iccid;
	}

	/*
	 * 通过iccid，更新profile状态
	 */
	private void updateProfileState(String iccid,String profileState) {
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByPrimaryKey(iccid);
		String stateOfProfile =  rspProfileWithBLOBs.getProfileState();
		if(StringUtils.equalsIgnoreCase(stateOfProfile, ProfileStates.PROFILE_AVAILABLE)){
			rspProfileWithBLOBs.setProfileState(profileState);
			rspProfileService.updateByPrimaryKeySelective(rspProfileWithBLOBs);
		}else{
			throw new RuntimeException("当前profile状态不符合条件");
		}
	}

	/*
	 * 通过profileType找到相应iccid
	 */
	private String selectIccid(String msisdn) {
		return rspProfileService.selectByMsisdn(msisdn).getIccid();
	}

	private String respBodyByBean(String expectIccid) {
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Success");
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		DownloadOrderResp resp = new DownloadOrderResp();
		resp.setHeader(head);
		resp.setIccid(expectIccid);
		return MessageHelper.gs.toJson(resp);
	}
}
