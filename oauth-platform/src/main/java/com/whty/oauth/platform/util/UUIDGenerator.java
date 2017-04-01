package com.whty.oauth.platform.util;

import java.util.Random;

/**
 * @ClassName UUIDGenerator
 * @author Administrator
 * @date 2017-3-3 上午9:49:59
 * @Description TODO(生成uuid)
 */
public class UUIDGenerator {

	public static String getUUID(int num) {
		byte[] b = new byte[num];
		Random random = new Random();
		random.nextBytes(b);
		return bytesToHex(b);
	}
	
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
}
