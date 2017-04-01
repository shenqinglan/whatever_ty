package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * 用户注册
 * @author dengzm
 *
 */
@MsgType("phone.001.001.01")
public class UserRegistReqBody extends RequestMsgBody{

	private String cardNO;
	private String phoneNum;
	private String password;
	private String name;
	private String gender;
	private String idType;
	private String idNumber;
	private String email;
	private String captcha;
	private String captchaID;
	
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getCaptchaID() {
		return captchaID;
	}
	public void setCaptchaID(String captchaID) {
		this.captchaID = captchaID;
	}
}
