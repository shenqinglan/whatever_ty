package com.whty.euicc.packets.message.request;
import com.whty.euicc.packets.message.MsgType;

@MsgType("updatePolicyRules")
public class UpdatePolilcyRulesReqBody extends EuiccReqBody{
	private String smSrId;
	private POL2Type pol2Rules;
	public String getSmSrId() {
		return smSrId;
	}
	public void setSmSrId(String smSrId) {
		this.smSrId = smSrId;
	}
	public POL2Type getPol2Rules() {
		return pol2Rules;
	}
	public void setPol2Rules(POL2Type pol2Rules) {
		this.pol2Rules = pol2Rules;
	}
}
