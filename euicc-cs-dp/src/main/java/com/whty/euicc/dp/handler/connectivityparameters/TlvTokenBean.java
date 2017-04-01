package com.whty.euicc.dp.handler.connectivityparameters;

public class TlvTokenBean {
	private String cmdString;
	private String tokenVerKcv;
	public TlvTokenBean(String cmdString, String tokenVerKcv) {
		super();
		this.cmdString = cmdString;
		this.tokenVerKcv = tokenVerKcv;
	}
	public String getCmdString() {
		return cmdString;
	}
	public void setCmdString(String cmdString) {
		this.cmdString = cmdString;
	}
	public String getTokenVerKcv() {
		return tokenVerKcv;
	}
	public void setTokenVerKcv(String tokenVerKcv) {
		this.tokenVerKcv = tokenVerKcv;
	}
	

}
