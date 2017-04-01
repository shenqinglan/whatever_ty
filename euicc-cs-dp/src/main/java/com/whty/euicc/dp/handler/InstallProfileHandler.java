package com.whty.euicc.dp.handler;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.DeleteProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.InstallProfileByDpReqBody;
import com.whty.euicc.packets.message.request.InstallProfileReqBody;
import com.whty.euicc.packets.message.request.ProfileDownloadCompletedReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * SM-DP的下载Profile服务业务
 * @author Administrator
 *
 */
@Service("installProfileByDp")
public class InstallProfileHandler implements HttpHandler {
	private Logger logger = LoggerFactory.getLogger(InstallProfileHandler.class);
	
	@Autowired
	private EuiccProfileService profileService;
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		InstallProfileByDpReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		    reqBody = (InstallProfileByDpReqBody) reqMsgObject.getBody();
		    /*
			scp03(reqBody);
			String profile = encryptionProfile(reqBody);
			*/
		    EuiccProfileWithBLOBs profileBean = getProfile(reqBody);
		    reqBody.setIsdPAid(profileBean.getIsdPAid());
		    reqBody.setProfileFile(profileBean.getDerFile());
			sendProfile(reqBody);
			profileDownloadComplete(reqBody);
		} catch (Exception e) {
			e.printStackTrace();
			//返回失败信息
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage(ErrorCode.BUSI_9999,e.getMessage());
			}
			
		}
		CacheUtil.remove("scp03"+reqBody.getEid());
		String respMsg = new Gson().toJson(respMessage);
		logger.info("-----------------installprofile-dp返回结果:{}",respMsg);
		return respMsg.getBytes();
	}

	
	/**
	 * 下载完成结果处理
	 * @param reqBody
	 * @throws Exception 
	 */
	/*private void profileDownloadComplet(InstallProfileByDpReqBody reqBody) throws Exception {
		String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"profileDownloadCompleted\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"eid\":\""+reqBody.getEid()+"\",\"iccid\":\""+reqBody.getIccid()+"\",\"isdPAid\":\""+reqBody.getIsdPAid()+"\"}}";
        HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
        RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
        System.out.println(new String(msgBype));
	}*/

	public void profileDownloadComplete(InstallProfileByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("profileDownloadCompleted");
        ProfileDownloadCompletedReqBody requestBody = new ProfileDownloadCompletedReqBody();
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setIsdPAid(reqBody.getIsdPAid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	/**
	 * scp03认证
	 * @param reqBody
	 * @throws Exception
	 */
	/*
	private void scp03(InstallProfileReqBody reqBody) throws Exception {
		String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"scp03\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"eid\":\""+reqBody.getEid()+"\"}}";
	    HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
        RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
        System.out.println(new String(msgBype));
	}
	
	*/
	/**
	 * 加密profile
	 * @param reqBody
	 * @return
	 */
	/*
	private String  encryptionProfile(InstallProfileReqBody reqBody) {
		Scp03 scp = new Scp03();
		String encCounter = scp.getEncCounter();//todo
		String initializeUpdateResp = CacheUtil.getString("scp03"+reqBody.getEid());	
		InitializeUpdateRespBean initUpdateRespBean = new Gson().fromJson(initializeUpdateResp,InitializeUpdateRespBean.class);
		String S_MAC = initUpdateRespBean.getS_MAC();
		String S_ENC = initUpdateRespBean.getS_ENC();
		EuiccProfileWithBLOBs profileBean = profileService.selectByPrimaryKey(reqBody.getIccid());
		return scp.encryptionData(profileBean.getDerFile(), encCounter, S_ENC, S_MAC);
	}
	*/
	/**
	 * 查询数据库，获得.der的profile
	 * @param reqBody
	 * @return
	 */
	private EuiccProfileWithBLOBs getProfile(InstallProfileByDpReqBody reqBody){
		return profileService.selectByPrimaryKey(reqBody.getIccid());
	}
	
	
	

	/**
	 * 调用dp发送profile
	 * @param reqBody
	 * @param profile
	 * @throws Exception
	 */
	/*private void sendProfil(InstallProfileByDpReqBody reqBody,String profile) throws Exception {
		String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"installProfile\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"eid\":\""+reqBody.getEid()+"\",\"iccid\":\""+reqBody.getIccid()+"\",\"profileFile\":\""+profile+"\"}}";
        HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
        RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}*/
	public void sendProfile(InstallProfileByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("installProfile");
        InstallProfileReqBody requestBody = new InstallProfileReqBody();
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setIsdPAid(reqBody.getIsdPAid());
		requestBody.setProfileFile(reqBody.getProfileFile());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	logger.info("error during installation,delete isd-p");
        	profileDeleteByHttps(reqBody);
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	/**
	 * DP 向SR发送删除isd-p请求
	 * @param reqBody
	 * @throws Exception 
	 */
	public void profileDeleteByHttps(InstallProfileByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("deleteProfileByHttps");
        DeleteProfileByHttpsReqBody requestBody = new DeleteProfileByHttpsReqBody();
        requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setErrorDeal(true);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	

}
