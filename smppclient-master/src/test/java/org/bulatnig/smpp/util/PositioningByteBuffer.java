package org.bulatnig.smpp.util;

import java.io.UnsupportedEncodingException;

/**
 * Converts java types to byte array according to SMPP protocol.
 * Implemented, using java.nio.ByteBuffer as template.
 * You should remember, that all SMPP simple numeric types are unsigned,
 * but Java types are always signed.
 * <p/>
 * Not thread safe.
 * <p/>
 * Implementation notes: buffer may be read or write types. Append and remove operations should not be mixed
 * and should be called only on corresponding buffer type.
 *
 * @author Bulat Nigmatullin
 */
public class PositioningByteBuffer {

    /**
     * Default buffer capacity. It should be enough to contain SUBMIT_SM or DELIVER_SM with 140 bytes text.
     */
    public static final int DEFAULT_CAPACITY = 250;


    /**
     * Byte buffer.
     */
    private final byte[] buffer;
    /**
     * Buffer type: read or write. Affects array and length operations.
     */
    private final boolean read;

    /**
     * Cursor position. Next write or read operation will start from this array element.
     */
    private int position = 0;

    /**
     * Create buffer with default capacity.
     */
    public PositioningByteBuffer() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Create buffer with given capacity.
     *
     * @param capacity buffer capacity
     */
    public PositioningByteBuffer(int capacity) {
        buffer = new byte[capacity];
        read = false;
    }

    /**
     * Create buffer based on provided array.
     *
     * @param b byte array
     */
    public PositioningByteBuffer(byte[] b) {
        buffer = b;
        read = true;
    }

    /**
     * Возвращает массив байтов.
     *
     * @return массив байтов
     */
    public byte[] array() {
        if (read)
            return buffer;
        else {
            byte[] result = new byte[position];
            System.arraycopy(buffer, 0, result, 0, position);
            return result;
        }
    }

    /**
     * Возвращает длину массива.
     *
     * @return длина массива
     */
    public int length() {
        if (read)
            return buffer.length;
        else
            return position;
    }

    /**
     * Добавляет байты в массив.
     *
     * @param bytes byte array
     * @return this buffer
     */
    public PositioningByteBuffer appendBytes(byte[] bytes) {
        return appendBytes(bytes, bytes.length);
    }

    /**
     * Добавляет байты в массив.
     *
     * @param bytes  byte array
     * @param length bytes length to add
     * @return this buffer
     */
    public PositioningByteBuffer appendBytes(byte[] bytes, int length) {
        System.arraycopy(bytes, 0, buffer, position, length);
        position += length;
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
    public PositioningByteBuffer appendByte(int value) throws IllegalArgumentException {
        if (value >= 0 && value < 256) {
            buffer[position] = (byte) value;
            position++;
        } else
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
    public PositioningByteBuffer appendShort(int value) throws IllegalArgumentException {
        if (value >= 0 && value < 65536) {
            buffer[position] = (byte) (value >>> 8);
            buffer[position + 1] = (byte) value;
            position += 2;
        } else
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
    public PositioningByteBuffer appendInt(long value) throws IllegalArgumentException {
        if (value >= 0 && value < 4294967296L) {
            buffer[position] = (byte) (value >>> 24);
            buffer[position + 1] = (byte) (value >>> 16);
            buffer[position + 2] = (byte) (value >>> 8);
            buffer[position + 3] = (byte) value;
            position += 4;
        } else
            throw new IllegalArgumentException("Short value should be between 0 and 4294967295.");
        return this;
    }

    /**
     * Добавляет строку C-Octet String в массив.
     *
     * @param cstring строка типа C-Octet (по протоколу SMPP), may be null
     * @return this buffer
     */
    public PositioningByteBuffer appendCString(String cstring) {
        if (cstring != null && cstring.length() > 0) {
            try {
                appendBytes(cstring.getBytes("US-ASCII"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("US-ASCII charset is not supported. Consult developer.", e);
            }
        }
        position++; // always append terminating ZERO
        return this;
    }

    /**
     * Append string using ASCII charset.
     *
     * @param string string value, may be null
     * @return this buffer
     */
    public PositioningByteBuffer appendString(String string) {
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
     */
    public PositioningByteBuffer appendString(String string, String charsetName) {
        if (string != null && string.length() > 0) {
            try {
                appendBytes(string.getBytes(charsetName));
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
     */
    public int readByte() {
        return buffer[0] & 0xFF;
    }

    /**
     * Read short value from buffer
     *
     * @return short value
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
     */
    public long readInt() {
        return readInt(0);
    }

    /**
     * Read int value from buffer
     *
     * @param offset start reading from offset byte
     * @return int value
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
     */
    public int removeByte() {
        int result = readByte();
        position++;
        return result;
    }

    /**
     * Удаляет один short из массива и возвращает его.
     *
     * @return удаленный short
     */
    public int removeShort() {
        int result = readShort();
        position += 2;
        return result;
    }

    /**
     * Удаляет один int из массива и возвращает его.
     *
     * @return удаленные int
     */
    public long removeInt() {
        long result = readInt();
        position += 4;
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
        for (int i = position; i < buffer.length; i++) {
            if (buffer[i] == 0) {
                zeroPos = i;
                break;
            }
        }
        if (zeroPos > -1) { // found terminating ZERO
            String result = null;
            if (zeroPos > position) {
                try {
                    result = new String(buffer, 0, zeroPos, "US-ASCII");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("US-ASCII charset is not supported. Consult developer.", e);
                }
            }
            position = zeroPos + 1;
            return result;
        } else {
            throw new TerminatingNullNotFoundException();
        }
    }

    /**
     * Remove Octet String from buffer and return it in ASCII encoding..
     *
     * @param length string length
     * @return removed string
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
     */
    public String removeString(int length, String charsetName) {
        String result;
        try {
            result = new String(buffer, position, length, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported charset name: " + charsetName, e);
        }
        position += length;
        return result;
    }

    /**
     * Remove bytes from buffer and return them.
     *
     * @param count count of bytes to remove
     * @return removed bytes
     */
    public byte[] removeBytes(int count) {
        byte[] result = readBytes(count);
        position += count;
        return result;
    }

    /**
     * Возвращает строку отображающую содержимое массива.
     *
     * @return содержимое массива
     */
    public String hexDump() {
        StringBuilder builder = new StringBuilder();
        for (byte b : array()) {
            builder.append(Character.forDigit((b >> 4) & 0x0f, 16));
            builder.append(Character.forDigit(b & 0x0f, 16));
        }
        return builder.toString();
    }

    /**
     * Read bytes.
     *
     * @param count count of bytes to read
     * @return readed bytes
     */
    private byte[] readBytes(int count) {
        byte[] resBuf = new byte[count];
        System.arraycopy(buffer, position, resBuf, 0, count);
        return resBuf;
    }
}
