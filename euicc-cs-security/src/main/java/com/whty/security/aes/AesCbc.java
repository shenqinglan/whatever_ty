package com.whty.security.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.whty.security.util.Converts;

/**
 * AES CBC模式下的encrypt/decrypt
 * @author shen
 */
public class AesCbc
{
	
	/**
	 * AES CBC加解密,无填充
	 * @param sSrc待加/解密数据
	 * @param sKey密钥
	 * @param iv初始向量
	 * @param mode 加解密模式。为0表示加密，为1表示解密
	 * @return
	 * @throws Exception
	 */
	public static String aesCbc1(String sSrc,String sKey,String iv,int mode)throws Exception{
		try {
			
			 int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
			 
			 if (sSrc.length() / 2 % 16 != 0) {
					int padlen2 = 16 - sSrc.length() / 2 % 16;
					sSrc = sSrc + "00000000000000000000000000000000".substring(0, padlen2 * 2);
				}
				
			byte[] raw = Converts.stringToBytes(sKey);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec ivSpec = new IvParameterSpec(Converts.stringToBytes(iv));
			cipher.init(opmode, skeySpec, ivSpec);
			 return (Converts.bytesToHex(cipher.doFinal(Converts .stringToBytes(sSrc)))).toUpperCase();
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
		
	}
	/**
	 * AES CBC加解密,PKCS5填充
	 * @param sSrc待加/解密数据
	 * @param sKey密钥
	 * @param iv初始向量
	 * @param mode加解密模式。为0表示加密，为1表示解密
	 * @return
	 * @throws Exception
	 */
	public static String aesCbc2(String sSrc,String sKey,String iv,int mode)throws Exception{
		try {
			
			 int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
			byte[] raw = Converts.stringToBytes(sKey);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivSpec = new IvParameterSpec(Converts.stringToBytes(iv));
			cipher.init(opmode, skeySpec, ivSpec);
			 return (Converts.bytesToHex(cipher.doFinal(Converts .stringToBytes(sSrc)))).toUpperCase();
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
		
	}
	

}
