package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * DeliverSm response PDU.
 *
 * @author Bulat Nigmatullin
 */
public class DeliverSmResp extends AbstractPdu {

    public DeliverSmResp() {
        super(CommandId.DELIVER_SM_RESP);
    }

    DeliverSmResp(ByteBuffer bb) throws PduException {
        super(bb);
        try {
            bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Message id parsing failed.", e);
        }
    }

    @Override
    protected ByteBuffer body() {
        return new ByteBuffer().appendByte(0);
    }
}
