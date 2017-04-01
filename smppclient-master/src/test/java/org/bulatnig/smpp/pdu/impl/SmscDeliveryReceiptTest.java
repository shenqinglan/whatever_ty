package org.bulatnig.smpp.pdu.impl;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SmscDeliveryReceipt test.
 *
 * @author Bulat Nigmatullin
 */
public class SmscDeliveryReceiptTest {

    @Test
    public void test1() {
        SmscDeliveryReceipt sdr = new SmscDeliveryReceipt("id:c449ab9744f47b6af1879e49e75e4f40 sub:001 dlvrd:0 submit date:0610191018 done date:0610191018 stat:ACCEPTD err:0 text:This is an Acti".getBytes());
        assertEquals("c449ab9744f47b6af1879e49e75e4f40", sdr.getId());
        assertEquals("001", sdr.getSub());
        assertEquals("0", sdr.getDlvrd());
        assertEquals("0610191018", sdr.getSubmitDate());
        assertEquals("0610191018", sdr.getDoneDate());
        assertEquals("ACCEPTD", sdr.getStat());
        assertEquals("0", sdr.getErr());
        assertEquals("This is an Acti", sdr.getText());
    }

    @Test
    public void test2() {
        SmscDeliveryReceipt sdr = new SmscDeliveryReceipt("id:7220bb6bd0be98fa628de66590f80070 sub:001 dlvrd:1 submit date:0610190851 done date:0610190951 stat:DELIVRD err:0 text:This is an Acti".getBytes());
        assertEquals("7220bb6bd0be98fa628de66590f80070", sdr.getId());
        assertEquals("001", sdr.getSub());
        assertEquals("1", sdr.getDlvrd());
        assertEquals("0610190851", sdr.getSubmitDate());
        assertEquals("0610190951", sdr.getDoneDate());
        assertEquals("DELIVRD", sdr.getStat());
        assertEquals("0", sdr.getErr());
        assertEquals("This is an Acti", sdr.getText());
    }

    @Test
    public void test3() {
        SmscDeliveryReceipt sdr = new SmscDeliveryReceipt("id:b756c4f97aa2e1e67377dffc5e2f7d9b sub:001 dlvrd:0 submit date:0610191211 done date:0610191211 stat:REJECTD err:1 text:This is an Acti".getBytes());
        assertEquals("b756c4f97aa2e1e67377dffc5e2f7d9b", sdr.getId());
        assertEquals("001", sdr.getSub());
        assertEquals("0", sdr.getDlvrd());
        assertEquals("0610191211", sdr.getSubmitDate());
        assertEquals("0610191211", sdr.getDoneDate());
        assertEquals("REJECTD", sdr.getStat());
        assertEquals("1", sdr.getErr());
        assertEquals("This is an Acti", sdr.getText());
    }

    @Test
    public void test4() {
        SmscDeliveryReceipt sdr = new SmscDeliveryReceipt("id:bd778cd76ae9e79da2ddc8188c68f8c1 sub:001 dlvrd:0 submit date:0610191533 done date:0610191539 stat:UNDELIV err:1 text:This is an Acti".getBytes());
        assertEquals("bd778cd76ae9e79da2ddc8188c68f8c1", sdr.getId());
        assertEquals("001", sdr.getSub());
        assertEquals("0", sdr.getDlvrd());
        assertEquals("0610191533", sdr.getSubmitDate());
        assertEquals("0610191539", sdr.getDoneDate());
        assertEquals("UNDELIV", sdr.getStat());
        assertEquals("1", sdr.getErr());
        assertEquals("This is an Acti", sdr.getText());
    }

    @Test
    public void test5() {
        SmscDeliveryReceipt sdr = new SmscDeliveryReceipt("id:1661543146 sub:001 dlvrd:001 submit date:1101261110 done date:1101261110 stat:DELIVRD err:000 text:Hello, how are you?".getBytes());
        assertEquals("1661543146", sdr.getId());
        assertEquals("001", sdr.getSub());
        assertEquals("001", sdr.getDlvrd());
        assertEquals("1101261110", sdr.getSubmitDate());
        assertEquals("1101261110", sdr.getDoneDate());
        assertEquals("DELIVRD", sdr.getStat());
        assertEquals("000", sdr.getErr());
        assertEquals("Hello, how are you?", sdr.getText());
    }

    @Test
    public void testToBytes() {
        SmscDeliveryReceipt sdr = new SmscDeliveryReceipt();
        sdr.setId("1661543146");
        sdr.setSub("001");
        sdr.setDlvrd("001");
        sdr.setSubmitDate("1101261110");
        sdr.setDoneDate("1101261110");
        sdr.setStat("DELIVRD");
        sdr.setErr("000");
        sdr.setText("Hello, how are you?");
        assertArrayEquals("id:1661543146 sub:001 dlvrd:001 submit date:1101261110 done date:1101261110 stat:DELIVRD err:000 text:Hello, how are you?".getBytes(), sdr.toBytes());
    }

}
