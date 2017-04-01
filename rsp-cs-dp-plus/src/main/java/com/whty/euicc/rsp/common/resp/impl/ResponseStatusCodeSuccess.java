package com.whty.euicc.rsp.common.resp.impl;

import com.whty.euicc.rsp.common.resp.ResponseStatusCodeImpl;
import com.whty.euicc.rsp.packets.message.response.base.StatusCodeData;

public class ResponseStatusCodeSuccess implements ResponseStatusCodeImpl{
	
	private StatusCodeData codeData;

	/**
	 * 
	 */
	public ResponseStatusCodeSuccess() {
		super();
	}

	/**
	 * @param codeData
	 */
	public ResponseStatusCodeSuccess(StatusCodeData codeData) {
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
		return null;
	}

}
