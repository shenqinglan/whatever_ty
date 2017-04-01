/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-13
 * Id: ResponseResultSuccess.java,v 1.0 2016-12-13 下午4:32:50 Administrator
 */
package com.whty.euicc.rsp.common.resp.impl;

import com.whty.euicc.rsp.common.resp.ResponseStatusImpl;

/**
 * @ClassName ResponseResultSuccess
 * @author Administrator
 * @date 2016-12-13 下午4:32:50
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ResponseStatusSuccess implements ResponseStatusImpl {

	/** 
	 * @author Administrator
	 * @date 2016-12-13
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.euicc.rsp.common.resp.AbstractResponseResult#getStatus()
	 */
	@Override
	public String getStatus() {
		return "Executed-Success";
	}
}
