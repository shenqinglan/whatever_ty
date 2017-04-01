package com.whty.euicc.rsp.packets.message.request;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

@MsgType("/gsma/rsp2/es2plus/releaseProfile")
public class ReleaseProfileReq extends RequestMsgBody {
	private String iccid;

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	

}
