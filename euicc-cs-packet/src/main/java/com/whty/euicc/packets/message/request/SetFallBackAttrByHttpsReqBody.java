package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

@MsgType("setFallBackAttrByHttps")
public class SetFallBackAttrByHttpsReqBody extends EuiccReqBody{

	private String isdPAid;
	
	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
}
