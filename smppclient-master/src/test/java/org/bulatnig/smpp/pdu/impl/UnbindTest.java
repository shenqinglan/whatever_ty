package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unbind test.
 *
 * @author Bulat Nigmatullin
 */
public class UnbindTest {

    @Test
    public void bytesToObject() throws PduException {
        ByteBuffer sbb = new ByteBuffer();
        sbb.appendInt(16L);
        sbb.appendInt(6L);
        sbb.appendInt(88);
        sbb.appendInt(987654321L);
        Unbind u = new Unbind(sbb);
        assertEquals(CommandId.UNBIND, u.getCommandId());
        assertEquals(CommandStatus.ESME_RTHROTTLED, u.getCommandStatus());
        assertEquals(987654321L, u.getSequenceNumber());
    }

    @Test
    public void objectToBytes() throws PduException {
        Unbind u = new Unbind();
        u.setCommandStatus(CommandStatus.ESME_RSUBMITFAIL);
        u.setSequenceNumber(0);
        assertEquals("00000010000000060000004500000000", u.buffer().hexDump());
    }

}
