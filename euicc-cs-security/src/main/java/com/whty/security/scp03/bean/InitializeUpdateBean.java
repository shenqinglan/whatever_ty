package com.whty.security.scp03.bean;
/**
 * 初始化更新结果体
 * @author Administrator
 *
 */
public class InitializeUpdateBean {
	private String apdu;
	private String hostChallenge;
	public InitializeUpdateBean(String apdu, String hostChallenge) {
		super();
		this.apdu = apdu;
		this.hostChallenge = hostChallenge;
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
	
	
	
}
