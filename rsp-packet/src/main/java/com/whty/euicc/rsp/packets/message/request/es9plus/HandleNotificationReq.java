/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: HandleNotificationReq.java,v 1.0 2016-12-6 下午4:28:21 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es9plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName HandleNotificationReq
 * @author Administrator
 * @date 2016-12-6 下午4:28:21
 * "required" : ["pendingNotification"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es9plus/smdp/handleNotification")
public class HandleNotificationReq extends RequestMsgBody {

	/**
	 * "type" : "string",
	 * "format" : "base64",
	 * "description" : "base64 encoded binary data containing the PendingNotification defined in section 5.7.10"
	 */
	private String pendingNotification;

	/**
	 * @return the pendingNotification
	 */
	public String getPendingNotification() {
		return pendingNotification;
	}

	/**
	 * @param pendingNotification the pendingNotification to set
	 */
	public void setPendingNotification(String pendingNotification) {
		this.pendingNotification = pendingNotification;
	}
	
	private String profileInstallationResultData;
	private String euiccSignPIR;

	/**
	 * @return the profileInstallationResultData
	 */
	public String getProfileInstallationResultData() {
		return profileInstallationResultData;
	}

	/**
	 * @param profileInstallationResultData the profileInstallationResultData to set
	 */
	public void setProfileInstallationResultData(
			String profileInstallationResultData) {
		this.profileInstallationResultData = profileInstallationResultData;
	}

	/**
	 * @return the euiccSignPIR
	 */
	public String getEuiccSignPIR() {
		return euiccSignPIR;
	}

	/**
	 * @param euiccSignPIR the euiccSignPIR to set
	 */
	public void setEuiccSignPIR(String euiccSignPIR) {
		this.euiccSignPIR = euiccSignPIR;
	}
}
