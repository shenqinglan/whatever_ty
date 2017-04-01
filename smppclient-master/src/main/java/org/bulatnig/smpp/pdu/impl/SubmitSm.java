package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * This operation is used by an ESME to submit a short message to the SMSC for
 * onward transmission to a specified short message entity (SME). The submit_sm
 * PDU does not support the transaction message mode.
 *
 * @author Bulat Nigmatullin
 */
public class SubmitSm extends AbstractPdu {

    /**
     * The service_type parameter can be used to indicate the SMS Application
     * service associated with the message. Specifying the service_type allows
     * the ESME to • avail of enhanced messaging services such as “replace by
     * service” type • to control the teleservice used on the air interface. Set
     * to NULL for default SMSC settings.
     */
    private String serviceType;
    /**
     * Type of Number for source address. If not known, set to NULL (Unknown).
     */
    private int sourceAddrTon;
    /**
     * Numbering Plan Indicator for source address. If not known, set to NULL
     * (Unknown).
     */
    private int sourceAddrNpi;
    /**
     * Address of SME which originated this message. If not known, set to NULL
     * (Unknown).
     */
    private String sourceAddr;
    /**
     * Type of Number for destination.
     */
    private int destAddrTon;
    /**
     * Numbering Plan Indicator for destination.
     */
    private int destAddrNpi;
    /**
     * Destination address of this short message. For mobile terminated
     * messages, this is the directory number of the recipient MS.
     */
    private String destinationAddr;
    /**
     * Indicates Message Mode & Message Type.
     */
    private int esmClass;
    /**
     * Protocol Identifier. Network specific field.
     */
    private int protocolId;
    /**
     * Designates the priority level of the message.
     */
    private int priorityFlag;
    /**
     * The short message is to be scheduled by the SMSC for delivery. Set to
     * NULL for immediate message delivery.
     */
    private String scheduleDeliveryTime;
    /**
     * The validity period of this message. Set to NULL to request the SMSC
     * default validity period.
     */
    private String validityPeriod;
    /**
     * Indicator to signify if an SMSC delivery receipt or an SME
     * acknowledgement is required.
     */
    private int registeredDelivery;
    /**
     * Flag indicating if submitted message should replace an existing message.
     */
    private int replaceIfPresentFlag;
    /**
     * Defines the encoding scheme of the short message user data.
     */
    private int dataCoding;
    /**
     * Indicates the short message to send from a list of predefined (‘canned’)
     * short messages stored on the SMSC. If not using an SMSC canned message,
     * set to NULL.
     */
    private int smDefaultMsgId;
    /**
     * Up to 254 octets of short message user data. The exact physical limit for
     * short_message size may vary according to the underlying network.<br/>
     * <p/>
     * Applications which need to send messages longer than 254 octets should
     * use the message_payload parameter. In this case the sm_length field
     * should be set to zero.<br/>
     * <p/>
     * Note: The short message data should be inserted in either the
     * short_message or message_payload fields. Both fields must not be used
     * simultaneously.
     */
    private byte[] shortMessage;

    public SubmitSm() {
        super(CommandId.SUBMIT_SM);
    }

    SubmitSm(ByteBuffer bb) throws PduException {
        super(bb);
        try {
            serviceType = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Service type parsing failed.", e);
        }
        sourceAddrTon = bb.removeByte();
        sourceAddrNpi = bb.removeByte();
        try {
            sourceAddr = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Service type parsing failed.", e);
        }
        destAddrTon = bb.removeByte();
        destAddrNpi = bb.removeByte();
        try {
            destinationAddr = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Service type parsing failed.", e);
        }
        esmClass = bb.removeByte();
        protocolId = bb.removeByte();
        priorityFlag = bb.removeByte();
        try {
            scheduleDeliveryTime = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Service type parsing failed.", e);
        }
        try {
            validityPeriod = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Service type parsing failed.", e);
        }
        registeredDelivery = bb.removeByte();
        replaceIfPresentFlag = bb.removeByte();
        dataCoding = bb.removeByte();
        smDefaultMsgId = bb.removeByte();
        int shortMessageLength = bb.removeByte();
        shortMessage = bb.removeBytes(shortMessageLength);
    }

    @Override
    protected ByteBuffer body() {
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(serviceType);
        bb.appendByte(sourceAddrTon);
        bb.appendByte(sourceAddrNpi);
        bb.appendCString(sourceAddr);
        bb.appendByte(destAddrTon);
        bb.appendByte(destAddrNpi);
        bb.appendCString(destinationAddr);
        bb.appendByte(esmClass);
        bb.appendByte(protocolId);
        bb.appendByte(priorityFlag);
        bb.appendCString(scheduleDeliveryTime);
        bb.appendCString(validityPeriod);
        bb.appendByte(registeredDelivery);
        bb.appendByte(replaceIfPresentFlag);
        bb.appendByte(dataCoding);
        bb.appendByte(smDefaultMsgId);
        if (shortMessage != null) {
            bb.appendByte(shortMessage.length);
            bb.appendBytes(shortMessage);
        } else {
            bb.appendByte(0);
        }
        return bb;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public int getDestAddrTon() {
        return destAddrTon;
    }

    public void setDestAddrTon(int destAddrTon) {
        this.destAddrTon = destAddrTon;
    }

    public int getDestAddrNpi() {
        return destAddrNpi;
    }

    public void setDestAddrNpi(int destAddrNpi) {
        this.destAddrNpi = destAddrNpi;
    }

    public String getDestinationAddr() {
        return destinationAddr;
    }

    public void setDestinationAddr(String destinationAddr) {
        this.destinationAddr = destinationAddr;
    }

    public int getEsmClass() {
        return esmClass;
    }

    public void setEsmClass(int esmClass) {
        this.esmClass = esmClass;
    }

    public int getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(int protocolId) {
        this.protocolId = protocolId;
    }

    public int getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(int priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public String getScheduleDeliveryTime() {
        return scheduleDeliveryTime;
    }

    public void setScheduleDeliveryTime(String scheduleDeliveryTime) {
        this.scheduleDeliveryTime = scheduleDeliveryTime;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public int getRegisteredDelivery() {
        return registeredDelivery;
    }

    public void setRegisteredDelivery(int registeredDelivery) {
        this.registeredDelivery = registeredDelivery;
    }

    public int getReplaceIfPresentFlag() {
        return replaceIfPresentFlag;
    }

    public void setReplaceIfPresentFlag(int replaceIfPresentFlag) {
        this.replaceIfPresentFlag = replaceIfPresentFlag;
    }

    public int getDataCoding() {
        return dataCoding;
    }

    public void setDataCoding(int dataCoding) {
        this.dataCoding = dataCoding;
    }

    public int getSmDefaultMsgId() {
        return smDefaultMsgId;
    }

    public void setSmDefaultMsgId(int smDefaultMsgId) {
        this.smDefaultMsgId = smDefaultMsgId;
    }

    public byte[] getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(byte[] shortMessage) {
        this.shortMessage = shortMessage;
    }
}
