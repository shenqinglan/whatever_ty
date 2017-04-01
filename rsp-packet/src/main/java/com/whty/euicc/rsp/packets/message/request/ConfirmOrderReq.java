package com.whty.euicc.rsp.packets.message.request;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

@MsgType("/gsma/rsp2/es2plus/confirmOrder")
public class ConfirmOrderReq extends RequestMsgBody {
	private String iccid;              //M
	private String eid;                //M:若EID被提供在之前的“ES2+.DownloadOrder”，则此处提供同一eid；若下载订单中没传，则此处必须传
	private String matchingId;         //O:若使用默认DP+，则此处为空串
	private String comfirmationCode;   //O:若需要确认码，则传
	private String smdsAddress;        //O:若需要DS走事件注册流程，则传；若用默认DP+，则不传
	private boolean releaseFlag;        //M:与profile状态设置相关
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getMatchingId() {
		return matchingId;
	}
	public void setMatchingId(String matchingId) {
		this.matchingId = matchingId;
	}
	public String getComfirmationCode() {
		return comfirmationCode;
	}
	public void setComfirmationCode(String comfirmationCode) {
		this.comfirmationCode = comfirmationCode;
	}
	public String getSmdsAddress() {
		return smdsAddress;
	}
	public void setSmdsAddress(String smdsAddress) {
		this.smdsAddress = smdsAddress;
	}
	public boolean getReleaseFlag() {
		return releaseFlag;
	}
	public void setReleaseFlag(boolean releaseFlag) {
		this.releaseFlag = releaseFlag;
	}
	

}
