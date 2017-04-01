package com.whty.euicc.rsp.oma.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * 转换操作的帮助函数,进制之间的转换.(不要加入其他方法)
 *
 */
public abstract class Converts {

    /**
     * 整数转为指定长度的字符串
     *
     * @param n
     *            整数
     * @param len
     *            指定长度
     * @return
     */
    public static String intToString(int n, int len) {
        String str = String.valueOf(n);
        int strLen = str.length();
        String zeros = "";
        for (int loop = len - strLen; loop > 0; loop--) {
            zeros += "0";
        }
        if (n >= 0) {
            return zeros + str;
        }
        else {
            return "-" + zeros + str.substring(1);
        }
    }

    /**
     * 字节数组转为16进制
     *
     * @param bytes
     *            字节数组
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer buff = new StringBuffer();
        int len = bytes.length;
        for (int j = 0; j < len; j++) {
            if ((bytes[j] & 0xff) < 16) {
                buff.append('0');
            }
            buff.append(Integer.toHexString(bytes[j] & 0xff));
        }
        return buff.toString();
    }

    /**
     * 字符串转换为字节数组
     * <p>
     * stringToBytes("0710BE8716FB"); return: b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
     */
    public static byte[] stringToBytes(String string) {
        if (string == null || string.length() == 0
                || string.length() % 2 != 0) {
            return null;
        }
        int stringLen = string.length();
        byte byteArrayResult[] = new byte[stringLen / 2];
        StringBuffer sb = new StringBuffer(string);
        String strTemp;
        int i = 0;
        while (i < sb.length() - 1) {
            strTemp = string.substring(i, i + 2);
            byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
            i += 2;
        }
        return byteArrayResult;
    }

