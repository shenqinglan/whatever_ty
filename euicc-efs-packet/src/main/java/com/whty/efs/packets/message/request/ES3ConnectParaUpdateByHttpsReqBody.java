package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;


@MsgType("connectParaUpdateByHttps")
public class ES3ConnectParaUpdateByHttpsReqBody extends EuiccReqBody implements MsgBody{
	private String seqCounter;
	private String isdPAID;
	private String smsCenterNo;
	
	public String getSeqCounter() {
		return seqCounter;
	}
	public void setSeqCounter(String seqCounter) {
		this.seqCounter = seqCounter;
	}
	public String getIsdPAID() {
		return isdPAID;
	}
	public void setIsdPAID(String isdPAID) {
		this.isdPAID = isdPAID;
	}
	public String getSmsCenterNo() {
		return smsCenterNo;
	}
	public void setSmsCenterNo(String smsCenterNo) {
		this.smsCenterNo = smsCenterNo;
	}
	
}
