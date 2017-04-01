/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: NotificationPointStatus.java,v 1.0 2016-12-6 下午2:32:31 Administrator
 */
package gsta.com.packet.message.response.base;


/**
 * @ClassName NotificationPointStatus
 * @author Administrator
 * "required" : ["status"]
 * @date 2016-12-6 下午2:32:31
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class NotificationPointStatus {
	
	/**
	 * "type" : "string",
	 * "description" : "Executed-Success, Executed-WithWarning, Failed or Expired"
	 */
	private String status;
	
	/**
	 * "type" : "object",
	 * "description" : "StatusCodeData"
	 */
	private StatusCodeData statusCodeData;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusCodeData
	 */
	public StatusCodeData getStatusCodeData() {
		return statusCodeData;
	}

	/**
	 * @param statusCodeData the statusCodeData to set
	 */
	public void setStatusCodeData(StatusCodeData statusCodeData) {
		this.statusCodeData = statusCodeData;
	}
}
