package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.Npi;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.Ton;
import org.bulatnig.smpp.util.ByteBuffer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DeliverSm test.
 *
 * @author Bulat Nigmatullin
 */
public class DeliverSmTest {

    @Test
    public void bytesToObject() throws PduException {
        ByteBuffer sbb = new ByteBuffer();
        sbb.appendInt(82);
        sbb.appendInt(5);
        sbb.appendInt(21);
        sbb.appendInt(123987465L);
        sbb.appendCString("typo");
        sbb.appendByte((short) 1);
        sbb.appendByte((short) 3);
        sbb.appendCString("sender");
        sbb.appendByte((short) 2);
        sbb.appendByte((short) 4);
        sbb.appendCString("receiver with long");
        sbb.appendByte((short) 14);
        sbb.appendByte((short) 15);
        sbb.appendByte((short) 16);
        sbb.appendCString("");
        sbb.appendCString("");
        sbb.appendByte((short) 17);
        sbb.appendByte((short) 18);
        sbb.appendByte((short) 19);
        sbb.appendByte((short) 20);
        sbb.appendByte((short) 21);
        sbb.appendString("aaaaaaabbbbbbbsssssss");
        DeliverSm deliver = new DeliverSm(sbb);
        assertEquals(CommandStatus.ESME_RINVSERTYP, deliver.getCommandStatus());
        assertEquals(123987465L, deliver.getSequenceNumber());
        assertEquals("typo", deliver.getServiceType());
        assertEquals(Ton.INTERNATIONAL, deliver.getSourceAddrTon());
        assertEquals(Npi.DATA, deliver.getSourceAddrNpi());
        assertEquals("sender", deliver.getSourceAddr());
        assertEquals(Ton.NATIONAL, deliver.getDestAddrTon());
        assertEquals(Npi.TELEX, deliver.getDestAddrNpi());
        assertEquals("receiver with long", deliver.getDestinationAddr());
        assertEquals(14, deliver.getEsmClass());
        assertEquals(15, deliver.getProtocolId());
        assertEquals(16, deliver.getPriorityFlag());
        assertNull(deliver.getScheduleDeliveryTime());
        assertNull(deliver.getValidityPeriod());
        assertEquals(17, deliver.getRegisteredDelivery());
        assertEquals(18, deliver.getReplaceIfPresentFlag());
        assertEquals(19, deliver.getDataCoding());
        assertEquals(20, deliver.getSmDefaultMsgId());
        assertEquals("aaaaaaabbbbbbbsssssss", new String(deliver.getShortMessage()));
    }

    @Test
    public void objectToBytes() throws PduException {
        DeliverSm deliver = new DeliverSm();
        assertEquals("000000210000000500000000000000000000000000000000000000000000000000",
                deliver.buffer().hexDump());
    }
}
