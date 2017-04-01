/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: ConfirmOrderReq.java,v 1.0 2016-12-6 下午2:12:14 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es2plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName ConfirmOrderReq
 * @author Administrator
 * @date 2016-12-6 下午2:12:14
 * "required" : ["iccid", "releaseFlag"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es2plus/smdp/confirmOrder")
public class ConfirmOrderReq extends RequestMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{19,20}$",
	 * "description" : "ICCID as desc in ITU-T E.118"
	 */
	private String iccid;
	
	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{32}$",
	 * "description" : "EID as desc in SGP.02"
	 */
	private String eid;
	
	/**
	 * "type" : "string",
	 * "description" : "as defined in section {5.3.2}"
	 */
	private String matchingId;
	
	/**
	 * "type" : "string",
	 * "description" : "as defined in section {5.3.2}"
	 */
	private String confirmationCode;
	
	/**
	 * "type" : "string",
	 * "description" : "as defined in section {5.3.2}"
	 */
	private String smdsAddress;
	
	/**
	 * "type" : "boolean",
	 * "description" : "as defined in section {5.3.2}"
	 */
	private Boolean releaseFlag;
	
	/**
	 * @return the iccid
	 */
	public String getIccid() {
		return iccid;
	}
	/**
	 * @param iccid the iccid to set
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	/**
	 * @return the eid
	 */
	public String getEid() {
		return eid;
	}
	/**
	 * @param eid the eid to set
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}
	/**
	 * @return the matchingId
	 */
	public String getMatchingId() {
		return matchingId;
	}
	/**
	 * @param matchingId the matchingId to set
	 */
	public void setMatchingId(String matchingId) {
		this.matchingId = matchingId;
	}
	/**
	 * @return the confirmationCode
	 */
	public String getConfirmationCode() {
		return confirmationCode;
	}
	/**
	 * @param confirmationCode the confirmationCode to set
	 */
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	/**
	 * @return the smdsAddress
	 */
	public String getSmdsAddress() {
		return smdsAddress;
	}
	/**
	 * @param smdsAddress the smdsAddress to set
	 */
	public void setSmdsAddress(String smdsAddress) {
		this.smdsAddress = smdsAddress;
	}
	/**
	 * @return the releaseFlag
	 */
	public Boolean getReleaseFlag() {
		return releaseFlag;
	}
	/**
	 * @param releaseFlag the releaseFlag to set
	 */
	public void setReleaseFlag(Boolean releaseFlag) {
		this.releaseFlag = releaseFlag;
	}
}
