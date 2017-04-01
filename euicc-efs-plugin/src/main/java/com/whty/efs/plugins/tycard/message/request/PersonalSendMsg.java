package com.whty.efs.plugins.tycard.message.request;

import com.whty.efs.common.util.SecurityUtil;

/**
 * 个人化发送报文类
 * @author Administrator
 *
 */
public class PersonalSendMsg {
	/**
	 * 报文类型，4个字节
	 * 00001001    个人化请求
	 * 80001002    个人化指令处理
	 * 80001003    个人化结束
	 */
	
	public PersonalSendMsg(String cmdType, String appAid, String cardNumber, String rapdu){
		this.cmdType = cmdType;
		this.appTag = appAid;
		this.remark = "00000000000000000000000000000000";
		this.cardNumber = cardNumber;
		this.rapdu = rapdu;
	}
	
	public PersonalSendMsg(String cmdType, String appAid, String cardNumber){
		this.cmdType = cmdType;
		this.appTag = appAid;
		this.remark = "00000000000000000000000000000000";
		this.cardNumber = cardNumber;
	}
	
	public PersonalSendMsg(String cmdType, String cardNumber, String sdAid, String instAid, String appAid) throws SecurityException{
		StringBuilder data = new StringBuilder("");
		data.append(cardNumber).append(sdAid);
		this.cmdType = cmdType;
		this.appTag = appAid;
		this.cardNumber = cardNumber;
		this.sdAid = sdAid;
		this.instAid = instAid;
		this.data  = SecurityUtil.encodeByMD5(data.toString());
		this.remark = "00000000000000000000000000000000";
		this.tokenFlag = "00";//00:不需要Token 01:需要Token
		this.tokenAlg = "";
	}
	
	private String cmdType;
	
	/**
	 * 保留字节，16个字节
	 */
	private String remark;
	
	/**
	 * 卡号，LV
	 */
	private String cardNumber;
	
	/**
	 * apdu指令，LV
	 */
	private String rapdu;
	
	/**
	 * 辅助安全域AID，LV
	 */
	private String sdAid;
	
	/**
	 * 分散因子，LV
	 */
	private String data;
	
	/**
	 * 实例aid，LV
	 */
	private String instAid;
	
	/**
	 * 是否需要Token，LV
	 */
	private String tokenFlag;//00:不需要Token 01:需要Token
	
	/**
	 * Token算法，LV
	 */
	private String tokenAlg;
	
	/**
	 * 应用标志
	 */
	private String appTag;
	


	public String getAppTag() {
		return appTag;
	}

	public void setAppTag(String appTag) {
		this.appTag = appTag;
	}

	public String getCmdType() {
		return cmdType;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getRapdu() {
		return rapdu;
	}

	public void setRapdu(String rapdu) {
		this.rapdu = rapdu;
	}



	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getInstAid() {
		return instAid;
	}

	public void setInstAid(String instAid) {
		this.instAid = instAid;
	}

	public String getTokenAlg() {
		return tokenAlg;
	}

	public void setTokenAlg(String tokenAlg) {
		this.tokenAlg = tokenAlg;
	}

	public String getTokenFlag() {
		return tokenFlag;
	}

	public void setTokenFlag(String tokenFlag) {
		this.tokenFlag = tokenFlag;
	}

	public String getSdAid() {
		return sdAid;
	}

	public void setSdAid(String sdAid) {
		this.sdAid = sdAid;
	}
}
