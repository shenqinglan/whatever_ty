package com.whty.oauth.platform.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.whty.euicc.sms.Sms;
import com.whty.euicc.sms.exception.SMSException;
import com.whty.oauth.platform.common.Constants;
import com.whty.oauth.platform.pojo.InterfaceInfo;
import com.whty.oauth.platform.pojo.OauthCard;
import com.whty.oauth.platform.pojo.OauthKey;
import com.whty.oauth.platform.service.IInterfaceInfoService;
import com.whty.oauth.platform.service.IOauthCardService;
import com.whty.oauth.platform.service.IOauthKeyService;
import com.whty.oauth.platform.util.HTTPWeb;
import com.whty.oauth.platform.util.HexUtils;

@RestController
@RequestMapping("/oauth")
public class ReceiveSmsController {
	private Logger logger = LoggerFactory.getLogger(ReceiveSmsController.class);
	
	@Autowired
	@Qualifier("oauthCardServiceImpl")
	private IOauthCardService cardService;
	
	@Autowired
	@Qualifier("interfaceInfoServiceImpl")
	private IInterfaceInfoService interfaceService;

	@Value("${interface.send.url}")
	private String interface_send_url;
	
	@Autowired
	@Qualifier("oauthKeyServiceImpl")
	private IOauthKeyService keyService;
	
	@Autowired  
    private RedisTemplate<String,Map<String, String>> redisTemplate; 

