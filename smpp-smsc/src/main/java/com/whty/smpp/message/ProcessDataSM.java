/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: ProcessSubmitSM.java,v 1.0 2017-1-23 下午3:14:42 Administrator
 */
package com.whty.smpp.message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.DataSM;
import com.whty.smpp.pdu.DataSMResp;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessSubmitSM
 * @author Administrator
 * @date 2017-1-23 下午3:14:42
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessDataSM implements IMessageResponse {

	Logger logger = LoggerFactory.getLogger(ProcessDataSM.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessDataSM(StandardProtocolHandler handler) {
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
		LoggingUtilities.hexDump(": Standard DATA_SM:", message, len);
		byte[] resp_message;
		DataSM smppmsg = new DataSM();
		smppmsg.demarshall(message);

		LoggingUtilities.logDecodedPdu(smppmsg);

		DataSMResp smppresp = new DataSMResp(smppmsg);

		if ((!handler.getSession().isBound())
				|| (!handler.getSession().isTransmitter())) {
			logger.debug("Invalid bind state. Must be bound as transmitter for this PDU");
			handler.setWasInvalidBindState(true);
			resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
			handler.logPduInfo(":DATA_SM_RESP (ESME_RINVBNDSTS):",
					resp_message, smppresp);
			handler.getConnection().writeResponse(resp_message);
			return;
		}

		if (StringUtils.isBlank(smppmsg.getSource_addr())) {
			resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVSRCADR, smppresp.getSeq_no());
			handler.logPduInfo(":DATA_SM_RESP (ESME_RINVSRCADR):",
					resp_message, smppresp);
			handler.getConnection().writeResponse(resp_message);
			return;
		}

		if (StringUtils.isBlank(smppmsg.getDestination_addr())) {
			resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVDSTADR, smppresp.getSeq_no());
			handler.logPduInfo(":DATA_SM_RESP (ESME_RINVDSTADR):",
					resp_message, smppresp);
			handler.getConnection().writeResponse(resp_message);
			return;
		}

		resp_message = smppresp.marshall();
		handler.logPduInfo(":DATA_SM_RESP:", resp_message, smppresp);
		handler.getConnection().writeResponse(resp_message);

	}

}
