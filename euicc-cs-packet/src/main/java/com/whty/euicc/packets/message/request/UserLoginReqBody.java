package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * 用户登录
 * @author dengzm
 *
 */
@MsgType("phone.002.001.01")
public class UserLoginReqBody extends RequestMsgBody{

	private String cardNO;
	private String phoneNum;
	private String password;
	private String random;
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
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
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
