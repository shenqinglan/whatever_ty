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
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.EuiccScp03;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccScp03Service;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.trigger.SmsTriggerUtil;
import com.whty.security.scp03t.scp03t.Scp03t;
import com.whty.security.scp03t.scp03t.bean.CmdApduBean;
import com.whty.security.scp03t.scp03t.bean.ExternalAuthBean;
import com.whty.security.scp03t.scp03t.bean.InitializeUpdateBean;
import com.whty.security.scp03t.scp03t.bean.InitializeUpdateRespBean;
import com.whty.security.scp03t.scp03t.counter.Scp03Counter;
import com.whty.security.scp03t.scp03t.mock.Scp03tMock;

/**
 * 下发profile指令给卡片
 * 
 * @author Administrator
 * 
 */
@Service("downloadProfileApdu")
public class DownloadProfileApduHandler extends AbstractHandler {
	private Logger logger = LoggerFactory
			.getLogger(DownloadProfileApduHandler.class);
	//private String keyENC = "26B021596FF67F775841C173027F6256";
	//private String keyMAC = "4B0DFE6C032668DB25EF4C45E59682FB";
	//置于euicc-sr-container中的配置文件中
	//private String keyVer = "30";
	//private String securedLevel = "33";
	//private String sqcCounter="000000";//todo  当密钥更新或新建时，通道计数器会置零；此外每打开通道一次，计数器+1
	//private int length = (1024) * 2; // Profile每包大小

	@Autowired
	private EuiccScp03Service euiccService;
	
	/**
	 * 下发apdu或者其它形式命令给eUICC卡片
	 * 
	 * @param requestStr
	 * @return
	 */
	public byte[] handle(String requestStr) {
		String apdu = "";
		String eid = TlsMessageUtils.getEid(requestStr);
		String sms = CacheUtil.getString(eid);
		SmsTrigger eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
		String data = eventTrigger.getProfileData();
		String length = SpringPropertyPlaceholderConfigurer.getStringProperty("each_seg_len");
		String[] profile = profileData(data, Integer.parseInt(length)*2);
		System.out.println("eventTrigger.getIccid(): " + eventTrigger.getIccid());
		String isdPAID = eventTrigger.getIsdPAid();
		System.out.println("isdPAID2333: " + isdPAID);
		System.out.println("eventTrigger.getStep(): " + eventTrigger.getStep());
		if (eventTrigger.getStep() == 0) {
			int num = getProfileNum(data);
			eventTrigger.setProfileNum(num);
			CacheUtil.put(eid, new Gson().toJson(eventTrigger));
			String apdu1 = initializeCmd(eid);
			String apdu2 = extendCmd(eid , isdPAID);
			apdu = apdu1 + apdu2; // 脚本中的初始化更新与外部鉴权是一起发送的
		} else if (eventTrigger.getStep() <= eventTrigger.getAllStep()) {
			int n = eventTrigger.getStep();
			apdu = encryptionProfile(profile[n - 1], eid , isdPAID,n);
			System.out.println(n+" profile apdu:"+apdu);
		}
		return httpPostResponseNoEnc(apdu, isdPAID);
	}

