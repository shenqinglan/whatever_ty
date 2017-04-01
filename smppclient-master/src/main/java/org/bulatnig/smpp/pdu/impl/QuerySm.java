package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * This command is issued by the ESME to query the status of a previously
 * submitted short message.<br/>
 * <p/>
 * The matching mechanism is based on the SMSC assigned message_id and source
 * address. Where the original submit_sm, data_sm or submit_multi ‘source
 * address’ was defaulted to NULL, then the source address in the query_sm
 * command should also be set to NULL.
 *
 * @author Bulat Nigmatullin
 */
public class QuerySm extends AbstractPdu {

    /**
     * Message ID of the message whose state is to be queried. This must be the
     * SMSC assigned Message ID allocated to the original short message when
     * submitted to the SMSC by the submit_sm, data_sm or submit_multi command,
     * and returned in the response PDU by the SMSC.
     */
    private String messageId;
    /**
     * Type of Number of message originator. This is used for verification
     * purposes, and must match that supplied in the original request PDU (e.g.
     * submit_sm). If not known, set to NULL.
     */
    private int sourceAddrTon;
    /**
     * Numbering Plan Identity of message originator. This is used for
     * verification purposes, and must match that supplied in the original
     * request PDU (e.g. submit_sm). If not known, set to NULL.
     */
    private int sourceAddrNpi;
    /**
     * Address of message originator. This is used for verification purposes,
     * and must match that supplied in the original request PDU (e.g.
     * submit_sm). If not known, set to NULL.
     */
    private String sourceAddr;

    public QuerySm() {
        super(CommandId.QUERY_SM);
    }

    protected QuerySm(ByteBuffer bb) throws PduException {
        super(bb);
        try {
            messageId = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Message id parsing failed.", e);
        }
        sourceAddrTon = bb.removeByte();
        sourceAddrNpi = bb.removeByte();
        try {
            sourceAddr = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Source address parsing failed.", e);
        }
    }

    @Override
    protected ByteBuffer body() {
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(messageId);
        bb.appendByte(sourceAddrTon);
        bb.appendByte(sourceAddrNpi);
        bb.appendCString(sourceAddr);
        return bb;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getSourceAddrTon() {
        return sourceAddrTon;
    }

    public void setSourceAddrTon(int sourceAddrTon) {
        this.sourceAddrTon = sourceAddrTon;
    }

    public int getSourceAddrNpi() {
        return sourceAddrNpi;
    }

    public void setSourceAddrNpi(int sourceAddrNpi) {
        this.sourceAddrNpi = sourceAddrNpi;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }
}
