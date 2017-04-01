package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.*;
import org.bulatnig.smpp.pdu.tlv.impl.DefaultTlvParser;
import org.bulatnig.smpp.pdu.tlv.TlvException;
import org.bulatnig.smpp.pdu.tlv.TlvParser;
import org.bulatnig.smpp.util.ByteBuffer;

/**
 * Default PDU parser implementation.
 *
 * @author Bulat Nigmatullin
 */
public class DefaultPduParser implements PduParser {

    public TlvParser tlvParser = new DefaultTlvParser();

    @Override
    public Pdu parse(ByteBuffer bb) throws PduException {
        final byte[] original = bb.array();
        long commandId = bb.readInt(4);
        AbstractPdu result;
        try {
            if (CommandId.SUBMIT_SM_RESP == commandId) {
                result = new SubmitSmResp(bb);
            } else if (CommandId.DELIVER_SM == commandId) {
                result = new DeliverSm(bb);
            } else if (CommandId.GENERIC_NACK == commandId) {
                result = new GenericNack(bb);
            } else if (CommandId.BIND_RECEIVER == commandId) {
                result = new BindReceiver(bb);
            } else if (CommandId.BIND_RECEIVER_RESP == commandId) {
                result = new BindReceiverResp(bb);
            } else if (CommandId.BIND_TRANSMITTER == commandId) {
                result = new BindTransmitter(bb);
            } else if (CommandId.BIND_TRANSMITTER_RESP == commandId) {
                result = new BindTransmitterResp(bb);
            } else if (CommandId.QUERY_SM == commandId) {
                result = new QuerySm(bb);
            } else if (CommandId.QUERY_SM_RESP== commandId) {
                result = new QuerySmResp(bb);
            } else if (CommandId.SUBMIT_SM == commandId) {
                result = new SubmitSm(bb);
            } else if (CommandId.DELIVER_SM_RESP == commandId) {
                result = new DeliverSmResp(bb);
            } else if (CommandId.UNBIND == commandId) {
                result = new Unbind(bb);
            } else if (CommandId.UNBIND_RESP == commandId) {
                result = new UnbindResp(bb);
            } else if (CommandId.BIND_TRANSCEIVER == commandId) {
                result = new BindTransceiver(bb);
            } else if (CommandId.BIND_TRANSCEIVER_RESP == commandId) {
                result = new BindTransceiverResp(bb);
            } else if (CommandId.ENQUIRE_LINK == commandId) {
                result = new EnquireLink(bb);
            } else if (CommandId.ENQUIRE_LINK_RESP == commandId) {
                result = new EnquireLinkResp(bb);
            } else if (CommandId.ALERT_NOTIFICATION == commandId) {
                result = new AlertNotification(bb);
            } else {
                throw new PduException("Corresponding PDU not found: " + bb.hexDump() + ".");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new PduException("Malformed PDU: " + new ByteBuffer(original).hexDump() + ".", e);
        }
        if (bb.length() > 0) {
            try {
                result.tlvs = tlvParser.parse(bb);
            } catch (TlvException e) {
                throw new PduException("TLV parsing failed. Malformed PDU: " + new ByteBuffer(original).hexDump() + ".", e);
            }
        }
        return result;
    }
}
