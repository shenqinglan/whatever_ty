package com.whty.euicc.rsp.util;

import java.security.MessageDigest;
import org.apache.commons.lang3.StringUtils;
import com.whty.euicc.common.utils.SecurityUtil;

public class SHA256Util {
	public static void main(String[] args) {
		String string = Encrypt("zhaozhao","");
		System.out.println(string);
	}
	
	/**
	 *  对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
	 * @param strSrc 要加密的字符串
	 * @param encName 加密类型
	 * @return
	 */
	public static String Encrypt(String strSrc, String encName){
        MessageDigest md = null;
        String strDes = null;
        byte[] bytes = SecurityUtil.hexToBytes(strSrc);
        try {
            if (StringUtils.isBlank(encName)) {
               encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bytes);
            strDes = bytes2Hex(md.digest()).toUpperCase(); // to HexString
        } catch (Exception e) {
            return null;
        }
        return strDes;
	    }

	/**
	 * byte数组转16进制字符串
	 * @param bts
	 * @return
	 */
	public static String bytes2Hex(byte[] bts) {
	        String des = "";
	        String tmp = null;
	        for (int i = 0; i < bts.length; i++) {
	            tmp = (Integer.toHexString(bts[i] & 0xFF));
	            if (tmp.length() == 1) {
	                des += "0";
	            }
	            des += tmp;
	        }
	        return des;
	    }


}
