package com.whty.euicc.rsp.packets.message.response;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

public class DownloadOrderResp extends ResponseMsgBody {
	private String iccid;                 //M

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	

}
