package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * 密码找回
 * @author dengzm
 *
 */
@MsgType("phone.004.001.01")
public class RefindPwdReqBody extends RequestMsgBody{
	private String cardNO;
	private String phoneNum;
	private String newpwd;
	private String idType;
	private String idNumber;
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
}