    /**
     * 整形转换为字节数组
     * <p>
     * n=1000000000(0x3B9ACA00) return: byte[0]:3b byte[1]:9a byte[2]:ca
     * byte[3]:00
     * <p>
     * 注意: 整形范围为: [ -2^32, 2^32-1]
     */
    public static byte[] intToBytes(int n) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(n);
        return bb.array();
    }

    /**
     * Long转换为字节数组
     *
     * @param l
     * @return
     */
    public static byte[] longToBytes(long l) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(l);
        return bb.array();
    }

    /**
     * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val
     *            int 待转换整数
     * @param len
     *            int 指定长度
     * @return String
     */
    public static String intToHex(int val, int len) {
        String result = Integer.toHexString(val).toUpperCase();
        int rLen = result.length();
        if (rLen > len) {
            return result.substring(rLen - len, rLen);
        }
        if (rLen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rLen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * 将长整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val
     *            int 待转换长整数
     * @param len
     *            int 指定长度
     * @return String
     */
    public static String longToHex(long val, int len) {
        String result = Long.toHexString(val).toUpperCase();
        int rLen = result.length();
        if (rLen > len) {
            return result.substring(rLen - len, rLen);
        }
        if (rLen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rLen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * @param hex
     *            将16进制的ascii 转成中文
     * @return
     */
    public static String hexToAscii(String hex) {
        byte[] buffer = new byte[hex.length() / 2];
        String strByte;

        for (int i = 0; i < buffer.length; i++) {
            strByte = hex.substring(i * 2, i * 2 + 2);
            buffer[i] = (byte) Integer.parseInt(strByte, 16);
        }

        return new String(buffer);
    }

    /**
     * @param hex
     *            每两个字节进行处理
     * @return
     */
    public static byte[] hexToBytes(String hex) {
        byte[] buffer = new byte[hex.length() / 2];
        String strByte;

        for (int i = 0; i < buffer.length; i++) {
            strByte = hex.substring(i * 2, i * 2 + 2);
            buffer[i] = (byte) Integer.parseInt(strByte, 16);
        }

        return buffer;
    }

    /**
     * 字节数组转为16进制
     *
     * @param bytes
     *            字节数组
     * @return
     */
    public static String asciiToHex(String asciiString) {
        if (asciiString == null) {
            return "";
        }
        StringBuffer buff = new StringBuffer();
        byte[] bytes = asciiString.getBytes();
        int len = bytes.length;
        for (int j = 0; j < len; j++) {
            if ((bytes[j] & 0xff) < 16) {
                buff.append('0');
            }
            buff.append(Integer.toHexString(bytes[j] & 0xff));
        }
        return buff.toString();
    }

    /**
     * 取十进制数偶数位，左补0
     *
     * @param n
     *            十进制数
     * @param number
     *            位数
     * @return
     */
    public static String getDecimal(int n, int number) {
        Integer n1 = new Integer(n);
        String strN = n1.toString();
        int j = number - strN.length();
        for (int i = 0; i < j; i++) {
            strN = "0" + strN;
        }
        return strN;
    }

    public static String getLengthTLV(int n1) {
        int n = n1;
        n = n / 2;
        String hex = "";
        if (n < 128) {
            hex = intToHex(n, 2);
        }
        else if (n >= 128 && n < 256) {
            hex = "81" + intToHex(n, 2);
        }
        else if (n >= 256) {
            hex = "82" + intToHex(n, 4);
        }
        return hex;
    }

    /**
     * ************************************************************** 描述： UCS2编码
     * 参数： src,字符串 返回： 返回byte数组,或者null
     * ****************************************************************
     */
    public static byte[] ucs2Encode(String src) {
        if (src == null || src.length() == 0) {
            return null;
        }
        byte[] btdest = null;
        try {
            if (!isAcsiiEncode(src)) {
                byte[] btsrc = src.getBytes("UTF-16BE");
                btdest = new byte[btsrc.length + 1];
                btdest[0] = (byte) 0x80;
                System.arraycopy(btsrc, 0, btdest, 1, btsrc.length);
            }
            else {
                return src.getBytes();
            }
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return btdest;
    }

    /**
     * ************************************************************** 描述：
     * 判断是否ascii编码的字符串 参数： src,字符串 返回： true,false
     * ****************************************************************
     */
    public static boolean isAcsiiEncode(String src) {
        if (src == null || src.length() == 0) {
            return false;
        }
        try {
            byte[] btsrc = src.getBytes("UTF-16BE");
            if (btsrc.length % 2 != 0) {
                return false;
            }
            for (int i = 0; i < btsrc.length; i += 2) {
                if (!(btsrc[i] == 0x0 && btsrc[i + 1] < 0x7f)) {
                    return false;
                }
            }
            return true;
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val
     *            待转换整数
     * @param len
     *            指定长度
     * @return String
     */
    public static String int2HexStr(int val, int len) {
        String result = Integer.toHexString(val).toUpperCase(); // EEEEEEEEE
        int rLen = result.length();
        if (rLen > len) {
            return result.substring(rLen - len, rLen);
        }
        if (rLen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rLen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }



    /**
     * 填充00数据，如果结果数据块是8的倍数，不再进行追加,如果不是,左补0，直到数据块的长度是8的倍数。
     *
     * @param data
     *            待填充0的数据
     * @return
     */
    public static String padding0(String data1) {
        String data = data1;
        int padlen = 8 - (data.length()) % 8;
        if (padlen != 8) {
            String padstr = "";
            for (int i = 0; i < padlen; i++) {
                padstr += "0";
            }
            data = padstr + data;
            return data;
        }
        else {
            return data;
        }
    }

    public static byte[] ucs2EncodeEndian(String src) {
        if (src == null || src.length() == 0) {
            return null;
        }
        byte[] btdest = null;
        try {
            if (!isAcsiiEncode(src)) {
                /*
                 * UUTF-16 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
                 * EFF，Big-Endian的；FFFE，Little-Endian
                 */
                btdest = src.getBytes("UTF-16");

            }
            else {
                return src.getBytes();
            }
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return btdest;
    }

    /**
	   * 将字符串右边填充空格到len长度
	   * @param s
	   * @param len
	   * @return
	   */
	  public static String paddingSpaceRight(String s,int len){
	      int strLen = s.length();
	      String zeros = "";
	      for (int loop = len - strLen; loop > 0; loop--) {
	        zeros += " ";
	      }
	      return s + zeros;
	  }
	  
	  /**
	   * 将字符串左边填充空格到len长度
	   * @param s
	   * @param len
	   * @return
	   */
	  public static String paddingSpaceLeft(String s,int len){
	      int strLen = s.length();
	      String zeros = "";
	      for (int loop = len - strLen; loop > 0; loop--) {
	        zeros += " ";
	      }
	      return zeros + s;
	  }
	  
	  /**
	   * 将字符串右边填充空格到len长度
	   * @param s
	   * @param len
	   * @return
	   */
	  public static String paddingZeroRight(String s,int len){
	      int strLen = s.length();
	      String zeros = "";
	      for (int loop = len - strLen; loop > 0; loop--) {
	        zeros += "0";
	      }
	      return s + zeros;
	  }
	  
	  /**
	   * 将字符串左边填充空格到len长度
	   * @param s
	   * @param len
	   * @return
	   */
	  public static String paddingZeroLeft(String s,int len){
	      int strLen = s.length();
	      String zeros = "";
	      for (int loop = len - strLen; loop > 0; loop--) {
	        zeros += "0";
	      }
	      return zeros + s;
	  }
	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param b
	 * @return
	 */
	public static String bytesToString(byte b[]) {
	    StringBuilder sb = new StringBuilder();
	    for (byte element : b) {
	        int tmp = element & 0xff;
	        sb.append(String.format("%02X", tmp));
	    }
	    return sb.toString();
	}
	public static String merge(String resApdu)
    {
        return "<APDU>" + resApdu + "</APDU>";
    }
}
