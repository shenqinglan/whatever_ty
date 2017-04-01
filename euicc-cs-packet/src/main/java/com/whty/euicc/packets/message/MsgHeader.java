package com.whty.euicc.packets.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.whty.euicc.common.utils.DateUtil;


/**
 * 标准报文头
 * @author gaofeng 
 *
 */
public class MsgHeader {
	public MsgHeader(){}
	
	
	public MsgHeader(String msgType) {
		super();
		this.msgType = msgType;
	}


	public MsgHeader(String version, String sender, String receiver, String sendTime, String msgType, String tradeNO, String tradeRefNO, String sessionID, String deviceType){
		this.version = version;
		this.sender = sender;
		this.receiver = receiver;
		this.sendTime = sendTime;
		this.msgType = msgType;
		this.tradeNO = tradeNO;
		this.tradeRefNO = tradeRefNO;
		this.sessionID = sessionID;
		this.deviceType = deviceType;
	}
	
	/**版本*/
	private String version="01";
	/**发起方*/
	private String sender;
	/**接收方*/
	private String receiver;
	/**发送时间*/
	private String sendTime = DateUtil.dateToDateString(new Date());
	/**消息类型*/
	private String msgType;
	/**交易号*/
	private String tradeNO = DateUtil.dateToDateString(new Date(),DateUtil.yyyyMMddHHmmss_EN);
	/**交易参考号*/
	private String tradeRefNO="0";
	/**会话标识*/
	private String sessionID;
	/**设备类型*/
	private String deviceType;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getTradeNO() {
		return tradeNO;
	}
	public void setTradeNO(String tradeNO) {
		this.tradeNO = tradeNO;
	}
	public String getTradeRefNO() {
		return tradeRefNO;
	}
	public void setTradeRefNO(String tradeRefNO) {
		this.tradeRefNO = tradeRefNO;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
}
