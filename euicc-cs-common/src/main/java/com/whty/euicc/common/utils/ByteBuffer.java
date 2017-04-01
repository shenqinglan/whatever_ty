// Copyright (C) 2012 WHTY
package com.whty.euicc.common.utils;

import com.whty.euicc.common.exception.NotEnoughDataException;
import com.whty.euicc.common.exception.TerminatingZeroNotFoundException;

import java.io.UnsupportedEncodingException;

/**
 * @author ysl
 */
public class ByteBuffer {

    private byte[] buffer;
    private static final byte SZ_BYTE = 1;
    private static final byte SZ_SHORT = 2;
    private static final byte SZ_INT = 4;
    private static final byte SZ_LONG = 8;
    private static byte[] zero;

    // American Standard Code for Information Interchange
    public static final String ENC_ASCII = "ASCII";
    // Windows Latin-1
    public static final String ENC_CP1252 = "Cp1252";
    // ISO 8859-1, Latin alphabet No. 1
    public static final String ENC_ISO8859_1 = "ISO8859_1";
    /**
     * Sixteen-bit Unicode Transformation Format, big-endian byte order with
     * byte-order mark
     */
    public static final String ENC_UTF16_BEM = "UnicodeBig";
    /**
     * Sixteen-bit Unicode Transformation Format, big-endian byte order
     */
    public static final String ENC_UTF16_BE = "UnicodeBigUnmarked";
    /**
     * Sixteen-bit Unicode Transformation Format, little-endian byte order with
     * byte-order mark
     */
    public static final String ENC_UTF16_LEM = "UnicodeLittle";
    /**
     * Sixteen-bit Unicode Transformation Format, little-endian byte order
     */
    public static final String ENC_UTF16_LE = "UnicodeLittleUnmarked";
    /**
     * Eight-bit Unicode Transformation Format
     */
    public static final String ENC_UTF8 = "UTF8";
    /**
     * Sixteen-bit Unicode Transformation Format, byte order specified by a
     * mandatory initial byte-order mark
     */
    public static final String ENC_UTF16 = "UTF-16";
    /**
     * GSM 7-bit unpacked Requires JVM 1.4 or later
     *
     */
    public static final String ENC_GSM7BIT = "X-Gsm7Bit";

    static {
        zero = new byte[1];
        zero[0] = 0;
    }

    public ByteBuffer() {
        buffer = null;
    }

