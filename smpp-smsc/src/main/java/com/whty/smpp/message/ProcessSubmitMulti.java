/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: ProcessSubmitMulti.java,v 1.0 2017-1-23 下午3:16:15 Administrator
 */
package com.whty.smpp.message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.SubmitMulti;
import com.whty.smpp.pdu.SubmitMultiResp;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessSubmitMulti
 * @author Administrator
 * @date 2017-1-23 下午3:16:15
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessSubmitMulti implements IMessageResponse {

	Logger logger = LoggerFactory.getLogger(ProcessSubmitMulti.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessSubmitMulti(StandardProtocolHandler handler) {
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
		LoggingUtilities.hexDump(": Standard SUBMIT_MULTI:", message, len);
		byte[] resp_message;
		SubmitMulti smppmsg = new SubmitMulti();
		smppmsg.demarshall(message);
		LoggingUtilities.logDecodedPdu(smppmsg);

		SubmitMultiResp smppresp = new SubmitMultiResp(smppmsg);

		if ((!handler.getSession().isBound())
				|| (!handler.getSession().isTransmitter())) {
			logger.debug("Invalid bind state. Must be bound as transmitter for this PDU");
			handler.setWasInvalidBindState(true);
			resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
			handler.logPduInfo(": Standard SUBMIT_MULTI (ESME_RINVBNDSTS):",
					resp_message, smppresp);
			handler.getConnection().writeResponse(resp_message);
			return;
		}

		if (StringUtils.isBlank(smppmsg.getSource_addr())) {
			resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVSRCADR, smppresp.getSeq_no());
			handler.logPduInfo(": Standard SUBMIT_MULTI (ESME_RINVSRCADR):",
					resp_message, smppresp);
			handler.getConnection().writeResponse(resp_message);
			return;
		}

		if (smppmsg.getNumber_of_dests() < 1) {
			resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVNUMDESTS, smppresp.getSeq_no());
			handler.logPduInfo(": Standard SUBMIT_MULTI (ESME_RINVNUMDESTS):",
					resp_message, smppresp);
			handler.getConnection().writeResponse(resp_message);
			return;
		}

		resp_message = smppresp.marshall();
		handler.logPduInfo(":SUBMIT_MULTI_RESP:", resp_message, smppresp);

		handler.getConnection().writeResponse(resp_message);

	}

}
