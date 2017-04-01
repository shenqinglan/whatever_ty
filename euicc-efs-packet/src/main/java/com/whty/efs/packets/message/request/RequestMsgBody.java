package com.whty.efs.packets.message.request;

import java.security.InvalidParameterException;

import com.whty.efs.packets.message.Body;

/**
 * 请求消息体<抽象类>
 * 
 * @author baojw@whty.com.cn
 * @date 2014年10月11日 下午3:00:46
 */
public abstract class RequestMsgBody implements Body {

	/**
	 * 校验请求对象中的参数
	 *
	 * @throws InvalidParameterException
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月14日 下午7:15:29
	 */
	public void checkParameters() throws InvalidParameterException {
		// do nothing
	}
}
