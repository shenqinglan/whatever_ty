/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: CancelOrderReq.java,v 1.0 2016-12-6 下午2:58:27 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es2plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName CancelOrderReq
 * @author Administrator
 * "required" : ["iccid"]
 * @date 2016-12-6 下午2:58:27
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es2plus/smdp/cancelOrder")
public class CancelOrderReq extends RequestMsgBody {

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
	 * "description" : "as defined in section {5.3.4}"
	 */
	private String finalProfileStatusIndicator;
	
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
	 * @return the finalProfileStatusIndicator
	 */
	public String getFinalProfileStatusIndicator() {
		return finalProfileStatusIndicator;
	}
	/**
	 * @param finalProfileStatusIndicator the finalProfileStatusIndicator to set
	 */
	public void setFinalProfileStatusIndicator(String finalProfileStatusIndicator) {
		this.finalProfileStatusIndicator = finalProfileStatusIndicator;
	}
}
