/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-13
 * Id: ResponseBodyImpl.java,v 1.0 2016-12-13 上午10:46:30 Administrator
 */
package com.whty.euicc.rsp.common.resp;

import com.whty.euicc.rsp.packets.message.response.base.ResponseMsgBody;

/**
 * @ClassName ResponseMsgBodyImpl
 * @author Administrator
 * @date 2016-12-13 上午10:46:30
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface ResponseMsgBodyImpl {

	String responseDataJson(ResponseBodyHeaderImpl bodyHeader, ResponseMsgBody responseBody);
}
