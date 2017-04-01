package com.whty.smpp.socket.util;

import java.io.UnsupportedEncodingException;

import com.whty.smpp.socket.exception.TlvConvertException;
import com.whty.smpp.socket.message.Tlv;
/**
 * 
 * @ClassName TlvUtil
 * @author Administrator
 * @date 2017-3-10 下午1:44:53
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class TlvUtil {

    /**
     * Writes a variable length C-String (null terminated) TLV.  If the String
     * is null this method will only write out the NULL byte (0x00) TLV. Uses
     * ISO-8859-1 as the charset to convert the String into bytes.
     * @param tag The TLV tag value
     * @param value The String to include in the TLV value
     * @return A new TLV
     * @throws TlvConvertException Thrown if there is an error during conversion
     *      of the value into TLV.
     */
    static public Tlv createNullTerminatedStringTlv(short tag, String value) throws TlvConvertException {
        return createNullTerminatedStringTlv(tag, value, "ISO-8859-1");
    }

    /**
     * Writes a variable length C-String (null terminated) TLV.  If the String
     * is null this method will only write out the NULL byte (0x00) TLV.
     * @param tag The TLV tag value
     * @param value The String to include in the TLV value
     * @param charsetName The charsetName to use to convert the String into bytes
     * @return A new TLV
     * @throws TlvConvertException Thrown if there is an error during conversion
     *      of the value into TLV.
     */
    static public Tlv createNullTerminatedStringTlv(short tag, String value, String charsetName) throws TlvConvertException {
        try {
            if (value == null) {
                value = "";
            }
            // convert the string into a byte array
            byte[] bytes = value.getBytes(charsetName);
            // create a new byte array with a null byte
            byte[] bytes0 = new byte[bytes.length+1];
            System.arraycopy(bytes, 0, bytes0, 0, bytes.length);
            return new Tlv(tag, bytes0);
        } catch (UnsupportedEncodingException e) {
            throw new TlvConvertException("String", "unsupported charset " + e.getMessage());
        }
    }

    /**
     * Writes a fixed length String TLV with null bytes filling the remaining
     * part of the byte array.  For example, if "1" is the String and the fixed
     * length is 4, then the internal value of the TLV would be "31000000".
     * @param tag The TLV tag value
     * @param value The String to include in the TLV value
     * @param fixedLength The fixed length of the byte array value of the TLV
     * @return A new TLV
     * @throws TlvConvertException Thrown if there is an error during conversion
     *      of the value into TLV.
     */
    static public Tlv createFixedLengthStringTlv(short tag, String value, int fixedLength) throws TlvConvertException {
        return createFixedLengthStringTlv(tag, value, fixedLength, "ISO-8859-1");
    }

    /**
     * Writes a fixed length String TLV with null bytes filling the remaining
     * part of the byte array.  For example, if "1" is the String and the fixed
     * length is 4, then the internal value of the TLV would be "31000000".
     * @param tag The TLV tag value
     * @param value The String to include in the TLV value
     * @param fixedLength The fixed length of the byte array value of the TLV
     * @param charsetName The charsetName to use to convert the String into bytes
     * @return A new TLV
     * @throws TlvConvertException Thrown if there is an error during conversion
     *      of the value into TLV.
     */
    static public Tlv createFixedLengthStringTlv(short tag, String value, int fixedLength, String charsetName) throws TlvConvertException {
        try {
            if (value == null) {
                value = "";
            }
            // convert the string into a byte array
            byte[] bytes = value.getBytes(charsetName);
            // is this string longer than the fixed length?
            if (bytes.length > fixedLength) {
                throw new TlvConvertException("String", "length exceeds fixed length ["+fixedLength+"]");
            }
            // do we need to pad the string?
            if (bytes.length < fixedLength) {
                 // create a new byte array with a null byte
                byte[] bytes0 = new byte[fixedLength];
                System.arraycopy(bytes, 0, bytes0, 0, bytes.length);
                bytes = bytes0; // use the new array
            }
            return new Tlv(tag, bytes);
        } catch (UnsupportedEncodingException e) {
            throw new TlvConvertException("String", "unsupported charset " + e.getMessage());
        }
    }

}
