package com.whty.efs.common.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	// private final Logger logger = LoggerFactory.getLogger(Encryptor.class);

	/**
	 * 加密算法
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年9月28日 下午1:57:58
	 */
	// public enum Algorithm {
	// DES, DESede, Blowfish
	// }

	/**
	 * 块加密模式
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年9月28日 下午2:38:11
	 */
	// public enum BlockCypherMode {
	// /**
	// * 电子密码本（Electronic Code Book）
	// */
	// ECB,
	// /**
	// * 密码块链接（Cipher Block Chaining）
	// */
	// CBC,
	// /**
	// * 密码反馈（Cipher Feedback）
	// */
	// CFB,
	// /**
	// * 输出反馈（Output Feedback）
	// */
	// OFB
	// }

	@SuppressWarnings("unused")
	private Cipher buildDesCipher(byte[] key, int opmode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		return buildCipher(key, "DES", "DES/ECB/NoPadding", opmode);
	}

	private Cipher buildDesedeCipher(byte[] key, int opmode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

		byte[] md5 = md5(key);
		byte[] _key = new byte[24];
		System.arraycopy(md5, 0, _key, 0, 16);
		System.arraycopy(md5, 0, _key, 16, 8);

		Cipher cipher = buildCipher(_key, "DESede", "DESede/ECB/NoPadding", opmode);

		return cipher;
	}

	private Cipher buildCipher(byte[] key, String algorithm, String transformation, int opmode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

		// 构造安全密钥实例
		SecretKey secretKey = new SecretKeySpec(key, algorithm);

		// 构造加密器
		Cipher cipher = Cipher.getInstance(transformation);
		// byte[] biv = new byte[8];
		// System.arraycopy(key, 0, biv, 0, 8);
		// IvParameterSpec iv = new IvParameterSpec(biv);

		// 初始化加密器
		cipher.init(opmode, secretKey);

		return cipher;
	}

	/**
	 * 加密<br>
	 * 1.固定加密算法/模式/填充方式（DESede/ECB/NoPadding）的加密方法。<br>
	 * 2.密钥必须是长度为24的字节数组<br>
	 * 3.明文字节数组长度不是8的倍数，末尾用0填充补齐
	 * 
	 * @param key
	 * @param plaintext
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.InvalidKeyException
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws java.security.InvalidAlgorithmParameterException
	 * @date 2014年9月28日 下午2:49:31
	 */
	public byte[] encrypt(byte[] key, byte[] plaintext) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// 构造加密器
		Cipher cipher = buildDesedeCipher(key, Cipher.ENCRYPT_MODE);
		// 填充
		plaintext = padding(plaintext);
		// 加密
		byte[] ciphertext = cipher.doFinal(plaintext);
		// 编码
		byte[] result = encodeBase64(ciphertext);
		// 输出
		return result;
	}

	/**
	 * 解密<br>
	 * 1.固定加密算法/模式/填充方式（DESede/ECB/NoPadding）的解密方法。<br>
	 * 2.密钥必须是长度为24的字节数组<br>
	 * 3.自动剔除密文字节数组末尾的(byte)0字节
	 * 
	 * @param key
	 * @param ciphertext
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.InvalidKeyException
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws java.security.InvalidAlgorithmParameterException
	 * @date 2014年9月28日 下午2:49:40
	 */
	public byte[] decrypt(byte[] key, byte[] ciphertext) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// 构造加密器
		Cipher cipher = buildDesedeCipher(key, Cipher.DECRYPT_MODE);
		// 反编码
		byte[] text = decodeBase64(ciphertext);
		// 解密
		byte[] plaintext = cipher.doFinal(text);
		// 反填充
		plaintext = unpadding(plaintext);
		// 输出
		return plaintext;
	}

	/**
	 * 补齐填充
	 * 
	 * @param plaintext
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年9月28日 下午2:59:35
	 */
	private byte[] padding(byte[] plaintext) {
		if (null == plaintext) {
			return null;
		}
		int length = plaintext.length;
		int k = plaintext.length % 8;
		if (k == 0) {
			return plaintext;
		}

		byte[] dest = new byte[length + 8 - k];
		System.arraycopy(plaintext, 0, dest, 0, length);
		return dest;
	}

	/**
	 * 反补齐填充
	 * 
	 * @param ciphertext
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年9月28日 下午2:59:58
	 */
	private byte[] unpadding(byte[] ciphertext) {
		if (null == ciphertext) {
			return null;
		}
		int length = 0;
		for (int i = ciphertext.length - 1; i > -1; i--) {
			if (ciphertext[i] != 0) {
				length = i + 1;
				break;
			}
		}

		if (length == ciphertext.length) {
			return ciphertext;
		}

		byte[] dest = new byte[length];
		System.arraycopy(ciphertext, 0, dest, 0, length);
		return dest;
	}

	private byte[] encodeBase64(byte[] b) {
		if (b == null) {
			return null;
		}

		if (b.length == 0) {
			return new byte[0];
		}

		byte[] res = new byte[b.length * 2];
		for (int i = 0; i < b.length; i++) {
			toArray(b[i], res, i * 2);
		}
		return res;
	}

	private byte[] decodeBase64(byte[] b) {
		if (b == null) {
			return null;
		}

		if (b.length == 0) {
			return new byte[0];
		}

		byte[] res = new byte[b.length / 2];
		for (int i = 0; i < res.length; i++) {
			toByte(b, res, i);
		}
		return res;
	}

	/**
	 * 字节转字节数组
	 * 
	 * @param b
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月9日 下午3:20:22
	 */
	private void toArray(byte b, byte[] out, int index) {
		int tmp = b & 0xFF;
		out[index + 1] = (byte) int2byte(tmp & 0xF);
		if (tmp > 9) {
			out[index] = (byte) int2byte(tmp >> 4);
		} else {
			out[index] = 48;
		}
	}

	private int int2byte(int i) {
		return (i < 10) ? (48 + i) : (87 + i);
	}

	/**
	 * 字节数组转字节
	 * 
	 * @param b
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月9日 下午3:20:56
	 */
	private void toByte(byte[] b, byte[] out, int index) {
		int end = byte2int(b[index * 2 + 1]);
		if (b[index * 2] != 48) {
			end += (byte2int(b[index * 2]) << 4);
		}
		out[index] = (byte) end;
	}

	private int byte2int(byte i) {
		return (i < 87) ? (i - 48) : (i - 87);
	}

	private byte[] md5(byte[] txt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(txt);
		return md.digest();
	}

	public static String toString(byte[] data) {
		if (null == data) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < data.length; i++) {
				sb.append(data[i]).append(',');
			}
			return sb.toString();
		}
	}

	public static byte[] padding80(byte[] b) {
		if (b == null)
			return null;

		int len = b.length;
		int mode = len % 8;

		byte[] dest = new byte[len + 8 - mode];

		System.arraycopy(b, 0, dest, 0, len);
		dest[len] = (byte) 0x80;
		return dest;
	}
	
	public static byte[] unpadding80(byte[] b) {
		if (b == null)
			return null;

		int length = 0;
		for(int i=b.length -1;i>-1;i--){
			if(b[i] == (byte)0x80){
				length = i;
				break;
			}
		}

		byte[] dest = new byte[length];
		System.arraycopy(b, 0, dest, 0, length);
		return dest;
	}

	/**
	 * 加密
	 * 
	 * @param icv
	 * @param key
	 * @param inputData
	 * @return
	 * @throws SecurityException
	 */
	public static byte[] descbcEncrypt(byte[] icv, byte[] key, byte[] inputData) throws SecurityException {
		return descbc(icv, key, Cipher.ENCRYPT_MODE, inputData);
	}

	/**
	 * 解密
	 * 
	 * @param icv
	 * @param key
	 * @param inputData
	 * @return
	 * @throws SecurityException
	 */
	public static byte[] descbcDecrypt(byte[] icv, byte[] key, byte[] inputData) throws SecurityException {
		return descbc(icv, key, Cipher.DECRYPT_MODE, inputData);
	}

	private static byte[] descbc(byte[] icv, byte[] key, int mode, byte[] inputData) throws SecurityException {
		try {
			SecretKey keySpec = null;

			Cipher enc = null;

			// 判断密钥长度
			if (key.length == 8) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(key, "DES");
				// key 生成算法
				enc = Cipher.getInstance("DES/CBC/NoPadding");
			} else if (key.length == 16) {
				// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
				byte[] key1 = new byte[24];
				System.arraycopy(key, 0, key1, 0, 16);
				System.arraycopy(key, 0, key1, 16, 8);
				//System.out.println("KEY:" + KmsUtils.byteArray2String(key1));
				keySpec = new SecretKeySpec(key1, "DESede");
				// 将key前8个字节复制到keycbc的最后8个字节生成算法
				enc = Cipher.getInstance("DESede/CBC/NoPadding");
			} else if (key.length == 24) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(key, "DESede"); // key
				// 生成算法
				enc = Cipher.getInstance("DESede/CBC/NoPadding");
			} else {
				throw new SecurityException("密钥长度错误");
			}
			//System.out.println("ICV:" + KmsUtils.byteArray2String(icv));
			// 生成ICV
			IvParameterSpec ivSpec = new IvParameterSpec(icv); // icv

			// 初始化
			enc.init(mode, keySpec, ivSpec);
			//System.out.println("Dat:" + KmsUtils.byteArray2String(inputData));
			byte[] Txt = enc.doFinal(inputData); // 开始计算
			//System.out.println("Txt:" + KmsUtils.byteArray2String(Txt));
			// 返回加解密结果
			return Txt;

		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (NoSuchPaddingException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new SecurityException(e);
		} catch (IllegalBlockSizeException e) {
			throw new SecurityException(e);
		} catch (BadPaddingException e) {
			throw new SecurityException(e);
		}
	}
}
