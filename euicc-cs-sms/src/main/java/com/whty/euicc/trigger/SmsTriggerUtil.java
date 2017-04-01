package com.whty.euicc.trigger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.telecom.http.tls.test.Util;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.constant.SmsTriggerState;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.properties.EnvProperty;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.common.utils.HttpUtil;
import com.whty.euicc.common.utils.IpUtil;
import com.whty.euicc.common.utils.Tool;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.packets.message.request.ConnectParaUpdateByHttpsReqBody;
import com.whty.euicc.packets.message.request.CreateAdditionalKeySetReqBody;
import com.whty.euicc.packets.message.request.EuiccReqBody;
import com.whty.euicc.packets.message.request.GetScp03CounterByHttpsReqBody;
import com.whty.euicc.packets.message.request.InstallProfileReqBody;
import com.whty.euicc.packets.message.request.PersonalISDPReqBody;
import com.whty.euicc.packets.message.request.PorReqBody;
import com.whty.euicc.packets.message.request.SetFallBackAttrByHttpsReqBody;
import com.whty.euicc.packets.message.request.AuthenticateSMSRReqBody;
import com.whty.euicc.packets.message.request.UpdateSrAddressParaByHttpsReqBody;
import com.whty.euicc.sms.Sms;
/**
 * 短信发送工具类
 * @author Administrator
 *
 */
@Service
public class SmsTriggerUtil {
	private final String localHostIP = IpUtil.getLocalIp();
	private final String port = SpringPropertyPlaceholderConfigurer.getStringProperty("tls_port");
	private static Logger logger =  LoggerFactory.getLogger(SmsTriggerUtil.class);
	
	private final static int timeout = Integer.parseInt(StringUtils.defaultString(SpringPropertyPlaceholderConfigurer.getStringProperty("card_timeout"),"60"));
	
	@Autowired
	private Sms sms;
	
	@Autowired
	private EuiccCardService cardService;
	
	/**
	 * 发送短信，保存触发条件
	 * @param reqBody
	 */
	public void sendTriggerSms(EuiccReqBody reqBody,String eventType) {
		sendSms(reqBody);
		saveTrigger(reqBody,eventType);	
	}
	
	/**
	 * 等待卡片处理完成
	 * @param reqBody
	 */
	public SmsTrigger waitProcessResult(EuiccReqBody reqBody) {
		boolean flag = true;
		SmsTrigger eventTrigger = null;
		int count = 0;
		String key = reqBody.getEid();
		if(StringUtils.isNotBlank(reqBody.getEid()) && StringUtils.isNotBlank(reqBody.getPhoneNo())){
			//sms方式下删除、主删除、更新连接参数、fallback(主键是手机号码)
			key = reqBody.getPhoneNo();
		}
		while(flag){
			count++;
			String smsTrigger = CacheUtil.getString(key);
			if(!StringUtils.isEmpty(smsTrigger)){
				eventTrigger = new Gson().fromJson(smsTrigger,SmsTrigger.class);
				if(StringUtils.equals(SmsTriggerState.FINISH, eventTrigger.getState())){
					if(eventTrigger.isProcessResult())CacheUtil.remove(key);
					logger.info("----------已等待卡片通知处理完成");
					flag = false;
				}
	        } 
			if(count >= timeout){
				logger.info("----------等待卡片通知超时");
				eventTrigger.setProcessResult(false);
				flag = false;
			}   
			try {
				Thread.sleep(1000);
				logger.info("----------等待卡片通知");
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new EuiccBusiException("1002","业务异常");
			}
		}
		return eventTrigger;
	}
	
	public static void notifyProcessResult(SmsTrigger eventTrigger,boolean processResult) {
		eventTrigger.setState(SmsTriggerState.FINISH);
		eventTrigger.setProcessResult(processResult);
		String key = eventTrigger.getEid();
		if(StringUtils.isNotBlank(eventTrigger.getEid()) && StringUtils.isNotBlank(eventTrigger.getPhoneNo())){
			//sms方式下删除、主删除、更新连接参数、fallback(主键是手机号码)
			key =eventTrigger.getPhoneNo();
		}
		CacheUtil.put(key, new Gson().toJson(eventTrigger));		
	}

	/**
	 * 发送短信给短信网关或测试工具
	 * @param reqBody
	 */
	public void sendSms(EuiccReqBody reqBody) {
		logger.info("sms:{}",reqBody.getSms());
		String eid = reqBody.getEid();
		String phoneNo = reqBody.getPhoneNo();
		List<String> enSmsList =new ArrayList<String>();
		if(StringUtils.isBlank(phoneNo)){
			EuiccCard card = cardService.selectByPrimaryKey(eid);
			phoneNo = card.getPhoneNo();
		}
		String commandString ="";
		if (StringUtils.isBlank(reqBody.getSms())) {		
			commandString = getTriggerSmsApdu();
		} else {
			commandString = reqBody.getSms();
		}
		logger.info("plain sms:{}",commandString);
		//当指定tar值，覆盖预置的tar值
		enSmsList = chooseSendType(reqBody, eid, commandString);
		
		String isHuaWei = SpringPropertyPlaceholderConfigurer.getStringProperty("isHuaWei");
		for(String enSms : enSmsList){
			logger.info("en sms:{}",enSms);
			if(EnvProperty.isProduction()){
				logger.info("调用短信网关");
				if (StringUtils.equals(isHuaWei, "true")) {
					callHuaWei(phoneNo,enSms);
				} else {
					callSmsGateway(phoneNo, enSms);
				}
			} else {
				logger.info("调用工具");
//				SocketConnector(enSms);
			}
		}

	}

