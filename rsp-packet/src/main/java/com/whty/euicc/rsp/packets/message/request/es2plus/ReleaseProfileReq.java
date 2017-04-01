/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: ReleaseProfileReq.java,v 1.0 2016-12-6 下午3:08:52 Administrator
 */
package com.whty.euicc.rsp.packets.message.request.es2plus;

import com.whty.euicc.rsp.packets.message.MsgType;
import com.whty.euicc.rsp.packets.message.request.base.RequestMsgBody;

/**
 * @ClassName ReleaseProfileReq
 * @author Administrator
 * @date 2016-12-6 下午3:08:52
 * "required" : ["iccid"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("/gsma/rsp2/es2plus/smdp/releaseProfile")
public class ReleaseProfileReq extends RequestMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{19,20}$",
	 * "description" : "ICCID as desc in ITU-T E.118"
	 */
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
