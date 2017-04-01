// Copyright (C) 2012 WHTY
package com.whty.euicc.common.caputils;

/**
 * @author ShanSheng
 *
 */
public class TLVData {
    public LvData lv;

    public String tLVdata;

    public int tag = -1;

    private TLVData(String data) {
        this.tLVdata = data;
        tag = Integer.parseInt(data.substring(0, 2), 16);
        lv = LvData.setLV(tLVdata.substring(2));
        if (lv.lvdata != null) {
            tLVdata = tLVdata.substring(0, 2) + lv.lvdata;
        }
        else {
            tLVdata = null;
        }
    }

    private TLVData(String data, int llen) {
        this.tLVdata = data;
        tag = Integer.parseInt(data.substring(0, 2), 16);
        lv = LvData.setLV(tLVdata.substring(2), llen);
        if (lv.lvdata != null) {
            tLVdata = tLVdata.substring(0, 2) + lv.lvdata;
        }
        else {
            tLVdata = null;
        }
    }

    static public TLVData setTLV(String data) {
        TLVData tlv = new TLVData(data);
        return tlv;
    }

    /**
     *
     * @param data
     * @param L_len
     *            LV格式中L的长度
     * @return
     */
    static public TLVData setTLV(String data, int llen) {
        TLVData tlv = new TLVData(data, llen);
        return tlv;
    }

    /**
     *
     * @param tag
     * @param LV
     * @return
     */
    static public TLVData setLV(int tag, String lV) {
        String tlvStr = String.format("%2X", tag) + lV;
        TLVData tlv = new TLVData(tlvStr);
        return tlv;
    }

    /**
     *
     * @param tag
     * @param LV
     * @param L_len
     * @return
     */
    static public TLVData setLV(int tag, String lV, int llen) {
        String tlvStr = String.format("%02X", tag) + lV;
        TLVData tlv = new TLVData(tlvStr, llen);
        return tlv;
    }

    static public TLVData setV(int tag, String v) {
        String tlvStr = String.format("%02X", tag)
                + String.format("%02X", v.length() / 2) + v;
        TLVData tlv = new TLVData(tlvStr);
        if (v.length() % 2 != 0) {
            tlv.tLVdata = null;
        }
        return tlv;
    }

    static public TLVData setV(int tag, String v, int llen) {
        String tlvStr = String.format("%02X", tag)
                + String.format("%0" + 2 * llen + "X", v.length() / 2) + v;
        TLVData tlv = new TLVData(tlvStr);
        if (v.length() % 2 != 0) {
            tlv.tLVdata = null;
        }
        return tlv;
    }

    @Override
    public String toString() {
        return lv.lvdata;
    }
}

