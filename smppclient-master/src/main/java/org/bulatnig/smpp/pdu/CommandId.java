package org.bulatnig.smpp.pdu;

/**
 * Standard PDU identificators.
 *
 * @author Bulat Nigmatullin
 */
public enum CommandId {
    INSTANCE;

    /**
     * This is a generic negative acknowledgement to an SMPP PDU submitted with
     * an invalid message header. A generic_nack response is returned in the
     * following cases:<br/> • Invalid command_length<br/> If the receiving
     * SMPP entity, on decoding an SMPP PDU, detects an invalid command_length
     * (either too short or too long), it should assume that the data is
     * corrupt. In such cases a generic_nack PDU must be returned to the message
     * originator. • Unknown command_id<br/> If an unknown or invalid
     * command_id is received, a generic_nack PDU must also be returned to the
     * originator.
     */
    public static final long GENERIC_NACK = 0x80000000L;
    /**
     * An ESME bound as a Receiver is authorised to receive short messages from
     * the SMSC and to return the corresponding SMPP message responses to the
     * SMSC.
     */
    public static final long BIND_RECEIVER = 0x00000001L;
    /**
     * BindReceiver Response PDU.
     */
    public static final long BIND_RECEIVER_RESP = 0x80000001L;
    /**
     * An ESME bound as a Transmitter is authorised to send short messages to
     * the SMSC and to receive the corresponding SMPP responses from the SMSC.
     * An ESME indicates its desire not to receive (mobile) originated messages
     * from other SME’s (e.g. mobile stations) by binding as a Transmitter.
     */
    public static final long BIND_TRANSMITTER = 0x00000002L;
    /**
     * BindTransmitter Response PDU.
     */
    public static final long BIND_TRANSMITTER_RESP = 0x80000002L;
    /**
     * This command is issued by the ESME to query the status of a previously
     * submitted short message.<br/> The matching mechanism is based on the
     * SMSC assigned message_id and source address. Where the original
     * submit_sm, data_sm or submit_multi ‘source address’ was defaulted to
     * NULL, then the source address in the query_sm command should also be set
     * to NULL.
     */
    public static final long QUERY_SM = 0x00000003L;
    /**
     * QuerySM Response PDU.
     */
    public static final long QUERY_SM_RESP = 0x80000003L;
    /**
     * This operation is used by an ESME to submit a short message to the SMSC
     * for onward transmission to a specified short message entity (SME). The
     * submit_sm PDU does not support the transaction message mode.
     */
    public static final long SUBMIT_SM = 0x00000004L;
    /**
     * SubmitSM Response PDU.
     */
    public static final long SUBMIT_SM_RESP = 0x80000004L;
    /**
     * The deliver_sm is issued by the SMSC to send a message to an ESME. Using
     * this command,the SMSC may route a short message to the ESME for delivery.
     * <br/>
     * <p/>
     * In addition the SMSC uses the deliver_sm operation to transfer the
     * following types of short messages to the ESME:-<br/> • SMSC Delivery
     * Receipt. A delivery receipt relating to a a message which had been
     * previously submitted with the submit_sm operation and the ESME had
     * requested a delivery receipt via the registered_delivery parameter. The
     * delivery receipt data relating to the original short message will be
     * included in the short_message field of the deliver_sm. (Reference
     * Appendix B for an example Delivery Receipt format.)<br/> • SME Delivery
     * Acknowledgement. The user data of the SME delivery acknowledgement is
     * included in the short_message field of the deliver_sm<br/> • SME
     * Manual/User Acknowledgement. The user data of the SME delivery
     * acknowledgement is included in the short_message field of the deliver_sm
     * <br/> • Intermediate Notification.
     */
    public static final long DELIVER_SM = 0x00000005L;
    /**
     * DeliverSM Response PDU.
     */
    public static final long DELIVER_SM_RESP = 0x80000005L;
    /**
     * The purpose of the SMPP unbind operation is to deregister an instance of
     * an ESME from the SMSC and inform the SMSC that the ESME no longer wishes
     * to use this network connection for the submission or delivery of
     * messages.<br/> Thus, the unbind operation may be viewed as a form of
     * SMSC logoff request to close the current SMPP session.
     */
    public static final long UNBIND = 0x00000006L;
    /**
     * Unbind Response PDU.
     */
    public static final long UNBIND_RESP = 0x80000006L;
    /**
     * This command is issued by the ESME to replace a previously submitted
     * short message that is still pending delivery. The matching mechanism is
     * based on the message_id and source address of the original message.<br/>
     * <p/>
     * Where the original submit_sm ‘source address’ was defaulted to NULL, then
     * the source address in the replace_sm command should also be NULL
     */
    public static final long REPLACE_SM = 0x00000007L;
    /**
     * ReplaceSM Response PDU.
     */
    public static final long REPLACE_SM_RESP = 0x80000007L;
    /**
     * This command is issued by the ESME to cancel one or more previously
     * submitted short messages that are still pending delivery. The command may
     * specify a particular message to cancel, or all messages for a particular
     * source, destination and service_type are to be cancelled.<br/> • If the
     * message_id is set to the ID of a previously submitted message, then
     * provided the source address supplied by the ESME matches that of the
     * stored message, that message will be cancelled.<br/> • If the message_id
     * is NULL, all outstanding undelivered messages with matching source and
     * destination addresses given in the PDU are cancelled. If provided,
     * service_type is included in this matching.<br/> Where the original
     * submit_sm, data_sm or submit_multi ‘source address’ was defaulted to
     * NULL, then the source address in the cancel_sm command should also be
     * NULL.
     */
    public static final long CANCEL_SM = 0x00000008L;
    /**
     * CancelSM Response PDU.
     */
    public static final long CANCEL_SM_RESP = 0x80000008L;
    /**
     * An ESME bound as a Transceiver is allowed to send messages to the SMSC
     * and receive messages from the SMSC over a single SMPP session.
     */
    public static final long BIND_TRANSCEIVER = 0x00000009L;
    /**
     * BindTransceiver Response PDU.
     */
    public static final long BIND_TRANSCEIVER_RESP = 0x80000009L;
    /**
     * This operation is used by the SMSC to signal an ESME to originate a
     * bind_receiver request to the SMSC.
     */
    public static final long OUTBIND = 0x0000000BL;
    /**
     * This message can be sent by either the ESME or SMSC and is used to
     * provide a confidencecheck of the communication path between an ESME and
     * an SMSC. On receipt of this request the receiving party should respond
     * with an enquire_link_resp, thus verifying that the application level
     * connection between the SMSC and the ESME is functioning. The ESME may
     * also respond by sending any valid SMPP primitive.
     */
    public static final long ENQUIRE_LINK = 0x00000015L;
    /**
     * EnquireLink Response PDU.
     */
    public static final long ENQUIRE_LINK_RESP = 0x80000015L;
    /**
     * The submit_multi operation may be used to submit an SMPP message for
     * delivery to multiple recipients or to one or more Distribution Lists. The
     * submit_multi PDU does not support the transaction message mode.
     */
    public static final long SUBMIT_MULTI = 0x00000021L;
    /**
     * SubmitMulti Response PDU.
     */
    public static final long SUBMIT_MULTI_RESP = 0x80000021L;
    /**
     * This message is sent by the SMSC to the ESME, when the SMSC has detected
     * that a particular mobile subscriber has become available and a delivery
     * pending flag had been set for that subscriber from a previous data_sm
     * operation.<br/>
     * <p/>
     * It may be used for example to trigger a data content ‘Push’ to the
     * subscriber from a WAP Proxy Server.<br/>
     * <p/>
     * Note: There is no alert_notification_resp PDU.
     */
    public static final long ALERT_NOTIFICATION = 0x00000102L;
    /**
     * This command is used to transfer data between the SMSC and the ESME. It
     * may be used by both the ESME and SMSC.<br/>
     * <p/>
     * This command is an alternative to the submit_sm and deliver_sm commands.
     * It is introduced as a new command to be used by interactive applications
     * such as those provided via a WAP framework.<br/>
     * <p/>
     * The ESME may use this command to request the SMSC to transfer a message
     * to an MS. The SMSC may also use this command to transfer an MS originated
     * message to an ESME.
     * <p/>
     * <br/> In addition, the data_sm operation can be used to transfer the
     * following types of special messages to the ESME:-<br/> • SMSC Delivery
     * Receipt.<br/> • SME Delivery Acknowledgement.The user data of the SME
     * delivery acknowledgement is included in the short_message field of the
     * data_sm<br/> •SME Manual/User Acknowledgement. The user data of the SME
     * delivery acknowledgement is included in the short_message field of the
     * data_sm<br/> • Intermediate Notification.<br/>
     */
    public static final long DATA_SM = 0x00000103L;
    /**
     *
     */
    public static final long DATA_SM_RESP = 0x80000103L;

}
