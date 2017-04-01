/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: SmppSessionHandler.java,v 1.0 2017-1-20 上午10:53:50 Administrator
 */
package com.whty.smpp.netty.handler;

import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;

/**
 * @ClassName SmppSessionHandler
 * @author Administrator
 * @date 2017-1-20 上午10:53:50
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface ISmppSessionHandler {

	public PduResponse firePduRequestReceived(PduRequest pduRequest);
	
}
