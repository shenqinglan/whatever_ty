package com.whty.euicc.rsp.handler.tls;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.rsp.constant.ProfileStates;
import com.whty.euicc.rsp.packets.message.EuiccMsg;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.MsgBody;
import com.whty.euicc.rsp.packets.message.request.ConfirmOrderReq;
import com.whty.euicc.rsp.packets.message.response.ConfirmOrderResp;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.rsp.util.RandomUtil;
import com.whty.euicc.rsp.util.SHA256Util;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;

/**
 * ES2+.ConfirmOrder()接口
 * 入参：iccid[M],eid[M],matchingId[O],comfirmationCode[O],smdsAddress[O],releaseFlag[M]
 * 出参：eid[C],matchingId[M],smdpAddress[O]
 * 期望状态：若eid绑定了profile订单，则返回；若返回了smdpAddress，则操作者用于生成激活码
 * @author 11
 *
 */
@Service("/gsma/rsp2/es2plus/confirmOrder")
public class ConfirmOrderHandler extends AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(ConfirmOrderHandler.class);
    
    @Autowired
    private RspProfileService rspProfileService;
    
    @Override
	public byte[] handle(String requestStr) {
		logger.info("confirmOrder begin");
		EuiccMsg<MsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr,"/gsma/rsp2/es2plus/confirmOrder");
		ConfirmOrderReq reqBody = (ConfirmOrderReq) reqMsgObject.getBody();
		String iccid = reqBody.getIccid();
		String eid = reqBody.getEid();
		String matchingId = reqBody.getMatchingId();
		String confirmationCode = reqBody.getComfirmationCode();
		String smdsAddress = reqBody.getSmdsAddress();
		boolean releaseFlag = reqBody.getReleaseFlag();
		logger.info("iccid:{},eid:{},matchingId:{},confirmationCode:{},smdsAddress:{},releaseFlag:{}"
				,iccid,eid,matchingId,confirmationCode,smdsAddress,releaseFlag);
		
		matchingId = (matchingId == null ? genarateMatchingId() : matchingId);//""空字符串原样保留
		//若为false，则要走ES2+.releaseFlag()流程
		String profileState = releaseFlag ? ProfileStates.PROFILE_RELEASED : ProfileStates.PROFILE_CONFIRMED;
		logger.info("profileState:{}",profileState);
		
		if(StringUtils.isNotBlank(confirmationCode)){
			//用SHA256计算确认码哈希值
			sha256ConfirmationCode(iccid, confirmationCode);
		}
		if(StringUtils.isNotBlank(smdsAddress)){
			//执行事件注册与指定的SM-DS
			eventRegisterProcedure();
		}
		
		updateProfileState(iccid, profileState, matchingId, eid);
		
		//save cache
		/*CacheBean cacheBean = new CacheBean.CacheBeanBuilder(eid).setMatchingId(matchingId).setConfirmationCode(confirmationCode).build();
		new JvmCacheUtil().put(eid, MessageHelper.gs.toJson(cacheBean));*/
		
		logger.info("confirmOrder end");
		return httpPostResponseByRsp("application/json",respBodyByBean(eid,matchingId));
	}
    
    /*
     * 更新Profile状态
     */
    private void updateProfileState(String iccid,String profileState, String matchingId, String eid) {
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByPrimaryKey(iccid);
		String stateOfProfile = rspProfileWithBLOBs.getProfileState();
		if(StringUtils.equalsIgnoreCase(stateOfProfile, ProfileStates.PROFILE_ALLOCATED) || 
		      StringUtils.equalsIgnoreCase(stateOfProfile, ProfileStates.PROFILE_LINKED)){
			rspProfileWithBLOBs.setProfileState(profileState);
			rspProfileWithBLOBs.setMatchingId(matchingId);
			rspProfileWithBLOBs.setEid(eid);
			rspProfileService.updateByPrimaryKeySelective(rspProfileWithBLOBs);
		}else{
			throw new RuntimeException("当前profile状态不符合条件");
		}
		
		
	}

    /*
     * DP+与DS走事件注册流程
     */
    private void eventRegisterProcedure() {
		// TODO
		
	}

	/*
     * 计算确认码哈希值SHA256(Confirmation Code)，并存储
     */
    private void sha256ConfirmationCode(String iccid, String confirmationCode) {
		String hashCc = SHA256Util.Encrypt(confirmationCode, "SHA-256");
		System.out.println("hashCc >> " + hashCc);
		RspProfileWithBLOBs rspProfileWithBLOBs = rspProfileService.selectByPrimaryKey(iccid);
		rspProfileWithBLOBs.setHashCc(hashCc);
		rspProfileService.updateByPrimaryKeySelective(rspProfileWithBLOBs);
	}

	/*
     * 生成matchingId，类似04386-AGYFT-A74Y8-3F815形式
     */
	private String genarateMatchingId() {
		return RandomUtil.genarateMatchingId();
	}

	private String respBodyByBean(String expectEid, String expectMatchingId) {
		FunctionExecutionStatus functionExecutionStatus = new FunctionExecutionStatus("Executed-Success");
		HeaderResp head = new HeaderResp(functionExecutionStatus);
		ConfirmOrderResp resp = new ConfirmOrderResp();
		resp.setHeader(head);
		resp.setEid(expectEid);                 //若绑定了此订单则返回eid
		resp.setMatchingId(expectMatchingId);
		resp.setSmdpAddress(SpringPropertyPlaceholderConfigurer.getStringProperty("dpPlus_address"));   //可能返回，操作者将使用这个值生成激活码，否则将使用默认的SM-DP+地址。
		return MessageHelper.gs.toJson(resp);
	}
	
}
