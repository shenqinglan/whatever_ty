package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("updatePolicyRules")
public class ES3UpdatePolilcyRulesReqBody extends PorReqBody implements MsgBody{
	private String smSrId;
	private String pol2Id;
	public String getSmSrId() {
		return smSrId;
	}
	public void setSmSrId(String smSrId) {
		this.smSrId = smSrId;
	}
	public String getPol2Id() {
		return pol2Id;
	}
	public void setPol2Id(String pol2Id) {
		this.pol2Id = pol2Id;
	}
}
