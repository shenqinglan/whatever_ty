package gsta.com.packet.message.response;

import gsta.com.packet.message.response.base.ResponseMsgBody;

public class ConfirmOrderResp extends ResponseMsgBody {
	private String eid;          //C
	private String matchingId;   //M
	private String smdpAddress;  //O
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
	public String getSmdpAddress() {
		return smdpAddress;
	}
	public void setSmdpAddress(String smdpAddress) {
		this.smdpAddress = smdpAddress;
	}
	

}
