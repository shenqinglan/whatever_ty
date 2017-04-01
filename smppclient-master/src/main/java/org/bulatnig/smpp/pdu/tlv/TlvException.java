package org.bulatnig.smpp.pdu.tlv;

import org.bulatnig.smpp.SmppException;

/**
 * TLV parsing failed.
 *
 * @author Bulat Nigmatullin
 */
public class TlvException extends SmppException {

    public TlvException() {
        super();
    }

    public TlvException(String message) {
        super(message);
    }

    public TlvException(String message, Throwable cause) {
        super(message, cause);
    }

    public TlvException(Throwable cause) {
        super(cause);
    }
}
