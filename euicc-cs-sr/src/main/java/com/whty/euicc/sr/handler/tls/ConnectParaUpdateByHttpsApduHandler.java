package com.whty.euicc.sr.handler.tls;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.ByteUtil;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.EuiccScp03;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.data.service.EuiccScp03Service;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;
import com.whty.security.aes.AesCbc;
import com.whty.security.scp03forupdate.Scp03ForUpdate;
import com.whty.security.scp03t.scp03t.bean.CmdApduBean;
import com.whty.security.scp03t.scp03t.bean.ExternalAuthBean;
import com.whty.security.scp03t.scp03t.bean.InitializeUpdateBean;
/**
 * https下更新连接参数的apdu指令
 * @author Administrator
 *
 */
@Service("connectParaUpdateByHttpsApdu")
public class ConnectParaUpdateByHttpsApduHandler extends AbstractHandler{
	
	private final String CLA = "80";
	private final String INS = "E2";
	private final String P1 = "88";
	private final String P2 = "00";
	
	private Logger logger = LoggerFactory.getLogger(ConnectParaUpdateByHttpsApduHandler.class);
	@Autowired
	private EuiccScp03Service euiccService;
	
	@Autowired
	private EuiccProfileService profileService;
	
	private String kerV = "00";
	private String securedLevel = "33";
	private String perMac = "00000000000000000000000000000000";
	

	public byte[] handle(String requestStr) {
		String apdu = "";
		String commandData = "";
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);
		SmsTrigger eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
		String isdPAID = eventTrigger.getIsdPAid();
		String counterValue = eventTrigger.getSeqCounter();
		String smsCenterNo = eventTrigger.getSmsCenterNo();
		logger.info("sequence number from DP:" + counterValue);
				
		CacheUtil.put(eid, new Gson().toJson(eventTrigger));
		if (eventTrigger.getStep() == 0) {
			apdu = initializeApdu(eid);
			logger.info("initialUpdate apdu:"+apdu);
		} else if (eventTrigger.getStep() == 1) {
			apdu = externalCmd(eid,isdPAID,counterValue);
			logger.info("externalAuth apdu:"+apdu);
			
		}else if(eventTrigger.getStep() <= eventTrigger.getAllStep()){
			try {
				commandData = chooseFunction(eventTrigger.getStep(), eid, isdPAID, smsCenterNo);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			logger.info("connectivity parameters apdu:"+commandData);
			try {
				apdu = gpScp03Command(commandData,eid,isdPAID);
				logger.info("gp apdu:"+apdu);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return httpPostResponseNoEnc(apdu, isdPAID);
	}
	
	/**
	 * 处理eUICC卡片返回结果（校验，处理）
	 * 
	 * @param requestStr
	 * @return
	 */
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		String eid = null;
		try {
			eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);
			eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
			if (eventTrigger.getStep() < 6) {
				logger.info("**********************"+eventTrigger.getStep()+"**************************");
				checkResp(requestStr);
				updateTrigger(eventTrigger);
			} else if (eventTrigger.getStep() == 6) {
				logger.info("**********************"+eventTrigger.getStep()+"**************************");
				
				CacheUtil.remove("scp03-initial" + eid);
				checkResp(requestStr);
				SmsTriggerUtil.notifyProcessResult(eventTrigger, processResult);
			}
		} catch (Exception e) {
			CacheUtil.remove("scp03-initial" + eid);
			logger.error("checkProcessResp error:{}", e.getMessage());
			eventTrigger.setErrorMsg(e.getMessage());
			e.printStackTrace();
			processResult = false;
			SmsTriggerUtil.notifyProcessResult(eventTrigger, processResult);
		}
		
		return processResult;
	}

	private void updateTrigger(SmsTrigger eventTrigger) {
		eventTrigger.setStep(eventTrigger.getStep() + 1);
		eventTrigger.setAllStep(6);
		CacheUtil.put(eventTrigger.getEid(), new Gson().toJson(eventTrigger));
	}
	/**
	 * 人为抛异常检查不合格
	 */
	private void checkResp(String requestStr) {
		logger.info("card resp:{}",requestStr);
		
		int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
		System.out.println(endOfDbl0D0A);
		String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
		logger.info("apdu:{}",requestStr);
		String dataCheck = paserCardContent(strData);
		String errorNote = "";
		if(StringUtils.equals(dataCheck,"9000")){   //相应返回值为‘9000’，则命令执行成功
			errorNote = "Command execution success";
		}else if(StringUtils.equals(dataCheck,"6A88")){  //相应返回值为‘6A88’，则引用数据无法找到
			errorNote = "Referenced data not found";
		}else if(StringUtils.equals(dataCheck,"6A84")){  //相应返回值为‘6A84’，则内存空间不够
			errorNote = "Not enough memory space";
		}else if(StringUtils.equals(dataCheck,"6A80")){  //相应返回值为‘6A80’，则APDU指令data域参数不正确
			errorNote = "Incorrect parameters in data field";
		}else if(StringUtils.equals(dataCheck,"6581")){  //相应返回值为‘6581’，则存储失败
			errorNote = "Memory failure";
		}else if(StringUtils.equals(dataCheck,"9484")){  //相应返回值为‘9484’，则卡片不支持该加密算法
			errorNote = "Algorithm not supported";
		}else if(StringUtils.equals(dataCheck,"9485")){  //相应返回值为‘9485’，则kcv值无效
			errorNote = "Invalid key check value";
		}else{
			errorNote = dataCheck + "The returned value of currently Enabled Profile has other error";
		}
		logger.info("card check:{}",errorNote);	
		if(!StringUtils.equals(dataCheck,"9000")){
			throw new EuiccBusiException("8010",errorNote);
		}
		
	}
	
