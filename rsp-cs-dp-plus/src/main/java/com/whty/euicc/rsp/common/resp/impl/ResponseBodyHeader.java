/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-13
 * Id: ResponseBodyHeaderSuccess.java,v 1.0 2016-12-13 上午9:58:57 Administrator
 */
package com.whty.euicc.rsp.common.resp.impl;

import com.whty.euicc.rsp.common.resp.ResponseBodyHeaderImpl;
import com.whty.euicc.rsp.common.resp.ResponseStatusCodeImpl;
import com.whty.euicc.rsp.common.resp.ResponseStatusImpl;
import com.whty.euicc.rsp.packets.message.response.base.FunctionExecutionStatus;
import com.whty.euicc.rsp.packets.message.response.base.HeaderResp;

/**
 * @ClassName ResponseBodyHeader
 * @author Administrator
 * @date 2016-12-13 上午9:58:57
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ResponseBodyHeader implements ResponseBodyHeaderImpl{
	
	private ResponseStatusCodeImpl statusCode;
	private ResponseStatusImpl status;
	
	/**
	 * @param statusCode
	 * @param status
	 * @param codeData
	 */
	public ResponseBodyHeader(ResponseStatusCodeImpl statusCode,
			ResponseStatusImpl status) {
		super();
		this.statusCode = statusCode;
		this.status = status;
	}



	/** 
	 * @author Administrator
	 * @date 2016-12-13
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.euicc.rsp.common.resp.ResponseBodyHeader#getResponseBodyHeader()
	 */
	@Override
	public HeaderResp getResponseBodyHeader() {
		FunctionExecutionStatus fes = new FunctionExecutionStatus(status.getStatus());
		fes.setStatusCodeData(statusCode.getStatusCode());
		HeaderResp header = new HeaderResp(fes);
		return header;
	}
}
