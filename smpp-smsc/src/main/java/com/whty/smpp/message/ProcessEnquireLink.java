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

import com.whty.smpp.pdu.EnquireLink;
import com.whty.smpp.pdu.EnquireLinkResp;
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
public class ProcessEnquireLink implements IMessageResponse {

	Logger logger = LoggerFactory.getLogger(ProcessEnquireLink.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessEnquireLink(StandardProtocolHandler handler) {
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
		LoggingUtilities.hexDump(": ENQUIRE_LINK:", message, len);
		EnquireLink smppmsg = new EnquireLink();
		byte[] resp_message;
		smppmsg.demarshall(message);

		LoggingUtilities.logDecodedPdu(smppmsg);

		EnquireLinkResp smppresp = new EnquireLinkResp(smppmsg);

		if (!handler.getSession().isBound()) {
			logger.debug("Invalid bind state. Must be bound for this PDU");
			handler.setWasInvalidBindState(true);
			resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
			handler.logPduInfo(": ENQUIRE_LINK_RESP (ESME_RINVBNDSTS):",
					resp_message, smppresp);
			handler.getConnection().writeResponse(resp_message);
			return;
		}

		resp_message = smppresp.marshall();
		handler.logPduInfo(":ENQUIRE_LINK_RESP:", resp_message, smppresp);
		handler.getConnection().writeResponse(resp_message);
	}

}
