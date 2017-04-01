package com.whty.security.scp03t.scp03t.bean;
/**
 * 初始化更新返回结果体
 * @author Administrator
 *
 */
public class InitializeUpdateRespBean {
	private boolean checkResult;
	private String cardChallenge;
	private String S_MAC;
	private String S_RMAC;
	private String S_ENC;
	private String hostChallenge;
	public InitializeUpdateRespBean(boolean checkResult) {
		super();
		this.checkResult = checkResult;
	}
	
	

	
	public InitializeUpdateRespBean(boolean checkResult, String cardChallenge,
			String s_MAC,String s_RMAC, String s_ENC, String hostChallenge) {
		super();
		this.checkResult = checkResult;
		this.cardChallenge = cardChallenge;
		S_MAC = s_MAC;
		S_RMAC = s_RMAC;
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
	public String getS_RMAC() {
		return S_RMAC;
	}
	public void setS_RMAC(String s_RMAC) {
		S_MAC = s_RMAC;
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