	private List<String> chooseSendType(EuiccReqBody reqBody, String eid,
			String commandString) {
		List<String> enSmsList;
		String tarValue = reqBody.getStrOfTar();
		if (StringUtils.isBlank(tarValue)) {
			enSmsList = sms.sendSmsNeedPor(eid,commandString,true);
		}else {
			logger.info("tarValue :" + tarValue);
			sms.initTar(tarValue);
			enSmsList = sms.sendSmsNeedPor(eid,commandString,true);
		}
		return enSmsList;
	}

	private void callHuaWei(final String phoneNo, final String enSms) {
		String hwSmsUrl = SpringPropertyPlaceholderConfigurer.getStringProperty("hw_sms_url");
		Map<String, String> argsMap = new HashMap<String, String>() {{ 
		    put( "report", "1" ); 
		    put( "src", "8613923799"); 
		    put( "dest", phoneNo ); 
		    put( "msg", enSms );
		}}; 
		String resp = HttpUtil.post(hwSmsUrl, argsMap);
		System.out.println(resp);
	}

	private void callSmsGateway(final String phoneNo, final String enSms) {
		String smsUrl = SpringPropertyPlaceholderConfigurer.getStringProperty("sms_url");	
		Map<String, String> argsMap = new HashMap<String, String>() {{ 
		    put( "report" , "1" ); 
		    put( "tp_pid" , "127" ); 
		    put( "tp_udhi" , "1" ); 
		    put( "msg_fmt" , "246" ); 
		    put( "src" , "10659818017705" ); 
		    put( "dest" , phoneNo ); 
		    put( "msg" , enSms ); 
		    put( "userid" , "2" ); 
		    put( "Platform" , "2" ); 
		}}; 
		String resp = HttpUtil.post(smsUrl, argsMap);
		System.out.println(resp);
	}


	/**
	 * 拼装短信内容，包含平台IP,PORT等参数
	 * @return
	 */
	private String getTriggerSmsApdu() {
		String commandString;
		String hostIP = StringUtils.defaultString(SpringPropertyPlaceholderConfigurer.getStringProperty("local_host_ip"),localHostIP);
		String ascIP = Util.byteArrayToHexString(Util.toByteArray(hostIP), "");//31302E382E34302E313430
		
		String[] hexHostIP = hostIP.split("\\.");
		System.out.println("hexHostIP " +hexHostIP.length);
		String ipTag = "3E0521";
		for (int i = 0; i < hexHostIP.length; i++) {
			ipTag += Tool.toHex(String.valueOf(hexHostIP[i]));
		}
		
		int lenAscIP = ascIP.length()/2;
		String lenString = Tool.toHex(String.valueOf(lenAscIP));
		String adminHost = "8A" + lenString + ascIP;
		//HTTP POST parameters value
		int postLen = adminHost.length()/2;
		lenString = Tool.toHex(String.valueOf(postLen));
		String post = "89" + lenString + adminHost;
		//Retry policy parameters
		//String retryPolicy = "86070000A50300001E";
		
		//3C030* 说明02是TCP，01是UDP
		String portHex = "3C0302" + Tool.toHex(port);
		String connectioParameters = "8413" + "35010339020500"+ portHex + ipTag;
		
		//int securityDomainLen = (connectioParameters.length() + retryPolicy.length() + post.length())/2;
		int securityDomainLen = (connectioParameters.length() + post.length())/2;
		lenString = Tool.toHex(String.valueOf(securityDomainLen));
		//Security Domain parameters value 
		String securityDomain = "83" + lenString + connectioParameters + post;

		//sms plainText
		int commandLen = securityDomain.length()/2;
		lenString = Tool.toHex(String.valueOf(commandLen));
		commandString = "81" + lenString + securityDomain;
		return commandString;
	}

