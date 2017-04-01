package com.whty.efs.packets.message;


/**
 * 标准报文头
 * @author gaofeng 
 *
 */
public class Header {
	

	private String from;
	public Header(String from, String to, String messageId, String action,
			String address, String referenceParameters, String metadata) {
		super();
		this.from = from;
		this.to = to;
		this.messageID = messageId;
		this.action = action;
		this.address = address;
		this.referenceParameters = referenceParameters;
		this.metadata = metadata;
	}

	private String to;
	private String messageID;
	private String action;
	private String address;
	private String referenceParameters;
	private String metadata;
	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getMessageID() {
		return messageID;
	}


	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}
	
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getReferenceParameters() {
		return referenceParameters;
	}


	public void setReferenceParameters(String referenceParameters) {
		this.referenceParameters = referenceParameters;
	}


	public String getMetadata() {
		return metadata;
	}


	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}


	public Header(){}
	public Header(String version, String sender, String receiver, String sendTime, String msgType, String tradeNO, String tradeRefNO, String sessionID, String deviceType){
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
	private String version;
	/**发起方*/
	private String sender;
	/**接收方*/
	private String receiver;
	/**发送时间*/
	private String sendTime;
	/**消息类型*/
	private String msgType;
	/**交易号*/
	private String tradeNO;
	/**交易参考号*/
	private String tradeRefNO;
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
