package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * The deliver_sm is issued by the SMSC to send a message to an ESME. Using this
 * command,the SMSC may route a short message to the ESME for delivery.<br/>
 * <p/>
 * In addition the SMSC uses the deliver_sm operation to transfer the following
 * types of short messages to the ESME:-<br/> • SMSC Delivery Receipt. A
 * delivery receipt relating to a a message which had been previously submitted
 * with the submit_sm operation and the ESME had requested a delivery receipt
 * via the registered_delivery parameter. The delivery receipt data relating to
 * the original short message will be included in the short_message field of the
 * deliver_sm. (Reference Appendix B for an example Delivery Receipt format.)<br/> •
 * SME Delivery Acknowledgement. The user data of the SME delivery
 * acknowledgement is included in the short_message field of the deliver_sm<br/> •
 * SME Manual/User Acknowledgement. The user data of the SME delivery
 * acknowledgement is included in the short_message field of the deliver_sm<br/> •
 * Intermediate Notification.
 *
 * @author Bulat Nigmatullin
 */
public class DeliverSm extends AbstractPdu {

    /**
     * The service_type parameter can be used to indicate the SMS Application
     * service associated with the message.
     */
    private String serviceType;
    /**
     * Type of Number for source address. If not known, set to NULL (Unknown).
     */
    private int sourceAddrTon;
    /**
     * Numbering Plan Indicator for source. If not known, set to NULL (Unknown).
     */
    private int sourceAddrNpi;
    /**
     * Address of SME which originated this message. If not known, set to NULL
     * (Unknown).
     */
    private String sourceAddr;
    /**
     * Type of number of destination SME.
     */
    private int destAddrTon;
    /**
     * Numbering Plan Indicator of destination SME.
     */
    private int destAddrNpi;
    /**
     * Destination address of destination SME.
     */
    private String destinationAddr;
    /**
     * Indicates Message Type and enhanced network services.
     */
    private int esmClass;
    /**
     * Protocol Identifier. Network Specific Field.
     */
    private int protocolId;
    /**
     * Designates the priority level of the message.
     */
    private int priorityFlag;
    /**
     * This field is unused for deliver_sm. It must be set to NULL.
     */
    private String scheduleDeliveryTime;
    /**
     * This field is unused for deliver_sm It must be set to NULL.
     */
    private String validityPeriod;
    /**
     * Indicates if an ESME acknowledgement is required.
     */
    private int registeredDelivery;
    /**
     * Not used in deliver_sm. It must be set to NULL.
     */
    private int replaceIfPresentFlag;
    /**
     * Indicates the encoding scheme of the short message.
     */
    private int dataCoding;
    /**
     * Unused in deliver_sm. It must be set to NULL.
     */
    private int smDefaultMsgId;
    /**
     * Up to 254 octets of short message user data.<br/>
     * <p/>
     * When sending messages longer than 254 octets the message_payload
     * parameter should be used and the sm_length parameter should be set to
     * zero.<br/>
     * <p/>
     * Note: The message data should be inserted in either the short_message or
     * the message_payload parameters. Both parameters must not be used
     * simultaneously.
     */
    private byte[] shortMessage;

    public DeliverSm() {
        super(CommandId.DELIVER_SM);
    }

    DeliverSm(ByteBuffer bb) throws PduException {
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
