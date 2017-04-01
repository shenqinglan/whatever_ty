package com.whty.smpp.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.message.IGenericNakResponse;
import com.whty.smpp.message.IMessageResponse;
import com.whty.smpp.message.ProcessBindReceiver;
import com.whty.smpp.message.ProcessBindTransceiver;
import com.whty.smpp.message.ProcessBindTransmitter;
import com.whty.smpp.message.ProcessCancelSM;
import com.whty.smpp.message.ProcessDataSM;
import com.whty.smpp.message.ProcessDataSMResp;
import com.whty.smpp.message.ProcessDeliverSMResp;
import com.whty.smpp.message.ProcessEnquireLink;
import com.whty.smpp.message.ProcessGenericNak;
import com.whty.smpp.message.ProcessQuerySM;
import com.whty.smpp.message.ProcessReplaceSM;
import com.whty.smpp.message.ProcessSubmitMulti;
import com.whty.smpp.message.ProcessSubmitSM;
import com.whty.smpp.message.ProcessUnBind;

import com.whty.smpp.pdu.Pdu;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pojo.Session;
import com.whty.smpp.queue.InboundQueue;

import com.whty.smpp.util.LoggingUtilities;
import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName StandardProtocolHandler
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class StandardProtocolHandler
{
    private Logger logger = LoggerFactory.getLogger(StandardProtocolHandler.class);
    Smsc smsc = Smsc.getInstance();
    InboundQueue iqueue = InboundQueue.getInstance();
	Session session = new Session();
    StandardConnectionHandler connection;
    boolean wasUnbindRequest = false;
    boolean wasBindReceiverRequest = false;
    boolean wasInvalidBindState = false;
    boolean failedAuthentication = false;
    RE address_range_regexp = null;
    
   	public InboundQueue getIqueue() {
   		return iqueue;
   	}
    
    public Session getSession()
    {
        return session;
    }

    public void setConnection(StandardConnectionHandler handler)
    {
        connection = handler;
    }

    public boolean wasBindReceiverRequest()
    {
        return wasBindReceiverRequest;
    }

   
    public boolean wasInvalidBindState()
    {
        return wasInvalidBindState;
    }

    public boolean wasUnbindRequest()
    {
        return wasUnbindRequest;
    }

    public void setWasBindReceiverRequest(boolean b)
    {
        wasBindReceiverRequest = b;
    }

    public void setWasInvalidBindState(boolean b)
    {
        wasInvalidBindState = b;
    }

    public void setWasUnbindRequest(boolean b)
    {
        wasUnbindRequest = b;
    }

    public boolean failedAuthentication()
    {
        return failedAuthentication;
    }

    public void setFailedAuthentication(boolean b)
    {
        failedAuthentication = b;
    }

    public StandardProtocolHandler()
    {
    }

    public String getName()
    {
        return "StandardProtocolHandler";
    }

    public StandardProtocolHandler(StandardConnectionHandler connection)
    {
        this.connection = connection;
    }
   
	public StandardConnectionHandler getConnection() {
		return connection;
	}

	static final int getBytesAsInt(byte i_byte)
    {
        return i_byte & 0xff;
    }

    void processMessage(byte[] message) throws Exception
    {
        int len = 0;
        int cmd = 0;
        int status = 0;
        len = PduUtilities.getIntegerValue(message, 0, 4);
        cmd = PduUtilities.getIntegerValue(message, 4, 4);
        status = PduUtilities.getIntegerValue(message, 8, 4);
        wasUnbindRequest = false;
        wasBindReceiverRequest = false;
        wasInvalidBindState = false;
        failedAuthentication = false;
        IMessageResponse response = null;
        IGenericNakResponse responseNak = null;
        switch (cmd)
        {
            case PduConstants.BIND_TRANSMITTER:
            	response = new ProcessBindTransmitter(this); 
                break;
            case PduConstants.BIND_RECEIVER:
            	response = new ProcessBindReceiver(this);
                break;
            case PduConstants.BIND_TRANSCEIVER:
            	response = new ProcessBindTransceiver(this);
                break;
            case PduConstants.SUBMIT_SM:
            	response = new ProcessSubmitSM(this);
                break;
            case PduConstants.DELIVER_SM_RESP:
            	response = new ProcessDeliverSMResp(this);
                break;
            case PduConstants.SUBMIT_MULTI:
                response = new ProcessSubmitMulti(this);
                break;
            case PduConstants.QUERY_SM:
            	response = new ProcessQuerySM(this);
                break;
            case PduConstants.CANCEL_SM:
            	response = new ProcessCancelSM(this);
                break;
            case PduConstants.REPLACE_SM:
                response = new ProcessReplaceSM(this);
                break;
            case PduConstants.ENQUIRE_LINK:
                response = new ProcessEnquireLink(this);
                break;
            case PduConstants.UNBIND:
            	response = new ProcessUnBind(this);
                break;
            case PduConstants.DATA_SM:
                response = new ProcessDataSM(this);
                break;
            case PduConstants.DATA_SM_RESP:
                response = new ProcessDataSMResp(this);
                break;
            case PduConstants.GENERIC_NAK:
            	response = new ProcessGenericNak(this);
                break;
            default:
                responseNak = new ProcessGenericNak(this);
                responseNak.processGenericNakResponse(message, len);
                return;
        }
        
        // 处理函数
        response.processMessageResponse(message, len);

        if ((wasUnbindRequest()) || (wasInvalidBindState())
                || (failedAuthentication()))
        {
            logger.debug("closing connection");
            getSession().setBound(false);
            connection.closeConnection();
        }
    }

    public void logPduInfo(String label, byte[] message, Pdu pdu) {
    	LoggingUtilities.hexDump(label, message, message.length);
        LoggingUtilities.logDecodedPdu(pdu);
    }

    public void setAddressRangeRegExp(String address_range) throws RESyntaxException
    {
        if (address_range == null || address_range.equals("*") || address_range.equals(""))
        {
            address_range = "[:alnum:]*";
        }
        address_range_regexp = new RE(address_range);
        logger.info("Made RE for " + address_range);
    }

    public boolean addressIsServicedByReceiver(String address)
    {
        if (StringUtils.isBlank(address))
            return true;
        else
        	return address_range_regexp.match(address);
    }
}
