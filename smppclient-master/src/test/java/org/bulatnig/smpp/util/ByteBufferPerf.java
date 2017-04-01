package org.bulatnig.smpp.util;

import org.junit.Test;

/**
 * ByteBuffer performance comparison.
 *
 * @author Bulat Nigmatullin
 */
public class ByteBufferPerf {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            test0();
        }
    }

    private void test0() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
//            byteBuffer(Integer.toString(i));
            newByteBuffer(Integer.toString(i));
        }
        long done = System.currentTimeMillis();
        System.out.println("Done in " + (done - start) + " ms.");
    }

    private Object byteBuffer(String type) {
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(type);
        bb.appendByte(1);
        bb.appendByte(2);
        bb.appendCString("79269240813");
        bb.appendByte(3);
        bb.appendByte(4);
        bb.appendCString("7474#1234567");
        bb.appendByte(5);
        bb.appendByte(6);
        bb.appendByte(7);
        bb.appendCString(null);
        bb.appendCString(null);
        bb.appendByte(8);
        bb.appendByte(9);
        bb.appendByte(10);
        bb.appendByte(11);
        bb.appendByte(12);
        bb.appendString("One two three four five.");
        return bb.array();
    }

    private Object newByteBuffer(String type) {
        PositioningByteBuffer bb = new PositioningByteBuffer();
        bb.appendCString(type);
        bb.appendByte(1);
        bb.appendByte(2);
        bb.appendCString("79269240813");
        bb.appendByte(3);
        bb.appendByte(4);
        bb.appendCString("7474#1234567");
        bb.appendByte(5);
        bb.appendByte(6);
        bb.appendByte(7);
        bb.appendCString(null);
        bb.appendCString(null);
        bb.appendByte(8);
        bb.appendByte(9);
        bb.appendByte(10);
        bb.appendByte(11);
        bb.appendByte(12);
        bb.appendString("One two three four five.");
        return bb.array();
    }

}
