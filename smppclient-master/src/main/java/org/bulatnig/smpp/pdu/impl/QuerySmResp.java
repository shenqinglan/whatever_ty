package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * QuerySM response PDU.
 *
 * @author Bulat Nigmatullin
 */
public class QuerySmResp extends AbstractPdu {

    /**
     * SMSC Message ID of the message whose state is being queried.
     */
    private String messageId;
    /**
     * Date and time when the queried message reached a final state. For
     * messages which have not yet reached a final state this field will contain
     * a single NULL octet.
     */
    private String finalDate;
    /**
     * Specifies the status of the queried short message.
     */
    private int messageState;
    /**
     * Where appropriate this holds a network error code defining the reason for
     * failure of message delivery.
     */
    private int errorCode;

    public QuerySmResp() {
        super(CommandId.QUERY_SM_RESP);
    }

    protected QuerySmResp(ByteBuffer bb) throws PduException {
        super(bb);
        try {
            messageId = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Message id parsing failed.", e);
        }
        try {
            finalDate = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Final date parsing failed.", e);
        }
        messageState = bb.removeByte();
        errorCode = bb.removeByte();
    }

    @Override
    protected ByteBuffer body() {
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(messageId);
        bb.appendCString(finalDate);
        bb.appendByte(messageState);
        bb.appendByte(errorCode);
        return bb;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public int getMessageState() {
        return messageState;
    }

    public void setMessageState(int messageState) {
        this.messageState = messageState;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
