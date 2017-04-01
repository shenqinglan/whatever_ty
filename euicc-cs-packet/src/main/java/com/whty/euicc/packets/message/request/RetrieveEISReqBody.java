package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * EIS信息集
 * @author Administrator
 *
 */
@MsgType("retrieveEISBySr")
public class RetrieveEISReqBody  extends RequestMsgBody{
	private String eid;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

}
