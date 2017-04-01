package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;


public class SrChangeSendReqBody extends EuiccReqBody implements MsgBody{
	
	private String targetSmsrId;
	
	public String getTargetSmsrId() {
		return targetSmsrId;
	}
	public void setTargetSmsrId(String targetSmsrId) {
		this.targetSmsrId = targetSmsrId;
	}
}