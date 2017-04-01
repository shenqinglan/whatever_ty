package com.whty.euicc.rsp.packets.message.request.base;

import java.security.InvalidParameterException;

import com.whty.euicc.rsp.packets.message.MsgBody;

/**
 * 请求消息体<抽象类>
 * 
 * @author baojw@whty.com.cn
 * @date 2014年10月11日 下午3:00:46
 */
public abstract class RequestMsgBody implements MsgBody {

	/**
	 * 校验请求对象中的参数
	 *
	 * @throws java.security.InvalidParameterException
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月14日 下午7:15:29
	 */
	public void checkParameters() throws InvalidParameterException {
		// do nothing
	}
}
