package com.whty.euicc.packets.message.request;

public class EuiccReqBody extends RequestMsgBody{
	private String eid;
	
	private String iccid;
	
	private String sms;//发磁短信内容
	
	private String phoneNo;

	/*
	 * tar值取ISD-R或ISD-P的AID的倒数第四个字节至倒数第二个字节，
	 * 例如ISD-R-AID为A0 00 00 05 59 10 10 FF FF FF FF 89 00 00 01 00,其tar值则为000001
	 */
	private String strOfTar;
	
	
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getStrOfTar() {
		return strOfTar;
	}

	public void setStrOfTar(String strOfTar) {
		this.strOfTar = strOfTar;
	}

	
}
