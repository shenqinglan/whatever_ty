package com.whty.euicc.sr.handler.netty.https.srchange.smsr2_receive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
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
import com.whty.euicc.common.utils.HttpClientSoap;
import com.whty.euicc.common.utils.SecurityUtil;
import com.whty.euicc.common.utils.XmlUtil;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccIsdR;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.EuiccScp03;
import com.whty.euicc.data.pojo.EuiccScp80;
import com.whty.euicc.data.pojo.EuiccScp81;
import com.whty.euicc.data.pojo.SmsTrigger;
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
import com.whty.euicc.packets.message.request.AuthenticateSMSRReqBody;
import com.whty.euicc.packets.message.request.CreateAdditionalKeySetReqBody;
import com.whty.euicc.packets.message.request.HandoverEUICCReceiveReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.FinaliseISDRhandoverReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.sr.handler.netty.https.srchange.smsr2_receive.service.CertSrEcdsaApdu;
import com.whty.euicc.sr.handler.netty.https.srchange.smsr2_receive.service.SecondStoreDataApdu;
import com.whty.euicc.sr.handler.netty.notify.ES4Notification;
import com.whty.euicc.trigger.SmsTriggerUtil;
import com.whty.security.aes.AesCmac;
import com.whty.security.ecc.ECCUtils;

/**
 * step 6
 * Verify ECASD Certificate
 * step 7
 * sr2向sr1发送EID,CERT.SR.ECDSA
 * step 13 
 * 获取sr1获取的RC
 * step 14
 * 生成临时密钥对，拼ePK_SR_ECKA
 * step 15
 * sr2调CreateAdditionalKeySet(eid, ePK.SR.ECKA)
 * step 23
 * sr2接收sr1发送的receipt，验证，生成密钥
 * step 24
 * sr2发短信至卡
 * step 27
 * sr2更新EIS
 * @author ZYJ
 *
 */
@Service("handoverEUICC")
public class HandoverEuiccReceiveHandler implements HttpHandler {

	private Logger logger = LoggerFactory.getLogger(HandoverEuiccReceiveHandler.class);
	
	private final String srChangeByWsYes = "1";
	private final String srChangeByWs = SpringPropertyPlaceholderConfigurer.getStringProperty("srChangeByWs");
	private static final String _url = SpringPropertyPlaceholderConfigurer.getStringProperty("es7SmsrService_url");
	
	private final String P = SpringPropertyPlaceholderConfigurer.getStringProperty("P");
	private final String A = SpringPropertyPlaceholderConfigurer.getStringProperty("A");
	private final String B = SpringPropertyPlaceholderConfigurer.getStringProperty("B");
	private final String Gx = SpringPropertyPlaceholderConfigurer.getStringProperty("Gx");
	private final String Gy = SpringPropertyPlaceholderConfigurer.getStringProperty("Gy");
	private final String N = SpringPropertyPlaceholderConfigurer.getStringProperty("N");
	private final String H = SpringPropertyPlaceholderConfigurer.getStringProperty("H");
	private final String eskEcdsa = SpringPropertyPlaceholderConfigurer.getStringProperty("eskEcdsa");
	//private final int Keylen = Integer.parseInt(SpringPropertyPlaceholderConfigurer.getStringProperty("Keylen"));
	private final int Keylen = 64;
	private final String share = SpringPropertyPlaceholderConfigurer.getStringProperty("share");
	
	private String Q_ECASD_ECKA="";
	private String Qx_ECASD_ECKA="";
	private String Qy_ECASD_ECKA="";
	
	//private final String share="108010";
	//private final String share = "108810";//协商scp80所需      
	
	@Autowired
	private SmsTriggerUtil smsTriggerUtil;
	
	@Autowired
	private CertSrEcdsaApdu cert;
	
	@Autowired
	private SecondStoreDataApdu second;
	
	@Autowired
	private EuiccCardService cardService;
	
    @Autowired	
	private EuiccProfileService profileService;
    
    @Autowired
    private EuiccIsdPService isdPService;
    
    @Autowired
    private EuiccIsdRService isdrService;
    
    @Autowired
    private EuiccScp03Service scp03Service;
    
    @Autowired
    private EuiccScp80Service scp80Service;
    
    @Autowired
    private EuiccScp81Service scp81Service;
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		HandoverEUICCReceiveReqBody reqBody = null;
		
