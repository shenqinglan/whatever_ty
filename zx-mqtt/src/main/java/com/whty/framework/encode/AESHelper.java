package com.whty.framework.encode;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.whty.framework.common.Base64Util;

public class AESHelper {
	public static void main(String[] args) throws Exception {
		SecureRandom sr = new SecureRandom();
		SecureRandom.getInstance("SHA1PRNG");
		byte[] ss = sr.generateSeed(16);
		String key = "87d5bd3767e841799a2de1501a634230";
		System.out.println("key: " + key);
		// 需要加密的字串
		String content = "{\"resp\": {\"state\": 1}, \"ts\": 1425457896, \"sid\": 3}";
		System.out.println("content: " + content);
		// 初始偏移向量
		String ivS = "426e26e82c704e59";
		System.out.println("ivS: " + ivS);
		// 加密
		String enString = AESHelper.Encrypt(content, key.getBytes("utf8"),
				ivS.getBytes("utf8"));
		System.out.println(" 加密后的字串是： " + enString);
		// 解密
		String DeString = AESHelper.Decrypt(enString, key.getBytes("utf8"));
		System.out.println(" 解密后的字串是： " + DeString);
	}

	/**
	 * aes cfb 模式加密
	 * 
	 * @param content
	 *            需要加密内容
	 * @param key
	 *            密钥
	 * @param ivS
	 *            偏移量
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String content, byte[] key, byte[] ivS)
			throws Exception {
		byte[] raw = key;
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
		IvParameterSpec iv = new IvParameterSpec(ivS);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes("utf8"));
		byte[] newB = new byte[ivS.length + encrypted.length];
		System.arraycopy(ivS, 0, newB, 0, ivS.length);
		System.arraycopy(encrypted, 0, newB, ivS.length, encrypted.length);
		return new String(Base64Util.encode(newB));
	}

	/**
	 * aes cfb 模式解密
	 * 
	 * @param content
	 *            需要解密内容
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String content, byte[] key) throws Exception {
		try {
			byte[] raw = key;
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
			byte[] encrypted = Base64Util.decode(content.getBytes());
			byte[] ivS = new byte[16];
			System.arraycopy(encrypted, 0, ivS, 0, 16);
			byte[] encrypted1 = new byte[encrypted.length - 16];
			System.arraycopy(encrypted, 16, encrypted1, 0,
					encrypted.length - 16);
			IvParameterSpec iv = new IvParameterSpec(ivS);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}
}