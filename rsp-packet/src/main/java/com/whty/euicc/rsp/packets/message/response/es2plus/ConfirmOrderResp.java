/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: ConfirmOrderResp.java,v 1.0 2016-12-6 下午2:15:08 Administrator
 */
package com.whty.euicc.rsp.packets.message.response.es2plus;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName ConfirmOrderResp
 * @author Administrator
 * @date 2016-12-6 下午2:15:08
 * "required" : []
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ConfirmOrderResp extends ResponseMsgBody {

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
	private String smdpAddress;
	
	/**
	 * 无参构造函数
	 */
	public ConfirmOrderResp() {
		super();
	}
	
	/**
	 * @param eid
	 * @param matchingId
	 * @param smdpAddress
	 */
	public ConfirmOrderResp(String eid, String matchingId, String smdpAddress) {
		super();
		this.eid = eid;
		this.matchingId = matchingId;
		this.smdpAddress = smdpAddress;
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