	/**
	 * socket连接测试工具
	 * @param sms
	 */
	private static void SocketConnector(String sms) {
		Socket socket;
		try {
			String tool_ip = SpringPropertyPlaceholderConfigurer.getStringProperty("tool_ip");
			String tool_port = SpringPropertyPlaceholderConfigurer.getStringProperty("tool_port");
			socket = new Socket(tool_ip,Integer.parseInt(tool_port));
			socket.setReceiveBufferSize(102400);
			socket.setKeepAlive(true);//底层的TCP实现会监视该连接是否有效
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			int length = sms.getBytes().length;
			out.writeInt(length);
			writeBytes(out, sms.getBytes(), length);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * 发送字节流
	 * @param out
	 * @param bytes
	 * @param length
	 * @throws IOException
	 */
	private static void writeBytes(DataOutputStream out,byte[] bytes,int length) throws IOException{
        out.writeInt(length);
        out.write(bytes,0,length);
        out.flush();
    }
	
	
	private static void saveTrigger(EuiccReqBody reqBody,String eventType) {
		SmsTrigger smsTrigger = new SmsTrigger();
		smsTrigger.setEid(reqBody.getEid());
		if(StringUtils.isNotBlank(reqBody.getIccid()))smsTrigger.setIccid(reqBody.getIccid());
		if(StringUtils.isNotBlank(eventType))smsTrigger.setEventType(eventType);
		if(StringUtils.isBlank(eventType)){//sms方式下eventType为空
			String keyString = reqBody.getEid();//sms方式下启用，禁用(主键是eid)
			if(StringUtils.isNotBlank(reqBody.getEid()) && StringUtils.isNotBlank(reqBody.getPhoneNo())){
				smsTrigger.setPhoneNo(reqBody.getPhoneNo());
				//sms方式下删除、主删除、更新连接参数、fallback(主键是手机号码)
				keyString = reqBody.getPhoneNo();
			}
			CacheUtil.put(keyString, new Gson().toJson(smsTrigger));
			return;
		}
		
		smsTrigger.setStep(0);
		
		if(reqBody instanceof PorReqBody){
			PorReqBody porReqBody = (PorReqBody) reqBody;
			smsTrigger.setNoticeType(porReqBody.getNoticeType());
			smsTrigger.setIccid(porReqBody.getIccid());
			smsTrigger.setIsdPAid(porReqBody.getIsdPAid());
		}
		
		if(reqBody instanceof PersonalISDPReqBody){
			PersonalISDPReqBody personalReqBody = (PersonalISDPReqBody) reqBody;
			smsTrigger.setEskDp(personalReqBody.getEskDp());
			smsTrigger.setEpkDp(personalReqBody.getEpkDp());
			smsTrigger.setCertDpEcdsa(personalReqBody.getCertDpEcdsa());
			smsTrigger.setIccid(personalReqBody.getIccid());
			smsTrigger.setIsdPAid(personalReqBody.getIsdPAid());
		}
		
		if(reqBody instanceof InstallProfileReqBody){
			InstallProfileReqBody installProfileReqBody = (InstallProfileReqBody) reqBody;
			smsTrigger.setProfileData(installProfileReqBody.getProfileFile());
			smsTrigger.setIccid(installProfileReqBody.getIccid());
			smsTrigger.setIsdPAid(installProfileReqBody.getIsdPAid());
		}
		if(reqBody instanceof AuthenticateSMSRReqBody) {
			AuthenticateSMSRReqBody changeReqBody = (AuthenticateSMSRReqBody) reqBody;
			smsTrigger.setCertSrEcdsa(changeReqBody.getCertSrEcdsa());
			smsTrigger.setIccid(changeReqBody.getIccid());
		}
		if (reqBody instanceof GetScp03CounterByHttpsReqBody) {
			GetScp03CounterByHttpsReqBody scp03CounterReqBody = (GetScp03CounterByHttpsReqBody)reqBody;
			smsTrigger.setIsdPAid(scp03CounterReqBody.getIsdPAID());
			
		}if (reqBody instanceof ConnectParaUpdateByHttpsReqBody) {
			ConnectParaUpdateByHttpsReqBody connectReqBody = (ConnectParaUpdateByHttpsReqBody)reqBody;
			smsTrigger.setIsdPAid(connectReqBody.getIsdPAID());
			smsTrigger.setSeqCounter(connectReqBody.getSeqCounter());
			smsTrigger.setSmsCenterNo(connectReqBody.getSmsCenterNo());
			
		}
		if(reqBody instanceof UpdateSrAddressParaByHttpsReqBody) {
			UpdateSrAddressParaByHttpsReqBody updateReqBody = (UpdateSrAddressParaByHttpsReqBody) reqBody;
			smsTrigger.setrId(updateReqBody.getrId());
			smsTrigger.setIsdRAid(updateReqBody.getIsdRAid());
			smsTrigger.setSrAddressPara(updateReqBody.getSrAddressPara());
		}
		if (reqBody instanceof SetFallBackAttrByHttpsReqBody) {
			SetFallBackAttrByHttpsReqBody setFallBackReqBody = (SetFallBackAttrByHttpsReqBody) reqBody;
			smsTrigger.setIsdPAid(setFallBackReqBody.getIsdPAid());
			smsTrigger.setIccid(setFallBackReqBody.getIccid());
			smsTrigger.setEid(setFallBackReqBody.getEid());
		}
		
		if(reqBody instanceof CreateAdditionalKeySetReqBody){
			CreateAdditionalKeySetReqBody createAdditionalKeySetReqBody = (CreateAdditionalKeySetReqBody) reqBody;
			smsTrigger.setEid(createAdditionalKeySetReqBody.getEid());
			smsTrigger.setEpkSrEcka(createAdditionalKeySetReqBody.getePK_SR_ECKA());
		}
		
		CacheUtil.put(reqBody.getEid(), new Gson().toJson(smsTrigger));
	}
}
