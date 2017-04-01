package gsta.com.packet.message.response;

import gsta.com.packet.message.response.base.ResponseMsgBody;

public class DownloadOrderResp extends ResponseMsgBody {
	private String iccid;                 //M

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	

}
