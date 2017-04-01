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

import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.Unbind;
import com.whty.smpp.pdu.UnbindResp;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessCancelSM
 * @author Administrator
 * @date 2017-1-23 下午3:17:31
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessUnBind implements IMessageResponse {

	Logger logger = LoggerFactory.getLogger(ProcessUnBind.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessUnBind(StandardProtocolHandler handler) {
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
		 LoggingUtilities.hexDump(": UNBIND:", message, len);
	        Unbind smppmsg = new Unbind();
	        byte[] resp_message;
	        smppmsg.demarshall(message);

	            LoggingUtilities.logDecodedPdu(smppmsg);

	        UnbindResp smppresp = new UnbindResp(smppmsg);

	        // Validate session
	        if (!handler.getSession().isBound())
	        {
	            logger.debug("Invalid bind state. Must be bound for this PDU");
	            handler.setWasInvalidBindState(true);
	            resp_message = smppresp.errorResponse(smppresp.getCmd_id(),
	                    PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
	            handler.logPduInfo(": UNBIND (ESME_RINVBNDSTS):", resp_message, smppresp);
	            handler.getConnection().writeResponse(resp_message);
	            return;
	        }

	        resp_message = smppresp.marshall();
	        if (handler.getSession().isReceiver())
	        {
	            smsc.receiverUnbound();
	        }
	        logger.debug("Receiver:" + handler.getSession().isReceiver() + ",Transmitter:"
	                + handler.getSession().isTransmitter());
	        
	        // 修改session的状态
	        handler.getSession().setBound(false);
	        handler.getSession().setReceiver(false);
	        handler.getSession().setTransmitter(false);
	        handler.setWasUnbindRequest(true);
	        
	        handler.logPduInfo(": UNBIND_RESP", resp_message,smppresp);
	        handler.getConnection().writeResponse(resp_message);
	}

}
