package com.whty.euicc.sr.handler.netty.https.srchange.smsr2_receive;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
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
import com.whty.euicc.packets.message.request.PrepareSmsrChangeReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;

/**
 *  SR Change step 1-3
 * @author 11
 *
 */
@Service("prepareSmsrChange")
public class PrepareSmsrChangeHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(PrepareSmsrChangeHandler.class);
	@Autowired
	private EuiccCardService euiccCardService;
	@Autowired
	private EuiccProfileService euiccProfileService;
	@Autowired
	private EuiccIsdPService euiccIsdPService;
	@Autowired
	private EuiccIsdRService euiccIsdRService;
	@Autowired
	private EuiccScp03Service euiccScp03Service;
	@Autowired
	private EuiccScp80Service euiccScp80Service;
	@Autowired
	private EuiccScp81Service euiccScp81Service;

	@Override
	public byte[] handle(String requestStr) {
		logger.info("准备SMSR切换......");
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		PrepareSmsrChangeReqBody prepareSmsrChangeReqBody = (PrepareSmsrChangeReqBody)reqMsgObject.getBody();
		String eid = prepareSmsrChangeReqBody.getEid();
		String currentSmsrId = prepareSmsrChangeReqBody.getCurrentSmsrId();
		logger.info("eid:{},currentSmsrId:{}",eid,currentSmsrId);
		
		try{
			checkInitialConditions(eid);
		}catch(Exception e){
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

	/**
	 * 查看SR2数据库中是否包含即将管理的相关信息
	 * @param eid
	 */
	private void checkInitialConditions(String eid) {
		EuiccCard card = euiccCardService.selectByPrimaryKey(eid);
		if(card!=null){
			throw new EuiccBusiException("0101", "SR2中已存在该card");
		}
		List<EuiccProfileWithBLOBs> profiles = euiccProfileService.selectByEid(eid);
		if(profiles!=null && !profiles.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该profile");
		}
		List<EuiccIsdP> isdPs = euiccIsdPService.selectListByEid(eid);
		if(isdPs!=null && !isdPs.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该isdp");
		}
		EuiccIsdR isdR = euiccIsdRService.selectByEid(eid);
		if(isdR!=null){
			throw new EuiccBusiException("0101", "SR2中已存在该isdr");
		}
		List<EuiccScp03> scp03s = euiccScp03Service.selectListByEid(eid);
		if(scp03s!=null && !scp03s.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该scp03");
		}
		List<EuiccScp80> scp80s = euiccScp80Service.selectListByEid(eid);
		if(scp80s!=null && !scp80s.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该scp80");
		}
		EuiccScp81 scp81 = euiccScp81Service.selectByEid(eid);
		if(scp81!=null){
			throw new EuiccBusiException("0101", "SR2中已存在该scp81");
		}
	}

}
