package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("getScp03CounterByHttps")
public class ES3GetScp03CounterReqBody extends EuiccReqBody implements MsgBody{

	private String isdPAID;

	public String getIsdPAID() {
		return isdPAID;
	}

	public void setIsdPAID(String isdPAID) {
		this.isdPAID = isdPAID;
	}
}
