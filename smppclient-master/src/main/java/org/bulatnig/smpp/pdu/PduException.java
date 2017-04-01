package org.bulatnig.smpp.pdu;

import org.bulatnig.smpp.SmppException;

/**
 * PDU parsing failed.
 *
 * @author Bulat Nigmatullin
 */
public class PduException extends SmppException {

    public PduException() {
        super();
    }

    public PduException(String message) {
        super(message);
    }

    public PduException(String message, Throwable cause) {
        super(message, cause);
    }

    public PduException(Throwable cause) {
        super(cause);
    }
}
