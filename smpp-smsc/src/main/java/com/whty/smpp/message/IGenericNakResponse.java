/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: IGenericNakResponse.java,v 1.0 2017-1-23 下午3:23:32 Administrator
 */
package com.whty.smpp.message;

/**
 * @ClassName IGenericNakResponse
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IGenericNakResponse extends IMessageResponse {

	public void processGenericNakResponse (byte[] message, int len) throws Exception;
}
