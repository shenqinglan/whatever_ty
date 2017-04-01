package org.bulatnig.smpp.pdu.tlv;

import org.bulatnig.smpp.util.ByteBuffer;

/**
 * Optional Parameters are fields, which may be present in a message. Optional
 * Parameters provide a mechanism for the future introduction of new parameters,
 * as and when defined in future versions of the SMPP protocol.<br/>
 * <p/>
 * Optional Parameters must always appear at the end of a PDU , in the "Optional
 * Parameters" section of the SMPP PDU. However, they may be included in any
 * convenient order within the "Optional Parameters" section of the SMPP PDU and
 * need not be encoded in the order presented in this document.<br/>
 * <p/>
 * For a particular SMPP PDU, the ESME or SMSC may include some, all or none of
 * the defined optional parameters as required for the particular application
 * context. For example a paging system may only need to include the “callback
 * number” related optional parameters in a submit_sm operation.
 *
 * @author Bulat Nigmatullin
 */
public interface Tlv {

    /**
     * Header length in bytes.
     */
    public static final int HEADER_LENGTH = 4;

    /**
     * Calculate and return TLV bytes.
     *
     * @return  tlv bytes
     * @throws TlvException TLV contains wrong values
     */
    public ByteBuffer buffer() throws TlvException;

    /**
     * The Tag field is used to uniquely identify the particular optional
     * parameter in question.<br>
     * <p/>
     * The optional parameter Tag field is always 2 octets in length.
     *
     * @return tag id
     */
    public int getTag();

    /**
     * Return TLV value bytes.
     *
     * @return  value bytes
     */
    public byte[] getValue();

    /**
     * Set TLV value in bytes.
     *
     * @param valueBytes value bytes
     */
    public void setValue(byte[] valueBytes);
}
