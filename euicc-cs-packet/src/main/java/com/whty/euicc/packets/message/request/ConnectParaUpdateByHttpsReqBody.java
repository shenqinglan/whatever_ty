package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;


@MsgType("connectParaUpdateByHttps")
public class ConnectParaUpdateByHttpsReqBody extends EuiccReqBody{
	private String seqCounter;
	private String isdPAID;
	private String smsCenterNo;
	

	public String getSmsCenterNo() {
		return smsCenterNo;
	}

	public void setSmsCenterNo(String smsCenterNo) {
		this.smsCenterNo = smsCenterNo;
	}

	public String getIsdPAID() {
		return isdPAID;
	}

	public void setIsdPAID(String isdPAID) {
		this.isdPAID = isdPAID;
	}

	public String getSeqCounter() {
		return seqCounter;
	}

	public void setSeqCounter(String seqCounter) {
		this.seqCounter = seqCounter;
	}

}
