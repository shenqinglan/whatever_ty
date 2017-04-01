package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

@MsgType("setFallBackAttrBySms")
public class SetFallBackAttrBySmsReqBody extends EuiccReqBody {
	private String isdPAid;

	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
	

}
