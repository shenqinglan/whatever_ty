package com.whty.euicc.cipher;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
 * AES加解密算法
 * @author Administrator
 *
 */
@Deprecated
public class AES {
	public static String cipherAlgorithm = "";
	public static final String KEY_ALGORITH = "AES";

	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm, "BC");

		cipher.init(2, k);

		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, byte[] key, byte[] IV)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm, "BC");

		cipher.init(2, k, new IvParameterSpec(IV));

		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm, "BC");

		cipher.init(1, k);

		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, byte[] key, byte[] IV)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm, "BC");

		cipher.init(1, k, new IvParameterSpec(IV));

		return cipher.doFinal(data);
	}

	public static String getCipherAlgorithm() {
		return cipherAlgorithm;
	}

	public static byte[] initkey(int keylength) throws Exception {
		SecretKey secretKey = null;
		switch (keylength) {
		case 128:
			KeyGenerator kg128 = KeyGenerator.getInstance("AES");

			kg128.init(128);

			secretKey = kg128.generateKey();
			break;
		case 192:
			KeyGenerator kg192 = KeyGenerator.getInstance("AES");

			kg192.init(192);

			secretKey = kg192.generateKey();
			break;
		case 256:
			KeyGenerator kg256 = KeyGenerator.getInstance("AES");

			kg256.init(256);

			secretKey = kg256.generateKey();
		}
		return secretKey.getEncoded();
	}

	public static void setCipherAlgorithm(String cipherAlgorithm) {
		AES.cipherAlgorithm = cipherAlgorithm;
	}

	private static Key toKey(byte[] key) throws CipherException {
		try {
			return new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			throw new CipherException("Error Key: " + e.getMessage());
		}
	}
}
