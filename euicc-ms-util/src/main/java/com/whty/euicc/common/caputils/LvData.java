// Copyright (C) 2012 WHTY
package com.whty.euicc.common.caputils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LV格式数据 所有字符串参数为 16进制 表达，字符个数必须为偶数个 所有长度参数为 字节 长度，为字符个数的一半 长度必须大于0
 *
 * @author ShanSheng
 *
 */
public class LvData {
    private static final Logger logger = LoggerFactory.getLogger(LvData.class);

    public String lvdata = "";

    public int lVlen = -1;

    public int llen = -1;

    public int vlen = -1;

    public String ldata = "";

    public String vdata = "";

    private LvData() {

    }

    private LvData(String data) {
        llen = 1;
        this.lvdata = data;
        praseLV();
    }

    private LvData(String data, int llen) {
        this.lvdata = data;
        this.llen = llen;
        praseLV();
    }

    static public LvData setLV(String lV) {
        LvData newLV = new LvData(lV);
        newLV.praseLV();
        return newLV;

    }

    static public LvData setLV(String lV, int llen) {
        LvData newLV = new LvData(lV, llen);
        newLV.praseLV();
        return newLV;

    }

    static public LvData setV(String v) {
        LvData newLV = new LvData();
        newLV.llen = 1;
        newLV.vdata = v;
        if (v.length() % 2 == 0) {
            newLV.vlen = v.length() / 2;
            newLV.ldata = String.format("%0" + newLV.llen * 2 + "X",
                    newLV.vlen);
            newLV.lvdata = newLV.ldata + newLV.vdata;
        }
        else {
            newLV.lvdata = null;
        }

        return newLV;

    }

    static public LvData setV(String v, int llen) {
        LvData newLV = new LvData();
        newLV.llen = llen;
        newLV.vdata = v;
        if (v.length() % 2 == 0) {
            newLV.vlen = v.length() / 2;
            newLV.ldata = String.format("%0" + newLV.llen * 2 + "X",
                    newLV.vlen);
            newLV.lvdata = newLV.ldata + newLV.vdata;
        }
        else {
            newLV.lvdata = null;
        }
        return newLV;
    }

    private boolean praseLV() {
        if (lvdata != null) {
            try {
                ldata = lvdata.substring(0, llen * 2);
                vdata = lvdata.substring(llen * 2,
                        2 * (Integer.parseInt(ldata, 16) + llen));
                vlen = vdata.length() / 2;
                lvdata = ldata + vdata;
                lVlen = llen + vlen;
                return true;
            }
            catch (StringIndexOutOfBoundsException e) {
                logger.error("StringIndexOutOfBoundsException:", e);
                lvdata = null;
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return vdata;
    }
}
