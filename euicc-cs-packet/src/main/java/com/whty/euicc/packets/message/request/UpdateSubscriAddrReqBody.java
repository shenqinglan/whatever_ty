package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

@MsgType("updateSubscriAddrBySr")
public class UpdateSubscriAddrReqBody extends EuiccReqBody {
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
