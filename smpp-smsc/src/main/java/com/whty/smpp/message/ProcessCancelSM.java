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

import com.whty.smpp.exceptions.InternalException;
import com.whty.smpp.exceptions.MessageStateNotFoundException;
import com.whty.smpp.pdu.CancelSM;
import com.whty.smpp.pdu.CancelSMResp;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pojo.MessageState;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessCancelSM
 * @author Administrator
 * @date 2017-1-23 下午3:17:31
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessCancelSM implements IMessageResponse {

	Logger logger = LoggerFactory.getLogger(ProcessCancelSM.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessCancelSM(StandardProtocolHandler handler) {
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
		  LoggingUtilities.hexDump(": CANCEL_SM:", message, len);
	        CancelSM smppmsg = new CancelSM();
	        byte[] resp_message;
	        smppmsg.demarshall(message);
	            LoggingUtilities.logDecodedPdu(smppmsg);

	        CancelSMResp smppresp = new CancelSMResp(smppmsg);

	        if ((!handler.getSession().isBound()) || (!handler.getSession().isTransmitter()))
	        {
	            logger.debug("Invalid bind state. Must be bound as transmitter for this PDU");
	            handler.setWasInvalidBindState(true);
	            resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
	                    PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
	            handler.logPduInfo(": CANCEL_SM_RESP (ESME_RINVBNDSTS):", resp_message,
	                    smppresp);
	            handler.getConnection().writeResponse(resp_message);
	            return;
	        }

	        try
	        {
	            smppresp = cancelSm(smppmsg, smppresp);
	        }
	        catch (MessageStateNotFoundException e)
	        {
	            resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
	                    PduConstants.ESME_RCANCELFAIL, smppresp.getSeq_no());
	            handler.logPduInfo(": CANCEL_SM_RESP (ESME_RCANCELFAIL):", resp_message,
	                    smppresp);
	            handler.getConnection().writeResponse(resp_message);
	            return;
	        }

	        resp_message = smppresp.marshall();
	        handler.logPduInfo(":CANCEL_SM_RESP:", resp_message, smppresp);
	        handler.getConnection().writeResponse(resp_message);
	}
	
	public CancelSMResp cancelSm(CancelSM q, CancelSMResp r)
			throws MessageStateNotFoundException, InternalException {
		MessageState m = new MessageState();
		// messageid specified
		if ((!q.getOriginal_message_id().equals(""))) {
			m = smsc.getOq().queryMessageState(q.getOriginal_message_id(), q
					.getSource_addr_ton(), q.getSource_addr_npi(), q
					.getSource_addr());
			r.setSeq_no(q.getSeq_no());
			smsc.getOq().removeMessageState(m);
			return r;
		}
		// messageid null (in PDU), service_type specified
		if ((q.getOriginal_message_id().equals(""))
				&& (!q.getService_type().equals(""))) {
			int c = cancelMessages(q.getService_type(), q.getSource_addr_ton(),
					q.getSource_addr_npi(), q.getSource_addr(), q
							.getDest_addr_ton(), q.getDest_addr_npi(), q
							.getDestination_addr());
			logger.info(c + " messages cancelled");
			r.setSeq_no(q.getSeq_no());
			return r;
		}
		// messageid null (in PDU), service_type null also
		if ((q.getOriginal_message_id().equals(""))
				&& (q.getService_type().equals(""))) {
			int c = cancelMessages(q.getSource_addr_ton(), q
					.getSource_addr_npi(), q.getSource_addr(), q
					.getDest_addr_ton(), q.getDest_addr_npi(), q
					.getDestination_addr());
			logger.info(c + " messages cancelled");
			r.setSeq_no(q.getSeq_no());
			return r;
		}
		logger.debug("Laws of physics violated. Well laws of logic anyway. Fell through conditions in smsc.cancelSm");
		logger.debug("Request is:" + q.toString());
		throw new InternalException(
				"Laws of physics violated. Well laws of logic anyway. Fell through conditions in smsc.cancelSm");
	}
	
	private int cancelMessages(String service_type, int source_addr_ton,
			int source_addr_npi, String source_addr, int dest_addr_ton,
			int dest_addr_npi, String destination_addr) {

		Object[] messages = smsc.getOq().getAllMessageStates();
		MessageState m;
		int s = messages.length;
		int c = 0;
		for (int i = 0; i < s; i++) {
			m = (MessageState) messages[i];
			if (m.getPdu().getService_type().equals(service_type)
					&& m.getPdu().getSource_addr_ton() == source_addr_ton
					&& m.getPdu().getSource_addr_npi() == source_addr_npi
					&& m.getPdu().getSource_addr().equals(source_addr)
					&& m.getPdu().getDest_addr_ton() == dest_addr_ton
					&& m.getPdu().getDest_addr_npi() == dest_addr_npi
					&& m.getPdu().getDestination_addr()
							.equals(destination_addr)) {
				c++;
				smsc.getOq().removeMessageState(m);
			}
		}
		return c;
	}

	private int cancelMessages(int source_addr_ton, int source_addr_npi,
			String source_addr, int dest_addr_ton, int dest_addr_npi,
			String destination_addr) {
		Object[] messages = smsc.getOq().getAllMessageStates();
		MessageState m;
		int s = messages.length;
		int c = 0;
		for (int i = 0; i < s; i++) {
			m = (MessageState) messages[i];
			if (m.getPdu().getSource_addr_ton() == source_addr_ton
					&& m.getPdu().getSource_addr_npi() == source_addr_npi
					&& m.getPdu().getSource_addr().equals(source_addr)
					&& m.getPdu().getDest_addr_ton() == dest_addr_ton
					&& m.getPdu().getDest_addr_npi() == dest_addr_npi
					&& m.getPdu().getDestination_addr()
							.equals(destination_addr)) {
				c++;
				smsc.getOq().removeMessageState(m);
			}
		}
		return c;
	}

}
