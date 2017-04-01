package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.PduParser;
import org.bulatnig.smpp.util.ByteBuffer;
import org.junit.Test;

/**
 * DefaultPduParser test.
 *
 * @author Bulat Nigmatullin
 */
public class DefaultPduParserTest {

    private final PduParser parser = new DefaultPduParser();

    @Test(expected = PduException.class)
    public void unknownPdu() throws PduException {
        final ByteBuffer bb = new ByteBuffer();
        bb.appendInt(16);
        bb.appendInt(0);
        bb.appendInt(0);
        bb.appendInt(0);

        parser.parse(bb);
    }

    @Test(expected = PduException.class)
    public void wrongBody() throws PduException {
        final ByteBuffer bb = new ByteBuffer();
        bb.appendInt(22);
        bb.appendInt(CommandId.ALERT_NOTIFICATION);
        bb.appendInt(0);
        bb.appendInt(0);
        bb.appendByte(0);
        bb.appendByte(0);
        bb.appendByte(1);
        bb.appendByte(2);
        bb.appendByte(3);
        bb.appendByte(0);

        parser.parse(bb);
    }

}
