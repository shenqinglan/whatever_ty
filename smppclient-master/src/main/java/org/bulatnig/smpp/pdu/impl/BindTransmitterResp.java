package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * BindTransmitter response PDU.
 *
 * @author Bulat Nigmatullin
 */
public class BindTransmitterResp extends AbstractPdu {

    /**
     * SMSC identifier. Identifies the SMSC to the ESME.
     */
    private String systemId;

    public BindTransmitterResp() {
        super(CommandId.BIND_TRANSMITTER_RESP);
    }

    BindTransmitterResp(ByteBuffer bb) throws PduException {
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