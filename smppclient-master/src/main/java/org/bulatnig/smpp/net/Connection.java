package org.bulatnig.smpp.net;

import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.PduParser;

import java.io.IOException;

/**
 * Connection with SMPP entity. Converts bytes to PDU and PDU to bytes. The same
 * connection may be reused many times.
 *
 * @author Bulat Nigmatullin
 */
public interface Connection {

    /**
     * Incoming PDU packets parser.
     *
     * @param parser parser instance
     */
    void setParser(PduParser parser);

    /**
     * Open connection.
     *
     * @throws IOException connection failed
     */
    void open() throws IOException;

    /**
     * Read PDU from input. Blocks until PDU received or exception throwed.
     *
     * @return PDU
     * @throws PduException parsing error
     * @throws IOException  I/O error
     */
    Pdu read() throws PduException, IOException;

    /**
     * Write PDU to output.
     *
     * @param pdu PDU for sending
     * @throws org.bulatnig.smpp.pdu.PduException
     *                     PDU to bytes converting error
     * @throws IOException I/O error
     */
    void write(Pdu pdu) throws PduException, IOException;

    /**
     * Close connection.
     */
    void close();

}
