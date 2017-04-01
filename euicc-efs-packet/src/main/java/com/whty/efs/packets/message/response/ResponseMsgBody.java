package com.whty.efs.packets.message.response;

import com.whty.efs.packets.message.Body;
/**
 * 响应消息体<抽象类>
 *
 * @author baojw@whty.com.cn
 * @date 2014年10月11日 下午3:01:12
 */
public abstract class ResponseMsgBody implements Body {
	public abstract RspnMsg getRspnMsg();
}

	