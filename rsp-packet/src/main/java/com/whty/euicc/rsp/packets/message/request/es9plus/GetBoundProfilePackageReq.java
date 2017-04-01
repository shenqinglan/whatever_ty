/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: GetBoundProfilePackageReq.java,v 1.0 2016-12-6 下午3:53:16 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es9plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName GetBoundProfilePackageReq
 * @author Administrator
 * @date 2016-12-6 下午3:53:16
 * "required" : ["transactionId", "prepareDownloadResponse"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es9plus/smdp/getBoundProfilePackage")
public class GetBoundProfilePackageReq extends RequestMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9,A-F]{2,32}$",
	 * "description" : "Hexadecimal representation of the TransactionID defined in Section 5.6.2"
	 */
	private String transactionId;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "base64 encoded binary data containing PrepareDownloadResponse defined in Section 5.6.2"
	 */
	private String euiccSigned2;
	
	private String euiccSignature2;

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
	 * @return the euiccSigned2
	 */
	public String getEuiccSigned2() {
		return euiccSigned2;
	}

	/**
	 * @param euiccSigned2 the euiccSigned2 to set
	 */
	public void setEuiccSigned2(String euiccSigned2) {
		this.euiccSigned2 = euiccSigned2;
	}

	/**
	 * @return the euiccSignature2
	 */
	public String getEuiccSignature2() {
		return euiccSignature2;
	}

	/**
	 * @param euiccSignature2 the euiccSignature2 to set
	 */
	public void setEuiccSignature2(String euiccSignature2) {
		this.euiccSignature2 = euiccSignature2;
	}
}
