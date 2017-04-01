/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: DownloadOrderResp.java,v 1.0 2016-12-6 下午2:06:46 Administrator
 */
package com.whty.euicc.rsp.packets.message.response.es2plus;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName DownloadOrderResp
 * @author Administrator
 * @date 2016-12-6 下午2:06:46
 * "required" : ["iccid"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DownloadOrderResp extends ResponseMsgBody {

	/**
	 * "type" : "string",
	 * "pattern" : "^[0-9]{19,20}$",
	 * "description" : "ICCID as desc in ITU-T E.118"
	 */
	private String iccid;

	/**
	 * 无参构造函数
	 */
	public DownloadOrderResp() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param iccid
	 */
	public DownloadOrderResp(String iccid) {
		super();
		this.iccid = iccid;
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
}
