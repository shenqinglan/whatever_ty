package com.whty.euicc.cipher;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
 * des加解密
 * @author Administrator
 *
 */
public class DESede {
	public static String cipherAlgorithm = "";
	public static final String KEY_ALGORITH = "DESede";

	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(getCipherAlgorithm(), "BC");

		cipher.init(2, k);

		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data, byte[] key, byte[] IV)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(getCipherAlgorithm(), "BC");

		cipher.init(2, k, new IvParameterSpec(IV));

		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(getCipherAlgorithm(), "BC");

		cipher.init(1, k);

		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, byte[] key, byte[] IV)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		Key k = toKey(key);

		Cipher cipher = Cipher.getInstance(getCipherAlgorithm(), "BC");

		cipher.init(1, k, new IvParameterSpec(IV));

		return cipher.doFinal(data);
	}

	public static String getCipherAlgorithm() {
		return cipherAlgorithm;
	}

	public static byte[] initkey(int keylength) throws Exception {
		SecretKey secretKey = null;
		switch (keylength) {
		case 112:
			KeyGenerator kg112 = KeyGenerator.getInstance("DESede");

			kg112.init(112);

			secretKey = kg112.generateKey();
			break;
		case 168:
			KeyGenerator kg168 = KeyGenerator.getInstance("DESede");

			kg168.init(168);

			secretKey = kg168.generateKey();
			break;
		case 128:
			Security.addProvider(new BouncyCastleProvider());

			KeyGenerator kg128 = KeyGenerator.getInstance("DESede", "BC");

			kg128.init(128);

			secretKey = kg128.generateKey();
			break;
		case 192:
			Security.addProvider(new BouncyCastleProvider());

			KeyGenerator kg192 = KeyGenerator.getInstance("DESede", "BC");

			kg192.init(192);

			secretKey = kg192.generateKey();
		}
		return secretKey.getEncoded();
	}

	public static void setCipherAlgorithm(String cipherAlgorithm) {
		DESede.cipherAlgorithm = cipherAlgorithm;
	}

	private static Key toKey(byte[] key) throws Exception {
		byte[] dealKey = new byte[24];
		if (16 == key.length) {
			for (int i = 0; i < key.length; i++) {
				dealKey[i] = key[i];
			}
			for (int j = 0; j < 8; j++) {
				dealKey[(key.length + j)] = key[j];
			}
			DESedeKeySpec dks = new DESedeKeySpec(dealKey);

			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");

			return keyFactory.generateSecret(dks);
		} else {
			DESedeKeySpec dks = new DESedeKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");

			return keyFactory.generateSecret(dks);
		}
	}
}
