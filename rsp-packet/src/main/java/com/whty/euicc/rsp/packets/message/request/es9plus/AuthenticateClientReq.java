/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: AuthenticateClientReq.java,v 1.0 2016-12-6 下午4:16:14 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es9plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName AuthenticateClientReq
 * @author Administrator
 * @date 2016-12-6 下午4:16:14
 * "required" : ["transactionId", "authenticateServerResponse"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es9plus/smdp/authenticateClient")
public class AuthenticateClientReq extends RequestMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9,A-F]{2,32}$",
	 * "description" : "Hexadecimal representation of the TransactionID defined in Section 5.6.3"
	 */
	private String transactionId;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "base64 encoded binary data containing AuthenticateServerResponse defined in Section 5.6.3"
	 */
	private String authenticateServerResponse;
	
	private String euiccSigned1;
	private String euiccSignature1;
	private String euiccCertificate;
	private String eumCertificate;

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the authenticateServerResponse
	 */
	public String getAuthenticateServerResponse() {
		return authenticateServerResponse;
	}

	/**
	 * @param authenticateServerResponse the authenticateServerResponse to set
	 */
	public void setAuthenticateServerResponse(String authenticateServerResponse) {
		this.authenticateServerResponse = authenticateServerResponse;
	}

	/**
	 * @return the euiccSigned1
	 */
	public String getEuiccSigned1() {
		return euiccSigned1;
	}

	/**
	 * @param euiccSigned1 the euiccSigned1 to set
	 */
	public void setEuiccSigned1(String euiccSigned1) {
		this.euiccSigned1 = euiccSigned1;
	}

	/**
	 * @return the euiccSignature1
	 */
	public String getEuiccSignature1() {
		return euiccSignature1;
	}

	/**
	 * @param euiccSignature1 the euiccSignature1 to set
	 */
	public void setEuiccSignature1(String euiccSignature1) {
		this.euiccSignature1 = euiccSignature1;
	}

	/**
	 * @return the euiccCertificate
	 */
	public String getEuiccCertificate() {
		return euiccCertificate;
	}

	/**
	 * @param euiccCertificate the euiccCertificate to set
	 */
	public void setEuiccCertificate(String euiccCertificate) {
		this.euiccCertificate = euiccCertificate;
	}

	/**
	 * @return the eumCertificate
	 */
	public String getEumCertificate() {
		return eumCertificate;
	}

	/**
	 * @param eumCertificate the eumCertificate to set
	 */
	public void setEumCertificate(String eumCertificate) {
		this.eumCertificate = eumCertificate;
	}
}
