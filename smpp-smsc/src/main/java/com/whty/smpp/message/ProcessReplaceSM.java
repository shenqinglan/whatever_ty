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

import com.whty.smpp.exceptions.MessageStateNotFoundException;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.ReplaceSM;
import com.whty.smpp.pdu.ReplaceSMResp;
import com.whty.smpp.pdu.SubmitSM;
import com.whty.smpp.pojo.MessageState;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessSubmitSM
 * @author Administrator
 * @date 2017-1-23 下午3:14:42
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessReplaceSM implements IMessageResponse {
	
	Logger logger = LoggerFactory.getLogger(ProcessReplaceSM.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessReplaceSM(StandardProtocolHandler handler) {
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
		LoggingUtilities.hexDump(": REPLACE_SM:", message, len);
        ReplaceSM smppmsg = new ReplaceSM();
        byte[] resp_message;
        smppmsg.demarshall(message);
        
            LoggingUtilities.logDecodedPdu(smppmsg);

        ReplaceSMResp smppresp = new ReplaceSMResp(smppmsg);

        if ((!handler.getSession().isBound()) || (!handler.getSession().isTransmitter()))
        {
            logger.debug("Invalid bind state. Must be bound as transmitter for this PDU");
            handler.setWasInvalidBindState(true);
            resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
                    PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
            handler.logPduInfo(": REPLACE_SM_RESP (ESME_RINVBNDSTS):", resp_message,smppresp);
            handler.getConnection().writeResponse(resp_message);
            return;
        }

        // 更新在outBoundQueue里面的原始消息，如果消息还在队列里面
        try
        {
            smppresp = replaceSm(smppmsg, smppresp);
        }
        catch (MessageStateNotFoundException e)
        {
            resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
                    PduConstants.ESME_RREPLACEFAIL, smppresp.getSeq_no());
            handler.logPduInfo(": REPLACE_SM_RESP (ESME_RREPLACEFAIL):", resp_message,smppresp);
            handler.getConnection().writeResponse(resp_message);
            return;
        }

        // 消息编码返回
        resp_message = smppresp.marshall();
        handler.logPduInfo(":REPLACE_SM_RESP:", resp_message,smppresp);
        handler.getConnection().writeResponse(resp_message);
	}
	
	public ReplaceSMResp replaceSm(ReplaceSM q, ReplaceSMResp r)
			throws MessageStateNotFoundException {
		MessageState m = new MessageState();
		m = smsc.getOq().queryMessageState(q.getMessage_id(), q.getSource_addr_ton(), q
				.getSource_addr_npi(), q.getSource_addr());
		SubmitSM pdu = m.getPdu();
		if (q.getSchedule_delivery_time() != null)
			pdu.setSchedule_delivery_time(q.getSchedule_delivery_time());
		if (q.getValidity_period() != null)
			pdu.setValidity_period(q.getValidity_period());
		pdu.setRegistered_delivery_flag(q.getRegistered_delivery_flag());
		pdu.setSm_default_msg_id(q.getSm_default_msg_id());
		pdu.setSm_length(q.getSm_length());
		pdu.setShort_message(q.getShort_message());
		m.setPdu(pdu);
		smsc.getOq().updateMessageState(m);
		logger.info("MessageState replaced with " + m.toString());
		r.setSeq_no(q.getSeq_no());
		return r;
	}

}
