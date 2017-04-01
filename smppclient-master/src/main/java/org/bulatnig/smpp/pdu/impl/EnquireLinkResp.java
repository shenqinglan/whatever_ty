package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;

/**
 * EnquireLink response PDU.
 *
 * @author Bulat Nigmatullin
 */
public class EnquireLinkResp extends AbstractPdu {

    public EnquireLinkResp() {
        super(CommandId.ENQUIRE_LINK_RESP);
    }

    protected EnquireLinkResp(ByteBuffer bb) throws PduException {
        super(bb);
    }

    @Override
    protected ByteBuffer body() {
        return null;
    }

}
