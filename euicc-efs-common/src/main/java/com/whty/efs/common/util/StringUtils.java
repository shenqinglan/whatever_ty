// Copyright (C) 2012 WHTY
package com.whty.efs.common.util;

import java.util.Random;

/**
 * 封装了TSM的特有方法
 *
 * @author cx
 * @date 2011-6-14 下午03:45:14
 */
public class StringUtils {

    /**
     * 计算字节校验和
     *
     * @param input
     * @return 注：源于类KeyDispersion
     */
    public static byte calcLRC(byte[] input) {
        byte ret = 0;
        for (int i = 0; i < input.length; i++) {
            ret ^= input[i];
        }
        return ret;
    }

    private static Random RANDOM = new Random(System.currentTimeMillis());
	private static char HEX_CHAR[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F' };
    
	private static char randomHexChar() {
		int random = RANDOM.nextInt() & 0xF;
		return HEX_CHAR[random];
	}
	
    /**
     * 生成len个字节的十六进制随机数字符串<br>
     * <br>
     * 例如:len=8 则可能会产生 DF15F0BDFADE5FAF 这样的字符串
     *
     * @param len
     *            待产生的字节数
     * @return String
     */
	public static String randomHexString(int len) {
		if (len < 1) {
			return null;
		}

		char[] chars = new char[len * 2];

		int index = 0;
		while ((index = RANDOM.nextInt() & 0xF) == 0)
			;
		chars[0] = HEX_CHAR[index];

		// 随机数字符
		for (int i = 1; i < len * 2; i++) {
			chars[i] = randomHexChar();
		}
		return new String(chars);
	}
    
    /**
     * 高效判非空方法
     * @param s 待判空字符串
     * @return
     */
    public static boolean isNotNull(String s){
		return (s != null && s.length() > 0);
    }
    /**
     * 判空方法
     * @param s
     * @return
     */
    public static boolean isNull(String s){
        return (s == null || s.length() == 0);
    }

}
