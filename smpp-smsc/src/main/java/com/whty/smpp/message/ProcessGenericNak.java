/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: ProcessCancelSM.java,v 1.0 2017-1-23 下午3:17:31 Administrator
 */
package com.whty.smpp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.GenericNak;
import com.whty.smpp.pdu.GenericNakResp;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessCancelSM
 * @author Administrator
 * @date 2017-1-23 下午3:17:31
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessGenericNak implements IGenericNakResponse {

	Logger logger = LoggerFactory.getLogger(ProcessGenericNak.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessGenericNak(StandardProtocolHandler handler) {
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
		byte[] resp_message;
		logger.info(": Received GENERIC_NAK");
		LoggingUtilities.hexDump("GENERIC_NAK MESSAGE", message, len);
		GenericNak smppmsg = new GenericNak();
		smppmsg.demarshall(message);
		LoggingUtilities.logDecodedPdu(smppmsg);
		GenericNakResp smppresp = new GenericNakResp(smppmsg);
		resp_message = smppresp.marshall();
		handler.logPduInfo(": returning GENERIC_NAK:", resp_message, smppresp);
		handler.getConnection().writeResponse(resp_message);
	}

	@Override
	public void processGenericNakResponse(byte[] message, int len)
			throws Exception {
		byte[] resp_message;
		logger.info(": GENERIC_NAK unrecognised");
		LoggingUtilities.hexDump(": UNRECOGNISED MESSAGE:", message, len);
		GenericNak smppmsg = new GenericNak();
		smppmsg.demarshall(message);
		LoggingUtilities.logDecodedPdu(smppmsg);
		GenericNakResp smppresp = new GenericNakResp(smppmsg);
		smppresp.setCmd_status(PduConstants.ESME_RINVCMDID);
		resp_message = smppresp.marshall();
		handler.logPduInfo(": returning GENERIC_NAK:", resp_message, smppresp);
		handler.getConnection().writeResponse(resp_message);
	}
}
