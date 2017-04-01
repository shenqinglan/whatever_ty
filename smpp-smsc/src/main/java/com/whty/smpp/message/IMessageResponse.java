/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: IMessageResponse.java,v 1.0 2017-1-23 下午2:55:58 Administrator
 */
package com.whty.smpp.message;

/**
 * @ClassName IMessageResponse
 * @author Administrator
 * @date 2017-1-23 下午2:55:58
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IMessageResponse {

	public void processMessageResponse (byte[] message, int len) throws Exception;
}
