package org.bulatnig.smpp.util;

import java.io.UnsupportedEncodingException;

/**
 * Converts java types to byte array according to SMPP protocol.
 * You should remember, that all SMPP simple numeric types are unsigned,
 * but Java types are always signed.
 * <p/>
 * Not thread safe.
 *
 * @author Bulat Nigmatullin
 */
public class ByteBuffer {

    /**
     * Empty byte array.
     */
    private static final byte[] EMPTY = new byte[0];

    /**
     * SMPP NULL character to append at the end of C-Octet String.
     */
    private static final byte[] ZERO = new byte[]{0};


    /**
     * Byte buffer.
     */
    private byte[] buffer;

    /**
     * Create empty buffer.
     */
    public ByteBuffer() {
        buffer = EMPTY;
    }

    /**
     * Create buffer based on provided array.
     *
     * @param b byte array
     */
    public ByteBuffer(byte[] b) {
        buffer = b;
    }

    /**
     * Возвращает массив байтов.
     *
     * @return массив байтов
     */
    public byte[] array() {
        return buffer;
    }

    /**
     * Возвращает длину массива.
     *
     * @return длина массива
     */
    public int length() {
        return buffer.length;
    }

    /**
     * Добавляет байты в массив.
     *
     * @param bytes byte array
     * @return this buffer
     */
    public ByteBuffer appendBytes(byte[] bytes) {
        return appendBytes(bytes, bytes.length);
    }

    /**
     * Добавляет байты в массив.
     *
     * @param bytes  byte array
     * @param length bytes length to add
     * @return this buffer
     */
    public ByteBuffer appendBytes(byte[] bytes, int length) {
        byte[] newBuffer = new byte[buffer.length + length];
        System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
        System.arraycopy(bytes, 0, newBuffer, buffer.length, length);
        buffer = newBuffer;
        return this;
    }

    /**
     * Добавляет переменную типа byte в массив.
     * Значение переменной должно быть в диапазоне от 0 до 255 включительно.
     *
     * @param value byte value to be appended
     * @return this buffer
     * @throws IllegalArgumentException задан неверный параметр
     */
    public ByteBuffer appendByte(int value) throws IllegalArgumentException {
        if (value >= 0 && value < 256)
            appendBytes(new byte[]{(byte) value});
        else
            throw new IllegalArgumentException("Byte value should be between 0 and 255.");
        return this;
    }

    /**
     * Добавляет переменную типа short в массив.
     * Значение переменной должно быть в диапазоне от 0 до 65535 включительно.
     *
     * @param value short value to be appended
     * @return this buffer
     * @throws IllegalArgumentException задан неверный параметр
     */
    public ByteBuffer appendShort(int value) throws IllegalArgumentException {
        if (value >= 0 && value < 65536)
            appendBytes(new byte[]{(byte) (value >>> 8), (byte) value});
        else
            throw new IllegalArgumentException("Short value should be between 0 and 65535.");
        return this;
    }

