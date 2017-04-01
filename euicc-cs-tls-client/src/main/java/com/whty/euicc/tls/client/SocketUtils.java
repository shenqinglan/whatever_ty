package com.whty.euicc.tls.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class SocketUtils {
    public static void close(Socket s){
        try {
            s.shutdownInput();
            s.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readBytes(DataInputStream in,int length) throws IOException {
        int r=0;
        byte[] data=new byte[length];
        while(r<length){
            r+=in.read(data,r,length-r);
        }

        return data;
    }

    public static void writeBytes(DataOutputStream out,byte[] bytes,int length) throws IOException{
        out.writeInt(length);
        out.write(bytes,0,length);
        out.flush();
    }
    
    public static byte[] readBytes(InputStream in) throws IOException {
    	 int r=0;
 	    byte[] data=new byte[5];
 	    while(r<5){
 	        r+=in.read(data,r,5-r);
 	    }
 	
 		String strTmpBuf = hexByteToString(data);
 		String lenString = strTmpBuf.substring(6);
 		int len = Integer.parseInt(lenString, 16);
 		
 	    byte[] data2=new byte[len];
 	    int i= 0;
 	    while(i<len){
 	    	i+=in.read(data2,i,len-i);
 	    }
 		
 	   String strTmpBuf2 = hexByteToString(data2);
 	
 	   return hexToBuffer(strTmpBuf+strTmpBuf2);

	}
    
    public static void writeBytes(OutputStream out,byte[] bytes) throws IOException{
        out.write(bytes);
        out.flush();
    }
    
    public static String hexByteToString (byte[] bytes) {
	    String ret = "";
	    for(int i = 0, len = bytes.length; i < len; i ++) {
	    	String hex =  Integer.toHexString(bytes[i] & 0xff).toUpperCase();
	    	ret += hex.length()  == 1 ? "0" + hex : hex;
	    }
	    return ret;
	}
    
    public static byte[] hexToBuffer(String hex) throws IOException{
    	if ((hex == null) || ("".equals(hex))) {
    		return new byte[0];
    	}
    	if (hex.length() % 2 != 0) {
    		throw new RuntimeException("字符串的长度不为偶数");
    	}
    	int len = hex.length() / 2;
    	byte[] byteArray = new byte[len];
    	int index = 0;
    	for (int i = 0; i < len; i++) {
    		byteArray[i] = ((byte)Integer.parseInt(
    		hex.substring(index, index + 2), 16));
    		index += 2;
    	}
    	
    	return byteArray;
	}
}

