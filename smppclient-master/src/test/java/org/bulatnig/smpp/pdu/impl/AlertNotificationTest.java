package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.PduParser;
import org.bulatnig.smpp.pdu.tlv.ParameterTag;
import org.bulatnig.smpp.pdu.tlv.Tlv;
import org.bulatnig.smpp.pdu.tlv.impl.TlvImpl;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * AlertNotification test.
 *
 * @author Bulat Nigmatullin
 */
public class AlertNotificationTest {

    private final PduParser parser = new DefaultPduParser();

    @Test
    public void bytesToObject() throws TerminatingNullNotFoundException, PduException {
        ByteBuffer bb = new ByteBuffer();
        bb.appendInt(49L);
        bb.appendInt(CommandId.ALERT_NOTIFICATION);
        bb.appendInt(0);
        bb.appendInt(1000123456L);
        bb.appendByte((short) 0);
        bb.appendByte((short) 0);
        bb.appendCString("99501363400");
        bb.appendByte((short) 2);
        bb.appendByte((short) 1);
        bb.appendCString("destination");
        bb.appendShort(0x0422);
        bb.appendShort(1);
        bb.appendByte((short) 2);
        AlertNotification an = (AlertNotification)parser.parse(bb);

        assertEquals(CommandId.ALERT_NOTIFICATION, an.getCommandId());
        assertEquals(0, an.getCommandStatus());
        assertEquals(1000123456L, an.getSequenceNumber());
        assertEquals(0, an.getSourceAddrTon());
        assertEquals(0, an.getSourceAddrNpi());
        assertEquals("99501363400", an.getSourceAddr());
        assertEquals(2, an.getEsmeAddrTon());
        assertEquals(1, an.getEsmeAddrNpi());
        assertEquals("destination", an.getEsmeAddr());

        assertNotNull(an.tlvs);
        Tlv tlv = an.tlvs.get(0x0422);
        assertNotNull(tlv);
        assertArrayEquals(new byte[] {2}, tlv.getValue());
    }

    @Test
    public void objectToBytes() throws PduException {
        AlertNotification an = new AlertNotification();
        an.setCommandStatus(0);
        an.setSequenceNumber(115);
        an.setSourceAddrTon(1);
        an.setSourceAddrNpi(6);
        an.setSourceAddr("remarema");
        an.setEsmeAddrTon(0);
        an.setEsmeAddrNpi(18);
        an.setEsmeAddr("destmy");
        an.tlvs = new HashMap<Integer, Tlv>();
        Tlv tlv = new TlvImpl(ParameterTag.MS_AVAILABILITY_STATUS);
        tlv.setValue(new ByteBuffer().appendByte(0).array());
        an.tlvs.put(tlv.getTag(), tlv);
        assertEquals("00000029000001020000000000000073010672656d6172656d61000012646573746d79000422000100", an.buffer().hexDump());
    }
}
