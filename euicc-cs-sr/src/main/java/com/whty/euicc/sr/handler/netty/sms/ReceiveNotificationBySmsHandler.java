package com.whty.euicc.sr.handler.netty.sms;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.ReceiveNotificationBySmsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * 接收通知服务
 * 
 * @author Administrator
 * 
 */
@Service("receiveNotificationBySms")
public class ReceiveNotificationBySmsHandler implements HttpHandler {
	private Logger logger = LoggerFactory
			.getLogger(ReceiveNotificationBySmsHandler.class);

	@Autowired
	private SmsTriggerUtil smsTriggerUtil;

	private final String notificationConfirmation = "80E28900073A08044E02";
	private String noticeConfirmationString;

	@Autowired
	private EuiccProfileService profileService;

	@Override
	public byte[] handle(String requestStr) {
		logger.info("SMS进入接收默认通知流程");
		String eidString = "";
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS, "success");
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse()
					.toEuiccMsg(requestStr.getBytes());
			ReceiveNotificationBySmsReqBody reqBody = (ReceiveNotificationBySmsReqBody) reqMsgObject
					.getBody();

			String scpdDataString = reqBody.getTpud();
			String phoneNo = reqBody.getPhoneNo();
			String plainText = scpdDataString;// Sms.smsNotification(scpdDataString);
			logger.info("plainText:{}", plainText);

			//sms方式下删除、主删除、更新连接参数、fallback(主键是手机号码)
			String sms = CacheUtil.getString(phoneNo);
			if (StringUtils.isNotBlank(sms)) {
				// 前台有触发行为，须通知前台
				SmsTrigger eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
				//短信内容要回传
				eventTrigger.setTpud(plainText);
				SmsTriggerUtil.notifyProcessResult(eventTrigger, true);
				return new Gson().toJson(respMessage).getBytes();
			}
			//此处只是处理卡上行的por通知，平台不做处理。
			int offsize = plainText.indexOf("0271");
			if (offsize != -1) {
				logger.info("por message:{}", plainText);
				return new Gson().toJson(respMessage).getBytes();
			}
			
			String securredData = paserData(plainText);// "E1"+Len+Value
			logger.info("securredData:{}", securredData);
			
			offsize = securredData.indexOf("E1");
			if (offsize == -1) {
				throw new EuiccBusiException("8000", "securredData error");
			}
			offsize += 2;
			String lenHexStr = securredData.substring(offsize, offsize + 2);// 35
			offsize += 2;
			// 16进制转换成10进制
			int len = Integer.parseInt(lenHexStr, 16) * 2;
			securredData = securredData.substring(offsize, offsize + len);// securredData，为4C*4D*4E数据
			logger.info("securredData_4c  :{}", securredData);
			offsize = securredData.indexOf("4C") + 4;
			eidString = securredData.substring(offsize, offsize + 32);
			// eidString = "89001012012341234012345678901224";
			offsize = securredData.indexOf("4D") + 4;
			String noticeType = securredData.substring(offsize, offsize + 2);
			offsize = securredData.indexOf("4E") + 4;
			String seqNumber = securredData.substring(offsize, offsize + 4);
			offsize = securredData.indexOf("4F") + 4;
			String isdpAid = securredData.substring(offsize, offsize + 32);
			logger.info("eid:{}", eidString);
			logger.info("seqNumber:{}", seqNumber);
			logger.info("noticeType:{}", noticeType);
			logger.info("isdpAid:{}", isdpAid);

			boolean flag = checkInitialConditions(eidString);
			if (!flag) {
				throw new EuiccBusiException("8000",
						"checkInitialConditions error");
			}

			noticeConfirmationString = notificationConfirmation + seqNumber
					+ "00";
			String plainSMS = ToTLV.toTLV("AA",
					ToTLV.toTLV("22", noticeConfirmationString));
			logger.info("plainSMS:{}", plainSMS);
			reqBody.setEid(eidString);
			reqBody.setSms(plainSMS);
			smsTriggerUtil.sendSms(reqBody);
			notifyProcessResult(eidString, noticeType, isdpAid);
		} catch (Exception e) {
			logger.error("ReceiveNotificationHandler", e.getMessage());
			e.printStackTrace();
		}

		return new Gson().toJson(respMessage).getBytes();
	}

	private boolean checkInitialConditions(String eid) {
		/*
		 * EuiccCard card = euiccCardMapper.selectByPrimaryKey(eid);
		 * 
		 * if (card == null) { return false; }
		 */
		return true;
	}

	/**
	 * 处理数据，修改数据库
	 * 
	 * @param eid
	 * @param type
	 * @param isdpAid
	 * @return
	 */
	private boolean processingData(String eid, String type, String isdpAid) {
		// 根据type做判断
		EuiccProfileWithBLOBs euiccProfile = profileService
				.selectByEidAndIsdPAid(eid, isdpAid);
		int count = profileService.checkCount(euiccProfile);
		if (count == 0) {
			return false;
		}

		if ("01".equals(type)) {
			// First network attachment
			// “设备第一次登网”通知在eUICC的生命周期中只发生一次
			String tmpVal = CacheUtil.getString(eid);
			if(StringUtils.isEmpty(tmpVal)){
				profileService.enableProfile(euiccProfile);
			}
		} else if (("02".equals(type)) || ("03".equals(type))) {
			profileService.enableProfile(euiccProfile);
		} else if ("05".equals(type)) {
			// Profile change after Fall-back
			// 查询fall-back状态，如果是激活状态，启用，不是激活，报错
			profileService.fallBackEnableProfile(euiccProfile);
		}

		return true;
	}

	/**
	 * 通知处理结果
	 * 
	 * @param eid
	 * @param type
	 * @param isdpAid
	 */
	private void notifyProcessResult(String eid, String type, String isdpAid) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		String sms = CacheUtil.getString(eid);
		if (StringUtils.isNotBlank(sms)) {
			// 前台有触发行为，须通知前台
			eventTrigger = new Gson().fromJson(sms, SmsTrigger.class);
			SmsTriggerUtil.notifyProcessResult(eventTrigger, processResult);
			return;
		}
		
		// 数据自己处理
		processingData(eid, type, isdpAid);
	}
	
	/**
	 * 解析0348短信，去掉UDH+command header.返回securred dada
	 * 
	 * @param plainText
	 * @return
	 */
	private String paserData(String plainText) {
		String lenHexStr;
		int offsize = plainText.indexOf("027000");// UDHL+IEIa+IEDLa
		
		if (offsize == -1) {
			return plainText;
		}
		offsize += 10;
		lenHexStr = plainText.substring(offsize, offsize + 2);// len的长度，不固定，可能为0x0D,0x11,0x15
		// 16进制转换成10进制
		int len = Integer.parseInt(lenHexStr, 16) * 2 + 2;
		offsize += len;
		// 过滤掉UDH+command header。得到的E1***
		return plainText.substring(offsize);
	}
}
