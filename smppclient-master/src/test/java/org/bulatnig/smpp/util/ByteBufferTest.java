package org.bulatnig.smpp.util;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * ByteBuffer test.
 *
 * @author Bulat Nigmatullin
 */
public class ByteBufferTest {

    @Test
    public void constructorEmpty() {
        ByteBuffer bb = new ByteBuffer();
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void constructorNotEmpty() {
        final byte[] bytes = new byte[]{12, 0, 10, 4};
        ByteBuffer bb = new ByteBuffer(bytes);
        assertEquals(bytes.length, bb.length());
        assertArrayEquals(bytes, bb.array());
        assertEquals("0c000a04", bb.hexDump());
    }

    @Test
    public void bytesToEmpty() {
        final byte[] bytes = new byte[]{(byte) 255, 50, 70, (byte) 255};
        ByteBuffer bb = new ByteBuffer();

        bb.appendBytes(bytes);

        assertEquals(bytes.length, bb.length());
        assertArrayEquals(bytes, bb.array());
        assertEquals("ff3246ff", bb.hexDump());

        byte[] removed = bb.removeBytes(bytes.length);

        assertArrayEquals(bytes, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void bytesToNonEmpty() {
        final byte[] bytes = new byte[]{(byte) 144, 100, 1, 0};
        final byte[] all = new byte[]{(byte) 255, (byte) 144, 100, 1, 0};
        ByteBuffer bb = new ByteBuffer(new byte[]{(byte) 255});

        bb.appendBytes(bytes);

        assertEquals(all.length, bb.length());
        assertArrayEquals(all, bb.array());
        assertEquals("ff90640100", bb.hexDump());

        byte[] removed = bb.removeBytes(all.length);

        assertArrayEquals(all, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void bytesEmpty() {
        final byte[] bytes = new byte[0];
        ByteBuffer bb = new ByteBuffer();
        bb.appendBytes(bytes);
        assertEquals(bytes.length, bb.length());
        assertArrayEquals(bytes, bb.array());
        assertEquals("", bb.hexDump());

        byte[] removed = bb.removeBytes(bytes.length);

        assertArrayEquals(bytes, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void smppByte() {
        int b = 15;
        ByteBuffer bb = new ByteBuffer();

        bb.appendByte(b);

        assertEquals(1, bb.length());
        assertArrayEquals(new byte[]{(byte) b}, bb.array());
        assertEquals("0f", bb.hexDump());

        int read = bb.readByte();

        assertEquals(b, read);

        int removed = bb.removeByte();

        assertEquals(b, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendByteNegative() {
        int b = -1;
        ByteBuffer bb = new ByteBuffer();
        bb.appendByte(b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendByteTooLarge() {
        int b = 256;
        ByteBuffer bb = new ByteBuffer();
        bb.appendByte(b);
    }

    @Test
    public void smppShort() {
        int s = 1500;
        ByteBuffer bb = new ByteBuffer();

        bb.appendShort(s);

        assertEquals(2, bb.length());
        assertArrayEquals(new byte[]{5, (byte) 220}, bb.array());
        assertEquals("05dc", bb.hexDump());

        int read = bb.readShort();

        assertEquals(s, read);

        int removed = bb.removeShort();

        assertEquals(s, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendShortNegative() {
        int s = -1500;
        ByteBuffer bb = new ByteBuffer();
        bb.appendShort(s);
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendShortTooLarge() {
        int s = 100000;
        ByteBuffer bb = new ByteBuffer();
        bb.appendShort(s);
    }

    @Test
    public void smppInt() {
        long l = 150000;
        ByteBuffer bb = new ByteBuffer();

        bb.appendInt(l);

        assertEquals(4, bb.length());
        assertArrayEquals(new byte[]{0, 2, 73, (byte) 240}, bb.array());
        assertEquals("000249f0", bb.hexDump());

        long read = bb.readInt();

        assertEquals(l, read);

        long removed = bb.removeInt();

        assertEquals(l, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendIntNegative() {
        long l = -1;
        ByteBuffer bb = new ByteBuffer();
        bb.appendInt(l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void appendIntTooLarge() {
        long l = 190000000000L;
        ByteBuffer bb = new ByteBuffer();
        bb.appendInt(l);
    }

    @Test
    public void cstring() throws TerminatingNullNotFoundException {
        final String s = "smpp";
        ByteBuffer bb = new ByteBuffer();

        bb.appendCString(s);

        assertEquals(s.length() + 1, bb.length());
        assertArrayEquals(new byte[]{(byte) 115, (byte) 109, (byte) 112, (byte) 112, 0}, bb.array());
        assertEquals("736d707000", bb.hexDump());

        String removed = bb.removeCString();

        assertEquals(s, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void cstringEmpty() throws TerminatingNullNotFoundException {
        final String s = "";
        ByteBuffer bb = new ByteBuffer();

        bb.appendCString(s);

        assertEquals(1, bb.length());
        assertArrayEquals(new byte[]{0}, bb.array());
        assertEquals("00", bb.hexDump());

        String removed = bb.removeCString();

        assertEquals(null, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void cstringNull() throws TerminatingNullNotFoundException {
        final String s = null;
        ByteBuffer bb = new ByteBuffer();

        bb.appendCString(s);

        assertEquals(1, bb.length());
        assertArrayEquals(new byte[]{0}, bb.array());
        assertEquals("00", bb.hexDump());

        String removed = bb.removeCString();

        assertEquals(s, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test(expected = TerminatingNullNotFoundException.class)
    public void cstringRemoveTerminatingNullNotFound() throws TerminatingNullNotFoundException {
        ByteBuffer bb = new ByteBuffer();
        bb.removeCString();
    }

    @Test
    public void cstringCheckTerminatingNullFind() throws TerminatingNullNotFoundException {
        final String s = "smpp";
        final int b = 0;
        ByteBuffer bb = new ByteBuffer();

        bb.appendCString(s);
        bb.appendByte(b);

        assertEquals(s.length() + 1 + 1, bb.length());
        assertArrayEquals(new byte[]{(byte) 115, (byte) 109, (byte) 112, (byte) 112, 0, 0}, bb.array());
        assertEquals("736d70700000", bb.hexDump());

        String removed = bb.removeCString();
        int removedB = bb.removeByte();

        assertEquals(s, removed);
        assertEquals(b, removedB);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void smppString() {
        final String s = "smpp";
        ByteBuffer bb = new ByteBuffer();

        bb.appendString(s);

        assertEquals(s.length(), bb.length());
        assertArrayEquals(new byte[]{(byte) 115, (byte) 109, (byte) 112, (byte) 112}, bb.array());
        assertEquals("736d7070", bb.hexDump());

        String removed = bb.removeString(s.length());

        assertEquals(s, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void smppStringUCS() {
        final String s = "Привет";
        ByteBuffer bb = new ByteBuffer();

        bb.appendString(s, "UTF-16BE");

        assertEquals(12, bb.length());
        assertEquals("041f04400438043204350442", bb.hexDump());

        String removed = bb.removeString(12, "UTF-16BE");

        assertEquals(s, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void smppStringEmpty() {
        final String s = "";
        ByteBuffer bb = new ByteBuffer();

        bb.appendString(s);

        assertEquals(s.length(), bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());

        String removed = bb.removeString(s.length());

        assertEquals(s, removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test
    public void smppStringNull() {
        final String s = null;
        ByteBuffer bb = new ByteBuffer();

        bb.appendString(s);

        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());

        String removed = bb.removeString(0);

        assertEquals("", removed);
        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test(expected = IllegalArgumentException.class)
    public void smppStringAppendWrongCharset() {
        ByteBuffer bb = new ByteBuffer();
        bb.appendString("Some text here.", "abfega");
    }

    @Test(expected = IllegalArgumentException.class)
    public void smppStringRemoveWrongCharset() {
        ByteBuffer bb = new ByteBuffer();
        bb.removeString(0, "abfega");
    }

    @Test
    public void chainingAppend() throws TerminatingNullNotFoundException {
        ByteBuffer bb = new ByteBuffer().appendByte(1).appendShort(2).appendInt(3).appendCString("smpp").appendString("smsc");

        assertEquals(1, bb.removeByte());
        assertEquals(2, bb.removeShort());
        assertEquals(3, bb.removeInt());
        assertEquals("smpp", bb.removeCString());
        assertEquals("smsc", bb.removeString(4));

        assertEquals(0, bb.length());
        assertArrayEquals(new byte[0], bb.array());
        assertEquals("", bb.hexDump());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readByteFromEmpty() {
        ByteBuffer bb = new ByteBuffer();
        bb.readByte();
    }

    @Test(expected = TerminatingNullNotFoundException.class)
    public void removeCStringFromEmpty() throws TerminatingNullNotFoundException {
        ByteBuffer bb = new ByteBuffer();
        bb.removeCString();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeStringFromEmpty() {
        ByteBuffer bb = new ByteBuffer();
        bb.removeString(15);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeBytesFromEmpty() {
        ByteBuffer bb = new ByteBuffer();
        bb.removeBytes(10);
    }

}