	/**
	 * 
	 * @param src
	 * @param msg
	 * @return
	 * @throws IOException
	 * @throws SMSException 
	 */
	@RequestMapping(value="/receiveSms")
	public String receiveSms(String src,String msg) throws IOException, SMSException{		
		logger.info("src:{},msg:{}",src,msg);
			
		String transactionID = null; //交易编号
		String confirmFlag = null;//确认标识 00：取消，01：成功，03：密码已经锁定
		String checkData = null;//用户取消或者未操作时该值为空，Data为 用户输入框输入验证码信息。验证码为4-8位字符或数字，采用ASCII编码方式
		String stateFlag = null;//注册状态标识 00：未注册，01：已注册
		String authCodeString = null;//认证码
		
		//数据库记录表，有一个ret_result字段，来记录接口返回结果，confirmFlag和stateFlag组合，会有重复。注册状态标识04，未注册，05已注册
		
		Sms sms = new Sms();
		
		Map<String, String> mapKey = new HashMap<String, String>();
		HashOperations<String, String, String> redisHash = redisTemplate.opsForHash();
		if (!redisHash.getOperations().hasKey(Constants.REDIS_KEY_NAME_PREFIX+src)) {
			List<OauthKey> listKey = keyService.findCardKey(src, "1", "1");
			if (listKey == null || listKey.size() < Constants.SCP80_ENCRYP_KEYS_NUMBER) {
				logger.info("get macKey from database null");
				return "encKey null";
			}
			for (OauthKey key : listKey) {
				if (StringUtils.equals(key.getKeyIndex(), Constants.SCP80_ENCRYP_KIC_INDEX)) {
					mapKey.put("ctKicKey", key.getMacKey());
				} else if (StringUtils.equals(key.getKeyIndex(), Constants.SCP80_ENCRYP_KID_INDEX)) {
					mapKey.put("ctKidKey", key.getMacKey());
				}
			}
			mapKey.put("ctSpi", "");
		} else {
			mapKey = redisHash.entries(Constants.REDIS_KEY_NAME_PREFIX+src);
		}
		
		logger.info("mapKey >>> {}", mapKey);
		
		if (mapKey.size() == 0) {
			logger.info("encKey Error");
			return "encKey Error";
		}
		
		//解密之后的明文上行数据体
		String plainString = sms.parSubFromCard(msg, mapKey.get("ctSpi"), mapKey.get("ctKicKey"), mapKey.get("ctKidKey"));	
		logger.info("plainString:{}",plainString);
		
		//解析数据
		String type = plainString.substring(0, 2);
		
		//注册流程
		if (type.equals(Constants.INTERFACE_TYPE_TRIGGER_REGISTER_MO))// type 为 51  业务注册接口，包含信息TransactionID
		{		
			processResigter(src,plainString);
			return "业务注册接口完成";
		}
		
		//基本快捷认证业务
		if (type.equals(Constants.INTERFACE_TYPE_BASE_OAUTH_MO))
		{
			type = Constants.INTERFACE_TYPE_CODE_BASE_OAUTH;
			transactionID = plainString.substring(4,20);
			confirmFlag = plainString.substring(20);	
			logger.info("confirmFlag:{}",confirmFlag);
			
			operateDatabaseContainState(src, type, transactionID, confirmFlag);
			return "基本快捷认证业务完成";
		};
		
		//基于验证码的快捷认证业务
		if (type.equals(Constants.INTERFACE_TYPE_VALIDCODE_OAUTH_MO))
		{
			type = Constants.INTERFACE_TYPE_CODE_VALIDCODE_OAUTH;
			transactionID = plainString.substring(4,20);
			confirmFlag = plainString.substring(20,22);
			authCodeString = plainString.substring(22);//卡片返回的数据是ASCII码的hex表示。需要转一下
			
			String asciiAuthCode = HexUtils.hexToAscii(authCodeString);
			System.out.println("confirmFlag " + confirmFlag);
			logger.info("confirmFlag:{}",confirmFlag);
			logger.info("asciiAuthCode:{}",asciiAuthCode);
			
			operateDatabaseContainStateAndAuthCode(src, type, transactionID, confirmFlag, asciiAuthCode);
			return "基于验证码的快捷认证业务完成";
		};

		//个人密码修改
		if (type.equals(Constants.INTERFACE_TYPE_CHANGE_PWD_MO))
		{
			type = Constants.INTERFACE_TYPE_CODE_CHANGE_PWD;
			transactionID = plainString.substring(4,20);
			confirmFlag = plainString.substring(20);	
			logger.info("confirmFlag:{}",confirmFlag);
			
			operateDatabaseContainState(src, type, transactionID, confirmFlag);
			return "个人密码修改业务完成";
		};
		
		//个人密码重置
		if (type.equals(Constants.INTERFACE_TYPE_RESET_PWD_MO))
		{
			type = Constants.INTERFACE_TYPE_CODE_RESET_PWD;
			transactionID = plainString.substring(4);
			
			operateDatabaseContainState(src, type, transactionID, "01");
			return "个人密码重置完成";
		}
		
		//获取卡片注册状态
		if (type.equals(Constants.INTERFACE_TYPE_REGISTER_STATE_MO))
		{
			type = Constants.INTERFACE_TYPE_CODE_REGISTER_STATE;
			transactionID = plainString.substring(4,20);
			stateFlag = plainString.substring(20);
			logger.info("stateFlag:{}",stateFlag);
			
			operateDatabaseContainState(src, type, transactionID, stateFlag);
			return "获取卡片注册状态业务完成";
		};


		return "非正常类型，无法解析";
	}

	/**
	 * 基于验证码快捷认证业务，数据库要存入验证码信息
	 * @param src
	 * @param type
	 * @param transactionID
	 * @param confirmFlag
	 * @param authCodeString
	 */
	private void operateDatabaseContainStateAndAuthCode(String src,
			String type, String transactionID, String confirmFlag,
			String authCodeString) {
		//修改数据库状态
		
		InterfaceInfo inf = interfaceService.findByMsisdnAndInterfaceTypeAndTransactionId(src, type, transactionID);
		if (inf == null) {
			throw new RuntimeException("记录为空!");
		}

		inf.setRetResult(confirmFlag);
		inf.setAuthdataOut(authCodeString);
		inf.setFinishTime(new Date());
		interfaceService.save(inf);
	}

