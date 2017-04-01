package com.whty.security.util;
import java.util.Locale;


public class ToHexUtil {
	
// 字节转16进制
	public static String byte2hex(byte[] data) {
		if (data == null) {
		      return "";
		    }
		    StringBuffer buff = new StringBuffer();
		    int len = data.length;
		    for (int j = 0; j < len; j++) {
		      if ((data[j] & 0xff) < 16) {
		        buff.append('0');
		      }
		      buff.append(Integer.toHexString(data[j] & 0xff));
		    }
		    return buff.toString();
		  
		
	}
	
// 16进制转字节
	public static byte[] hex2byte(String content) {    
		/*对输入值进行规范化整理*/
		content = content.trim().replace(" ", "").toUpperCase(Locale.US);
    	//处理值初始化
    	int m=0,n=0;
        int iLen=content.length()/2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间
        
        for (int i = 0; i < iLen; i++){
            m=i*2+1;
            n=m+1;
            ret[i] = (byte)(Integer.decode("0x"+ content.substring(i*2, m) + content.substring(m,n)) & 0xFF);
        }
        return ret;

        } 
		
	}