		try{
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (HandoverEUICCReceiveReqBody) reqMsgObject.getBody();
			
			//接收MNO传过来的currentSmsrId
			String currentSmsrId = reqBody.getCurrentSmsrId();
			System.out.println("*** MNO->currentSmsrId ***:"+currentSmsrId);
			//接收SR1发送过来的currentSmsrId
			String currentSmsrId2 = reqBody.getIsdrId();
			System.out.println("*** SR1->currentSmsrId ***:"+currentSmsrId2);
			
			//接收SR1发送过来的PK.ECDSA.ECKA
			verifyCERTEcasd(reqBody);
			
			if(!checkInitialConditions()){
				throw new EuiccBusiException("0101", "检查初始化状态失败");
			}
			byte[] CERT_SR_ECDSA=cert.firstStoreDataApdu();
			
			String keyPairs=ECCUtils.createECCKeyPair(P, A, B, Gx, Gy, N, H);
			if(StringUtils.isBlank(keyPairs)){
				throw new RuntimeException("生成密钥对算法出错");
			}
			String eSK_SR=keyPairs.substring(0, 64);
			System.out.println("eSK_SR_ECKA >>"+eSK_SR);
			String ePK_SR="04"+keyPairs.substring(64);
			System.out.println("ePK_SR_ECKA >>"+ePK_SR);
			
			EISUpdate(reqBody);
			
			RespMessage message = null;
			if(StringUtils.equals(srChangeByWsYes, srChangeByWs)){
				message = authenticateSmSrByHttp(new String(CERT_SR_ECDSA), reqBody.getEid());
			}else{
				message = authenticateSmSr(reqBody.getEid(), new String(CERT_SR_ECDSA));
			}
			
			if(!StringUtils.equals(ErrorCode.SUCCESS, message.getCode())){
				
				throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
	        }
			
			//*********************************************
			String rc = message.getData();
			
			//rc = "000102030405060708090A0B0C0D0E0F";    //本地测试用
			
			String ePK_SR_ECKA = new String(second.secondStoreDataApdu(rc, ePK_SR, eskEcdsa));
			
			if(StringUtils.equals(srChangeByWsYes, srChangeByWs)){
				message = createAdditionalKeySetByHttp(ePK_SR_ECKA, reqBody.getEid());
			}else{
				message = createAdditionalKeySet(reqBody.getEid(), ePK_SR_ECKA);
			}
			
			if(!StringUtils.equals(ErrorCode.SUCCESS, message.getCode())){
				
				throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
	        }
			//*********************************************
			
			//用eSK.SR.ECKA和PK.ECASD.ECKA计算ShS，从ShS和可选的DR获取key set KS2；验证receipt
			String receiptFromEuicc = message.getData();
			logger.info("receiptFromEuicc:{}",receiptFromEuicc);
			String key=ECCUtils.eccKeyAgreement(P, A, B, Gx, Gy, N, H, eSK_SR, Qx_ECASD_ECKA, Qy_ECASD_ECKA, share, Keylen);
			if(StringUtils.isBlank(key)){
				throw new RuntimeException("生成shs算法出错");
			}
			
			String receipt=key.substring(0, 32);
//			String calKey="A615900203099501108001808101108201018301209100";
			String calKey = "A615900203099501108001888101108201018301029100";   //协商scp80所需
//			String receiptExcept=DesMac.threeDesMac(calKey,receipt,0);          //脚本%sReceiptExpect = @mac_9797_1(3, DES,%sreceipt,%sA6,2)
			String receiptExcept = new AesCmac().calcuCmac1(calKey, receipt);   //scp80脚本%sReceiptExpect = @CMAC(%sreceipt,%sA6)  协商scp80所需
			logger.info("receiptExcept:{}",receiptExcept);
			
			String scp02_enc_isdr=key.substring(32, 64);
			String scp02_mac_isdr=key.substring(64,96);
			String scp02_dek_isdr=key.substring(96,128);
			
			logger.info("SM-SR change receipt:{}",receipt);
			logger.info("SM-SR change enc:{}",scp02_enc_isdr);
			logger.info("SM-SR change mac:{}",scp02_mac_isdr);
			logger.info("SM-SR change dek:{}",scp02_dek_isdr);
			
//			updateScp80(scp02_enc_isdr,scp02_mac_isdr);
			
			/*if(!StringUtils.equals(receiptExcept, receiptFromEuicc)){
				throw new EuiccBusiException("0001","check ["+receipt+"] is not equals "+receiptFromEuicc);
			}*/
			
//			EISUpdate(reqBody);
			
			finaliseISDRhandover(reqBody);
			// 通知MNO,SR已切换
			ES4Notification notify = new ES4Notification();
			notify.handleNotifyInSrChange();
		}catch (Exception e) {
			logger.error("smsr change error:{}",e.getMessage());
			//返回失败信息
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
			dataRollback(reqBody);
		
		}
		return new Gson().toJson(respMessage).getBytes();
	}

	private RespMessage createAdditionalKeySetByHttp(String ePK_SR_ECKA, String reqEid) throws IOException {
		String eid = SecurityUtil.encodeHexString(reqEid);
		String ephemeralPublicKey = SecurityUtil.encodeHexString(ePK_SR_ECKA);
		String xml = inputStream2String(HandoverEuiccReceiveHandler.class.getClassLoader().getResourceAsStream("es7CreateAdditionalKeySet.xml"));
		xml = messageFormat(xml, ephemeralPublicKey, eid);
		
    	String response = HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
    	String receipt = SecurityUtil.hexStringToString(XmlUtil.parseXml(response, "ES7-CreateAdditionalKeySetResponse", "Receipt"));
    	System.out.println("receipt >> " + receipt);
    	return new RespMessage(ErrorCode.SUCCESS, "success", receipt);
	}

	private RespMessage authenticateSmSrByHttp(String smsrCert, String reqEid) throws IOException {
		String eid = SecurityUtil.encodeHexString(reqEid);
		String smsrCertificate = SecurityUtil.encodeHexString(smsrCert);
		String xml = inputStream2String(HandoverEuiccReceiveHandler.class.getClassLoader().getResourceAsStream("es7AuthenticateSMSR.xml"));
		xml = messageFormat(xml, smsrCertificate, eid);
		
    	String response = HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
    	String rc = SecurityUtil.hexStringToString(XmlUtil.parseXml(response, "ES7-AuthenticateSMSRResponse", "RandomChallenge"));
    	System.out.println("rc >>> " + rc);
    	return new RespMessage(ErrorCode.SUCCESS, "success", rc);
	}

	private RespMessage createAdditionalKeySet(String eid, String ePK_SR_ECKA) throws Exception {
		System.out.println("Start CreateAdditionalKeySet ............");
		String sr_send_url = SpringPropertyPlaceholderConfigurer.getStringProperty("sr_send_url");
        MsgHeader header = new MsgHeader("createAdditionalKeySet");
        CreateAdditionalKeySetReqBody requestBody = new CreateAdditionalKeySetReqBody();
		requestBody.setEid(eid);
		requestBody.setePK_SR_ECKA(ePK_SR_ECKA);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(sr_send_url,json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
		if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
        return respMessage;
	}

	private RespMessage authenticateSmSr(String eid, String certSrEcdsa) throws Exception {
		System.out.println("Start AuthenticateSMSR ............");
		String sr_send_url = SpringPropertyPlaceholderConfigurer.getStringProperty("sr_send_url");
        MsgHeader header = new MsgHeader("authenticateSMSR");
        AuthenticateSMSRReqBody requestBody = new AuthenticateSMSRReqBody();
		requestBody.setEid(eid);
		requestBody.setCertSrEcdsa(certSrEcdsa);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(sr_send_url,json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
		if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
//			dataRollback(reqBody);
			System.out.println("101000101001010101001010");
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
        return respMessage;
	}

	private void dataRollback(HandoverEUICCReceiveReqBody reqBody) {
		cardService.deleteByPrimaryKey(reqBody.getEid());
		profileService.deleteByEid(reqBody.getEid());
		isdPService.deleteByEid(reqBody.getEid());
		isdrService.deleteByEid(reqBody.getEid());
		scp03Service.deleteByEid(reqBody.getEid());
		scp80Service.deleteByEid(reqBody.getEid());
		scp81Service.deleteByEid(reqBody.getEid());
	}

	/**
	 * SR2触发短信
	 * @throws Exception 
	 */
	private void finaliseISDRhandover(HandoverEUICCReceiveReqBody reqBody) throws Exception {
		System.out.println("Start FinaliseISDRhandover ...........");
		MsgHeader header = new MsgHeader("finaliseIsdrHandover");
		FinaliseISDRhandoverReqBody requestBody = new FinaliseISDRhandoverReqBody();
		requestBody.setEid(reqBody.getEid());
		smsTriggerUtil.sendTriggerSms(requestBody,"deleteKeysApdu");
		SmsTrigger smsTrigger = smsTriggerUtil.waitProcessResult(requestBody);
		
		if(!smsTrigger.isProcessResult()){
			throw new EuiccBusiException(ErrorCode.FAILURE,"error");
		}
	}

	/**
	 * 更新eis,表明现在由它来管理该eUICC,查数据库
	 */
	private void EISUpdate(HandoverEUICCReceiveReqBody reqBody) {
		EuiccCard card = cardService.selectByPrimaryKey(reqBody.getEid());
		if(card!=null){
			throw new EuiccBusiException("0101", "SR2中已存在该card");
		}
		
		EuiccCard card2 = reqBody.getCard();
		card2.setSmsrId("0819");
		card2.setCount(card2.getCount()+5);
		cardService.insertSelective(card2);
		logger.info("card信息转移完毕");
		
		List<EuiccProfileWithBLOBs> profiles = profileService.selectByEid(reqBody.getEid());
		System.out.println("profiles >>> "+profiles);
		if(profiles!=null && !profiles.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该profile");
		}
		
		List<EuiccProfileWithBLOBs> profiles2 = reqBody.getProfiles();
		profileService.insertProfiles(profiles2);
		logger.info("profile信息转移完毕");
		
		List<EuiccIsdP> isdPs = isdPService.selectListByEid(reqBody.getEid());
		if(isdPs!=null && !isdPs.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该isdp");
		}
		
		List<EuiccIsdP> isdPs2 = reqBody.getIsdp();
		isdPService.insertIsdps(isdPs2);
		logger.info("isdp信息转移完毕");
		
		EuiccIsdR isdR = isdrService.selectByEid(reqBody.getEid());
		if(isdR!=null){
			throw new EuiccBusiException("0101", "SR2中已存在该isdr");
		}
		
		EuiccIsdR isdR2 = reqBody.getIsdR();
		isdrService.insertSelective(isdR2);
		logger.info("isdr信息转移完毕");
		
		List<EuiccScp03> scp03s = scp03Service.selectListByEid(reqBody.getEid());
		if(scp03s!=null && !scp03s.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该scp03");
		}
		
		List<EuiccScp03> scp03s2 = reqBody.getScp03s();
		scp03Service.insertListScp03(scp03s2);
		logger.info("scp03信息转移完毕");
		
		List<EuiccScp80> scp80s = scp80Service.selectListByEid(reqBody.getEid());
		if(scp80s!=null && !scp80s.isEmpty()){
			throw new EuiccBusiException("0101", "SR2中已存在该scp80");
		}
		
		List<EuiccScp80> scp80s2 = reqBody.getScp80s();
		scp80Service.insertListScp80(scp80s2);
		logger.info("scp80信息转移完毕");
		
		EuiccScp81 scp81 = scp81Service.selectByEid(reqBody.getEid());
		if(scp81!=null){
			throw new EuiccBusiException("0101", "SR2中已存在该scp81");
		}
		
		EuiccScp81 scp812 = reqBody.getScp81();
		scp81Service.insertSelective(scp812);
		logger.info("scp81信息转移完毕");
		
	}

	/**
	 * smsr2向smsr1发送证书等
	 * @param eid
	 * @param iccid
	 * @param certSrEcdsa
	 * @param eskEcdsa
	 * @param epkSr
	 * @return
	 * @throws Exception
	 */
	private RespMessage triggerKeyExchageSmsNotice(String eid, String iccid, String certSrEcdsa,String eskEcdsa,String epkSr,HandoverEUICCReceiveReqBody reqBody)throws Exception{
		String sr_send_url = SpringPropertyPlaceholderConfigurer.getStringProperty("sr_send_url");
        MsgHeader header = new MsgHeader("srChangeKeyExchange");
        AuthenticateSMSRReqBody requestBody = new AuthenticateSMSRReqBody();
		requestBody.setEid(eid);
		requestBody.setIccid(iccid);
		requestBody.setCertSrEcdsa(certSrEcdsa);
		/*requestBody.setEskSr(eskEcdsa);   
		requestBody.setEpkSr(epkSr);*/
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(sr_send_url,json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
		if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
//			dataRollback(reqBody);
			System.out.println("101000101001010101001010");
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
        return respMessage;
        
	}

	/**
	 * 接收smsr1发送的EIS，校验EIS中的ECASD证书是否合法，并抽取PK.ECASD.ECKA
	 */
	private void verifyCERTEcasd(HandoverEUICCReceiveReqBody reqBody) {
		Q_ECASD_ECKA = reqBody.getCertOfEuicc();
		System.out.println("*** Q_ECASD_ECKA ***:"+Q_ECASD_ECKA);
		Qx_ECASD_ECKA=Q_ECASD_ECKA.substring(0, 64);
		Qy_ECASD_ECKA=Q_ECASD_ECKA.substring(64);
	}
	
	private boolean checkInitialConditions() {
		return true;
	}
	
	private String inputStream2String(InputStream is) throws IOException{
		   BufferedReader in = new BufferedReader(new InputStreamReader(is));
		   StringBuffer buffer = new StringBuffer();
		   String line = "";
		   while ((line = in.readLine()) != null){
		     buffer.append(line);
		   }
		   return buffer.toString();
	}
	
	/*
	 * 替换xml字符串中的{0},{1}
	 */
	private String messageFormat(String xmlString, String replaceString, String replaceString2){
		String str = MessageFormat.format(xmlString, replaceString, replaceString2);
		return str;
		
	}
	
	 
}
