package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;

/**
 * This is a generic negative acknowledgement to an SMPP PDU submitted with an
 * invalid message header. A generic_nack response is returned in the following
 * cases:<br/> • Invalid command_length<br/> If the receiving SMPP entity, on
 * decoding an SMPP PDU, detects an invalid command_length (either too short or
 * too long), it should assume that the data is corrupt. In such cases a
 * generic_nack PDU must be returned to the message originator. • Unknown
 * command_id<br/> If an unknown or invalid command_id is received, a
 * generic_nack PDU must also be returned to the originator.
 *
 * @author Bulat Nigmatullin
 */
public class GenericNack extends AbstractPdu {

    public GenericNack() {
        super(CommandId.GENERIC_NACK);
    }

    GenericNack(ByteBuffer bb) throws PduException {
        super(bb);
    }

    @Override
    protected ByteBuffer body() {
        return null;
    }
}
