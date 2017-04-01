package com.whty.netty;
/**
 * netty抽象接口
 * @author Administrator
 *
 */
public interface NettyHttpHandler {
	/**
	 * 处理请求
	 * @param requestStr
	 * @return
	 */
	public byte[] handle(String requestStr);
}
