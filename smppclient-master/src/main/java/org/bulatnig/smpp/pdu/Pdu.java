package org.bulatnig.smpp.pdu;

import org.bulatnig.smpp.util.ByteBuffer;

/**
 * Protocol Data Unit.
 *
 * @author Bulat Nigmatullin
 */
public interface Pdu {

    /**
     * Header length.
     */
    public static final int HEADER_LENGTH = 16;

    /**
     * Calculate and return PDU bytes.
     *
     * @return  pdu bytes
     * @throws PduException pdu contains wrong values
     */
    public ByteBuffer buffer() throws PduException;

    public long getCommandId();

    public long getCommandStatus();

    public long getSequenceNumber();

    public void setCommandStatus(long commandStatus);

    public void setSequenceNumber(long sequenceNumber);

}
