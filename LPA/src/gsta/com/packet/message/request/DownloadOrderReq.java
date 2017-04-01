package gsta.com.packet.message.request;

import gsta.com.packet.message.MsgType;
import gsta.com.packet.message.request.base.RequestMsgBody;

@MsgType("/gsma/rsp2/es2plus/downloadOrder")
public class DownloadOrderReq extends RequestMsgBody {
	private String eid;          //若SM-DS或默认SM-DP+要被用来下载Profile，那么需要EID
	private String iccid;        //iccid与profileType二者选其一
	private String profileType;
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