	/**
	 * 截取返回码
	 * @param responseData
	 * @return
	 */
	private String paserCardContent(String responseData){
		String retCheck = "1111";
		if(!responseData.substring(2, 12).equals("380D0AAF80") && !responseData.substring(2, 10).equals("0D0AAF80")
				&&!responseData.substring(2, 12).equals("300D0AAF80")){
			return "9999";
		}
		int offSize = responseData.indexOf("00000D0A30");
		retCheck = responseData.substring(offSize-4, offSize);
		logger.info("processing result code：" + retCheck);
		return retCheck;
	}

	/**
	 * step1:initialupdate
	 * @param eid
	 * @return
	 */
	private String initializeApdu(String eid) {
		InitializeUpdateBean initUpdateBean = Scp03ForUpdate.initializeUpdate(kerV);
		initUpdateBean.setHostChallenge(initUpdateBean.getHostChallenge());
		logger.info("hostchanllenge from initialUpdate:" + initUpdateBean.getHostChallenge());
		CacheUtil.put("scp03-initial" + eid, new Gson().toJson(initUpdateBean));
		String iniString = initUpdateBean.getApdu() + "00";
		String commandString = ToTLV.toTLV("22", iniString);
		return commandString;
	}
	/**
	 * step2:externalAuth
	 * @param eid
	 * @param isdPAID
	 * @param counterString
	 * @return
	 */
	private String externalCmd(String eid , String isdPAID,String counterString){
	    	ExternalAuthBean  apdu = null;
	    	String keyENC = getKeyENC(eid, isdPAID);
			String keyMAC = getKeyMAC(eid, isdPAID);
	    	
			try {
				String initializeUpdate = CacheUtil.getString("scp03-initial" + eid);
				InitializeUpdateBean initUpdateBean = new Gson().fromJson(
						initializeUpdate, InitializeUpdateBean.class);
				String hostChallenge = initUpdateBean.getHostChallenge();
				logger.info("hostchanllenge :" + hostChallenge);
				
				logger.info("85 sqcCounter :" + counterString);
				String cardChallenge = Scp03ForUpdate.cardChallenge(keyENC, counterString,isdPAID);
				logger.info("cardChallenge :" + cardChallenge);
				apdu = Scp03ForUpdate.externalAuthScp03(securedLevel,hostChallenge,cardChallenge,keyMAC,perMac);
				apdu.setCardCounter(counterString);
				CacheUtil.put("scp03-initial" + eid, new Gson().toJson(apdu));
				logger.info("encCounter value in externalAuth :" + apdu.getEncCounter());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("externalAuth error:{}", e.getMessage());
				throw new EuiccBusiException("0001", e.getMessage());
			}
			String apduString = apdu.getApdu() + "00";
			String commandString = ToTLV.toTLV("22", apduString);
			return commandString;

	    	
	    }
	/**
	 * step3:group command apdu  
	 * @param commandData
	 * @param eid
	 * @param isdPAID
	 * @return
	 * @throws Exception
	 */
	private String gpScp03Command(String commandData,String eid,String isdPAID) throws Exception{
	    	CmdApduBean encryptionData = null;
	    	String logicalChannel = "00";
	    	String keyENC = getKeyENC(eid, isdPAID);
			String keyMAC = getKeyMAC(eid, isdPAID);
	    	
	    	String cmdExternal = CacheUtil.getString("scp03-initial" + eid);
	    	ExternalAuthBean cmdExternalBean = new Gson().fromJson(
					cmdExternal, ExternalAuthBean.class);
			String hostChallenge = cmdExternalBean.getHostChallenge();
			logger.info("gp get hostchallengefromexternal :" + hostChallenge);
			String perMac = cmdExternalBean.getPerMac();
			logger.info("gp get permacfromexternal :" + perMac);
			String encCounter = cmdExternalBean.getEncCounter();//加密计数器
			logger.info("gp get enccounterefromexternal :" + encCounter);
			
			String cardChallenge = Scp03ForUpdate.cardChallenge(keyENC, cmdExternalBean.getCardCounter(), isdPAID);
			encryptionData = Scp03ForUpdate.gpApduScp03(commandData, hostChallenge, cardChallenge, encCounter, securedLevel, logicalChannel, keyENC, keyMAC, perMac);
			cmdExternalBean.setEncCounter(encryptionData.getCounter().toUpperCase());
			cmdExternalBean.setPerMac(encryptionData.getPerMac().toUpperCase());
			logger.info("permac value after encryption :" + encryptionData.getPerMac().toUpperCase());
			logger.info("encCounter value in last command :" + encryptionData.getCounter().toUpperCase());
			CacheUtil.put("scp03-initial" + eid, new Gson().toJson(cmdExternalBean));
			String dataTemp = encryptionData.getApdu() + "00";
			//String commandString = ToTLV.toTLV("AA", ToTLV.toTLV("22", dataTemp));
			String commandString = ToTLV.toTLV("22", dataTemp);
			return commandString;
	    	
	    }
	
