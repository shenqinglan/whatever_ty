package com.whty.rsp_lpa_app.bean;

import java.io.Serializable;

public class EnableMessage implements Serializable{
	private String iccid;
	private String result;
	
	public EnableMessage() {
		
	}
	
	public EnableMessage(String iccid, String result) {
		this.iccid = iccid;
		this.result = result;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
