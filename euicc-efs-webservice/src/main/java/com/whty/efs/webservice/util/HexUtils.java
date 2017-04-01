package com.whty.efs.webservice.util;

public class HexUtils {
	private final static byte[] hex = "0123456789ABCDEF".getBytes();
	public static String bytesToHexString(byte[] b) {  
        byte[] buff = new byte[2 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[2 * i + 1] = hex[b[i] & 0x0f];  
        }  
        return new String(buff);  
    }  
	
}
