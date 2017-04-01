package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

@MsgType("connectParaUpdateByDp")
public class ConnectParaUpdateByDpReqBody extends EuiccReqBody{
	private String isdPAid;
	private String smsCenterNo;

	public String getSmsCenterNo() {
		return smsCenterNo;
	}

	public void setSmsCenterNo(String smsCenterNo) {
		this.smsCenterNo = smsCenterNo;
	}

	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}

	

}
