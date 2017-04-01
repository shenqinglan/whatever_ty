package org.bulatnig.smpp.util;

import org.bulatnig.smpp.SmppException;

/**
 * C-Octet Sring terminating null character not found.
 *
 * @author Bulat Nigmatullin
 */
public class TerminatingNullNotFoundException extends SmppException {

    public TerminatingNullNotFoundException() {
        super("C-Octet String terminating zero not found.");
    }

}
