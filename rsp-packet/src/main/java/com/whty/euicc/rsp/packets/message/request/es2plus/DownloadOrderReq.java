/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: DownloadOrderReq.java,v 1.0 2016-12-6 下午1:57:55 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es2plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName DownloadOrderReq
 * @author Administrator
 * @date 2016-12-6 下午1:57:55
 * "required" : []
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es2plus/smdp/downloadOrder")
public class DownloadOrderReq extends RequestMsgBody {
	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{32}$",
	 * "description" : "EID as desc in SGP.02"
	 */
	private String eid;
	
	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{19,20}$",
	 * "description" : "ICCID as desc in ITU-T E.118"
	 */
	private String iccid;
	
	/**
	 * "type" : "string",
	 * "description" : "content free information defined by the Operator"
	 */
	private String profileType;
	
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
	 * @return the profileType
	 */
	public String getProfileType() {
		return profileType;
	}
	/**
	 * @param profileType the profileType to set
	 */
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
}

