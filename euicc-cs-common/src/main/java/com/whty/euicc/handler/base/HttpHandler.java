package com.whty.euicc.handler.base;
/**
 * http处理基类接口
 * @author Administrator
 *
 */
public interface HttpHandler {
	/**
	 * 处理请求
	 * @param requestStr
	 * @return
	 */
	public byte[] handle(String requestStr);
}
