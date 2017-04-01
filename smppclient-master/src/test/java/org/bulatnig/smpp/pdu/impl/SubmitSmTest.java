package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.Npi;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.Ton;
import org.bulatnig.smpp.util.ByteBuffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * SubmitSm test.
 *
 * @author Bulat Nigmatullin
 */
public class SubmitSmTest {

    @Test
    public void bytesToObject() throws PduException {
        ByteBuffer bb = new ByteBuffer();
        bb.appendInt(82);
        bb.appendInt(4);
        bb.appendInt(21);
        bb.appendInt(123987465L);
        bb.appendCString("typo");
        bb.appendByte((short)2);
        bb.appendByte((short)11);
        bb.appendCString("sender");
        bb.appendByte((short)3);
        bb.appendByte((short)6);
        bb.appendCString("receiver with long");
        bb.appendByte((short)14);
        bb.appendByte((short)15);
        bb.appendByte((short)16);
        bb.appendCString("");
        bb.appendCString("");
        bb.appendByte((short)17);
        bb.appendByte((short)18);
        bb.appendByte((short)19);
        bb.appendByte((short)20);
        bb.appendByte((short)21);
        bb.appendString("aaaaaaabbbbbbbsssssss");
        SubmitSm submit = new SubmitSm(bb);
        assertEquals(CommandStatus.ESME_RINVSERTYP, submit.getCommandStatus());
        assertEquals(123987465L, submit.getSequenceNumber());
        assertEquals("typo", submit.getServiceType());
        assertEquals(Ton.NATIONAL, submit.getSourceAddrTon());
        assertEquals(11, submit.getSourceAddrNpi());
        assertEquals("sender", submit.getSourceAddr());
        assertEquals(Ton.NETWORK_SPECIFIC, submit.getDestAddrTon());
        assertEquals(Npi.LAND_MOBILE, submit.getDestAddrNpi());
        assertEquals("receiver with long", submit.getDestinationAddr());
        assertEquals(14, submit.getEsmClass());
        assertEquals(15, submit.getProtocolId());
        assertEquals(16, submit.getPriorityFlag());
        assertEquals(null, submit.getScheduleDeliveryTime());
        assertEquals(null, submit.getValidityPeriod());
        assertEquals(17, submit.getRegisteredDelivery());
        assertEquals(18, submit.getReplaceIfPresentFlag());
        assertEquals(19, submit.getDataCoding());
        assertEquals(20, submit.getSmDefaultMsgId());
        assertEquals("aaaaaaabbbbbbbsssssss", new String(submit.getShortMessage()));
    }

    @Test
    public void objectToBytes() throws PduException {
        SubmitSm submit = new SubmitSm();
        assertEquals("000000210000000400000000000000000000000000000000000000000000000000",
                submit.buffer().hexDump());
    }

    @Test
    public void parseNull() throws PduException {
        SubmitSm submit = new SubmitSm();
        new DefaultPduParser().parse(submit.buffer());
    }
}
