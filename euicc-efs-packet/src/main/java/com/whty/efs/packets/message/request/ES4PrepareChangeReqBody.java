package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("prepareSmsrChange")
public class ES4PrepareChangeReqBody extends EuiccReqBody implements MsgBody {
	private String currentSmsrId;

	public String getCurrentSmsrId() {
		return currentSmsrId;
	}

	public void setCurrentSmsrId(String currentSmsrId) {
		this.currentSmsrId = currentSmsrId;
	}

}
