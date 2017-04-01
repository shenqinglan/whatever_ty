package com.whty.euicc.tasks.dto;

import com.whty.euicc.command.dto.KeySessionInfo;

public class EAInfo {
	private KeySessionInfo keySessionInfo;
	private String apdu;
	public EAInfo(KeySessionInfo keySessionInfo, String apdu) {
		this.keySessionInfo = keySessionInfo;
		this.apdu = apdu;
	}
	public KeySessionInfo getKeySessionInfo() {
		return keySessionInfo;
	}
	public String getApdu() {
		return apdu;
	}	
}
