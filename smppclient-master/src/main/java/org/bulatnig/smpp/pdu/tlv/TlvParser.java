package org.bulatnig.smpp.pdu.tlv;

import org.bulatnig.smpp.util.ByteBuffer;

import java.util.Map;

/**
 * TLV parser.
 *
 * @author Bulat Nigmatullin
 */
public interface TlvParser {

    /**
     * Parse TLV's from bytes.
     *
     * @param bb byte buffer
     * @return map parameter tag to tlv, may be null
     * @throws TlvException tlv parsing error
     */
    Map<Integer, Tlv> parse(ByteBuffer bb) throws TlvException;

}
