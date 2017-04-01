package com.whty.security.scp03.bean;
/**
 * 初始化更新返回结果体
 * @author Administrator
 *
 */
public class InitializeUpdateRespBean {
	private boolean checkResult;
	private String cardChallenge;
	private String S_MAC;
	private String S_ENC;
	private String hostChallenge;
	public InitializeUpdateRespBean(boolean checkResult) {
		super();
		this.checkResult = checkResult;
	}
	
	

	
	public InitializeUpdateRespBean(boolean checkResult, String cardChallenge,
			String s_MAC, String s_ENC, String hostChallenge) {
		super();
		this.checkResult = checkResult;
		this.cardChallenge = cardChallenge;
		S_MAC = s_MAC;
		S_ENC = s_ENC;
		this.hostChallenge = hostChallenge;
	}




	public String getS_ENC() {
		return S_ENC;
	}

	public void setS_ENC(String s_ENC) {
		S_ENC = s_ENC;
	}

	public boolean isCheckResult() {
		return checkResult;
	}
	public void setCheckResult(boolean checkResult) {
		this.checkResult = checkResult;
	}
	public String getS_MAC() {
		return S_MAC;
	}
	public void setS_MAC(String s_MAC) {
		S_MAC = s_MAC;
	}

	public String getCardChallenge() {
		return cardChallenge;
	}

	public void setCardChallenge(String cardChallenge) {
		this.cardChallenge = cardChallenge;
	}




	public String getHostChallenge() {
		return hostChallenge;
	}




	public void setHostChallenge(String hostChallenge) {
		this.hostChallenge = hostChallenge;
	}
	
	
	
}