    public ByteBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }


    public int length() {
        if (buffer == null) {
            return 0;
        }
        else {
            return buffer.length;
        }
    }

    public void appendByte(byte data) {
        byte[] byteBuf = new byte[SZ_BYTE];
        byteBuf[0] = data;
        appendBytes0(byteBuf, SZ_BYTE);
    }

    public void appendShort(short data) {
        byte[] shortBuf = new byte[SZ_SHORT];
        shortBuf[1] = (byte) (data & 0xff);
        shortBuf[0] = (byte) ((data >>> 8) & 0xff);
        appendBytes0(shortBuf, SZ_SHORT);
    }

    public void appendInt(int data) {
        byte[] intBuf = new byte[SZ_INT];
        intBuf[3] = (byte) (data & 0xff);
        intBuf[2] = (byte) ((data >>> 8) & 0xff);
        intBuf[1] = (byte) ((data >>> 16) & 0xff);
        intBuf[0] = (byte) ((data >>> 24) & 0xff);
        appendBytes0(intBuf, SZ_INT);
    }

    public void appendLong(long data) {
        byte[] intBuf = new byte[SZ_LONG];
        intBuf[7] = (byte) (data & 0xff);
        intBuf[6] = (byte) ((data >>> 8) & 0xff);
        intBuf[5] = (byte) ((data >>> 16) & 0xff);
        intBuf[4] = (byte) ((data >>> 24) & 0xff);
        intBuf[3] = (byte) ((data >>> 32) & 0xff);
        intBuf[2] = (byte) ((data >>> 40) & 0xff);
        intBuf[1] = (byte) ((data >>> 48) & 0xff);
        intBuf[0] = (byte) ((data >>> 56) & 0xff);
        appendBytes0(intBuf, SZ_LONG);
    }

    public void appendCString(String string) {
        try {
            appendString0(string, true, ENC_UTF8);
        }
        catch (UnsupportedEncodingException e) {
            try {
                appendString0(string, true, ENC_ASCII);
            }
            catch (UnsupportedEncodingException e2) {
                ; // this can't happen as we use ASCII encoding
                // whatever is in the buffer it gets interpreted as ascii
            }
        }
    }

    public void appendCString(String string, String encoding)
        throws UnsupportedEncodingException {
        appendString0(string, true, encoding);
    }

    public void appendString(String string) {
        try {
            appendString(string, ENC_UTF8);
        }
        catch (UnsupportedEncodingException e) {
            try {
                appendString(string, ENC_ASCII);
            }
            catch (UnsupportedEncodingException e2) {
               ; // this can't happen as we use ASCII encoding
                // whatever is in the buffer it gets interpreted as ascii
            }
        }
    }

    public void appendString(String string, String encoding)
        throws UnsupportedEncodingException {
        appendString0(string, false, encoding);
    }

    public void appendString(String string, int count) {
        try {
            appendString(string, count, ENC_UTF8);
        }
        catch (UnsupportedEncodingException e) {
            try {
                appendString(string, count, ENC_ASCII);
            }
            catch (UnsupportedEncodingException e2) {
               ; // this can't happen as we use ASCII encoding
                // whatever is in the buffer it gets interpreted as ascii
            }
        }
    }

    public void appendString(String string, int count, String encoding)
        throws UnsupportedEncodingException {
        String subStr = string.substring(0, count);
        appendString0(subStr, false, encoding);
    }

    private void appendString0(String string, boolean isCString,
        String encoding) throws UnsupportedEncodingException {
        if ((string != null) && (string.length() > 0)) {
            byte[] stringBuf = null;
            if (encoding != null) {
                stringBuf = string.getBytes(encoding);
            }
            else {
                stringBuf = string.getBytes();
            }
            if ((stringBuf != null) && (stringBuf.length > 0)) {
                appendBytes0(stringBuf, stringBuf.length);
            }
        }
        if (isCString) {
            appendBytes0(zero, 1); // always append terminating zero
        }
    }

    public void appendBuffer(ByteBuffer buf) {
        if (buf != null) {
            try {
                appendBytes(buf, buf.length());
            }
            catch (NotEnoughDataException e) {
               ; // can't happen as appendBytes only complains
                // when count>buf.length
            }
        }
    }

    public void appendBytes(ByteBuffer bytes, int count)
        throws NotEnoughDataException {
        if (count > 0) {
            if (bytes == null) {
                throw new NotEnoughDataException(0, count);
            }
            if (bytes.length() < count) {
                throw new NotEnoughDataException(bytes.length(), count);
            }
            appendBytes0(bytes.getBuffer(), count);
        }
    }

    public void appendBytes(byte[] bytes, int count1) {
        int count = count1;
        if (bytes != null) {
            if (count > bytes.length) {
                count = bytes.length;
            }
            appendBytes0(bytes, count);
        }
    }

    public void appendBytes(byte[] bytes) {
        if (bytes != null) {
            appendBytes0(bytes, bytes.length);
        }
    }

    public byte removeByte() throws NotEnoughDataException {
        byte result = 0;
        byte[] resBuff = removeBytes(SZ_BYTE).getBuffer();
        result = resBuff[0];

        return result;
    }

    public short removeShort() throws NotEnoughDataException {
        short result = 0;
        byte[] resBuff = removeBytes(SZ_SHORT).getBuffer();
        result |= resBuff[0] & 0xff;
        result <<= 8;
        result |= resBuff[1] & 0xff;
        return result;
    }

    public int removeInt() throws NotEnoughDataException {
        int result = readInt();
        removeBytes0(SZ_INT);
        return result;
    }

    public int readInt() throws NotEnoughDataException {
        int result = 0;
        int len = length();
        if (len >= SZ_INT) {
            result |= buffer[0] & 0xff;
            result <<= 8;
            result |= buffer[1] & 0xff;
            result <<= 8;
            result |= buffer[2] & 0xff;
            result <<= 8;
            result |= buffer[3] & 0xff;
            return result;
        }
        else {
            throw new NotEnoughDataException(len, 4);
        }
    }

    public long removeLong() throws NotEnoughDataException {
        long result = readLong();
        removeBytes0(SZ_LONG);
        return result;
    }

    public long readLong() throws NotEnoughDataException {
        long result = 0;
        int len = length();
        if (len >= SZ_LONG) {
            result |= buffer[0] & 0xff;
            result <<= 8;
            result |= buffer[1] & 0xff;
            result <<= 8;
            result |= buffer[2] & 0xff;
            result <<= 8;
            result |= buffer[3] & 0xff;
            result <<= 8;
            result |= buffer[4] & 0xff;
            result <<= 8;
            result |= buffer[5] & 0xff;
            result <<= 8;
            result |= buffer[6] & 0xff;
            result <<= 8;
            result |= buffer[7] & 0xff;
            return result;
        }
        else {
            throw new NotEnoughDataException(len, 4);
        }
    }

    public String removeCString() throws NotEnoughDataException,
            TerminatingZeroNotFoundException {
        int len = length();
        int zeroPos = 0;
        if (len == 0) {
            throw new NotEnoughDataException(0, 1);
        }
        while ((zeroPos < len) && (buffer[zeroPos] != 0)) {
            zeroPos++;
        }
        if (zeroPos < len) { // found terminating zero
            String result = null;
            if (zeroPos > 0) {
                try {
                    result = new String(buffer, 0, zeroPos, ENC_UTF8);
                }
                catch (UnsupportedEncodingException e) {
                    try {
                        result = new String(buffer, 0, zeroPos, ENC_ASCII);
                    }
                    catch (UnsupportedEncodingException e2) {
                       ; // this can't happen as we use ASCII encoding
                        // whatever is in the buffer it gets interpreted as
                        // ascii
                    }
                }
            }
            else {
                result = "";
            }
            removeBytes0(zeroPos + 1);
            return result;
        }
        else {
            throw new TerminatingZeroNotFoundException();
        }
    }

    public String removeString(int size, String encoding)
        throws NotEnoughDataException, UnsupportedEncodingException {
        int len = length();
        if (len < size) {
            throw new NotEnoughDataException(len, size);
        }
        UnsupportedEncodingException encodingException = null;
        String result = null;
        if (len > 0) {
            try {
                if (encoding != null) {
                    result = new String(buffer, 0, size, encoding);
                }
                else {
                    result = new String(buffer, 0, size);
                }
            }
            catch (UnsupportedEncodingException e) {
                encodingException = e;
            }
            removeBytes0(size);
        }
        else {
            result = "";
        }
        if (encodingException != null) {
            throw encodingException;
        }
        return result;
    }

    public ByteBuffer removeBuffer(int count) throws NotEnoughDataException {
        return removeBytes(count);
    }

    public ByteBuffer removeBytes(int count) throws NotEnoughDataException {
        ByteBuffer result = readBytes(count);
        removeBytes0(count);
        return result;
    }

    // just removes bytes from the buffer and doesnt return anything
    public void removeBytes0(int count1) throws NotEnoughDataException {
        int count = count1;
        if (count < 0) {
            count = Integer.MAX_VALUE;
        }
        int len = length();
        int lefts = len - count;
        if (lefts > 0) {
            byte[] newBuf = new byte[lefts];
            System.arraycopy(buffer, count, newBuf, 0, lefts);
            setBuffer(newBuf);
        }
        else {
            setBuffer(null);
        }
    }

    public ByteBuffer readBytes(int count) throws NotEnoughDataException {
        int len = length();
        ByteBuffer result = null;
        if (count > 0) {
            if (len >= count) {
                byte[] resBuf = new byte[count];
                System.arraycopy(buffer, 0, resBuf, 0, count);
                // 从数组buffer中的第一个元素复制起，复制到resBuf中，从第一个元素起，复制count个
                result = new ByteBuffer(resBuf);
                return result;
            }
            else {
                throw new NotEnoughDataException(len, count);
            }
        }
        else {
            return result; // just null as wanted count = 0
        }
    }

    // everything must be checked before calling this method
    // and count > 0
    private void appendBytes0(byte[] bytes, int count) {
        int len = length();
        byte[] newBuf = new byte[len + count];
        if (len > 0) {
            System.arraycopy(buffer, 0, newBuf, 0, len);
        }
        System.arraycopy(bytes, 0, newBuf, len, count);
        setBuffer(newBuf);
    }

    public String getHexDump() {
        StringBuffer dump = new StringBuffer(255);
        try {
            int dataLen = length();
            byte[] buffer = getBuffer();
            for (int i = 0; i < dataLen; i++) {
                dump.append(Character.forDigit((buffer[i] >> 4) & 0x0f, 16))
                        .append(Character.forDigit(buffer[i] & 0x0f, 16));
            }
        }
/*
  		catch (Throwable t) {//sonar 严重违规
            // catch everything as this is for debug
*/
        catch (Exception e) {
            dump.append("Throwable caught when dumping = ");
            dump.append(e);
        }
        return dump.toString().toUpperCase();
    }
}
