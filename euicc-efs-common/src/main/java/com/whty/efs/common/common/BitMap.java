// Copyright (C) 2012 WHTY
package com.whty.efs.common.common;

import java.util.Vector;

import com.whty.efs.common.util.Converts;

/**
 * 位运算 Created by IntelliJ IDEA. User: zsr Date: 11-11-4 Time: 下午4:05 To change
 * this template use File | Settings | File Templates.
 */
public class BitMap {

    /**
     * 8583得到位图
     *
     * @return
     */
    public static String getBitMap(int[] fields) {
        char[] ch = new char[64];
        for (int i = 0; i < 64; i++) {
            ch[i] = '0';
        }

        for (int field : fields) {
            ch[field - 1] = '1';
        }

        long ret = Long.parseLong(new String(ch), 2);
        return Converts.longToHex(ret, 16);
    }

    /**
     * vector里存的是2进制数据中为1的位数
     *
     * @param bipMap
     *            16进制字符
     * @param bipNumber
     *            2进制的位数(4的倍数)
     * @return 例如：bipMap=70 转2进制为1110000 填充至8位01110000 那么vector里存的就是{2,3,4}
     */
    public static Vector<Integer> generateBitMap(String bipMap, int bipNumber) {
        Long l = Long.parseLong(bipMap, 16);
        String binary = Long.toBinaryString(l);
        int r = bipNumber - binary.length();
        for (int j = 0; j < r; j++) {
            binary = "0" + binary;
        }
        Vector<Integer> vbip = new Vector<Integer>();

        for (int i = 0; i < binary.length(); i++) {
            String c = binary.substring(i, i + 1);
            if ("1".equals(c)) {
                vbip.add(i + 1);
            }
        }

        return vbip;
    }
}

