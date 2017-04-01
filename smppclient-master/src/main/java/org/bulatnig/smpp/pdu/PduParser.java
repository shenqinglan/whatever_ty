package org.bulatnig.smpp.pdu;

import org.bulatnig.smpp.util.ByteBuffer;

/**
 * Parses PDU's from bytes.
 *
 * @author Bulat Nigmatullin
 */
public interface PduParser {

    /**
     * Parse PDU.
     *
     * @param bb one pdu bytes
     * @return Pdu
     * @throws PduException pdu parsing failed
     */
    Pdu parse(ByteBuffer bb) throws PduException;

}
