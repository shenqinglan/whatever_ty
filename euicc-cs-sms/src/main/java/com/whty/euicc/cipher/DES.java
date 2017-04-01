package com.whty.euicc.cipher;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DES {
	public static String cipherAlgorithm = "";
	public static final String KEY_ALGORITH = "DES";

	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm);

		cipher.init(2, k);

		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, byte[] key, byte[] IV)
			throws Exception {
		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm);

		cipher.init(2, k, new IvParameterSpec(IV));

		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm);

		cipher.init(1, k);

		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, byte[] key, byte[] IV)
			throws Exception {
		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(cipherAlgorithm);

		cipher.init(1, k, new IvParameterSpec(IV));

		return cipher.doFinal(data);
	}

	public static String getCipherAlgorithm() {
		return cipherAlgorithm;
	}

	public static byte[] initkey(int keylength) throws Exception {
		SecretKey secretKey = null;
		switch (keylength) {
		case 56:
			KeyGenerator kg56 = KeyGenerator.getInstance("DES");

			kg56.init(56);

			secretKey = kg56.generateKey();
			break;
		case 64:
			Security.addProvider(new BouncyCastleProvider());

			KeyGenerator kg64 = KeyGenerator.getInstance("DES");

			kg64.init(64);

			secretKey = kg64.generateKey();
		}

		return secretKey.getEncoded();
	}

	public static void setCipherAlgorithm(String cipherAlgorithm) {
		DES.cipherAlgorithm = cipherAlgorithm;
	}

	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		SecretKey secretkey = keyFactory.generateSecret(dks);
		return secretkey;
	}
}