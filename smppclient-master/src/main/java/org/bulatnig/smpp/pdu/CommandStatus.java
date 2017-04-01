package org.bulatnig.smpp.pdu;

/**
 * Standard command statuses.
 * <p/>
 * The command_status field indicates the success or failure of an SMPP request.
 * It is relevant only in the SMPP response PDU and it must contain a NULL value
 * in an SMPP request PDU.
 *
 * @author Bulat Nigmatullin
 */
public enum CommandStatus {
    INSTANCE;

    /**
     * No Error.
     */
    public static final long ESME_ROK = 0x00000000L;
    /**
     * Message Length is invalid.
     */
    public static final long ESME_RINVMSGLEN = 0x00000001L;
    /**
     * Command Length is invalid.
     */
    public static final long ESME_RINVCMDLEN = 0x00000002L;
    /**
     * Invalid Command ID.
     */
    public static final long ESME_RINVCMDID = 0x00000003L;
    /**
     * Incorrect BIND Status for given command.
     */
    public static final long ESME_RINVBNDSTS = 0x00000004L;
    /**
     * ESME ALready in Bound State.
     */
    public static final long ESME_RALYBND = 0x00000005L;
    /**
     * Invalid Priority Flag.
     */
    public static final long ESME_RINVPRTFLG = 0x00000006L;
    /**
     * Invalid Registered Delivery Flag.
     */
    public static final long ESME_RINVREGDLVFLG = 0x00000007L;
    /**
     * System Error.
     */
    public static final long ESME_RSYSERR = 0x00000008L;
    /**
     * Invalid Source Address.
     */
    public static final long ESME_RINVSRCADR = 0x0000000AL;
    /**
     * Invalid Dest Addr.
     */
    public static final long ESME_RINVDSTADR = 0x0000000BL;
    /**
     * Message ID is invalid.
     */
    public static final long ESME_RINVMSGID = 0x0000000CL;
    /**
     * Bind Failed.
     */
    public static final long ESME_RBINDFAIL = 0x0000000DL;
    /**
     * Invalid Password.
     */
    public static final long ESME_RINVPASWD = 0x0000000EL;
    /**
     * Invalid System ID.
     */
    public static final long ESME_RINVSYSID = 0x0000000FL;
    /**
     * Cancel SM Failed.
     */
    public static final long ESME_RCANCELFAIL = 0x00000011L;
    /**
     * Replace SM Failed.
     */
    public static final long ESME_RREPLACEFAIL = 0x00000013L;
    /**
     * Message Queue Full.
     */
    public static final long ESME_RMSGQFUL = 0x00000014L;
    /**
     * Invalid Service Type.
     */
    public static final long ESME_RINVSERTYP = 0x00000015L;
    /**
     * Invalid number of destinations.
     */
    public static final long ESME_RINVNUMDESTS = 0x00000033L;
    /**
     * Invalid Distribution List name.
     */
    public static final long ESME_RINVDLNAME = 0x00000034L;
    /**
     * Destination flag is invalid (submit_multi).
     */
    public static final long ESME_RINVDESTFLAG = 0x00000040L;
    /**
     * Invalid ‘submit with replace’ request (i.e. submit_sm with
     * replace_if_present_flag set).
     */
    public static final long ESME_RINVSUBREP = 0x00000042L;
    /**
     * Invalid esm_class field data.
     */
    public static final long ESME_RINVESMCLASS = 0x00000043L;
    /**
     * Cannot Submit to Distribution List.
     */
    public static final long ESME_RCNTSUBDL = 0x00000044L;
    /**
     * submit_sm or submit_multi failed.
     */
    public static final long ESME_RSUBMITFAIL = 0x00000045L;
    /**
     * Invalid Source address TON.
     */
    public static final long ESME_RINVSRCTON = 0x00000048L;
    /**
     * Invalid Source address NPI.
     */
    public static final long ESME_RINVSRCNPI = 0x00000049L;
    /**
     * Invalid Destination address TON.
     */
    public static final long ESME_RINVDSTTON = 0x00000050L;
    /**
     * Invalid Destination address NPI.
     */
    public static final long ESME_RINVDSTNPI = 0x00000051L;
    /**
     * Invalid system_type field.
     */
    public static final long ESME_RINVSYSTYP = 0x00000053L;
    /**
     * Invalid replace_if_present flag.
     */
    public static final long ESME_RINVREPFLAG = 0x00000054L;
    /**
     * Invalid number of messages.
     */
    public static final long ESME_RINVNUMMSGS = 0x00000055L;
    /**
     * Throttling error (ESME has exceeded allowed message limits).
     */
    public static final long ESME_RTHROTTLED = 0x00000058L;
    /**
     * Invalid Scheduled Delivery Time.
     */
    public static final long ESME_RINVSCHED = 0x00000061L;
    /**
     * Invalid message validity period (Expiry time).
     */
    public static final long ESME_RINVEXPIRY = 0x00000062L;
    /**
     * Predefined Message Invalid or Not Found.
     */
    public static final long ESME_RINVDFTMSGID = 0x00000063L;
    /**
     * ESME Receiver Temporary App Error Code.
     */
    public static final long ESME_RX_T_APPN = 0x00000064L;
    /**
     * ESME Receiver Permanent App Error Code.
     */
    public static final long ESME_RX_P_APPN = 0x00000065L;
    /**
     * ESME Receiver Reject Message Error Code.
     */
    public static final long ESME_RX_R_APPN = 0x00000066L;
    /**
     * query_sm request failed.
     */
    public static final long ESME_RQUERYFAIL = 0x00000067L;
    /**
     * Error in the optional part of the PDU Body.
     */
    public static final long ESME_RINVOPTPARSTREAM = 0x000000C0L;
    /**
     * Optional Parameter not allowed.
     */
    public static final long ESME_ROPTPARNOTALLWD = 0x000000C1L;
    /**
     * Invalid Parameter Length.
     */
    public static final long ESME_RINVPARLEN = 0x000000C2L;
    /**
     * Expected Optional Parameter missing.
     */
    public static final long ESME_RMISSINGOPTPARAM = 0x000000C3L;
    /**
     * Invalid Optional Parameter Value.
     */
    public static final long ESME_RINVOPTPARAMVAL = 0x000000C4L;
    /**
     * Delivery Failure (used for data_sm_resp).
     */
    public static final long ESME_RDELIVERYFAILURE = 0x000000FEL;
    /**
     * Unknown Error.
     */
    public static final long ESME_RUNKNOWNERR = 0x000000FFL;

}
