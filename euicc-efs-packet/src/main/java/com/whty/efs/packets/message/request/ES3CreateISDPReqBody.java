package com.whty.efs.packets.message.request;

import java.math.BigInteger;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("createISDPByDp")
public class ES3CreateISDPReqBody extends PorReqBody implements MsgBody{
	private String mnoId;
	private BigInteger requiredMemory;
	public String getMnoId() {
		return mnoId;
	}
	public void setMnoId(String mnoId) {
		this.mnoId = mnoId;
	}
	public BigInteger getRequiredMemory() {
		return requiredMemory;
	}
	public void setRequiredMemory(BigInteger requiredMemory) {
		this.requiredMemory = requiredMemory;
	}
}
