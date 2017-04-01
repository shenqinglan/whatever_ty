/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: HandleDownloadProgressInfoResp.java,v 1.0 2016-12-6 下午3:11:24 Administrator
 */
package com.whty.euicc.rsp.packets.message.response.es2plus;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName HandleDownloadProgressInfoResp
 * @author Administrator
 * @date 2016-12-6 下午3:11:24
 * "required" : []
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class HandleDownloadProgressInfoResp extends ResponseMsgBody {

	private String iccid;

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
}
