package com.whty.efs.packets.message.request;

import java.util.List;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("getStatusByHttps")
public class ES4GetStatusReqBody extends EuiccReqBody implements MsgBody {
	private List<String > iccidList;

	public List<String> getIccidList() {
		return iccidList;
	}

	public void setIccidList(List<String> iccidList) {
		this.iccidList = iccidList;
	}


}
