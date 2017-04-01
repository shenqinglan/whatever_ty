package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
/**
 * Https下SM-SR的下载完成请求
 * @author Administrator
 *
 */
@MsgType("profileDownloadCompleted")
public class ProfileDownloadCompletedReqBody extends EuiccReqBody{
	private String isdPAid;
	
	public String getIsdPAid() {
		return isdPAid;
	}
	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}

}
