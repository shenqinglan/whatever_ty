/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-6
 * Id: CancelSessionResp.java,v 1.0 2016-12-6 下午4:37:37 Administrator
 */
package com.whty.euicc.rsp.packets.message.response.es9plus;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName CancelSessionResp
 * @author Administrator
 * @date 2016-12-6 下午4:37:37
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class CancelSessionResp extends ResponseMsgBody {

	/*
	 * The "ES9+.CancelSession" function has no <JSON body> part of the <JSONresponseMessage>.
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