	/**
	 * 选择将要发送的apdu
	 * @param triggerStep
	 * @param eid
	 * @param isdPAid
	 * @param smsCenterNo
	 * @return
	 * @throws Exception
	 */
	private String chooseFunction(int triggerStep,String eid,String isdPAid,String smsCenterNo) throws Exception{
		String commandData = "";
		if (triggerStep == 2) {
			commandData = commandOrg(eid, isdPAid, smsCenterNo);
		}else if (triggerStep == 3) {
			commandData = tlv45IsdpApdu(eid,isdPAid);
		}else if (triggerStep == 4) {
			commandData = tlv42IsdpApdu(eid, isdPAid);
		}else if (triggerStep == 5) {
			commandData = tlvToken(eid, isdPAid);
		}else if (triggerStep == 6) {
			commandData = tlv5F20IsdpApdu(eid, isdPAid);
		}
		return commandData;
	}
	  /**
	   * 获取短信中心号码并拼装
	   * @param eid
	   * @param isdPAid
	   * @return
	   */
	  private String commandOrg(String eid,String isdPAid,String smsCenter){
		  EuiccProfileWithBLOBs profileWithBLOBs = profileService.selectByEidAndIsdPAid(eid,isdPAid);
		  logger.info("smscenter number:" + smsCenter);
		  //将新的短信中心号码放进数据库
		  String smsNumber = profileWithBLOBs.getSmscenterNo();
		  if (!StringUtils.equals(smsCenter, smsNumber)) {
			  EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
			  record.setIccid(profileWithBLOBs.getIccid());
			  record.setSmscenterNo(smsCenter);
			  profileService.updateByPrimaryKeySelective(record);
		  }
		  //未经过翻转的短信中心号码：
		  if ( smsCenter.length()%2 != 0) {
				int padlen = 2 - smsCenter.length()%2;
				smsCenter = smsCenter + "FF".substring(0, padlen);
			}
		  String centerNoTemp = ByteUtil.reverseSmsCenNo(smsCenter);
		  String resultCenterNo = ToTLV.toTLV("06", "91" + centerNoTemp);
		  logger.info("smscenterno after reverse:" + resultCenterNo);
		  String paramISDP = ToTLV.toTLV("A0", resultCenterNo);
		  String cmdData = "80E28800" + ToTLV.toTLV(ToTLV.toTLV("3A07", paramISDP));
		  return cmdData;
	  }
	  /**
	   * isdpImage apdu
	   * @param eid
	   * @param isdPAid
	   * @return
	   */
	  public String tlv45IsdpApdu(String eid,String isdPAid){
		  EuiccProfileWithBLOBs record = profileService.selectByEidAndIsdPAid(eid,isdPAid);
			String input = record.getIsdPImageNo();
			String paramIsdp1 = ToTLV.toTLV("45", input);
			String dataString = ToTLV.toTLV("0070", paramIsdp1);
			String dataField = ToTLV.toTLV(dataString);
			StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(dataField);
			return apdu.toString();
		}
	  /**
	   * isdpId apdu
	   * @param eid
	   * @param isdPAid
	   * @return
	   */
	  public String tlv42IsdpApdu(String eid,String isdPAid){
		  EuiccProfileWithBLOBs record = profileService.selectByEidAndIsdPAid(eid,isdPAid);
			String input = record.getIsdPIdNo();
			String paramIsdp1 = ToTLV.toTLV("42", input);
			String dataString = ToTLV.toTLV("0070", paramIsdp1);
			String dataField = ToTLV.toTLV(dataString);
			StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(dataField);
			return apdu.toString();
		}
	  /**
	   * applicationProvider apdu
	   * @param eid
	   * @param isdPAid
	   * @return
	   */
	  public String tlv5F20IsdpApdu(String eid,String isdPAid){
		  EuiccProfileWithBLOBs record = profileService.selectByEidAndIsdPAid(eid,isdPAid);
			String input = record.getApplicationProviderNo();
			String paramIsdp1 = ToTLV.toTLV("5F20", input);
			String dataString = ToTLV.toTLV("0070", paramIsdp1);
			String dataField = ToTLV.toTLV(dataString);
			StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(dataField);
			return apdu.toString();
		}
	  
