package com.whty.euicc.data.message.request;

public class EuiccReqBody extends RequestMsgBody{
	private String eid;
	
	private String iccid;
	
	private String sms;//发磁短信内容
	
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	
}
