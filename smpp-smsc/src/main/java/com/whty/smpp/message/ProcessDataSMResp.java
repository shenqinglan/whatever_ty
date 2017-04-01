/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: ProcessSubmitSM.java,v 1.0 2017-1-23 下午3:14:42 Administrator
 */
package com.whty.smpp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.DataSMResp;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessSubmitSM
 * @author Administrator
 * @date 2017-1-23 下午3:14:42
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessDataSMResp implements IMessageResponse {

	Logger logger = LoggerFactory.getLogger(ProcessDataSMResp.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessDataSMResp(StandardProtocolHandler handler) {
		super();
		this.handler = handler;
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @param message
	 * @param len
	 * @throws Exception
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.message.IMessageResponse#processMessageResponse(byte[],
	 *      int)
	 */
	@Override
	public void processMessageResponse(byte[] message, int len)
			throws Exception {
		LoggingUtilities.hexDump(": DATA_SM_RESP request", message, len);
		DataSMResp smppmsg = new DataSMResp();
		smppmsg.demarshall(message);
		handler.logPduInfo(": DATA_SM_RESP: response", message, smppmsg);
	}

}
