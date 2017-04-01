/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-7
 * Id: BaseOauthController.java,v 1.0 2017-3-7 下午3:35:28 Administrator
 */
package com.whty.oauth.platform.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.whty.euicc.sms.Sms;
import com.whty.oauth.platform.common.Constants;
import com.whty.oauth.platform.pojo.InterfaceInfo;
import com.whty.oauth.platform.pojo.OauthCard;
import com.whty.oauth.platform.pojo.OauthKey;
import com.whty.oauth.platform.pojo.ReqParam;
import com.whty.oauth.platform.service.IInterfaceInfoService;
import com.whty.oauth.platform.service.IOauthCardService;
import com.whty.oauth.platform.service.IOauthKeyService;
import com.whty.oauth.platform.util.HTTPWeb;
import com.whty.oauth.platform.util.HexUtils;
import com.whty.oauth.platform.util.ToTLV;
import com.whty.oauth.platform.util.UUIDGenerator;


/**
 * @ClassName QuickOauthController
 * @author Administrator
 * @date 2017-3-7 下午3:53:45
 * @Description TODO(快捷认证业务接口)
 */
@RestController
@RequestMapping("/oauth")
public class QuickOauthController {
	
	private Logger logger = LoggerFactory.getLogger(QuickOauthController.class);
	
	@Value("${sms.send.url}")
    private String smsSendUrl;
	
	@Value("${sms.send.type}")
	private String smsSendType;
	
	@Value("${sms.tool.ip}")
	private String smsToolIp;
	
	@Value("${sms.tool.port}")
	private String smsToolPort;
	
	/*
	 * scp80秘钥
	 */
	
	@Value("${scp80.encry.ctSpi}")
	private String ctSpi;
	
	@Value("${scp80.encry.ctKic}")
	private String ctKic;
	
	@Value("${scp80.encry.ctKid}")
	private String ctKid;
	
	@Value("${scp80.encry.ctTar}")
	private String ctTar;
	
	@Value("${scp80.encry.ctCntr}")
	private String ctCntr;
	
	@Autowired
	@Qualifier("interfaceInfoServiceImpl")
	private IInterfaceInfoService interfaceInfoServie;
	
	@Autowired
	@Qualifier("oauthCardServiceImpl")
	private IOauthCardService euiccCardService;
	
	@Autowired
	@Qualifier("oauthKeyServiceImpl")
	private IOauthKeyService keyService;
	
	@Autowired  
    private RedisTemplate<String,Object> redisTemplate; 
	
	private String respcode = "0000";
	private String respdesc = "";
	
	/**
	 * 认证服务器触发注册接口
	 * @param phoneNo
	 * url = http://localhost:8080/v1/platform/down/triggerRegister
	 * @return
	 */
	@RequestMapping(value="/v1/platform/down/triggerRegister")
	public Object triggerResigter(ReqParam req){
		String phoneNo = req.getPhoneNo();
		String interfaceType = null;
		String transactionId = null;
		boolean flag = false;
		if (StringUtils.isNotBlank(req.getUserType()) && Integer.parseInt(req.getUserType()) == 1) {
			logger.info("receive msg from inner service..");
			interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_TRIGGER_REGISTER_IN);
			transactionId = req.getTransactionId();
			if (StringUtils.isBlank(transactionId)) {
				return responseInfo("", Constants.REQ_PARAM_SMS_TRANSACTION_NULL, "接口内部调用获取transaction为空!");
			}
			
		} else {
			interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_TRIGGER_REGISTER);
			flag = true;
			
