package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

@MsgType("prepareSmsrChange")
public class PrepareSmsrChangeReqBody extends EuiccReqBody {

	private String currentSmsrId;

	public String getCurrentSmsrId() {
		return currentSmsrId;
	}

	public void setCurrentSmsrId(String currentSmsrId) {
		this.currentSmsrId = currentSmsrId;
	}
	
	
}
