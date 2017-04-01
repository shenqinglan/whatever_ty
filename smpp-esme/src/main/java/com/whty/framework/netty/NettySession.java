package com.whty.framework.netty;
/**
 * @ClassName NettySession
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface NettySession {

	public byte[] sendMsg(MsgSend msg, long timeoutMillis);
	
}
