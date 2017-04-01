package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("updateSubscriAddrBySr")
public class ES3UpdateSubscriAddrReqBody extends EuiccReqBody implements MsgBody{
	private String imsi;
	private String msisdn;
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
}
