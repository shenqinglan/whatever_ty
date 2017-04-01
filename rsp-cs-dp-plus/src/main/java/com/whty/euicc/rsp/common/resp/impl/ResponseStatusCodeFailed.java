/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-13
 * Id: ResponseStatusCodeFailed.java,v 1.0 2016-12-13 下午5:22:10 Administrator
 */
package com.whty.euicc.rsp.common.resp.impl;

import com.whty.euicc.rsp.common.resp.ResponseStatusCodeImpl;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;

/**
 * @ClassName ResponseStatusCodeFailed
 * @author Administrator
 * @date 2016-12-13 下午5:22:10
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ResponseStatusCodeFailed implements ResponseStatusCodeImpl {
	
	private StatusCodeData codeData;
	/**
	 * 
	 */
	public ResponseStatusCodeFailed() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codeData
	 */
	public ResponseStatusCodeFailed(StatusCodeData codeData) {
		super();
		this.codeData = codeData;
	}

	/** 
	 * @author Administrator
	 * @date 2016-12-13
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.euicc.rsp.common.resp.ResponseStatusCodeImpl#getStatusCode()
	 */
	@Override
	public StatusCodeData getStatusCode() {
		return codeData;
	}
}
