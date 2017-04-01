package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;

/**
 * Unbind Response PDU.
 *
 * @author Bulat Nigmatullin
 */
public class UnbindResp extends AbstractPdu {

    public UnbindResp() {
        super(CommandId.UNBIND_RESP);
    }

    UnbindResp(ByteBuffer bb) throws PduException {
        super(bb);
    }

    @Override
    protected ByteBuffer body() {
        return null;
    }

}
