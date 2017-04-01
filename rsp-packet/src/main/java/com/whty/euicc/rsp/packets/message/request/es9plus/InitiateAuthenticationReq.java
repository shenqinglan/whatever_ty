/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-14
 * Id: InitiateAuthenticationReq.java,v 1.0 2016-12-14 上午9:37:44 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es9plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName InitiateAuthenticationReq
 * @author Administrator
 * @date 2016-12-14 上午9:37:44
 * "required" : ["euiccChallenge", "euiccInfo1", "smdpAddress"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es9plus/smdp/initiateAuthentication")
public class InitiateAuthenticationReq extends RequestMsgBody {

	/**
	 * 	"type" : "string",
	 *  "format" : "base64",
	 *  "description" : "base64 encoded binary data containing eUICC Challenge defined in Section 5.6.1"
	 */
	private String euiccChallenge;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "base64 encoded binary data containing euiccinfo1 defined in Section 5.6.1"
	 */
	private String euiccInfo1;
	
	/**
	 * "type" : "string",
	 * "description" : "SM-DP+ Address as defined in Section 5.6.1"
	 */
	private String smdpAddress;

	/**
	 * @return the euiccChallenge
	 */
	public String getEuiccChallenge() {
		return euiccChallenge;
	}

	/**
	 * @param euiccChallenge the euiccChallenge to set
	 */
	public void setEuiccChallenge(String euiccChallenge) {
		this.euiccChallenge = euiccChallenge;
	}

	/**
	 * @return the euiccInfo1
	 */
	public String getEuiccInfo1() {
		return euiccInfo1;
	}

	/**
	 * @param euiccInfo1 the euiccInfo1 to set
	 */
	public void setEuiccInfo1(String euiccInfo1) {
		this.euiccInfo1 = euiccInfo1;
	}

	/**
	 * @return the smdpAddress
	 */
	public String getSmdpAddress() {
		return smdpAddress;
	}

	/**
	 * @param smdpAddress the smdpAddress to set
	 */
	public void setSmdpAddress(String smdpAddress) {
		this.smdpAddress = smdpAddress;
	}
}
