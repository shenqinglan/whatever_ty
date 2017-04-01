package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.tlv.Tlv;
import org.bulatnig.smpp.pdu.tlv.TlvException;
import org.bulatnig.smpp.util.ByteBuffer;

import java.util.Map;

/**
 * PDU header.
 *
 * @author Bulat Nigmatullin
 */
public abstract class AbstractPdu implements Pdu {

    // lazy init tlv map
    public Map<Integer, Tlv> tlvs;

    private final long commandId;
    private long commandStatus;
    private long sequenceNumber;

    /**
     * Construct new PDU.
     *
     * @param commandId PDU command identificator
     */
    protected AbstractPdu(long commandId) {
        this.commandId = commandId;
        this.commandStatus = 0;
        this.sequenceNumber = 0;
    }

    /**
     * Parse PDU from bytes.
     *
     * @param bb pdu bytes
     */
    protected AbstractPdu(ByteBuffer bb) {
        bb.removeInt(); // PDU length value not stored
        commandId = bb.removeInt();
        commandStatus = bb.removeInt();
        sequenceNumber = bb.removeInt();
    }

    /**
     * Calculate and return PDU body bytes.
     *
     * @return body bytes, can be null
     */
    protected abstract ByteBuffer body();

    /**
     * Calculate and return PDU bytes.
     *
     * @return pdu bytes
     * @throws PduException pdu contains wrong values
     */
    @Override
    public ByteBuffer buffer() throws PduException {
        long length = HEADER_LENGTH;
        ByteBuffer body = body();
        if (body != null)
            length += body.length();
        ByteBuffer tlv = tlv();
        if (tlv != null)
            length += tlv.length();
        ByteBuffer bb = new ByteBuffer();
        bb.appendInt(length);
        bb.appendInt(commandId);
        bb.appendInt(commandStatus);
        bb.appendInt(sequenceNumber);
        if (body != null)
            bb.appendBytes(body.array());
        if (tlv != null)
            bb.appendBytes(tlv.array());
        return bb;
    }

    private ByteBuffer tlv() throws PduException {
        if (tlvs != null) {
            ByteBuffer result = new ByteBuffer();
            for (Tlv tlv : tlvs.values()) {
                try {
                    result.appendBytes(tlv.buffer().array());
                } catch (TlvException e) {
                    throw new PduException("Tlv to bytes parsing error.", e);
                }
            }
            return result;
        } else
            return null;
    }

    @Override
    public long getCommandId() {
        return commandId;
    }

    @Override
    public long getCommandStatus() {
        return commandStatus;
    }

    @Override
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public void setCommandStatus(long commandStatus) {
        this.commandStatus = commandStatus;
    }

    @Override
    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
