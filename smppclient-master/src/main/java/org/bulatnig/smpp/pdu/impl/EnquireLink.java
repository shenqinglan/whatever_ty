package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;

/**
 * This message can be sent by either the ESME or SMSC and is used to provide a
 * confidencecheck of the communication path between an ESME and an SMSC. On
 * receipt of this request the receiving party should respond with an
 * enquire_link_resp, thus verifying that the application level connection
 * between the SMSC and the ESME is functioning. The ESME may also respond by
 * sending any valid SMPP primitive.
 *
 * @author Bulat Nigmatullin
 */
public class EnquireLink extends AbstractPdu {

    public EnquireLink() {
        super(CommandId.ENQUIRE_LINK);
    }

    protected EnquireLink(ByteBuffer bb) throws PduException {
        super(bb);
    }

    @Override
    protected ByteBuffer body() {
        return null;
    }

}
