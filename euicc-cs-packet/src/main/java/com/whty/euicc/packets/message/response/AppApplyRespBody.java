package com.whty.euicc.packets.message.response;

import com.whty.euicc.packets.message.response.attr.AttrProcessId;

public class AppApplyRespBody extends BaseRespBody implements AttrProcessId {

	public AppApplyRespBody() {
		super();
	}

	private String processId;
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	

}
