package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;


@MsgType("updateConnectivityParametersBySms")
public class UpdateConnectivityParametersReqBody extends EuiccReqBody  {
	private String connectivityData;
	private byte[] scp03SqCounterString;
	private String isdPAid;
	private String smsCenterNo;

	

	public byte[] getScp03SqCounterString() {
		return scp03SqCounterString;
	}

	public void setScp03SqCounterString(byte[] scp03SqCounterString) {
		this.scp03SqCounterString = scp03SqCounterString;
	}

	public String getConnectivityData() {
		return connectivityData;
	}

	public void setConnectivityData(String connectivityData) {
		this.connectivityData = connectivityData;
	}

	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}

	public String getSmsCenterNo() {
		return smsCenterNo;
	}

	public void setSmsCenterNo(String smsCenterNo) {
		this.smsCenterNo = smsCenterNo;
	}

}
