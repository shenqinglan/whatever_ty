/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: CancelSessionReq.java,v 1.0 2016-12-6 下午4:35:39 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es9plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName CancelSessionReq
 * @author Administrator
 * @date 2016-12-6 下午4:35:39
 * "required" : ["transactionId", "cancelSessionResponse"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es9plus/smdp/cancelSession")
public class CancelSessionReq extends RequestMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9,A-F]{2,32}$",
	 * "description" : "Hexadecimal representation of the TransactionID defined in Section 5.6.5"
	 */
	private String transactionId;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "base64 encoded binary data containing CancelSessionResponse data object defined in Section 5.7.14"
	 */
	private String cancelSessionResponse;
	
	private String euiccCancelSessionSigned;
	private String euiccCancelSessionSignature;
	
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
	 * @return the cancelSessionResponse
	 */
	public String getCancelSessionResponse() {
		return cancelSessionResponse;
	}
	/**
	 * @param cancelSessionResponse the cancelSessionResponse to set
	 */
	public void setCancelSessionResponse(String cancelSessionResponse) {
		this.cancelSessionResponse = cancelSessionResponse;
	}
	/**
	 * @return the euiccCancelSessionSigned
	 */
	public String getEuiccCancelSessionSigned() {
		return euiccCancelSessionSigned;
	}
	/**
	 * @param euiccCancelSessionSigned the euiccCancelSessionSigned to set
	 */
	public void setEuiccCancelSessionSigned(String euiccCancelSessionSigned) {
		this.euiccCancelSessionSigned = euiccCancelSessionSigned;
	}
	/**
	 * @return the euiccCancelSessionSignature
	 */
	public String getEuiccCancelSessionSignature() {
		return euiccCancelSessionSignature;
	}
	/**
	 * @param euiccCancelSessionSignature the euiccCancelSessionSignature to set
	 */
	public void setEuiccCancelSessionSignature(String euiccCancelSessionSignature) {
		this.euiccCancelSessionSignature = euiccCancelSessionSignature;
	}
	
}
