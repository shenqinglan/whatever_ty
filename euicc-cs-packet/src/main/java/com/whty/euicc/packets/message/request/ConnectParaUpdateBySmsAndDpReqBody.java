package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;


@MsgType("connectParaUpdateBySmsAndDp")
public class ConnectParaUpdateBySmsAndDpReqBody extends EuiccReqBody{
	private String isdPAid;
	private String smsCenterNo;

	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}

	public String getSmsCenterNo() {
		return smsCenterNo;
	}

	public void setSmsCenterNo(String smsCenterNo) {
		this.smsCenterNo = smsCenterNo;
	}

}
