package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.PduParser;
import org.bulatnig.smpp.pdu.tlv.ParameterTag;
import org.bulatnig.smpp.util.ByteBuffer;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * BindTransceiverResp test.
 *
 * @author Bulat Nigmatullin
 */
public class BindTransceiverRespTest {

    private final PduParser parser = new DefaultPduParser();

    @Test
    public void bytesToObject() throws PduException {
        ByteBuffer bb = new ByteBuffer();
        bb.appendInt(37L);
        bb.appendInt(2147483657L);
        bb.appendInt(0);
        bb.appendInt(2000123456L);
        bb.appendCString("Rome is the cap");
        bb.appendShort(0x0210);
        bb.appendShort(1);
        bb.appendByte((short) 0);
        BindTransceiverResp btr = (BindTransceiverResp)parser.parse(bb);
        assertEquals(CommandId.BIND_TRANSCEIVER_RESP, btr.getCommandId());
        assertEquals(CommandStatus.ESME_ROK, btr.getCommandStatus());
        assertEquals(2000123456L, btr.getSequenceNumber());
        assertEquals("Rome is the cap", btr.getSystemId());
        assertArrayEquals(new byte[]{0}, btr.tlvs.get(ParameterTag.SC_INTERFACE_VERSION).getValue());
    }

    @Test
    public void objectToBytes() throws PduException {
        BindTransceiverResp btr = new BindTransceiverResp();
        btr.setCommandStatus(CommandStatus.ESME_ROK);
        btr.setSequenceNumber(123456789L);
        btr.setSystemId("COMMANDOS!");
//        btr.setScInterfaceVersion(new ScInterfaceVersion((short)100));
//        assertEquals("000000208000000900000000075bcd15434f4d4d414e444f5321000210000164", btr.buffer().hexDump());
        assertEquals("0000001b8000000900000000075bcd15434f4d4d414e444f532100", btr.buffer().hexDump());
    }
}
