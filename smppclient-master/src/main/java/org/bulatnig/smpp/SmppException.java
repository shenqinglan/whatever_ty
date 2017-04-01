package org.bulatnig.smpp;

/**
 * General SMPP exception.
 * 
 * @author Bulat Nigmatullin
 */
public class SmppException extends Exception {

    public SmppException() {
        super();
    }

    public SmppException(String message) {
        super(message);
    }

    public SmppException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmppException(Throwable cause) {
        super(cause);
    }
}
