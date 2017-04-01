package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

@MsgType("getScp03CounterByHttps")
public class GetScp03CounterByHttpsReqBody extends EuiccReqBody{
	private String isdPAID;

	public String getIsdPAID() {
		return isdPAID;
	}

	public void setIsdPAID(String isdPAID) {
		this.isdPAID = isdPAID;
	}
	

}
