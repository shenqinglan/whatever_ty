/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: GetBoundProfilePackageResp.java,v 1.0 2016-12-6 下午3:56:01 Administrator
 */
package com.whty.euicc.rsp.packets.message.response.es9plus;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName GetBoundProfilePackageResp
 * @author Administrator
 * @date 2016-12-6 下午3:56:01
 * "required" : ["transactionId", "boundProfilePackage"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class GetBoundProfilePackageResp extends ResponseMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9,A-F]{2,32}$",
	 * "description" : "Hexadecimal representation of the TransactionID defined in Section 5.6.2"
	 */
	private String transactionId;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "base64 encoded binary data containing Bound Profile Package defined in Section 5.6.2"
	 */
	private String boundProfilePackage;

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
	 * @return the boundProfilePackage
	 */
	public String getBoundProfilePackage() {
		return boundProfilePackage;
	}

	/**
	 * @param boundProfilePackage the boundProfilePackage to set
	 */
	public void setBoundProfilePackage(String boundProfilePackage) {
		this.boundProfilePackage = boundProfilePackage;
	}
}
