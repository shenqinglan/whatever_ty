package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * BindTransceiver test.
 *
 * @author Bulat Nigmatullin
 */
public class BindTransceiverTest {

    @Test
    public void bytesToObject() throws PduException {
        ByteBuffer bb = new ByteBuffer();
        bb.appendInt(65L);
        bb.appendInt(9L);
        bb.appendInt(194);
        bb.appendInt(666666L);
        bb.appendCString("text here");
        bb.appendCString("pasvordo");
        bb.appendCString("world");
        bb.appendByte((short) 100);
        bb.appendByte((short) 0);
        bb.appendByte((short) 0);
        bb.appendCString("adresatos poluchatos");
        BindTransceiver bt = new BindTransceiver(bb);
        assertEquals(CommandId.BIND_TRANSCEIVER, bt.getCommandId());
        assertEquals(CommandStatus.ESME_RINVPARLEN, bt.getCommandStatus());
        assertEquals(666666L, bt.getSequenceNumber());
        assertEquals("text here", bt.getSystemId());
        assertEquals("pasvordo", bt.getPassword());
        assertEquals("world", bt.getSystemType());
        assertEquals((short) 100, bt.getInterfaceVersion());
        assertEquals(0, bt.getAddrTon());
        assertEquals(0, bt.getAddrNpi());
        assertEquals("adresatos poluchatos", bt.getAddressRange());
    }

    @Test
    public void objectToBytes() throws PduException {
        BindTransceiver bt = new BindTransceiver();
        bt.setSystemId("kirpich");
        bt.setPassword(".$#/`~7");
        bt.setSystemType("-------");
        bt.setInterfaceVersion((short) 115);
        bt.setAddrTon(1);
        bt.setAddrNpi(0);
        bt.setAddressRange(")(*&^%$#@!");
        assertEquals("000000360000000900000000000000006b697270696368002e24232f607e37002d2d2d2d2d2d2d0073010029282a265e252423402100", bt.buffer().hexDump());
    }
}
