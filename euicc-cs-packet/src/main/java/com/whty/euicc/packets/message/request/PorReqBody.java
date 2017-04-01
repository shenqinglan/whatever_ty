package com.whty.euicc.packets.message.request;

public class PorReqBody extends EuiccReqBody {
	private String noticeType;//卡片通知处理类型,非输入参数 、
	
	private String isdPAid;

	public String getNoticeType() {
		return noticeType;
	}
	
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	
	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
	
}
