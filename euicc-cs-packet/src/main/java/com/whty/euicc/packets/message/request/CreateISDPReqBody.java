package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
/**
 * 创建ISD-P请求
 * @author Administrator
 *
 */
@MsgType("createISDP")
public class CreateISDPReqBody extends EuiccReqBody{
	private String isdPAid;
	
	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
}
