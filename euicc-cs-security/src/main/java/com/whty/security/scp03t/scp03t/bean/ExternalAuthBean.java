package com.whty.security.scp03t.scp03t.bean;
/**
 * 外部认证结果体
 * @author Administrator
 *
 */
public class ExternalAuthBean {
	private String apdu;
	private String perMac;
	private String hostChallenge;
	private String cardCounter;
	private String encCounter;
	public ExternalAuthBean(String apdu, String perMac,String hostChallenge,String encCounter) {
		super();
		this.apdu = apdu;
		this.perMac = perMac;
		this.hostChallenge = hostChallenge;
		this.encCounter = encCounter;
	}
	public String getApdu() {
		return apdu;
	}
	public void setApdu(String apdu) {
		this.apdu = apdu;
	}
	public String getPerMac() {
		return perMac;
	}
	public void setPerMac(String perMac) {
		this.perMac = perMac;
	}
	public String getHostChallenge() {
		return hostChallenge;
	}
	public void setHostChallenge(String hostChallenge) {
		this.hostChallenge = hostChallenge;
	}
	public String getEncCounter() {
		return encCounter;
	}
	public void setEncCounter(String encCounter) {
		this.encCounter = encCounter;
	}
	public String getCardCounter() {
		return cardCounter;
	}
	public void setCardCounter(String cardCounter) {
		this.cardCounter = cardCounter;
	}
}
