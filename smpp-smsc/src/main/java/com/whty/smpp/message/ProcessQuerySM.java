/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: ProcessQuerySM.java,v 1.0 2017-1-23 下午3:16:57 Administrator
 */
package com.whty.smpp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.exceptions.MessageStateNotFoundException;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.QuerySM;
import com.whty.smpp.pdu.QuerySMResp;
import com.whty.smpp.pojo.MessageState;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessQuerySM
 * @author Administrator
 * @date 2017-1-23 下午3:16:57
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessQuerySM implements IMessageResponse {
	
	Logger logger = LoggerFactory.getLogger(ProcessQuerySM.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessQuerySM(StandardProtocolHandler handler) {
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
	 * @see com.whty.smpp.message.IMessageResponse#processMessageResponse(byte[], int)
	 */
	@Override
	public void processMessageResponse(byte[] message, int len)
			throws Exception {
        LoggingUtilities.hexDump(": QUERY_SM:", message, len);
        QuerySM smppmsg = new QuerySM();
        byte[] resp_message;
        smppmsg.demarshall(message);
        LoggingUtilities.logDecodedPdu(smppmsg);

        QuerySMResp smppresp = new QuerySMResp(smppmsg);

        if ((!handler.getSession().isBound()) || (!handler.getSession().isTransmitter()))
        {
            logger.debug("Invalid bind state. Must be bound as transmitter for this PDU");
            handler.setWasInvalidBindState(true);
            resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
                    PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
            handler.logPduInfo(": QUERY_SM (ESME_RINVBNDSTS):", resp_message, smppresp);
            handler.getConnection().writeResponse(resp_message);
            return;
        }

        // 在outBound队列里面查询smpp消息
        try
        {
            smppresp = querySm(smppmsg, smppresp);
        }
        catch (MessageStateNotFoundException e)
        {
            resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
                    PduConstants.ESME_RQUERYFAIL, smppresp.getSeq_no());
            handler.logPduInfo(": QUERY_SM_RESP (ESME_RQUERYFAIL):", resp_message, smppresp);
            handler.getConnection().writeResponse(resp_message);
            return;
        }
        resp_message = smppresp.marshall();
        handler.logPduInfo(":QUERY_SM_RESP:", resp_message, smppresp);
        handler.getConnection().writeResponse(resp_message);
	}
	
	public QuerySMResp querySm(QuerySM q, QuerySMResp r)
			throws MessageStateNotFoundException {
		MessageState m = new MessageState();
		m = smsc.getOq().queryMessageState(q.getOriginal_message_id(), q
				.getOriginating_ton(), q.getOriginating_npi(), q
				.getOriginating_addr());
		r.setMessage_state(m.getState());
		if (m.getFinalDate() != null)
			r.setFinal_date(m.getFinalDate().getDateString());
		else
			r.setFinal_date("");
		return r;
	}

}