			// 随机生成 transactionId
			transactionId = UUIDGenerator.getUUID(Constants.UUID_RANDOM_NUM_8_BYTE);
			logger.info("receive msg from Outer interface exec..");
		}
		
		logger.info("triggerResigter transactionId   >>> "+transactionId);
		
		// 校验数据
		OauthCard euiccCard = checkParam1(req);
		if (euiccCard == null) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 新业务分析，如果卡片的状态已注册，那么就不下发注册消息
		 */
		if (flag == true && StringUtils.equals(euiccCard.getCardState(), Constants.CARD_REGISTER_FINISH_REGISTER)) {
			logger.info("卡片已经注册!");
			return responseInfo("", Constants.REQ_PARAM_CARD_ALREADY_REGISTER, "卡片已经注册完成!");
		}
		
		/*
		 * 封装tlv
		 */
		String tpdu = wrapTlv(interfaceType, transactionId);
		
		/*
		 * 0348加密
		 */
		String smsMsg = encrypData(phoneNo, tpdu, euiccCard.getEid(), euiccCard.getIccid());
		if (StringUtils.isBlank(smsMsg)) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 保存接口调用信息
		 */
		if (flag) {
			InterfaceInfo traceInfo = interfaceInfoServie.getByMsisdnAndTransactionId(req.getPhoneNo(), req.getTransactionId());
			if (traceInfo == null) {
				saveInterfaceInfo(transactionId, euiccCard, Constants.INTERFACE_TYPE_CODE_TRIGGER_REGISTER, tpdu, smsMsg);
			}
		}
		
		/*
		 * 调用发送短信的接口
		 */
		sendSmsMsg(phoneNo, smsMsg, Integer.parseInt(smsSendType));
		
		/*
		 * 返回信息
		 */
		return responseInfo(transactionId, Constants.RSP_CODE_SUCCESS, "");
	}

	/**
	 * 基本快捷认证业务接口
	 * @param phoneNo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/v1/platform/down/baseOauth")
	public Object baseOauth(ReqParam req) {
		String phoneNo = req.getPhoneNo();
		String authData = req.getAuthData();
		
		logger.info("baseOauth params : phoneNo >>> "+phoneNo+"   ,authData >>> "+
				authData+"   ,authType >>> "+req.getAuthType()+"   ,format >>> "+req.getFormat());
		
		String interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_BASE_OAUTH);
		
		// 随机生成 transactionId
		String transactionId = UUIDGenerator.getUUID(Constants.UUID_RANDOM_NUM_8_BYTE);
		
		logger.info("baseOauth transactionId  >>> "+transactionId);
		
		// 校验数据
		OauthCard euiccCard = checkParam2(req);
		if (euiccCard == null) {
			return responseInfo("", respcode, respdesc);
		}
		
		// 根据指定编码编码消息
		//String formatHex = ToTLV.intToHex(Integer.parseInt(req.getFormat()));
		//String authTypeHex = ToTLV.intToHex(Integer.parseInt(req.getAuthType()));
		//第三方接口发送数据为16进制编码格式，不需要转换，注释掉上面的两行代码。
		/*前半字节用于表示数据显示方式，后半字节用于表示认证方式：
			0X：采用 getinput显示方式；
			1X：采用displaytext显示方式 , 设置为高优先级显示级别。
			X0：无需口令验证
			X1：需要口令验证
		 * */
		String formatHex = req.getFormat();
		String authTypeHex = req.getAuthType();
		
		//认证数据可以不填写
		if (authData !=null ) {
			if (StringUtils.equals(formatHex,Constants.AUTH_ENCODE_FORMAT_ASCII)) {
				try {
					authData = HexUtils.getHexResult(authData);
					logger.info("ascii encode data :authData >>> "+authData);
				} catch (Exception e) {
					return responseInfo("", Constants.REQ_PARAM_TYPE_ENCODE_ERROR, "请求参数数据编码错误!");
				}
				
			} else if (StringUtils.equals(formatHex,Constants.AUTH_ENCODE_FORMAT_UCS2)) {
				try {		
					authData = HexUtils.convert(authData);  
					logger.info("ucs2 encode data :authData >>> "+authData);
				} catch (Exception e) {
					return responseInfo("", Constants.REQ_PARAM_TYPE_ENCODE_ERROR, "请求参数数据编码错误!");
				}
			} else {
				return responseInfo("", Constants.REQ_PARAM_AUTH_FORMAT_ERROR, "请求参数编码格式不支持!");
			}
			
			/*
			 * 校验认证数据的长度
			 */
			if (authData.length()/2 > 120) {
				return responseInfo("", Constants.REQ_PARAM_AUTH_DATA_LENGTH_ERROR, "请求authData长度超过120字节!");
			}
		}
	
		/*
		 * 封装tlv
		 */
		String tpdu = wrapTlv(interfaceType, authData, transactionId, formatHex, authTypeHex);
		
		/*
		 * 0348加密
		 */
		String  smsMsg = encrypData(phoneNo, tpdu, euiccCard.getEid(), euiccCard.getIccid());
		if (StringUtils.isBlank(smsMsg)) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 保存接口调用信息
		 */
		InterfaceInfo traceInfo = saveInterfaceInfo(transactionId, euiccCard, Constants.INTERFACE_TYPE_CODE_BASE_OAUTH, tpdu, smsMsg);
		
		/*
		 * 调用发送短信的接口
		 */
		sendSmsMsg(phoneNo, smsMsg, Integer.parseInt(smsSendType));
		
		/*
		 * 返回信息
		 */
		return responseInfo(traceInfo.getTransactionId(), Constants.RSP_CODE_SUCCESS, "");
	}

	
	
	/**
	 * 基于验证码的快捷认证业务接口
	 * @param phoneNo
	 * @return
	 */
	@RequestMapping(value="/v1/platform/down/validCodeOauth")
	public Object validCodeOauth(ReqParam req){
		String phoneNo = req.getPhoneNo();
		String authData = req.getAuthData();
		
		logger.info("baseOauth params : phoneNo >>> "+phoneNo+"   ,authData >>> "+
				authData+"   ,authType >>> "+req.getAuthType()+"   ,format >>> "+req.getFormat());
		
		String interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_VALIDCODE_OAUTH);
		
		// 随机生成 transactionId
		String transactionId = UUIDGenerator.getUUID(Constants.UUID_RANDOM_NUM_8_BYTE);
		
		logger.info("validCodeOauth transactionId  >>> "+transactionId);
		
		// 校验数据
		OauthCard euiccCard = checkParam2(req);
		if (euiccCard == null) {
			return responseInfo("", respcode, respdesc);
		}
		
		//String formatHex = ToTLV.intToHex(Integer.parseInt(req.getFormat()));
		//String authTypeHex = ToTLV.intToHex(Integer.parseInt(req.getAuthType()));
		//第三方接口发送数据为16进制编码格式，不需要转换，注释掉上面的两行代码。
		/*前半字节用于表示数据显示方式，后半字节用于表示认证方式：
			0X：采用 getinput显示方式；
			1X：采用displaytext显示方式 , 设置为高优先级显示级别。
			X0：无需口令验证
			X1：需要口令验证
		 * */
		String formatHex = req.getFormat();
		String authTypeHex = req.getAuthType();
		if (authData !=null ) {
			if (StringUtils.equals(formatHex,Constants.AUTH_ENCODE_FORMAT_ASCII)) {
				try {
					authData = HexUtils.getHexResult(authData);
					logger.info("ascii encode data :authData >>> "+authData);
				} catch (Exception e) {
					return responseInfo("", Constants.REQ_PARAM_TYPE_ENCODE_ERROR, "请求参数数据编码错误!");
				}
				
			} else if (StringUtils.equals(formatHex,Constants.AUTH_ENCODE_FORMAT_UCS2)) {
				try {
					authData = HexUtils.convert(authData);  
					logger.info("ucs2 encode data :authData >>> "+authData);
				} catch (Exception e) {
					return responseInfo("", Constants.REQ_PARAM_TYPE_ENCODE_ERROR, "请求参数数据编码错误!");
				}
			} else {
				return responseInfo("", Constants.REQ_PARAM_AUTH_FORMAT_ERROR, "请求参数编码格式不支持!");
			}
			
			/*
			 * 校验认证数据的长度
			 */
			if (authData.length()/2 > 120) {
				return responseInfo("", Constants.REQ_PARAM_AUTH_DATA_LENGTH_ERROR, "请求authData长度超过120字节!");
			}
		}
		/*
		 * 封装tlv
		 */
		String tpdu = wrapTlv(interfaceType, authData, transactionId, formatHex, authTypeHex);
		
		/*
		 * 0348加密
		 */
		String  smsMsg = encrypData(phoneNo, tpdu, euiccCard.getEid(), euiccCard.getIccid());
		if (StringUtils.isBlank(smsMsg)) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 保存接口调用信息
		 */
		InterfaceInfo traceInfo = saveInterfaceInfo(transactionId, euiccCard, Constants.INTERFACE_TYPE_CODE_VALIDCODE_OAUTH, authData, tpdu, smsMsg);
		
		/*
		 * 调用发送短信的接口
		 */
		sendSmsMsg(phoneNo, smsMsg, Integer.parseInt(smsSendType));
		
		/*
		 * 返回信息
		 */
		return responseInfo(traceInfo.getTransactionId(), Constants.RSP_CODE_SUCCESS, "");
	}
	
	/**
	 * 个人密码修改接口
	 * @param phoneNo
	 * @return
	 */
	@RequestMapping(value="/v1/platform/down/changePwd")
	public Object changePwd(ReqParam req){
		String phoneNo = req.getPhoneNo();
		String interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_CHANGE_PWD);
		
		// 随机生成 transactionId
		String transactionId = UUIDGenerator.getUUID(Constants.UUID_RANDOM_NUM_8_BYTE);
		logger.info("changePwd transactionId  >>> "+transactionId);
		
		// 校验数据
		OauthCard euiccCard = checkParam1(req);
		if (euiccCard == null) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 封装tlv
		 */
		String tpdu = wrapTlv(interfaceType, transactionId);
		
		/*
		 * 0348加密
		 */
		String smsMsg = encrypData(phoneNo, tpdu, euiccCard.getEid(), euiccCard.getIccid());
		if (StringUtils.isBlank(smsMsg)) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 保存接口调用信息
		 */
		InterfaceInfo traceInfo = saveInterfaceInfo(transactionId, euiccCard, Constants.INTERFACE_TYPE_CODE_CHANGE_PWD, tpdu, smsMsg);
		
		/*
		 * 调用发送短信的接口
		 */
		sendSmsMsg(phoneNo, smsMsg, Integer.parseInt(smsSendType));
		
		/*
		 * 返回信息
		 */
		return responseInfo(traceInfo.getTransactionId(), Constants.RSP_CODE_SUCCESS, "");
		
	}
	
	/**
	 * 个人密码重置接口
	 * @param phoneNo
	 * @return
	 */
	@RequestMapping(value="/v1/platform/down/resetPwd")
	public Object resetPwd(ReqParam req){
		String phoneNo = req.getPhoneNo();
		String interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_RESET_PWD);
		
		// 随机生成 transactionId
		String transactionId = UUIDGenerator.getUUID(Constants.UUID_RANDOM_NUM_8_BYTE);
		logger.info("resetPwd transactionId  >>> "+transactionId);
		
		// 校验数据
		OauthCard euiccCard = checkParam1(req);
		if (euiccCard == null) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 封装tlv
		 */
		String tpdu = wrapTlv(interfaceType, transactionId);
		
		/*
		 * 0348加密
		 */
		String smsMsg = encrypData(phoneNo, tpdu, euiccCard.getEid(), euiccCard.getIccid());
		if (StringUtils.isBlank(smsMsg)) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 保存接口调用信息
		 */
		InterfaceInfo traceInfo = saveInterfaceInfo(transactionId, euiccCard, Constants.INTERFACE_TYPE_CODE_RESET_PWD, tpdu, smsMsg);
		
		/*
		 * 调用发送短信的接口
		 */
		sendSmsMsg(phoneNo, smsMsg, Integer.parseInt(smsSendType));
		
		/*
		 * 返回信息
		 */
		return responseInfo(traceInfo.getTransactionId(), Constants.RSP_CODE_SUCCESS, "");
	
	}
	
	/**
	 * 获取卡片注册状态接口
	 * @param phoneNo
	 * @return
	 */
	@RequestMapping(value="/v1/platform/down/getRegisterStatus")
	public Object getRegisterStatus(ReqParam req){
		
		String phoneNo = req.getPhoneNo();
		String interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_REGISTER_STATE);
		
		// 随机生成 transactionId
		String transactionId = UUIDGenerator.getUUID(Constants.UUID_RANDOM_NUM_8_BYTE);
		logger.info("getRegisterStatus transactionId  >>> "+transactionId);
		
		// 校验数据
		OauthCard euiccCard = checkParam1(req);
		if (euiccCard == null) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 封装tlv
		 */
		String tpdu = wrapTlv(interfaceType, transactionId);
		
		/*
		 * 0348加密
		 */
		String smsMsg = encrypData(phoneNo, tpdu, euiccCard.getEid(), euiccCard.getIccid());
		if (StringUtils.isBlank(smsMsg)) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 保存接口调用信息
		 */
		InterfaceInfo traceInfo = saveInterfaceInfo(transactionId, euiccCard, Constants.INTERFACE_TYPE_CODE_REGISTER_STATE, tpdu, smsMsg);
		
		/*
		 * 调用发送短信的接口
		 */
		sendSmsMsg(phoneNo, smsMsg, Integer.parseInt(smsSendType));
		
		/*
		 * 返回信息
		 */
		return responseInfo(traceInfo.getTransactionId(), Constants.RSP_CODE_SUCCESS, "");
	}
	
	/**
	 * @author Administrator
	 * @date 2017-3-10
	 * @param req
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping(value="/v1/platform/down/clearRegister")
	public Object clearRegister(ReqParam req){
		String phoneNo = req.getPhoneNo();
		String interfaceType = Integer.toHexString(Constants.INTERFACE_TYPE_CLEAR_REGISTER);
		
		// 随机生成 transactionId
		String transactionId = UUIDGenerator.getUUID(Constants.UUID_RANDOM_NUM_8_BYTE);
		logger.info("clearRegister transactionId  >>> "+transactionId);
		
		// 校验数据
		OauthCard euiccCard = checkParam1(req);
		if (euiccCard == null) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 封装tlv
		 */
		String tpdu = wrapTlv(interfaceType, transactionId);
		
		/*
		 * 0348加密
		 */
		String smsMsg = encrypData(phoneNo, tpdu, euiccCard.getEid(), euiccCard.getIccid());
		if (StringUtils.isBlank(smsMsg)) {
			return responseInfo("", respcode, respdesc);
		}
		
		/*
		 * 保存接口调用信息
		 */
		InterfaceInfo traceInfo = saveInterfaceInfo(transactionId, euiccCard, Constants.INTERFACE_TYPE_CODE_CLEAR_REGISTER, tpdu, smsMsg);
		
		/*
		 * 更新card注册状态  置为 00
		 */
		euiccCard.setCardState(Constants.CARD_REGISTER_NO_REGISTER);
		updateEuiccCard(euiccCard);
		
		/*
		 * 清除手机号码的缓存
		 */
		clearCacheData(Constants.REDIS_KEY_NAME_PREFIX+phoneNo);
		
		/*
		 * 调用发送短信的接口
		 */
		sendSmsMsg(phoneNo, smsMsg, Integer.parseInt(smsSendType));
		
		/*
		 * 返回信息
		 */
		return responseInfo(traceInfo.getTransactionId(), Constants.RSP_CODE_SUCCESS, "");
	}
	
	private void clearCacheData(String ...keys) {
		for (int i=0;i<keys.length;i++) {
			redisTemplate.delete(keys[i]);
		}
	}
	
	private void updateEuiccCard(OauthCard card) {
		euiccCardService.save(card);
	}

	private JSONObject responseInfo(String transactionId, String respcode, String respdesc) {
		JSONObject result = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String timeStamp = sdf.format(time);
		result.put("accepttime", timeStamp);
		result.put("transactionId", transactionId);
		result.put("respcode",respcode);
		result.put("respdesc", respdesc);
		return result;
		
	}

	private void sendSmsMsg(String phoneNo, String smsMsg, Integer sendType) {
		if (sendType == 1) {
			JSONObject smsJson = new JSONObject();
			smsJson.put("dest", phoneNo);
			smsJson.put("msg", smsMsg);
			String smsResult = HTTPWeb.post(smsSendUrl, smsJson.toJSONString());
			logger.info("smsResult >>> "+smsResult);
		} else if (sendType == 2) {
			// callHuaWei(phoneNo,enSms);
		} else{
			logger.info("调用工具");
			SocketConnector(smsMsg);
		}
	}

	private InterfaceInfo saveInterfaceInfo(String transactionId,
			OauthCard euiccCard, String typeCode, String tpdu, String smsMsg) {
		JSONObject json = new JSONObject();
		InterfaceInfo traceInfo = new InterfaceInfo();
		
		json.put("phoneNo", euiccCard.getMsisdn());
		json.put("tpdu", tpdu);
		json.put("smsMsg", smsMsg);
		traceInfo.setOperatorTime(new Date());
		traceInfo.setTransactionId(transactionId);
		traceInfo.setInterfaceType(typeCode);
		traceInfo.setEid(euiccCard.getEid());
		traceInfo.setCardManufacturerId(euiccCard.getCardManufacturerId());
		traceInfo.setMsisdn(euiccCard.getMsisdn());
		traceInfo.setDownRecord(json.toJSONString());
		interfaceInfoServie.save(traceInfo);
		return traceInfo;
	}
	
	private InterfaceInfo saveInterfaceInfo(String transactionId,
			OauthCard euiccCard, String typeCode, String authdata, String tpdu, String smsMsg) {
		JSONObject json = new JSONObject();
		InterfaceInfo traceInfo = new InterfaceInfo();
		
		json.put("phoneNo", euiccCard.getMsisdn());
		json.put("tpdu", tpdu);
		json.put("smsMsg", smsMsg);
		traceInfo.setDownRecord(json.toJSONString());
		traceInfo.setOperatorTime(new Date());
		traceInfo.setTransactionId(transactionId);
		traceInfo.setInterfaceType(typeCode);
		traceInfo.setEid(euiccCard.getEid());
		traceInfo.setCardManufacturerId(euiccCard.getCardManufacturerId());
		traceInfo.setMsisdn(euiccCard.getMsisdn());
		traceInfo.setAuthdataIn(authdata);
		interfaceInfoServie.save(traceInfo);
		return traceInfo;
	}

	private String encrypData(String phoneNo, String tpdu, String eid, String iccid) {
		
		/*
		 * 数据库查询秘钥信息
		 */
		String versionKic = ctKic.substring(0, 1);
		String versionKid = ctKid.substring(0, 1);
		
		Map<String, String> mapKey = new HashMap<String, String>();
		HashOperations<String, String, String> redisHash = redisTemplate.opsForHash();
		if (!redisHash.getOperations().hasKey(Constants.REDIS_KEY_NAME_PREFIX+phoneNo)) {
			List<OauthKey> listKey = new ArrayList<OauthKey>();
			listKey = keyService.findCardKey(eid, iccid, versionKic, versionKid);
			if (listKey == null || listKey.size() < Constants.SCP80_ENCRYP_KEYS_NUMBER) {
				respcode = Constants.SCP80_ENCRPY_QERRY_KEY_NULL;
				respdesc = "通过卡片eid和iccid和version查询不到对应秘钥信息!";
				return null;
			}
			for (OauthKey key : listKey) {
				if (StringUtils.equals(key.getKeyIndex(), Constants.SCP80_ENCRYP_KIC_INDEX)) {
					mapKey.put("ctKicKey", key.getMacKey());
				} else if (StringUtils.equals(key.getKeyIndex(), Constants.SCP80_ENCRYP_KID_INDEX)) {
					mapKey.put("ctKidKey", key.getMacKey());
				}
			}
			mapKey.put("ctSpi", ctSpi);
			mapKey.put("ctKic", ctKic);
			mapKey.put("ctKid", ctKid);
			mapKey.put("ctTar", ctTar);
			mapKey.put("ctCntr", ctCntr);
			redisHash.putAll(Constants.REDIS_KEY_NAME_PREFIX+phoneNo, mapKey);
			logger.info("tpdu>>>{}, phoneNo>>>{}, ctSpi>>>{}, ctKic>>>{} , ctKid>>>{}, ctTar>>>{}, ctCntr>>>{}, mapKey>>>{}",
					tpdu, phoneNo, ctSpi, ctKic, ctKid, ctTar, ctCntr, mapKey);
		} else {
			mapKey = redisHash.entries(Constants.REDIS_KEY_NAME_PREFIX+phoneNo);
			logger.info("mapKey>>>{}", mapKey);
		}
		
		Sms smsUtil = new Sms();
		List<String> msgs = new ArrayList<String>();
		try {
			msgs = smsUtil.sendSmsForCTSim(tpdu, mapKey);
		} catch (Exception e) {
			respcode = Constants.SCP80_ENCRPY_MSG_ERROR;
			respdesc = "通过SCP80加密运行错误!";
			return null;
		}
		if (msgs == null || msgs.size() == 0) {
			respcode = Constants.SCP80_ENCRPY_PARAMS_ERROR;
			respdesc = "通过SCP80加密参数错误!";
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String str : msgs) {
			sb.append(str);
		}
		logger.info("encryData >>> "+sb.toString());
		return sb.toString();
	}

	private String wrapTlv(String interfaceType, String transactionId) {
		String tlvData = ToTLV.toEccTLV(interfaceType, transactionId);
		logger.info("tlvData  " + tlvData);
		return tlvData;
	}
	
	private OauthCard checkParam1(ReqParam req) {
		// 如果参数为空
		if (StringUtils.isBlank(req.getPhoneNo())) {
			respcode = Constants.REQ_PARAM_NULL;
			respdesc = "电话号码为空!";
			return null;
		}
		
		try {
			Integer.parseInt(smsSendType);
		} catch (Exception e) {
			respcode = Constants.REQ_PARAM_SMS_SEND_ERROR;
			respdesc = "传入发送短信方式错误!";
			return null;
		}
		
		/*
		 * 通过phoneNo查询卡片信息
		 */
		OauthCard euiccCard = euiccCardService.findEuiccCardByPhoneNo(req.getPhoneNo());
		if (euiccCard == null) {
			respcode = Constants.REQ_PARAM_ERROR;
			respdesc = "通过电话号码查询不到对应卡片信息!";
			return null;
		}
		
		return euiccCard;
	}
	
	private OauthCard checkParam2(ReqParam req) {
		
		// 如果参数为空	
		if (StringUtils.isBlank(req.getPhoneNo()) || StringUtils.isBlank(req.getFormat()) || StringUtils.isBlank(req.getAuthType())) {
			respcode = Constants.REQ_PARAM_NULL;
			respdesc = "输入参数为空!";
			return null;
		}
		
		/*
		 * 解析输入参数信息
		 */
		try {
			Integer.valueOf(req.getFormat());
			Integer.valueOf(req.getAuthType());
		} catch (Exception e) {
			respcode = Constants.REQ_PARAM_ERROR;
			respdesc =  "编码格式或者认证类型输入参数错误!";
			return null;
		}
		
		/*
		 * 通过phoneNo查询卡片信息
		 */
		OauthCard euiccCard = euiccCardService.findEuiccCardByPhoneNo(req.getPhoneNo());
		if (euiccCard == null) {
			respcode = Constants.REQ_PARAM_ERROR;
			respdesc = "通过电话号码查询不到对应卡片信息!";
			return null;
		}
		
		return euiccCard;
	}
	
	private String wrapTlv(String interfaceType, String authData, String transactionId, String formatHex, String authTypeHex) {
		String commandData = ToTLV.appends(formatHex, transactionId, ToTLV.toEccTLV(authTypeHex, authData));
		String tlvData = ToTLV.toEccTLV(interfaceType , commandData);
		logger.info("tlvData >>> "+tlvData);
		return tlvData;
	}
	
	private void SocketConnector(String sms) {
		Socket socket;
		try {
			socket = new Socket(smsToolIp,Integer.parseInt(smsToolPort));
			socket.setReceiveBufferSize(102400);
			socket.setKeepAlive(true);//底层的TCP实现会监视该连接是否有效
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			int length = sms.getBytes().length;
			out.writeInt(length);
			writeBytes(out, sms.getBytes(), length);
		} catch (IOException e) {
			logger.info("获取socket连接失败!  socketServerIp >>> "+smsToolIp+"   socketServerPort >>> "+smsToolPort);
		}	
	}
	
	private void writeBytes(DataOutputStream out,byte[] bytes,int length) throws IOException{
        out.writeInt(length);
        out.write(bytes,0,length);
        out.flush();
    }

	public static String string2Unicode(String string) {  	   
	    StringBuffer unicode = new StringBuffer();     
	    for (int i = 0; i < string.length(); i++) {     
	        // 取出每一个字符  
	        char c = string.charAt(i);     
	        // 转换为unicode  
	        unicode.append(Integer.toHexString(c));  
	    }   
	    return unicode.toString();  
	} 
	
}
