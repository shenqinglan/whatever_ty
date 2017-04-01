/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: AuthenticateClientResp.java,v 1.0 2016-12-6 下午4:19:26 Administrator
 */
package com.whty.euicc.rsp.packets.message.response.es9plus;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName AuthenticateClientResp
 * @author Administrator
 * @date 2016-12-6 下午4:19:26
 * "required" : ["transactionId", "profileMetadata", " smdpSigned2","smdpSignature2", "smdpCertificate"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class AuthenticateClientResp extends ResponseMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9,A-F]{2,32}$",
	 * "description" : "Hexadecimal representation of the TransactionID defined in Section 5.6.3"
	 */
	private String transactionId;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : " base64 encoded binary data containing
	 * StoreMetadataRequest defined in section 5.5.3",
	 */
	private String profileMetadata;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "SmdpSigned2 encoded data object"
	 */
	private String smdpSigned2;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "SM-DP+ signature as defined in ES10b.PrepareDownload"
	 */
	private String smdpSignature2;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "The Certificate as required by ES10b.PrepareDownload"
	 */
	private String smdpCertificate;

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
	 * @return the profileMetadata
	 */
	public String getProfileMetadata() {
		return profileMetadata;
	}

	/**
	 * @param profileMetadata the profileMetadata to set
	 */
	public void setProfileMetadata(String profileMetadata) {
		this.profileMetadata = profileMetadata;
	}

	/**
	 * @return the smdpSigned2
	 */
	public String getSmdpSigned2() {
		return smdpSigned2;
	}

	/**
	 * @param smdpSigned2 the smdpSigned2 to set
	 */
	public void setSmdpSigned2(String smdpSigned2) {
		this.smdpSigned2 = smdpSigned2;
	}

	/**
	 * @return the smdpSignature2
	 */
	public String getSmdpSignature2() {
		return smdpSignature2;
	}

	/**
	 * @param smdpSignature2 the smdpSignature2 to set
	 */
	public void setSmdpSignature2(String smdpSignature2) {
		this.smdpSignature2 = smdpSignature2;
	}

	/**
	 * @return the smdpCertificate
	 */
	public String getSmdpCertificate() {
		return smdpCertificate;
	}

	/**
	 * @param smdpCertificate the smdpCertificate to set
	 */
	public void setSmdpCertificate(String smdpCertificate) {
		this.smdpCertificate = smdpCertificate;
	}
}