	private int getProfileNum(String data) {
		String length = SpringPropertyPlaceholderConfigurer.getStringProperty("each_seg_len");
		int len = Integer.parseInt(length)*2;
		int num = data.length() / len;
		if (data.length() % len != 0) {
			num += 1;
		}
		return num;
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
			int num = eventTrigger.getProfileNum();
			if (eventTrigger.getStep() < num) {
				System.out.println("**********************"+eventTrigger.getStep()+"**************************");
				checkResp(requestStr);
				updateTrigger(eventTrigger);
			} else if (eventTrigger.getStep() == num) {
				System.out.println("**********************"+eventTrigger.getStep()+"**************************");
				//CacheUtil.remove("scp03" + eid);
				SmsTriggerUtil.notifyProcessResult(eventTrigger, processResult);
			}
		} catch (Exception e) {
			//CacheUtil.remove("scp03" + eid);
			logger.error("checkProcessResp error:{}", e.getMessage());
			eventTrigger.setErrorMsg(e.getMessage());
			e.printStackTrace();
			processResult = false;
			SmsTriggerUtil.notifyProcessResult(eventTrigger, processResult);
		}
		return processResult;
	}

	/**
	 * 人为抛异常检查不合格
	 * @param requestStr
	 */
	private void checkResp(String requestStr) {
		logger.info("card resp:{}",requestStr);
		int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
		System.out.println(endOfDbl0D0A);
		String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
		logger.info("apdu:{}",strData);
		String dataCheck = paserCardContent(strData);
		String errorNote = "";
		//initialise 失败
		String initError1 = ToTLV.toTLV("9F44", "01");
		String initError2 = ToTLV.toTLV("9F44", "03");
		//externalAuth 失败
		String externalError1 = ToTLV.toTLV("9F45", "01");
		String externalError2 = ToTLV.toTLV("9F45", "02");
		//下发profile数据 失败
		String profileExecuError1 =  ToTLV.toTLV("9F46", "01");
		String profileExecuError2 =  ToTLV.toTLV("9F46", "02");
		if(StringUtils.equals(dataCheck,"8400")){   //相应返回值为‘8400’，则命令执行成功
			errorNote = "Initialise Command execution success";
		}else if(StringUtils.equals(dataCheck,"8500")){  //相应返回值为‘8500’，则命令执行成功
			errorNote = "externalAuth Command execution success";
		}else if(StringUtils.equals(dataCheck,"8600")){  //相应返回值为‘8600’，则命令执行成功
			errorNote = "profile package Command execution success";
		}else if(StringUtils.equals(dataCheck,initError1)){ 
			errorNote = "error in length or structure of command data during initialise";
		}else if(StringUtils.equals(dataCheck,initError2)){  
			errorNote = "referenced data not found during initialise";
		}else if(StringUtils.equals(dataCheck,externalError1)){  
			errorNote = "error in length or structure of command data during externalAuth";
		}else if(StringUtils.equals(dataCheck,externalError2)){ 
			errorNote = "security error during externalAuth";
		}else if(StringUtils.equals(dataCheck,profileExecuError1)){  
			errorNote = "error in length or structure of command data during profile execution";
		}else if(StringUtils.equals(dataCheck,profileExecuError2)){  
			errorNote = "security error during profile execution";
		}else{
			errorNote = dataCheck + "The returned value of currently Enabled Profile has other error";
		}
		logger.info("card check:{}",errorNote);	
		if(StringUtils.equals(dataCheck.substring(0, 2),"9F")){
			throw new EuiccBusiException("8010","卡片返回值为9FXX，执行失败");
		}
		
	}

	private void updateTrigger(SmsTrigger eventTrigger) {
		eventTrigger.setStep(eventTrigger.getStep() + 1);
		eventTrigger.setAllStep(eventTrigger.getProfileNum());
		CacheUtil.put(eventTrigger.getEid(), new Gson().toJson(eventTrigger));
	}

	private String initializeCmd(String eid) {
		String keyVer = SpringPropertyPlaceholderConfigurer.getStringProperty("key_version");
		Scp03t scp = new Scp03t();
		String sqcCounter = Scp03Counter.addEncCounter("000000");
		System.out.println("sqcCounter " + sqcCounter);
		InitializeUpdateBean initUpdateBean = scp.initializeUpdateCmd(keyVer);
		initUpdateBean.setCardCount(sqcCounter);
		CacheUtil.put("scp03_0" + eid, new Gson().toJson(initUpdateBean));
		return initUpdateBean.getApdu();
	}

	private void checkInitializeResp(String eid , String isdPAID) {
		Scp03t scp = new Scp03t();
		String keyENC = getKeyENC(eid, isdPAID);
		String keyMAC = getKeyMAC(eid, isdPAID);
		try {
			logger.info("first check");
			String initializeUpdate = CacheUtil.getString("scp03_0" + eid);
			//CacheUtil.remove("scp03_0" + eid);
			InitializeUpdateBean initUpdateBean = new Gson().fromJson(
					initializeUpdate, InitializeUpdateBean.class);
			String hostChallenge = initUpdateBean.getHostChallenge();
			String receiveApdu = new Scp03tMock()
					.initializeUpdateResponse(hostChallenge);
			InitializeUpdateRespBean respBean = scp.checkInitializeUpdateResp(
					receiveApdu, hostChallenge, keyMAC, keyENC);
			
			if (!respBean.isCheckResult()) {
				throw new EuiccBusiException("0001",
						"initializeUpdate check error");
			}
			CacheUtil.put("scp03_0" + eid, new Gson().toJson(respBean));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("checkInitializeResp error:{}", e.getMessage());
			throw new EuiccBusiException("0001", e.getMessage());
		}
	}

	private String extendCmd(String eid , String isdPAID) {
		String securedLevel = SpringPropertyPlaceholderConfigurer.getStringProperty("secure_level");
		String apdu = "";
		Scp03t scp = new Scp03t();
		String keyENC = getKeyENC(eid, isdPAID);
		String keyMAC = getKeyMAC(eid, isdPAID);
		try {
			String initializeUpdate = CacheUtil.getString("scp03_0" + eid);
			//CacheUtil.remove("scp03_0" + eid);
			InitializeUpdateBean initUpdateBean = new Gson().fromJson(
					initializeUpdate, InitializeUpdateBean.class);
			String hostChallenge = initUpdateBean.getHostChallenge();
			System.out.println("85 sqcCounter " + initUpdateBean.getCardCount());
			String cardChallenge = scp.cardChallenge(keyENC, initUpdateBean.getCardCount(),
					isdPAID);
			ExternalAuthBean cmdExternal = new Scp03t().externalAuthCmd(
					securedLevel, hostChallenge, cardChallenge,/* S_MAC */
					keyMAC, Scp03Counter.perMac);
			cmdExternal.setCardCounter(initUpdateBean.getCardCount());
			apdu = cmdExternal.getApdu();
			CacheUtil.put("scp03_0" + eid, new Gson().toJson(cmdExternal));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("externalAuth error:{}", e.getMessage());
			throw new EuiccBusiException("0001", e.getMessage());
		}
		return apdu;
	}

	private void checkExtendResp() {
		String receiveExternalApdu = new Scp03tMock().externalAuthResponse();// test
		// todo
		// delete
		int resp = new Scp03t().checkExternalAuthResp(receiveExternalApdu);
		logger.info("second check:{}", resp);
	}


	private String encryptionProfile(String data,String eid , String isdPAID,int n) {
		Scp03t scp = new Scp03t();
		CmdApduBean encryptionDate = null;
		String keyENC = getKeyENC(eid, isdPAID);
		String keyMAC = getKeyMAC(eid, isdPAID);
		
		String cmdExternal = CacheUtil.getString("scp03_"+(n-1) + eid);
		//CacheUtil.remove("scp03_"+(n-1) + eid);
		ExternalAuthBean cmdExternalBean = new Gson().fromJson(
				cmdExternal, ExternalAuthBean.class);
		String hostChallenge = cmdExternalBean.getHostChallenge();
		String perMac = cmdExternalBean.getPerMac();
		String encCounter = cmdExternalBean.getEncCounter();
		String cardChallenge = scp.cardChallenge(keyENC, cmdExternalBean.getCardCounter(), isdPAID);
		encryptionDate = scp.encryptionData(data, hostChallenge, cardChallenge,
				encCounter, keyENC, keyMAC, perMac);
		cmdExternalBean.setEncCounter(encryptionDate.getCounter().toUpperCase());
		cmdExternalBean.setPerMac(encryptionDate.getPerMac().toUpperCase());
		CacheUtil.put("scp03_"+ n + eid, new Gson().toJson(cmdExternalBean));
		return encryptionDate.getApdu();
	}

	private void checkInstallResp() {
		// todo
	}

	private String[] profileData(String ProfileElement, int length) { // Profile数据分包
		int len = ProfileElement.length();
		int n = len / length;
		
		String[] data = new String[n+1];
		if (n != 0) {
			for (int i = 0; i < n; i++) {
				data[i] = ProfileElement
						.substring(i * length, (i + 1) * length);
			}
		}
		if (len % length != 0) {
			data[n] = ProfileElement.substring(length * n, len);
			System.out.println("数据分包个数为： " + (n+1));
		}else{
			System.out.println("数据分包个数为： " + n);
		}
		
		return data;

	}
	private String getKeyENC(String eid ,String isdPAid){
		EuiccScp03 record = new EuiccScp03();
		record.setId("01");
		record.setEid(eid);
		record.setIsdPAid(isdPAid);
		record = euiccService.selectByScp03(record);
		return record.getData();
		
	}
	private String getKeyMAC(String eid ,String isdPAid){
		EuiccScp03 record = new EuiccScp03();
		record.setId("02");
		record.setEid(eid);
		record.setIsdPAid(isdPAid);
		record = euiccService.selectByScp03(record);
		return record.getData();
	}
	/**
	 * 截取返回码
	 * @param responseData
	 * @return
	 */
	private String paserCardContent(String responseData){
		String IsdPCheck = "1111";
		if(!responseData.substring(2, 12).equals("380D0AAF80") && !responseData.substring(2, 10).equals("0D0AAF80")){
			return "9999";
		}
		int offSize = responseData.indexOf("00000D0A30");
		IsdPCheck = responseData.substring(offSize-4, offSize);
		logger.info("截取到的执行返回码：" + IsdPCheck);
		return IsdPCheck;
	}
}
