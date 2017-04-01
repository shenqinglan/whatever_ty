package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * BindTransceiver response PDU.
 *
 * @author Bulat Nigmatullin
 */
public class BindTransceiverResp extends AbstractPdu {

    /**
     * SMSC identifier. Identifies the SMSC to the ESME.
     */
    private String systemId;

    public BindTransceiverResp() {
        super(CommandId.BIND_TRANSCEIVER_RESP);
    }

    BindTransceiverResp(ByteBuffer bb) throws PduException {
        super(bb);
        try {
            systemId = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("System id parsing failed.", e);
        }
    }

    @Override
    protected ByteBuffer body() {
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(systemId);
        return bb;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
