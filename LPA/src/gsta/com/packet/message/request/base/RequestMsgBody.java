package gsta.com.packet.message.request.base;

import gsta.com.packet.message.MsgBody;

import java.security.InvalidParameterException;

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
