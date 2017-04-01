package com.whty.euicc.packets.message.response;

import com.whty.euicc.packets.message.MsgBody;
/**
 * 响应消息体<抽象类>
 *
 * @author baojw@whty.com.cn
 * @date 2014年10月11日 下午3:01:12
 */
public abstract class ResponseMsgBody implements MsgBody {
	public abstract RspnMsg getRspnMsg();
}

	