/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-14
 * Id: InitiateAuthenticationResp.java,v 1.0 2016-12-14 上午9:50:19 Administrator
 */
package com.whty.euicc.rsp.packets.message.response.es9plus;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName InitiateAuthenticationResp
 * @author Administrator
 * @date 2016-12-14 上午9:50:19
 * "required" : ["transactionId", "serverSigned1", "serverSignature1","euiccCiPKIdToBeUsed", "serverCertificate"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class InitiateAuthenticationResp extends ResponseMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9,A-F]{2,32}$",
	 * "description" : "Hexadecimal representation of the TransactionID defined in Section 5.6.1"
	 */
	private String transactionId;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "The data object as required by ES10b.AuthenticateServer"
	 */
	private String serverSigned1;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "The signature as required by ES10b.AuthenticateServer"
	 */
	private String serverSignature1;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "The CI Public Key to be used as required by ES10b.AuthenticateServer"
	 */
	private String euiccCiPKIdToBeUsed;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "The server Certificate as required by ES10b.AuthenticateServer"
	 */
	private String serverCertificate;

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
	 * @return the serverSigned1
	 */
	public String getServerSigned1() {
		return serverSigned1;
	}

	/**
	 * @param serverSigned1 the serverSigned1 to set
	 */
	public void setServerSigned1(String serverSigned1) {
		this.serverSigned1 = serverSigned1;
	}

	/**
	 * @return the serverSignature1
	 */
	public String getServerSignature1() {
		return serverSignature1;
	}

	/**
	 * @param serverSignature1 the serverSignature1 to set
	 */
	public void setServerSignature1(String serverSignature1) {
		this.serverSignature1 = serverSignature1;
	}

	/**
	 * @return the euiccCiPKIdToBeUsed
	 */
	public String getEuiccCiPKIdToBeUsed() {
		return euiccCiPKIdToBeUsed;
	}

	/**
	 * @param euiccCiPKIdToBeUsed the euiccCiPKIdToBeUsed to set
	 */
	public void setEuiccCiPKIdToBeUsed(String euiccCiPKIdToBeUsed) {
		this.euiccCiPKIdToBeUsed = euiccCiPKIdToBeUsed;
	}

	/**
	 * @return the serverCertificate
	 */
	public String getServerCertificate() {
		return serverCertificate;
	}

	/**
	 * @param serverCertificate the serverCertificate to set
	 */
	public void setServerCertificate(String serverCertificate) {
		this.serverCertificate = serverCertificate;
	}
}
