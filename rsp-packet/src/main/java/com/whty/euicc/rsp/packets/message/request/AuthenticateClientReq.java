package com.whty.euicc.rsp.packets.message.request;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

@MsgType("/gsma/rsp2/es9plus/authenticateClient")
public class AuthenticateClientReq extends RequestMsgBody{
	private String transactionId;
	private String euiccSigned1;
	private String euiccSignature1;
	private String euiccCertificate;
	private String eumCertificate;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getEuiccSigned1() {
		return euiccSigned1;
	}
	public void setEuiccSigned1(String euiccSigned1) {
		this.euiccSigned1 = euiccSigned1;
	}
	public String getEuiccSignature1() {
		return euiccSignature1;
	}
	public void setEuiccSignature1(String euiccSignature1) {
		this.euiccSignature1 = euiccSignature1;
	}
	public String getEuiccCertificate() {
		return euiccCertificate;
	}
	public void setEuiccCertificate(String euiccCertificate) {
		this.euiccCertificate = euiccCertificate;
	}
	public String getEumCertificate() {
		return eumCertificate;
	}
	public void setEumCertificate(String eumCertificate) {
		this.eumCertificate = eumCertificate;
	}
	

}
