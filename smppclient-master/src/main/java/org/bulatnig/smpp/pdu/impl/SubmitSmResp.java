package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * SubmitSm response PDU.
 *
 * @author Bulat Nigmatullin
 */
public class SubmitSmResp extends AbstractPdu {

    /**
     * This field contains the SMSC message ID of the submitted message. It may
     * be used at a later stage to query the status of a message, cancel or
     * replace the message.
     */
    private String messageId;

    public SubmitSmResp() {
        super(CommandId.SUBMIT_SM_RESP);
    }

    SubmitSmResp(ByteBuffer bb) throws PduException {
        super(bb);
        if (CommandStatus.ESME_ROK == getCommandStatus()) {
            try {
                messageId = bb.removeCString();
            } catch (TerminatingNullNotFoundException e) {
                throw new PduException("Message id parsing failed.", e);
            }
        } else if (bb.length() == 1) {
            // Some SMSC ignore protocol and add null messageId value. This is the workaround for them.
            bb.removeByte();
        }
    }

    @Override
    protected ByteBuffer body() {
        if (CommandStatus.ESME_ROK != getCommandStatus())
            return null;
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(messageId);
        return bb;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
