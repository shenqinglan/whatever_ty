package org.bulatnig.smpp.pdu.tlv;

/**
 * Standard TLV's Parameter Tags.
 *
 * @author Bulat Nigmatullin
 */
public enum ParameterTag {
    INSTANCE;

    /**
     * The dest_addr_subunit parameter is used to route messages when received
     * by a mobile station, for example to a smart card in the mobile station or
     * to an external device connected to the mobile station.
     */
    public static final int DEST_ADDR_SUBUNIT = 0x0005;
    /**
     * The dest_network_type parameter is used to indicate a network type
     * associated with the destination address of a message. In the case that
     * the receiving system (e.g. SMSC) does not support the indicated network
     * type, it may treat this a failure and return a response PDU reporting a
     * failure.
     */
    public static final int DEST_NETWORK_TYPE = 0x0006;
    /**
     * The dest_bearer_type parameter is used to request the desired bearer for
     * delivery of the message to the destination address. In the case that the
     * receiving system (e.g. SMSC) does not support the indicated bearer type,
     * it may treat this a failure and return a response PDU reporting a
     * failure.
     */
    public static final int DEST_BEARER_TYPE = 0x0007;
    /**
     * This parameter defines the telematic interworking to be used by the
     * delivering system for the destination address. This is only useful when a
     * specific dest_bearer_type parameter has also been specified as the value
     * is bearer dependent. In the case that the receiving system (e.g. SMSC)
     * does not support the indicated telematic interworking, it may treat this
     * a failure and return a response PDU reporting a failure.
     */
    public static final int DEST_TELEMATICS_ID = 0x0008;
    /**
     * The source_addr_subunit parameter is used to indicate where a message
     * originated in the mobile station, for example a smart card in the mobile
     * station or an external device connected to the mobile station.
     */
    public static final int SOURCE_ADDR_SUBUNIT = 0x000D;
    /**
     * The source_network_type parameter is used to indicate the network type
     * associated with the device that originated the message.
     */
    public static final int SOURCE_NETWORK_TYPE = 0x000E;
    /**
     * The source_bearer_type parameter indicates the wireless bearer over which
     * the message originated.
     */
    public static final int SOURCE_BEARER_TYPE = 0x000F;
    /**
     * The source_telematics_id parameter indicates the type of telematics
     * interface over which the message originated.
     */
    public static final int SOURCE_TELEMATICS_ID = 0x0010;
    /**
     * This parameter defines the number of seconds which the sender requests
     * the SMSC to keep the message if undelivered before it is deemed expired
     * and not worth delivering. If the parameter is not present, the SMSC may
     * apply a default value.
     */
    public static final int QOS_TIME_TO_LIVE = 0x0017;
    /**
     * The payload_type parameter defines the higher layer PDU type contained in
     * the message payload.
     */
    public static final int PAYLOAD_TYPE = 0x0019;
    /**
     * The payload_type parameter defines the higher layer PDU type contained in
     * the message payload.
     */
    public static final int ADDITIONAL_STATUS_INFO_TEXT = 0x001D;
    /**
     * The receipted_message_id parameter indicates the ID of the message being
     * receipted in an SMSC Delivery Receipt. This is the opaque SMSC message
     * identifier that was returned in the message_id parameter of the SMPP
     * response PDU that acknowledged the submission of the original message.
     */
    public static final int RECEIPTED_MESSAGE_ID = 0x001E;
    /**
     * The ms_msg_wait_facilities parameter allows an indication to be provided
     * to an MS that there are messages waiting for the subscriber on systems on
     * the PLMN. The indication can be an icon on the MS screen or other MMI
     * indication.<br/>
     *
     * The ms_msg_wait_facilities can also specify the type of message
     * associated with the message waiting indication.
     */
    public static final int MS_MSG_WAIT_FACILITIES = 0x0030;
    /**
     * The privacy_indicator indicates the privacy level of the message.
     */
    public static final int PRIVACY_INDICATOR = 0x0201;
    /**
     * The source_subaddress parameter specifies a subaddress associated with
     * the originator of the message.
     */
    public static final int SOURCE_SUBADDRESS = 0x0202;
    /**
     * The dest_subaddress parameter specifies a subaddress associated with the
     * destination of the message.
     */
    public static final int DEST_SUBADDRESS = 0x0203;
    /**
     * A reference assigned by the originating SME to the short message.
     */
    public static final int USER_MESSAGE_REFERENCE = 0x0204;
    /**
     * A response code set by the user in a User Acknowledgement/Reply message.
     * The response codes are application specific.
     */
    public static final int USER_RESPONSE_CODE = 0x0205;
    /**
     * The source_port parameter is used to indicate the application port number
     * associated with the source address of the message.
     */
    public static final int SOURCE_PORT = 0x020A;
    /**
     * The destination_port parameter is used to indicate the application port
     * number associated with the destination address of the message.
     */
    public static final int DESTINATION_PORT = 0x020B;
    /**
     * The sar_msg_ref_num parameter is used to indicate the reference number
     * for a particular concatenated short message.
     */
    public static final int SAR_MSG_REF_NUM = 0x020C;
    /**
     * The language_indicator parameter is used to indicate the language of the
     * short message.
     */
    public static final int LANGUAGE_INDICATOR = 0x020D;
    /**
     * The sar_total_segments parameter is used to indicate the total number of
     * short messages within the concatenated short message.
     */
    public static final int SAR_TOTAL_SEGMENTS = 0x020E;
    /**
     * The sar_segment_seqnum parameter is used to indicate the sequence number
     * of a particular short message within the concatenated short message.
     */
    public static final int SAR_SEGMENT_SEQNUM = 0x020F;
    /**
     * The sc_interface_version parameter is used to indicate the SMPP version
     * supported by the SMSC. It is returned in the bind response PDUs.
     */
    public static final int SC_INTERFACE_VERSION = 0x0210;
    /**
     * callback_num_pres_ind.
     */
    public static final int CALLBACK_NUM_PRES_IND = 0x0302;
    /**
     * The callback_num_atag parameter associates an alphanumeric display with
     * the call back number.
     */
    public static final int CALLBACK_NUM_ATAG = 0x0303;
    /**
     * The number_of_messages parameter is used to indicate the number of
     * messages stored in a mailbox.
     */
    public static final int NUMBER_OF_MESSAGES = 0x0304;
    /**
     * The callback_num parameter associates a call back number with the
     * message. In TDMA networks, it is possible to send and receive multiple
     * callback numbers to/from TDMA mobile stations.
     */
    public static final int CALLBACK_NUM = 0x0381;
    /**
     * The dpf_result parameter is used in the data_sm_resp PDU to indicate if
     * delivery pending flag (DPF) was set for a delivery failure of the short
     * message..<br/>
     *
     * If the dpf_result parameter is not included in the data_sm_resp PDU, the
     * ESME should assume that DPF is not set.<br/>
     *
     * Currently this parameter is only applicable for the Transaction message
     * mode.
     */
    public static final int DPF_RESULT = 0x0420;
    /**
     * An ESME may use the set_dpf parameter to request the setting of a
     * delivery pending flag (DPF) for certain delivery failure scenarios, such
     * as<br/> - MS is unavailable for message delivery (as indicated by the
     * HLR)<br/>
     *
     * The SMSC should respond to such a request with an alert_notification PDU
     * when it detects that the destination MS has become available.<br/>
     *
     * The delivery failure scenarios under which DPF is set is SMSC
     * implementation and network implementation specific. If a delivery pending
     * flag is set by the SMSC or network (e.g. HLR), then the SMSC should
     * indicate this to the ESME in the data_sm_resp message via the dpf_result
     * parameter.
     */
    public static final int SET_DPF = 0x0421;
    /**
     * The ms_availability_status parameter is used in the alert_notification
     * operation to indicate the availability state of the MS to the ESME.<br/>
     *
     * If the SMSC does not include the parameter in the alert_notification
     * operation, the ESME should assume that the MS is in an “available” state.
     */
    public static final int MS_AVAILABILITY_STATUS = 0x0422;
    /**
     * The network_error_code parameter is used to indicate the actual network
     * error code for a delivery failure. The network error code is technology
     * specific.
     */
    public static final int NETWORK_ERROR_CODE = 0x0423;
    /**
     * The message_payload parameter contains the user data.
     */
    public static final int MESSAGE_PAYLOAD = 0x0424;
    /**
     * The delivery_failure_reason parameter is used in the data_sm_resp
     * operation to indicate the outcome of the message delivery attempt (only
     * applicable for transaction message mode). If a delivery failure due to a
     * network error is indicated, the ESME may check the network_error_code
     * parameter (if present) for the actual network error code.<br/>
     *
     * The delivery_failure_reason parameter is not included if the delivery
     * attempt was successful.
     */
    public static final int DELIVERY_FAILURE_REASON = 0x0425;
    /**
     * The more_messages_to_send parameter is used by the ESME in the submit_sm
     * and data_sm operations to indicate to the SMSC that there are further
     * messages for the same destination SME. The SMSC may use this setting for
     * network resource optimization.
     */
    public static final int MORE_MESSAGES_TO_SEND = 0x0426;
    /**
     * The message_state optional parameter is used by the SMSC in the
     * deliver_sm and data_sm PDUs to indicate to the ESME the final message
     * state for an SMSC Delivery Receipt.
     */
    public static final int MESSAGE_STATE = 0x0427;
    /**
     * The ussd_service_op parameter is required to define the USSD service
     * operation when SMPP is being used as an interface to a (GSM) USSD system.
     */
    public static final int USSD_SERVICE_OP = 0x0501;
    /**
     * The display_time parameter is used to associate a display time of the
     * short message on the MS.
     */
    public static final int DISPLAY_TIME = 0x1201;
    /**
     * The sms_signal parameter is used to provide a TDMA MS with alert tone
     * information associated with the received short message.
     */
    public static final int SMS_SIGNAL = 0x1203;
    /**
     * The ms_validity parameter is used to provide an MS with validity
     * information associated with the received short message.
     */
    public static final int MS_VALIDITY = 0x1204;
    /**
     * The alert_on_message_delivery parameter is set to instruct a MS to alert
     * the user (in a MS implementation specific manner) when the short message
     * arrives at the MS.
     */
    public static final int ALERT_ON_MESSAGE_DELIVERY = 0x130C;
    /**
     * The its_reply_type parameter is a required parameter for the CDMA
     * Interactive Teleservice as defined by the Korean PCS carriers [KORITS].
     * It indicates and controls the MS user’s reply method to an SMS delivery
     * message received from the ESME.
     */
    public static final int ITS_REPLY_TYPE = 0x1380;
    /**
     * The its_session_info parameter is a required parameter for the CDMA
     * Interactive Teleservice as defined by the Korean PCS carriers [KORITS].
     * It contains control information for the interactive session between an MS
     * and an ESME.
     */
    public static final int ITS_SESSION_INFO = 0x1383;

}
