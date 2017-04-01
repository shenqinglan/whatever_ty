package com.whty.euicc.common.utils;

public class Converts {

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
        int rlen = result.length();
        if (rlen > len) {
            return result.substring(rlen - len, rlen);
        }
        if (rlen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rlen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    public static void main(String[] args){
    	String apdu = "80500000085BEA6B0F6BD32AB800";
    	int val = apdu.length() -2;
    	int len = 4;
    	System.out.println(intToHex(val, len));
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
        int rlen = result.length();
        if (rlen > len) {
            return result.substring(rlen - len, rlen);
        }
        if (rlen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rlen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * 获得ascii码
     * @param str
     * @return
     */
    public static String getAscii(String str) {
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c > 255) {
                sb.append("//u");
                j = (c >>> 8);
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);
                j = (c & 0xFF);
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);
            } else {
                sb.append(c);
            }

        }
        return (new String(sb));
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



    /**
     * 将元为单位的转换为分 调换小数点，支撑以逗号区分的金额
     *
     * ＠param amount
     * ＠return
     */
    public static String changeY2F(String amount){
        String currency =  amount.replaceAll("\\￥|\\￥|\\,", "");  //处理惩罚包含， ￥ 或者￥的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if(index == -1){
            amLong = Long.valueOf(currency+"00");
        }else if(length - index >= 3){
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
        }else if(length - index == 2){
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
        }else{
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
        }
        return amLong.toString();
    }


    public static String changeF2Y(Long amount){
//      if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {
//          throw new Exception("金额格局有误");
//      }

      int flag = 0;
      String amString = amount.toString();
//      if(amString.charAt(0)=='"-"'){
//          flag = 1;
//          amString = amString.substring(1);
//      }
      StringBuffer result = new StringBuffer();
      if(amString.length()==1){
          result.append("0.0").append(amString);
      }else if(amString.length() == 2){
          result.append("0.").append(amString);
      }else{
          String intString = amString.substring(0,amString.length()-2);
          for(int i=1; i<=intString.length();i++){
              if( (i-1)%3 == 0 && i !=1){
                  result.append(",");
              }
              result.append(intString.substring(intString.length()-i,intString.length()-i+1));
          }
          result.reverse().append(".").append(amString.substring(amString.length()-2));
      }
      if(flag == 1){
          return "-"+result.toString();
      }else{
          return result.toString();
      }
  }
}
