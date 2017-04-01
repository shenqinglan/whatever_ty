package gsta.com.packet.message.request;

import gsta.com.packet.message.MsgType;
import gsta.com.packet.message.request.base.RequestMsgBody;

@MsgType("/gsma/rsp2/es9plus/getBoundProfilePackage")
public class GetBoundProfilePackageReq extends RequestMsgBody {
	private String transactionId;
	private String euiccSigned2;
	private String euiccSignature2;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getEuiccSigned2() {
		return euiccSigned2;
	}
	public void setEuiccSigned2(String euiccSigned2) {
		this.euiccSigned2 = euiccSigned2;
	}
	public String getEuiccSignature2() {
		return euiccSignature2;
	}
	public void setEuiccSignature2(String euiccSignature2) {
		this.euiccSignature2 = euiccSignature2;
	}
	

}
