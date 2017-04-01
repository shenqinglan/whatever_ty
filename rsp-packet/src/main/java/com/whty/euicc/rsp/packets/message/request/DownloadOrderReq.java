package com.whty.euicc.rsp.packets.message.request;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

@MsgType("/gsma/rsp2/es2plus/downloadOrder")
public class DownloadOrderReq extends RequestMsgBody {
	private String eid;          //若SM-DS或默认SM-DP+要被用来下载Profile，那么需要EID
	private String iccid;        //iccid与profileType二者选其一
	private String profileType;
	private String msisdn;       //新增，通过手机号找到唯一一条profile记录
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

}
