package com.whty.security.scp03t.scp03t.bean;
/**
 * 初始化更新结果体
 * @author Administrator
 *
 */
public class InitializeUpdateBean {
	private String apdu;
	private String hostChallenge;
	private String cardCount;
	public InitializeUpdateBean(String apdu, String hostChallenge) {
		super();
		this.apdu = apdu;
		this.hostChallenge = hostChallenge;
	}
	public InitializeUpdateBean(String apdu, String hostChallenge,String cardCount) {
		super();
		this.apdu = apdu;
		this.hostChallenge = hostChallenge;
		this.cardCount = cardCount;
	}
	public String getApdu() {
		return apdu;
	}
	public void setApdu(String apdu) {
		this.apdu = apdu;
	}
	public String getHostChallenge() {
		return hostChallenge;
	}
	public void setHostChallenge(String hostChallenge) {
		this.hostChallenge = hostChallenge;
	}
	public String getCardCount() {
		return cardCount;
	}
	public void setCardCount(String cardCount) {
		this.cardCount = cardCount;
	}

	
}
