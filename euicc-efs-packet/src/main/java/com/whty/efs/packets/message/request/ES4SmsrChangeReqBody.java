package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("srChangeSend")
public class ES4SmsrChangeReqBody extends EuiccReqBody implements MsgBody {
private String targetSmsrId;
	
	public String getTargetSmsrId() {
		return targetSmsrId;
	}
	public void setTargetSmsrId(String targetSmsrId) {
		this.targetSmsrId = targetSmsrId;
	}

}
