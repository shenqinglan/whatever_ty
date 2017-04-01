package com.whty.framework.utils;
/**
 * @ClassName StringUtil
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class StringUtil {

	public static byte[] hexString2Bytes(String hexString) throws Exception {
        byte[] array = new byte[hexString.length()/2];
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) Integer.parseInt(hexString.substring(i*2, (i+1)*2), 16);
        }
        return array;
    }

	public static String bytes2HexString(byte[] b) {
		byte[] hex = "0123456789ABCDEF".getBytes(); 
        byte[] buff = new byte[2 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[2 * i + 1] = hex[b[i] & 0x0f];  
        }  
        return new String(buff);  
    }

}
