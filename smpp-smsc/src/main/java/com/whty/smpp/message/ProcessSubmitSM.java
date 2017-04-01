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

import com.whty.smpp.exceptions.OutboundQueueFullException;
import com.whty.smpp.manager.Counter;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.SubmitSM;
import com.whty.smpp.pdu.SubmitSMResp;
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
public class ProcessSubmitSM implements IMessageResponse {
	
	Logger logger = LoggerFactory.getLogger(ProcessSubmitSM.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessSubmitSM(StandardProtocolHandler handler) {
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
		LoggingUtilities.hexDump(": Standard SUBMIT_SM:", message, len);
        byte[] respMessage;
        
        // message解码成pdu
        SubmitSM smppmsg = new SubmitSM();
        smppmsg.demarshall(message);
        LoggingUtilities.logDecodedPdu(smppmsg);

        // 接收到的消息添加
        ++Counter.counter;

        SubmitSMResp smppresp = new SubmitSMResp(smppmsg);

        // 校验一下session的取值
        if ((!handler.getSession().isBound()) || (!handler.getSession().isTransmitter()))
        {
            logger.debug("Invalid bind state. Must be bound as transmitter for this PDU");
            handler.setWasInvalidBindState(true);
            respMessage = smppresp.errorResponse(smppresp.getCmd_id(),
                    PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
            handler.logPduInfo(":SUBMIT_SM_RESP (ESME_RINVBNDSTS):", respMessage, smppresp);
            handler.getConnection().writeResponse(respMessage);
            return;
        }

        if (StringUtils.isBlank(smppmsg.getDestination_addr()))
        {
            respMessage = smppresp.errorResponse(smppresp.getCmd_id(),
                    PduConstants.ESME_RINVDSTADR, smppresp.getSeq_no());
            handler.logPduInfo(":SUBMIT_SM_RESP (ESME_RINVDSTADR):", respMessage, smppresp);
            handler.getConnection().writeResponse(respMessage);
            return;
        }

        // 消息添加到生命周期管理系统进行管理  oubBound
        MessageState m = null;
        try
        {
            m = new MessageState(smppmsg, smppresp.getMessage_id());
            smsc.getOq().addMessageState(m);
        }
        catch (OutboundQueueFullException e)
        {
            logger.debug("OutboundQueue full.");
			respMessage = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RMSGQFUL, smppresp.getSeq_no());
			handler.logPduInfo(":SUBMIT_SM_RESP (ESME_RMSGQFUL):", respMessage, smppresp);
			handler.getConnection().writeResponse(respMessage);
            return;
        }
        respMessage = smppresp.marshall();

        handler.logPduInfo(":SUBMIT_SM_RESP:", respMessage, smppresp);

        handler.getConnection().writeResponse(respMessage);
        
        // 更新消息管理的消息，设置responseSend标记为真
        smsc.getOq().setResponseSent(m);
	}

}
