package com.whty.security.util;


/**
 * 转换操作的帮助函数,进制之间的转换.
 * @author shen
 */
public class Converts {
	
	 /**
	  * 字节转16进制
	  * @param bytes
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
	   * 16进制转字节
	   * @param hexString
	   * @return
	   */

	  public static byte[] hexToBytes(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	        }  
	  
	  public static byte hexToByte(String hex) throws Exception {
			byte res = 0;

			if (hex.length() != 2) {
				throw new Exception("字符串长度不为2");
			}
			res = (byte) Integer.parseInt(hex, 16);

			return res;
		}
	  /**
	   * 字符串转换为字节数组
	   * @param string 字符串
	   * stringToBytes("0710BE8716FB"); return: b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
	   */
	  public static byte[] stringToBytes(String string) {
	    if (string == null || string.length() == 0 || string.length() % 2 != 0) {
	      return null;
	    }
	    int stringLen = string.length();
	    byte byteArrayResult[] = new byte[stringLen / 2];
	    StringBuffer sb = new StringBuffer(string);
	    String strTemp;
	    int i = 0;
	    while (i < sb.length() - 1) {
	      strTemp = string.substring(i, i + 2);
	      byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp,16);
	      i += 2;
	    }
	    return byteArrayResult;
	  }
	  
}