	  /**
	   * token密钥 apdu
	   * @param eid
	   * @param isdPAid
	   * @return
	   * @throws Exception
	   */
	  public String tlvToken(String eid,String isdPAid) throws Exception {
			String tokenKey = "20212223242526270101010101010101";
			String keyVer1 = "70";//脚本上为70
			String keyCheckData = "01010101010101010101010101010101";
			String ivString = "00000000000000000000000000000000";
			String keyDek = getKeyDek(eid, isdPAid);
			EuiccProfileWithBLOBs record = profileService.selectByEidAndIsdPAid(eid,isdPAid);
			String tokenId = record.getTokenIdNo().substring(record.getTokenIdNo().length()-2, record.getTokenIdNo().length());
			String encryptEncKey = AesCbc.aesCbc1(tokenKey, keyDek, ivString, 0);
			String tempString = AesCbc.aesCbc1(keyCheckData, tokenKey, ivString, 0);
			String KCV = tempString.substring(0,6);
			String tlvKCV = ToTLV.toTLV(KCV);
//			String tokenVerKcv = keyVer1 + KCV;
			String tlvEncKey = ToTLV.toTLV(ToTLV.toTLV(encryptEncKey));
			String cmdString = ToTLV.toTLV(keyVer1 + "88" + tlvEncKey + tlvKCV);
			String cmdData = "80D800" + tokenId + cmdString;
			return cmdData;
		}
		
	  /**
	   * 获取enc
	   * @param eid
	   * @param isdPAid
	   * @return
	   */
	  private String getKeyENC(String eid ,String isdPAid){
		  EuiccScp03 record = new EuiccScp03();
		  record.setId("01");
		  record.setEid(eid);
		  record.setIsdPAid(isdPAid);
		  record = euiccService.selectByScp03(record);
		  return record.getData();

	  }
	  /**
	   * 获取mac
	   * @param eid
	   * @param isdPAid
	   * @return
	   */
	  private String getKeyMAC(String eid ,String isdPAid){
		  EuiccScp03 record = new EuiccScp03();
		  record.setId("02");
		  record.setEid(eid);
		  record.setIsdPAid(isdPAid);
		  record = euiccService.selectByScp03(record);
		  return record.getData();
	  }
	  /**
	   * 获取dek
	   * @param eid
	   * @param isdPAid
	   * @return
	   */
	  private String getKeyDek(String eid ,String isdPAid){
		  EuiccScp03 record = new EuiccScp03();
		  record.setId("03");
		  record.setEid(eid);
		  record.setIsdPAid(isdPAid);
		  record = euiccService.selectByScp03(record);
		  return record.getData();
	  }
		/**
		 * step4:预拼装step3 指令的卡片返回值，用来验证正确性
		 * @param eid
		 * @param isdPAID
		 * @param inputData
		 * @return
		 * @throws Exception
		 */
		private String gpScp03Resp(String eid,String isdPAID,String inputData) throws Exception{
			String keyENC = getKeyENC(eid, isdPAID);
			String keyMAC = getKeyMAC(eid, isdPAID);
	    	String status = "9000";
	    	String cmdExternal = CacheUtil.getString("scp03-initial" + eid);
	    	ExternalAuthBean cmdExternalBean = new Gson().fromJson(
					cmdExternal, ExternalAuthBean.class);
	    	String perMac = cmdExternalBean.getPerMac();
	    	String encCounter = cmdExternalBean.getEncCounter();
	    	logger.info("permac value from redis:" + perMac);
	    	
	    	String hostChanllenge = cmdExternalBean.getHostChallenge();
	    	String cardChallenge = Scp03ForUpdate.cardChallenge(keyENC, cmdExternalBean.getCardCounter(), isdPAID);
	    	String respCmdString = Scp03ForUpdate.gpRespScp03(hostChanllenge,cardChallenge,encCounter,inputData, status, securedLevel,keyENC,keyMAC,perMac);
	    	String resultStr = ToTLV.toTLV("23", respCmdString);
	    	CacheUtil.remove("scp03-initial" + eid);
	    	return resultStr;
	    	
	    }

}
