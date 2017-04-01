package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * UnbindResp test.
 *
 * @author Bulat Nigmatullin
 */
public class UnbindRespTest {

    @Test
    public void bytesToObject() throws PduException {
        ByteBuffer sbb = new ByteBuffer();
        sbb.appendInt(16L);
        sbb.appendInt(2147483654L);
        sbb.appendInt(0L);
        sbb.appendInt(1111111111L);
        UnbindResp ur = new UnbindResp(sbb);
        assertEquals(CommandId.UNBIND_RESP, ur.getCommandId());
        assertEquals(CommandStatus.ESME_ROK, ur.getCommandStatus());
        assertEquals(1111111111L, ur.getSequenceNumber());
    }

    @Test
    public void objectToBytes() throws PduException {
        UnbindResp ur = new UnbindResp();
        ur.setCommandStatus(CommandStatus.ESME_RINVDLNAME);
        ur.setSequenceNumber(4294967295L);
        assertEquals("000000108000000600000034ffffffff", ur.buffer().hexDump());
    }
}
