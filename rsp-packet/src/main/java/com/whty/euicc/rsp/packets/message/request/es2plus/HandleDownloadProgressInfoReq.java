/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: ReleaseProfileReq.java,v 1.0 2016-12-6 下午2:26:50 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es2plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;
import com.whty.euicc.rsp.packets.message.response.base.NotificationPointStatus;

/**
 * @ClassName HandleDownloadProgressInfoReq
 * @author Administrator
 * "required" : ["iccid", "profileType", "timestamp", "notificationPointId","notificationPointStatus"]
 * @date 2016-12-6 下午2:26:50
 * @Description TODO(请求参数)
 */
@MsgType("/gsma/rsp2/es2plus/smdp/handleDownloadProgressInfo")
public class HandleDownloadProgressInfoReq extends RequestMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{32}$",
	 * "description" : "EID as described in SGP.02"
	 */
	private String eid;
	
	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{19,20}$",
	 * "description" : "ICCID as described in ITU-T E.118"
	 */
	private String iccid;
	
	/**
	 * "type" : "string",
	 * "description" : "Content free information defined by the Operator(e.g.‘P9054-2’)"
	 */
	private String profileType;
	
	/**
	 * "type" : "string",
	 * "pattern" : "$[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}[T,D,Z]{1}$",
	 * "description" : " String format as specified by W3C: YYYY-MM-DDThh:mm:ssTZD (E.g. 2001-12-17T09:30:47Z)"
	 */
	private String timestamp;
	
	/**
	 * "type" : "integer",
	 * "description" : "Identification of the step reached in the procedure"
	 */
	private Integer notificationPointId;
	
	/**
	 * "type" : "object",
	 * "description" : "ExecutionStatus Common Data Type"
	 */
	private NotificationPointStatus notificationPointStatus;
	
	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "base64 encoded binary data containing the Result data contained in the ProfileInstallationResult"
	 */
	private String resultData;

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

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the notificationPointId
	 */
	public Integer getNotificationPointId() {
		return notificationPointId;
	}

	/**
	 * @param notificationPointId the notificationPointId to set
	 */
	public void setNotificationPointId(Integer notificationPointId) {
		this.notificationPointId = notificationPointId;
	}

	/**
	 * @return the notificationPointStatus
	 */
	public NotificationPointStatus getNotificationPointStatus() {
		return notificationPointStatus;
	}

	/**
	 * @param notificationPointStatus the notificationPointStatus to set
	 */
	public void setNotificationPointStatus(
			NotificationPointStatus notificationPointStatus) {
		this.notificationPointStatus = notificationPointStatus;
	}

	/**
	 * @return the resultData
	 */
	public String getResultData() {
		return resultData;
	}

	/**
	 * @param resultData the resultData to set
	 */
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
}
