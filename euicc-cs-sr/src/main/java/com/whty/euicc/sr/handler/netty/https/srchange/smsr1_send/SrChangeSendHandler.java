package com.whty.euicc.sr.handler.netty.https.srchange.smsr1_send;

import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccIsdR;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.EuiccScp03;
import com.whty.euicc.data.pojo.EuiccScp80;
import com.whty.euicc.data.pojo.EuiccScp81;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccIsdPService;
import com.whty.euicc.data.service.EuiccIsdRService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.data.service.EuiccScp03Service;
import com.whty.euicc.data.service.EuiccScp80Service;
import com.whty.euicc.data.service.EuiccScp81Service;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.HandoverEUICCReceiveReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.SrChangeSendReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.sr.handler.netty.notify.ES7Notification;
/**
 * step 5
 * sr1发起服务，发送eid等数据信息至sr2
 * step 29
 * sr1删除EIS
 * @author lw
 *
 */
@Service("srChangeSend")
public class SrChangeSendHandler implements HttpHandler{

	private Logger logger = LoggerFactory.getLogger(SrChangeSendHandler.class);
	// 要发送给sr2的currentSmsrId
	private String currentSmsrId = "9180";
	// 要发送给sr2的PK.ECDSA.ECKA
	//private String eUICC_CERT = "3F157A6242EC54888EB50967BD84D1A885E51D66B2F6CD135354724C91D790EDB48B015F6B272DF50E49EAB2E1383BF0EEB0E9848543B971397D274D88E8EA77";//sr2欲抽取出的PK.ECASD.ECKA,具体证书待定
	private String eUICC_CERT = SpringPropertyPlaceholderConfigurer.getStringProperty("eUICC_CERT");
	
	private static final String sr_change_type = SpringPropertyPlaceholderConfigurer.getStringProperty("sr_change_type");
	private static final String SR_CHANGE_SINGLE = "1";
	
	private static boolean isSingleSr(){
		return StringUtils.equals(sr_change_type, SR_CHANGE_SINGLE) ? true : false;
	}
	
	@Autowired
	private EuiccCardService euiccCardService;
	
	@Autowired
	private EuiccProfileService euiccProfileService;
	
	@Autowired
	private EuiccIsdPService euiccIsdPService;
	
	@Autowired
	private EuiccIsdRService euiccIsdrService;
	
	@Autowired
	private EuiccScp03Service euiccScp03Service;
	
	@Autowired
	private EuiccScp80Service euiccScp80Service;
	
	@Autowired
	private EuiccScp81Service euiccScp81Service;
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		SrChangeSendReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (SrChangeSendReqBody) reqMsgObject.getBody();
			
			String eid = reqBody.getEid();
			EuiccCard euiccCard = euiccCardService.selectByPrimaryKey(eid);
			if(euiccCard == null){
				throw new EuiccBusiException("0102", "EID Unknown");
			}
			
			// 获取MNO传入的targetSmsrId
			String targetSmsrId = reqBody.getTargetSmsrId();
			System.out.println("*** targetSmsrId ***:"+targetSmsrId);
			
			EuiccCard card = euiccCardService.selectByPrimaryKey(reqBody.getEid());
			List<EuiccProfileWithBLOBs> profiles = euiccProfileService.selectByEid(reqBody.getEid());
			List<EuiccIsdP> isdPs = euiccIsdPService.selectListByEid(reqBody.getEid());
			EuiccIsdR isdR = euiccIsdrService.selectByEid(reqBody.getEid());
			List<EuiccScp03> scp03s = euiccScp03Service.selectListByEid(reqBody.getEid());
			List<EuiccScp80> scp80s = euiccScp80Service.selectListByEid(reqBody.getEid());
			EuiccScp81 scp81 = euiccScp81Service.selectByEid(reqBody.getEid());
			
			System.out.println("SR1准备删除EIS......");
			if(isSingleSr())deleteEIS(reqBody);//单台主机测试，先删除
			
			RespMessage message = handoverEuicc(reqBody.getEid(), currentSmsrId, eUICC_CERT, card ,profiles, isdPs, isdR, scp03s, scp80s, scp81);
			if(!StringUtils.equals(ErrorCode.SUCCESS, message.getCode())){
				throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
	        }
			
			
			// 通知SM-SR2已成功删除EIS
			deletionNotification();
			if(!isSingleSr())deleteEIS(reqBody);//2台主机测试，后删除
		} catch (Exception e) {
			logger.error("SM-SR Change error:{}",e.getMessage());
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

	/**
	 * 删除EIS
	 * @param reqBody
	 */
	private void deleteEIS(SrChangeSendReqBody reqBody) {
		euiccCardService.deleteByPrimaryKey(reqBody.getEid());
		euiccProfileService.deleteByEid(reqBody.getEid());
		euiccIsdPService.deleteByEid(reqBody.getEid());
		euiccIsdrService.deleteByEid(reqBody.getEid());
		euiccScp03Service.deleteByEid(reqBody.getEid());
		euiccScp80Service.deleteByEid(reqBody.getEid());
		euiccScp81Service.deleteByEid(reqBody.getEid());
	}

	/**
	 * SR1向SR2发送EID等
	 * @param eid
	 * @param iccid
	 * @param cERTDPECDSA
	 * @return 
	 * @throws Exception
	 */
	private RespMessage handoverEuicc(String eid, String isdr, String cert, EuiccCard card, List<EuiccProfileWithBLOBs> profiles, List<EuiccIsdP> isdps, EuiccIsdR isdR, List<EuiccScp03> scp03s, List<EuiccScp80> scp80s, EuiccScp81 scp81) throws Exception {
		String sr_receive_url = SpringPropertyPlaceholderConfigurer.getStringProperty("sr_receive_url");
        MsgHeader header = new MsgHeader("handoverEUICC");
        HandoverEUICCReceiveReqBody requestBody = new HandoverEUICCReceiveReqBody();
		requestBody.setEid(eid);
		requestBody.setIsdrId(isdr);
		requestBody.setCertOfEuicc(cert);
		requestBody.setCard(card);
		requestBody.setProfiles(profiles);
		requestBody.setIsdp(isdps);
		requestBody.setIsdR(isdR);
		requestBody.setScp03s(scp03s);
		requestBody.setScp80s(scp80s);
		requestBody.setScp81(scp81);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(sr_receive_url,json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
		if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
        return respMessage;
	}
	
	/**
	 *  通知SM-SR2已成功删除EIS
	 * @param eis
	 * @throws Exception
	 */
	private void deletionNotification() throws Exception {
		ES7Notification notify = new ES7Notification();
		notify.handleNotifyInSrChange();
	}
	
	
}
