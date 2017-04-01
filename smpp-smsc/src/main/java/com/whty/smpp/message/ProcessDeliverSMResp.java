/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: ProcessDeliverSMResp.java,v 1.0 2017-1-23 下午3:15:35 Administrator
 */
package com.whty.smpp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.DeliverSMResp;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessDeliverSMResp
 * @author Administrator
 * @date 2017-1-23 下午3:15:35
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessDeliverSMResp implements IMessageResponse {
	
	Logger logger = LoggerFactory.getLogger(ProcessDeliverSMResp.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessDeliverSMResp(StandardProtocolHandler handler) {
		this.handler = handler;
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-23
	 * @param message
	 * @param len
	 * @throws Exception
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.message.IMessageResponse#processMessageResponse(byte[], int)
	 */
	@Override
	public void processMessageResponse(byte[] message, int len)
			throws Exception {
		 LoggingUtilities.hexDump(": DELIVER_SM_RESP:", message, len);
	        DeliverSMResp smppmsg = new DeliverSMResp();
	        smppmsg.demarshall(message);
	        LoggingUtilities.logDecodedPdu(smppmsg);
	        if (smppmsg.getCmd_status() == 0)
	        {
	        	logger.info("deliver sm ok success");
	        }
	        else
	        {
	        	logger.info("deliver sm fail");
	        }

	        handler.getIqueue().deliveryResult(smppmsg.getSeq_no(), smppmsg.getCmd_status());
	}

}