    /**
     * Добавляет переменную типа int в массив.
     * Значение переменной должно быть в диапазоне от 0 до 4294967295 включительно.
     *
     * @param value short-переменная
     * @return this buffer
     * @throws IllegalArgumentException задан неверный параметр
     */
    public ByteBuffer appendInt(long value) throws IllegalArgumentException {
        if (value >= 0 && value < 4294967296L)
            appendBytes(new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value});
        else
            throw new IllegalArgumentException("Short value should be between 0 and 4294967295.");
        return this;
    }

    /**
     * Добавляет строку C-Octet String в массив.
     *
     * @param cstring строка типа C-Octet (по протоколу SMPP), may be null
     * @return this buffer
     */
    public ByteBuffer appendCString(String cstring) {
        if (cstring != null && cstring.length() > 0) {
            try {
                byte[] stringBuf = cstring.getBytes("US-ASCII");
                appendBytes(stringBuf);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("US-ASCII charset is not supported. Consult developer.", e);
            }
        }
        appendBytes(ZERO); // always append terminating ZERO
        return this;
    }

    /**
     * Append string using ASCII charset.
     *
     * @param string string value, may be null
     * @return this buffer
     */
    public ByteBuffer appendString(String string) {
        return appendString(string, "US-ASCII");
    }

    /**
     * Append string using charset name.
     * Note: UTF-16(UCS2) uses Byte Order Mark at the head of string and
     * it may be unsupported by your Operator. So you should consider using
     * UTF-16BE or UTF-16LE instead of UTF-16.
     *
     * @param string      encoded string, null allowed
     * @param charsetName encoding character set name
     * @return this buffer
     * @throws IllegalArgumentException wrong charset name
     */
    public ByteBuffer appendString(String string, String charsetName) {
        if (string != null && string.length() > 0) {
            try {
                byte[] stringBuf = string.getBytes(charsetName);
                appendBytes(stringBuf);
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("Wrong charset name provided.", e);
            }
        }
        return this;
    }

    /**
     * Read one byte from byte buffer
     *
     * @return byte was read
     * @throws IndexOutOfBoundsException out of buffer
     */
    public int readByte() {
        return buffer[0] & 0xFF;
    }

    /**
     * Read short value from buffer
     *
     * @return short value
     * @throws IndexOutOfBoundsException out of buffer
     */
    public int readShort() {
        int result = 0;
        result |= buffer[0] & 0xFF;
        result <<= 8;
        result |= buffer[1] & 0xFF;
        return result;
    }

    /**
     * Read int value from buffer
     *
     * @return int value
     * @throws IndexOutOfBoundsException out of buffer
     */
    public long readInt() {
        return readInt(0);
    }

    /**
     * Read int value from buffer
     *
     * @param offset start reading from offset byte
     * @return int value
     * @throws IndexOutOfBoundsException out of buffer
     */
    public long readInt(int offset) {
        long result = 0;
        result |= buffer[offset] & 0xFF;
        result <<= 8;
        result |= buffer[offset + 1] & 0xFF;
        result <<= 8;
        result |= buffer[offset + 2] & 0xFF;
        result <<= 8;
        result |= buffer[offset + 3] & 0xFF;
        return result;
    }

    /**
     * Удаляет один byte из массива и возвращает его.
     *
     * @return удаленный byte
     * @throws IndexOutOfBoundsException out of buffer
     */
    public int removeByte() {
        int result = readByte();
        removeBytes0(1);
        return result;
    }

    /**
     * Удаляет один short из массива и возвращает его.
     *
     * @return удаленный short
     * @throws IndexOutOfBoundsException out of buffer
     */
    public int removeShort() {
        int result = readShort();
        removeBytes0(2);
        return result;
    }

    /**
     * Удаляет один int из массива и возвращает его.
     *
     * @return удаленные int
     * @throws IndexOutOfBoundsException out of buffer
     */
    public long removeInt() {
        long result = readInt();
        removeBytes0(4);
        return result;
    }

    /**
     * Удаляет строку C-Octet String из массива и возращает строку.
     *
     * @return C-Octet String, may be null
     * @throws TerminatingNullNotFoundException
     *          null character not found in the buffer
     */
    public String removeCString() throws TerminatingNullNotFoundException {
        int zeroPos = -1;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] == 0) {
                zeroPos = i;
                break;
            }
        }
        if (zeroPos == -1)
            throw new TerminatingNullNotFoundException();
        String result = null;
        if (zeroPos > 0) {
            try {
                result = new String(buffer, 0, zeroPos, "US-ASCII");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("US-ASCII charset is not supported. Consult developer.", e);
            }
        }
        removeBytes0(zeroPos + 1);
        return result;
    }

    /**
     * Remove Octet String from buffer and return it in ASCII encoding..
     *
     * @param length string length
     * @return removed string
     * @throws IndexOutOfBoundsException out of buffer
     */
    public String removeString(int length) {
        return removeString(length, "US-ASCII");
    }

    /**
     * Remove Octet string from buffer and return it in charsetName encoding.
     * <p/>
     * Note: Even if string length is 0, zero-length String object created and returned.
     * This behavior is differ from C-Octet String cause user know Octet String length
     * and may not call this method if String length is 0 and he need null.
     *
     * @param length      string length
     * @param charsetName string charset name
     * @return removed string
     * @throws IndexOutOfBoundsException out of buffer
     */
    public String removeString(int length, String charsetName) {
        String result;
        try {
            result = new String(buffer, 0, length, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported charset name: " + charsetName, e);
        }
        removeBytes0(length);
        return result;
    }

    /**
     * Remove bytes from buffer and return them.
     *
     * @param count count of bytes to remove
     * @return removed bytes
     * @throws IndexOutOfBoundsException out of buffer
     */
    public byte[] removeBytes(int count) {
        byte[] result = readBytes(count);
        removeBytes0(count);
        return result;
    }

    /**
     * Возвращает строку отображающую содержимое массива.
     *
     * @return содержимое массива
     */
    public String hexDump() {
        StringBuilder builder = new StringBuilder();
        for (byte b : buffer) {
            builder.append(Character.forDigit((b >> 4) & 0x0f, 16));
            builder.append(Character.forDigit(b & 0x0f, 16));
        }
        return builder.toString();
    }

    /**
     * Just removes bytes from the buffer and doesnt return anything.
     *
     * @param count removed bytes
     */
    private void removeBytes0(int count) {
        int lefts = buffer.length - count;
        if (lefts > 0) {
            byte[] newBuf = new byte[lefts];
            System.arraycopy(buffer, count, newBuf, 0, lefts);
            buffer = newBuf;
        } else {
            buffer = EMPTY;
        }
    }

    /**
     * Read bytes.
     *
     * @param count count of bytes to read
     * @return readed bytes
     */
    private byte[] readBytes(int count) {
        byte[] resBuf = new byte[count];
        System.arraycopy(buffer, 0, resBuf, 0, count);
        return resBuf;
    }
}
