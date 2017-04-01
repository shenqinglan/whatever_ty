package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
import com.whty.euicc.packets.message.request.attr.WithAppAidVersion_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithSeID_MsgBody;

@MsgType("tath.112.001.01")
public class AppApplyReqBody extends RequestMsgBody implements
		WithSeID_MsgBody, WithAppAidVersion_MsgBody {

	/** 应用AID */
	private String appAID;
	/** 应用版本号 */
	private String appVersion;
	/** 安全载体标识 */
	private String seId;
	/** 持卡人姓名 */
	private String cardHolderName;
	/** 证件类型 */
	private String idType;
	/** 证件号码 */
	private String idNo;
	/** 手机号 */
	private String msisdn;
	/** 短信验证码 */
	private String smsAuthCode;
	/** 主账号 */
	private String pan;
	/** 联机密码 */
	private String pin;
	/** CVN2 */
	private String cvn2;
	/** 有效期 */
	private String expeiryDate;
	/** accountLimit */
	private String accountLimit;
	/** accountType */
	private String accountType;
	/** mac */
	private String mac;

	public String getSeId() {
		return seId;
	}

	public void setSeId(String seId) {
		this.seId = seId;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getSmsAuthCode() {
		return smsAuthCode;
	}

	public void setSmsAuthCode(String smsAuthCode) {
		this.smsAuthCode = smsAuthCode;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getExpeiryDate() {
		return expeiryDate;
	}

	public void setExpeiryDate(String expeiryDate) {
		this.expeiryDate = expeiryDate;
	}

	public String getAccountLimit() {
		return accountLimit;
	}

	public void setAccountLimit(String accountLimit) {
		this.accountLimit = accountLimit;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getAppAID() {
		return appAID;
	}

	public void setAppAID(String appAID) {
		this.appAID = appAID;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getSeID() {
		return seId;
	}

	public void setSeID(String seID) {
		this.seId = seID;
	}
}