	/**
	 * 认证服务器请求注册
	 * @param plainString
	 */
	private void processResigter(String msisdn ,String plainString) {	
		//包含TransactionID，卡上编码，空卡序列号，ICCID
		String transactionID = plainString.substring(4,20);
		logger.info("transactionID:{}",transactionID);
		String cardManufactureID = plainString.substring(20,22);
		logger.info("cardManufactureID:{}",cardManufactureID);
		
		String cardIDLen = plainString.substring(22, 24);
		int num = Integer.parseInt(cardIDLen, 16)*2;
		String cardID = plainString.substring(24,24+num); //此处使用的对应数据库的EID。空卡序列号，待确认
		logger.info("cardID：{} ",cardID);
		
		int offsize = 24 + num;
		String iCCIDLen= plainString.substring(offsize,offsize+2);	
		offsize +=2;
		num = Integer.parseInt(iCCIDLen, 16)*2;
		String iCCID = plainString.substring(offsize,offsize+num);	
		logger.info("iCCID：{} ",iCCID);
		
		//认证服务器收到卡片的请求信息，标记该用户的注册状态为已注册，记录用户的空卡序列号和 ICCID 信息，并下发注册响应指令
		processingData(cardManufactureID,msisdn,iCCID,cardID);
		
		triggerResigter(msisdn,transactionID);	
		
		//插入一条记录到接口调用记录表
		operateDatabaseFirst(msisdn, Constants.INTERFACE_TYPE_CODE_TRIGGER_REGISTER, transactionID);	
	}

	/**
	 * 操作数据库oauth_interface_info表，
	 * @param msisdn
	 * @param type
	 * @param transactionID
	 */
	private void operateDatabaseContainState(String msisdn, String type,String transactionID,String confirmState) {
		//修改数据库状态
		System.out.println("msisdn " + msisdn);
		System.out.println("type " + type);
		System.out.println("transactionID " + transactionID);
		
		InterfaceInfo inf = interfaceService.findByMsisdnAndInterfaceTypeAndTransactionId(msisdn, type, transactionID);
		if (inf == null) {
		
			throw new RuntimeException("记录为空!");
		}

		inf.setRetResult(confirmState);
		inf.setFinishTime(new Date());
		interfaceService.save(inf);
	}
	
	/**
	 * 操作数据库oauth_interface_info表
	 * @param msisdn
	 * @param type
	 * @param transactionID
	 */
	private void operateDatabaseFirst(String msisdn, String type,String transactionID) {
		//修改数据库状态
		OauthCard card = cardService.findByMsisdn(msisdn);
		if (card == null) {
			throw new RuntimeException("根据手机号码查询卡片为空!");
		}
		
		InterfaceInfo inf = interfaceService.findByMsisdnAndInterfaceTypeAndTransactionId(msisdn, type, transactionID);
		if (inf == null) {//卡开机主动注册，第一条记录操作时间，结束时间，由此处控制
			Date  time= new Date();
			InterfaceInfo newInfo = new InterfaceInfo();
			newInfo.setRetResult("01");
			newInfo.setInterfaceType(type);
			newInfo.setMsisdn(msisdn);
			newInfo.setTransactionId(transactionID);
			newInfo.setOperatorTime(time);
			newInfo.setCardManufacturerId(card.getCardManufacturerId());
			newInfo.setFinishTime(time);
			newInfo.setEid(card.getEid());
			interfaceService.save(newInfo);
			return ;
		}

		inf.setRetResult("01");
		inf.setFinishTime(new Date());
		interfaceService.save(inf);
	}
	
	/**
	 * 处理数据，修改数据库oauth_card表
	 * @param cardManufactureID
	 * @param msisdn
	 */
	private void processingData(String cardManufactureID, String msisdn, String iCCID, String cardID) {			
		OauthCard inf = cardService.findByMsisdnAndCardManufacturerIdAndIccid(msisdn,cardManufactureID,iCCID);
		if (inf == null) {
			//oauth_web会插入一些卡基本信息，手机号，ICCID，卡商信息，此处如果找不到基础信息，直接抛出异常
			throw new RuntimeException("根据手机号码,卡商，iccid查询卡片为空！");
		}
		
		//存数据库card表，注册请求，认证平台需要把卡信息录入到card表
		inf.setCardid(cardID);//空卡序列号
		inf.setCardState("01");//卡片状态为已注册
		cardService.save(inf);	
	}

	/**
	 * 卡片主动发起注册，请求认证服务器发起注册
	 * @param msisdn
	 * @param transactionID
	 * @param type
	 */
	private void triggerResigter(String msisdn, String transactionID) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("phoneNo", msisdn);
		json.put("transactionId", transactionID);
		json.put("userType", "1");
			
		String result =  HTTPWeb.post(interface_send_url, json.toJSONString());
		System.out.println("result " + result);
	}	
}
