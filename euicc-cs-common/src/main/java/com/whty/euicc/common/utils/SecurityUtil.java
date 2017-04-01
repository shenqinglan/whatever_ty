package com.whty.euicc.common.utils;

/**
 * Created by IntelliJ IDEA.
 * User: fibiger
 * Date: 2009-05-05
 * Time: 10:18:48
 * To change this template use File | Settings | File Templates.
 */

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * 安全工具类
 * @author Administrator
 *
 */
public class SecurityUtil {

	private static final String key = "CF2D0B9CCBC803FF89CB3FCAF54B61E9";
	private static final Charset UTF8 = Charset.forName("UTF-8");

	/**
	 * 将byte数组转换成16进制组成的字符串 例如 一个byte数组 b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	 * byte2hex(b); 将返回一个字符串"0710BE8716FB"
	 * 
	 * @param bytes
	 *            待转换的byte数组
	 * @return
	 */
	public static String bytesToHexString(byte[] bytes) {
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

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encodeHexString(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		final String hexString = "0123456789abcdef";
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String hexStringToString(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		final String hexString = "0123456789abcdef";
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray(), UTF8);
	}

	public static byte[] hexToBytes(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return buffer;
	}

	/**
	 * 将整数转换为16进制的n个字节
	 * 
	 * @param in
	 *            待转换的16进制字符串
	 * @return
	 */
	public static byte[] integer2Bytes(int in, int n) {
		byte[] bb = new byte[n];
		for (int i = 0; i < n; i++) {
			bb[i] = (byte) ((in >> (8 * (n - 1 - i))) & 0xff);
		}
		return bb;
	}

	/**
	 * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
	 * 
	 * @param val
	 *            待转换整数
	 * @param len
	 *            指定长度
	 * @return String
	 */
	public static String Int2HexStr(int val, int len) {
		String result = Integer.toHexString(val).toUpperCase();
		int r_len = result.length();
		if (r_len > len) {
			return result.substring(r_len - len, r_len);
		}
		if (r_len == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		for (int i = 0; i < len - r_len; i++) {
			strBuff.insert(0, '0');
		}
		return strBuff.toString();
	}

	/**
	 * 将长整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
	 * 
	 * @param val
	 *            待转换长整数
	 * @param len
	 *            指定长度
	 * @return String
	 */
	public static String Long2HexStr(long val, int len) {
		String result = Long.toHexString(val).toUpperCase();
		int r_len = result.length();
		if (r_len > len) {
			return result.substring(r_len - len, r_len);
		}
		if (r_len == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		for (int i = 0; i < len - r_len; i++) {
			strBuff.insert(0, '0');
		}
		return strBuff.toString();
	}

	/**
	 * 将整数转换成byte数组 例如: 输入: n = 1000000000 ( n = 0x3B9ACA00) 输出: byte[0]:3b
	 * byte[1]:9a byte[2]:ca byte[3]:00 注意: 输入的整数必须在[-2^32, 2^32-1]的范围内
	 * 
	 * @param n
	 *            整型值
	 * @return byte数组
	 */
	public static byte[] int2Bytes(int n) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(n);
		return bb.array();
	}

	/**
	 * 将长整数转换成byte数组 例如: 输入: n = 1000000000 ( n = 0x3B9ACA00) 输出: byte[0]:3b
	 * byte[1]:9a byte[2]:ca byte[3]:00 注意: 输入的长整数必须在[-2^64, 2^64-1]的范围内
	 * 
	 * @param l
	 *            长整型值
	 * @return byte数组
	 */
	public static byte[] long2Bytes(long l) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(l);
		return bb.array();
	}

	/**
	 * 将无符号的4个字节的byte数据转换成32bit的长整形
	 * 
	 * @param buf
	 *            无符号的4个字节数据
	 * @return 32bit的长整形
	 */
	public static long unsigned4BytesToInt(byte[] buf) {
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		firstByte = (0x000000FF & (buf[0]));
		secondByte = (0x000000FF & (buf[1]));
		thirdByte = (0x000000FF & (buf[2]));
		fourthByte = (0x000000FF & (buf[3]));
		return ((firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
	}

	/**
	 * 生成len个字节的十六进制随机数字符串 例如:len=8 则可能会产生 DF15F0BDFADE5FAF 这样的字符串
	 * 
	 * @param len
	 *            待产生的字节数
	 * @return String
	 */
	public static String yieldHexRand(int len) {
		StringBuffer strBufHexRand = new StringBuffer();
		Random rand = new Random(System.currentTimeMillis());
		int index;
		// 随机数字符
		char charArrayHexNum[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'0', 'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0; i < len * 2; i++) {
			index = Math.abs(rand.nextInt()) % 16;
			if (i == 0) {
				while (charArrayHexNum[index] == '0') {
					index = Math.abs(rand.nextInt()) % 16;
				}
			}
			strBufHexRand.append(charArrayHexNum[index]);
		}
		return strBufHexRand.toString();
	}

	/**
	 * 产生8字节的时间戳
	 * 
	 * @param l
	 *            时间
	 * @return 8字节的时间戳
	 */
	public static byte[] generate8bytetime(Long l) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(l);
		return bb.array();
	}

	/**
	 * 按位异或byte数组 (两个byte数组的长度必须一样)
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static String doXOR(String b1, String b2) {
		if (b1.length() != b2.length()) {
			return null;
		}

		byte[] byte1 = str2bytes(b1);
		byte[] byte2 = str2bytes(b2);

		byte[] result = new byte[byte1.length];
		for (int i = 0; i < byte1.length; i++) {
			int temp = (byte1[i] ^ byte2[i]) & 0xff;
			result[i] = (byte) temp;
		}
		return bytesToHexString(result).toUpperCase();
	}

	/**
	 * 按位求反byte数组
	 * 
	 * @param b
	 * @return
	 */
	public static String doReverse(String b) {
		byte[] byte1 = str2bytes(b);
		for (int i = 0; i < byte1.length; i++) {
			byte1[i] = (byte) (~byte1[i] & 0xff);
		}
		return bytesToHexString(byte1).toUpperCase();
	}

	/**
	 * 将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同
	 * 
	 * @param key
	 *            待转换密钥(16个字节 由2个8字节密钥组成)
	 * @return
	 */
	public static String key16To24(String key) {
		// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同
		if (key.length() == 32) {
			return key + key.substring(0, 16); // 将key前8个字节复制到keyresult的最后8个字节
		} else {
			return null;
		}
	}

	/**
	 * CBC模式中的DES/3DES/TDES算法(数据需要填充80),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	 * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	 * 
	 * @param key
	 *            加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	 *            24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	 * @param data
	 *            待加/解密数据
	 * @param icv
	 *            初始链值(8个字节) 一般为8字节的0x00 icv="0000000000000000"
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 */
	public static String descbcNeedPadding80(String key, String data,
			String icv, int mode) {
		return descbc(key, padding80(data), icv, mode);
	}

	/**
	 * ECB模式中的DES/3DES/TDES算法(数据需要填充80),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	 * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	 * 
	 * @param key
	 *            加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	 *            24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	 * @param data
	 *            待加/解密数据
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 * @throws SecurityException
	 */
	public static String desecbNeedPadding80(String key, String data, int mode)
			throws SecurityException {
		return desecb(key, padding80(data), mode);
	}

	/**
	 * ECB模式中的DES算法(数据不需要填充)
	 * 
	 * @param key
	 *            加解密密钥(8个字节)
	 * @param data
	 *            输入:待加/解密数据(长度必须是8的倍数) 输出:加/解密后的数据
	 * @param mode
	 *            0-加密，1-解密
	 * @return
	 * @throws SecurityException
	 */
	public static void des(byte[] key, byte[] data, int mode)
			throws SecurityException {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;

			// 生成安全密钥
			SecretKey keySpec = new SecretKeySpec(key, "DES");// key

			// 生成算法
			Cipher enc = Cipher.getInstance("DES/ECB/NoPadding");

			// 初始化
			enc.init(opmode, keySpec);

			// 加解密结果
			byte[] temp = enc.doFinal(data); // 开始计算

			System.arraycopy(temp, 0, data, 0, temp.length); // 将加解密结果复制一份到data
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);

		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (NoSuchPaddingException e) {
			throw new SecurityException(e);
		} catch (IllegalBlockSizeException e) {
			throw new SecurityException(e);
		} catch (BadPaddingException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * 产生MAC算法3,即Single DES加上最终的TDES MAC,支持8、16字节的密钥 这也叫Retail
	 * MAC,它是在[ISO9797-1]中定义的MAC算法3，带有输出变换3、没有截断，并且用DES代替块加密
	 * 
	 * @param key
	 *            密钥(8字节:CBC/DES算法 16字节:先CBC/DES，再完成3DES/TDES算法)
	 * @param data
	 *            要计算MAC的数据
	 * @param icv
	 *            初始链向量 (8个字节) 一般为8字节的0x00 icv="0000000000000000"
	 * @param padding
	 *            0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80
	 *            (GP卡用)3:填充0x05（卡门户应用）
	 * @param outlength
	 *            返回的MAC长度 1：4个字节 2：8个字节
	 * @return
	 * @throws SecurityException
	 */
	public static String generateMAC(String key, String data, String icv,
			int padding, int outlength) throws SecurityException {

		if (key.length() == 32 || key.length() == 16) {
			byte[] leftKey = new byte[8];
			byte[] rightKey = new byte[8];
			System.arraycopy(str2bytes(key), 0, leftKey, 0, 8); // 将key复制一份到leftKey

			byte[] icvTemp = str2bytes(icv); // 将icv复制一份到icvTemp

			// 实际参与计算的数据
			byte[] dataTemp = null;

			if (padding == 0) {
				dataTemp = str2bytes(padding00(data)); // 填充0x00
			} else if (padding == 1) {
				dataTemp = str2bytes(padding20(data)); // 填充0x20
			} else if (padding == 2) {
				dataTemp = str2bytes(padding80(data)); // 填充0x80
			} else if (padding == 3) {
				dataTemp = str2bytes(padding05(data));
			}// 填充0x05

			int nCount = dataTemp.length / 8;
			for (int i = 0; i < nCount; i++) {
				for (int j = 0; j < 8; j++)
					// 初始链值与输入数据异或
					icvTemp[j] ^= dataTemp[i * 8 + j];
				des(leftKey, icvTemp, 0); // DES加密
			}
			if (key.length() == 32) // 如果key的长度是16个字节
			{
				System.arraycopy(str2bytes(key), 8, rightKey, 0, 8); // 将key复制一份到rightKey
				des(rightKey, icvTemp, 1); // DES解密
				des(leftKey, icvTemp, 0); // DES加密
			}
			String mac = (bytesToHexString(icvTemp)).toUpperCase();
			return (outlength == 1 && padding != 1) ? mac.substring(0, 8) : mac;// 返回结果
		} else {
			return null;
		}
	}

	/**
	 * 产生MAC算法1,即Full TDES MAC,支持16、24字节的密钥 这也叫完整的TDES
	 * MAC,它是在[ISO9797-1]中定义的MAC算法1，带有输出变换1，没有截断，并且用TDES代替块加密。
	 * 
	 * @param key
	 *            密钥(16字节:3DES/TDES算法 24字节:3DES/TDES算法)
	 * @param data
	 *            要计算MAC的数据
	 * @param icv
	 *            初始链向量 (8个字节) 一般为8字节的0x00 icv="0000000000000000"
	 * @param padding
	 *            0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80 (GP卡用)
	 * @param outlength
	 *            返回的MAC长度 1：4个字节 2：8个字节
	 * @return
	 * @throws SecurityException
	 */
	public static String generateMACAlg1(String key, String data, String icv,
			int padding, int outlength) throws SecurityException {

		if (key.length() == 32 || key.length() == 48) {
			byte[] icvTemp = str2bytes(icv); // 将icv复制一份到icvTemp

			// 实际参与计算的数据
			byte[] dataTemp = null;

			if (padding == 0) {
				dataTemp = str2bytes(padding00(data)); // 填充0x00
			} else if (padding == 1) {
				dataTemp = str2bytes(padding20(data)); // 填充0x20
			} else {
				dataTemp = str2bytes(padding80(data)); // 填充0x80
			}

			int nCount = dataTemp.length / 8;
			for (int i = 0; i < nCount; i++) {
				for (int j = 0; j < 8; j++)
					// 初始链值与输入数据异或
					icvTemp[j] ^= dataTemp[i * 8 + j];
				String resulticv = desecb(key, bytesToHexString(icvTemp), 0); // 3DES/TDES加密
				System.arraycopy(str2bytes(resulticv), 0, icvTemp, 0, 8); // 将icv复制一份到icvTemp
			}
			String mac = (bytesToHexString(icvTemp)).toUpperCase();
			return (outlength == 1 && padding != 1) ? mac.substring(0, 8) : mac;// 返回结果
		} else {
			return null;
		}

	}

	/**
	 * 产生MAC算法2,即RIPEMD-MAC,支持8、16字节的密钥 这也叫RIPEMD-MAC(RIPEMD-MAC [RIPE 93] +
	 * EMAC (DMAC) [Petrank-Rackoff 98]),
	 * 它是在[ISO9797-1]中定义的MAC算法2，带有输出变换2、没有截断，并且用DES代替块加密
	 * 
	 * @param key
	 *            密钥(8字节:CBC/DES算法 16字节:先CBC/DES，再完成3DES/TDES算法)
	 * @param data
	 *            要计算MAC的数据
	 * @param icv
	 *            初始链向量 (8个字节) 一般为8字节的0x00 icv="0000000000000000"
	 * @param padding
	 *            0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80 (GP卡用)
	 * @param outlength
	 *            返回的MAC长度 1：4个字节 2：8个字节
	 * @return
	 * @throws SecurityException
	 */
	public static String generateMACAlg2(String key, String data, String icv,
			int padding, int outlength) throws SecurityException {

		if (key.length() == 32 || key.length() == 16) {
			byte[] leftKey = new byte[8];
			byte[] rightKey = new byte[8];
			System.arraycopy(str2bytes(key), 0, leftKey, 0, 8); // 将key复制一份到leftKey

			byte[] icvTemp = str2bytes(icv); // 将icv复制一份到icvTemp

			// 实际参与计算的数据
			byte[] dataTemp = null;

			if (padding == 0) {
				dataTemp = str2bytes(padding00(data)); // 填充0x00
			} else if (padding == 1) {
				dataTemp = str2bytes(padding20(data)); // 填充0x20
			} else {
				dataTemp = str2bytes(padding80(data)); // 填充0x80
			}

			int nCount = dataTemp.length / 8;
			for (int i = 0; i < nCount; i++) {
				for (int j = 0; j < 8; j++)
					// 初始链值与输入数据异或
					icvTemp[j] ^= dataTemp[i * 8 + j];
				des(leftKey, icvTemp, 0); // DES加密
			}
			if (key.length() == 32) // 如果key的长度是16个字节
			{
				System.arraycopy(str2bytes(key), 8, rightKey, 0, 8); // 将key复制一份到rightKey
				des(rightKey, icvTemp, 0); // DES加密
			}
			String mac = (bytesToHexString(icvTemp)).toUpperCase();
			return (outlength == 1 && padding != 1) ? mac.substring(0, 8) : mac;// 返回结果
		} else {
			return null;

		}

	}

	/**
	 * 产生MAC算法4,支持16、24字节的密钥
	 * 这也叫Mac-DES,它是在[ISO9797-1]中定义的MAC算法4，带有输出变换4，没有截断，并且用DES代替块加密。
	 * 
	 * @param key
	 *            密钥(16字节:3DES/TDES算法 24字节:3DES/TDES算法)
	 * @param data
	 *            要计算MAC的数据
	 * @param icv
	 *            初始链向量 (8个字节) 一般为8字节的0x00 icv="0000000000000000"
	 * @param padding
	 *            0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80 (GP卡用)
	 * @param outlength
	 *            返回的MAC长度 1：4个字节 2：8个字节
	 * @return
	 * @throws SecurityException
	 */
	public static String generateMACAlg4(String key, String data, String icv,
			int padding, int outlength) throws SecurityException {

		byte[] leftKey = new byte[8];
		byte[] middleKey = new byte[8];
		byte[] rightKey = new byte[8];

		if (key.length() == 48) {
			System.arraycopy(str2bytes(key), 16, rightKey, 0, 8); // 将key的最右边8个字节复制一份到rightKey
		} else if (key.length() == 32) {
			System.arraycopy(str2bytes(key), 8, rightKey, 0, 8); // 将key的最右边8个字节复制一份到rightKey
		} else {
			return null;
		}

		System.arraycopy(str2bytes(key), 0, leftKey, 0, 8); // 将key的最左边8个字节复制一份到leftKey
		System.arraycopy(str2bytes(key), 8, middleKey, 0, 8); // 将key的中间8个字节复制一份到middleKey

		byte[] icvTemp = str2bytes(icv); // 将icv复制一份到icvTemp

		// 实际参与计算的数据
		byte[] dataTemp = null;

		if (padding == 0) {
			dataTemp = str2bytes(padding00(data)); // 填充0x00
		} else if (padding == 1) {
			dataTemp = str2bytes(padding20(data)); // 填充0x20
		} else {
			dataTemp = str2bytes(padding80(data)); // 填充0x80
		}

		int nCount = dataTemp.length / 8;
		for (int i = 0; i < nCount; i++) {
			for (int j = 0; j < 8; j++)
				// 初始链值与输入数据异或
				icvTemp[j] ^= dataTemp[i * 8 + j];
			des(leftKey, icvTemp, 0); // DES加密
			if (i == 0)
				des(rightKey, icvTemp, 0); // DES加密
			if (i == nCount - 1)
				des(middleKey, icvTemp, 0); // DES加密
		}
		String mac = (bytesToHexString(icvTemp)).toUpperCase();
		return (outlength == 1 && padding != 1) ? mac.substring(0, 8) : mac;// 返回结果

	}

	/**
	 * 生成CRC32
	 * 
	 * @param data
	 *            待处理数据
	 * @param strinitcrc
	 *            长度必须为8
	 * @param lastblock
	 *            1:最后取反
	 * @return 8个字节的CRC
	 */
	public static String generateCRC32(byte[] data, byte[] strinitcrc,
			int lastblock) {
		long crc32 = unsigned4BytesToInt(strinitcrc);
		for (int i = 0; i < data.length; i++)
			crc32 = lGenCRC32(crc32, data[i]);
		if (lastblock == 1)
			crc32 = ~crc32;
		return Long2HexStr(crc32, 8);
	}

	/**
	 * 生成CRC32
	 * 
	 * @param lOldCRC
	 *            the crc32 value
	 * @param ByteVal
	 *            the new data byte
	 * @return
	 */
	public static long lGenCRC32(long lOldCRC, byte ByteVal) {
		long TabVal;
		int j;
		TabVal = ((lOldCRC) ^ ByteVal) & 0xff;
		for (j = 8; j > 0; j--) {
			if ((TabVal & 1) == 1) {
				TabVal = (TabVal >> 1) ^ 0xEDB88320L;
			} else {
				TabVal >>= 1;
			}
		}
		return TabVal ^ (((lOldCRC) >> 8) & 0x00FFFFFFL);
	}

	/**
	 * SHA-1摘要
	 * 
	 * @param data
	 *            要计算SHA-1摘要的数据
	 * @return 16进制字符串形式的SHA-1消息摘要，一般为20字节 为null表示操作失败
	 * @throws java.security.NoSuchAlgorithmException
	 */
	public static String generateSHA1(String data)
			throws NoSuchAlgorithmException {

		// 使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

		// 开始使用算法
		messageDigest.update(str2bytes(data));

		// 输出算法运算结果
		byte[] hashValue = messageDigest.digest(); // 20位字节

		return (bytesToHexString(hashValue)).toUpperCase();

	}

	public static byte[] generateSHA1(byte[] data)
			throws NoSuchAlgorithmException {

		// 使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

		// 开始使用算法
		messageDigest.update(data);

		// 输出算法运算结果
		byte[] hashValue = messageDigest.digest(); // 20位字节

		return hashValue;

	}

	/**
	 * RSA签名
	 * 
	 * @param N
	 *            RSA的模modulus
	 * @param E
	 *            RSA公钥的指数exponent
	 * @param D
	 *            RSA私钥的指数exponent
	 * @param data
	 *            输入数据
	 * @param mode
	 *            0-加密，1-解密
	 * @param type
	 *            0-私钥加密，公钥解密 1-公钥加密，私钥解密
	 * @return 签名后的数据 为null表示操作失败
	 * @throws SecurityException
	 */
	public static String generateRSA(String N, String E, String D, String data,
			int mode, int type) throws SecurityException {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			BigInteger big_N = new BigInteger(N);
			Key key = null;

			if (mode != type) { // 生成公钥
				BigInteger big_E = new BigInteger(E);
				KeySpec keySpec = new RSAPublicKeySpec(big_N, big_E);
				key = keyFactory.generatePublic(keySpec);
			} else { // 生成私钥
				BigInteger big_D = new BigInteger(D);
				KeySpec keySpec = new RSAPrivateKeySpec(big_N, big_D);
				key = keyFactory.generatePrivate(keySpec);
			}

			// 获得一个RSA的Cipher类，使用私钥加密
			Cipher cipher = Cipher.getInstance("RSA"); // RSA/ECB/PKCS1Padding

			// 初始化
			cipher.init(opmode, key);

			// 返回加解密结果
			return (bytesToHexString(cipher.doFinal(str2bytes(data))))
					.toUpperCase();// 开始计算
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (InvalidKeySpecException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (NoSuchPaddingException e) {
			throw new SecurityException(e);
		} catch (IllegalBlockSizeException e) {
			throw new SecurityException(e);
		} catch (BadPaddingException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * 生成带SHA-1摘要的RSA签名
	 * 
	 * @param N
	 *            RSA的模modulus
	 * @param D
	 *            RSA私钥的指数exponent
	 * @param data
	 *            输入数据
	 * @return 签名后的数据 为null表示操作失败
	 * @throws SecurityException
	 */
	public static String generateSHA1withRSA(String N, String D, String data)
			throws SecurityException {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			BigInteger big_N = new BigInteger(N);
			BigInteger big_D = new BigInteger(D);
			KeySpec keySpec = new RSAPrivateKeySpec(big_N, big_D);
			PrivateKey key = keyFactory.generatePrivate(keySpec);

			// 使用私钥签名
			Signature sig = Signature.getInstance("SHA1WithRSA"); // SHA1WithRSA
			sig.initSign(key);
			sig.update(str2bytes(data));

			// 返回加密结果
			return (bytesToHexString(sig.sign())).toUpperCase();// 开始计算
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (InvalidKeySpecException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (SignatureException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * 验证带SHA-1摘要的RSA签名
	 * 
	 * @param N
	 *            RSA的模modulus
	 * @param E
	 *            RSA公钥的指数exponent
	 * @param plaindata
	 *            原始数据
	 * @param signdata
	 *            签名数据
	 * @return 验证结果 true 验证成功 false 验证失败
	 * @throws SecurityException
	 */
	public static boolean verifySHA1withRSA(String N, String E,
			String plaindata, String signdata) throws SecurityException {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			BigInteger big_N = new BigInteger(N);
			BigInteger big_E = new BigInteger(E);
			KeySpec keySpec = new RSAPublicKeySpec(big_N, big_E);
			PublicKey key = keyFactory.generatePublic(keySpec);

			// 使用公钥验证
			Signature sig = Signature.getInstance("SHA1WithRSA"); // SHA1WithRSA
			sig.initVerify(key);
			sig.update(str2bytes(plaindata));

			// 返回验证结果
			return sig.verify(str2bytes(signdata));
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (InvalidKeySpecException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (SignatureException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * 生成带SHA-1摘要的RSA签名
	 * 
	 * @param key
	 *            DAP或者Token私钥 用PKCS#8编码
	 * @param data
	 *            要签名的原始数据
	 * @return 签名后的数据 为null表示操作失败
	 * @throws SecurityException
	 */
	public static String generateSHA1withRSA(String key, String data)
			throws SecurityException {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new PKCS8EncodedKeySpec(str2bytes(key)); // PKCS#8编码
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

			// 使用私钥签名
			Signature sig = Signature.getInstance("SHA1WithRSA"); // SHA1WithRSA
			sig.initSign(privateKey);
			sig.update(str2bytes(data));

			// 返回加密结果
			return (bytesToHexString(sig.sign())).toUpperCase();// 开始计算
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (InvalidKeySpecException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (SignatureException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * 验证带SHA-1摘要的RSA签名
	 * 
	 * @param key
	 *            Receipt公钥 用X.509编码
	 * @param plaindata
	 *            原始数据
	 * @param signdata
	 *            签名数据
	 * @return 验证结果 true 验证成功 false 验证失败
	 * @throws SecurityException
	 */
	public static boolean verifySHA1withRSA(String key, String plaindata,
			String signdata) throws SecurityException {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new X509EncodedKeySpec(
					SecurityUtil.str2bytes(key)); // X.509编码
			PublicKey publicKey = keyFactory.generatePublic(keySpec);

			// 使用公钥验证
			Signature sig = Signature.getInstance("SHA1WithRSA"); // SHA1WithRSA
			sig.initVerify(publicKey);
			sig.update(str2bytes(plaindata));

			// 返回验证结果
			return sig.verify(str2bytes(signdata));
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (InvalidKeySpecException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (SignatureException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * DAP计算，函数先对LOAD FILE(C4后面的不包括长度)计算hash(调用generateSHA1函数),所得结果20字节，
	 * 这个值可以放入install for load的HASH字段，再用DAP密钥对上一步的HASH内容进行Single DES Plus Final
	 * Triple DES算法(调用generateMAC函数) 或者RSA
	 * SSA-PKCS1-v1_5算法(调用generateSHA1withRSA函数),计算DAP。
	 * 
	 * @param key
	 *            算法1：DAP密钥 16字节 算法2：DAP私钥 用PKCS#8编码
	 * @param data
	 *            要计算的数据（C4后面的不包括长度）
	 * @param algorithm
	 *            要进行计算的算法 1：Single DES Plus Final Triple DES MAC(调用generateMAC)
	 *            2：RSA SSA-PKCS1-v1_5(调用generateSHA1withRSA)
	 * @return DAP数据 为null表示操作失败
	 * @throws SecurityException
	 * @throws java.security.NoSuchAlgorithmException
	 */
	public static String generateDAP(String key, String data, int algorithm)
			throws SecurityException, NoSuchAlgorithmException {
		return (algorithm == 2) ? generateSHA1withRSA(key, generateSHA1(data))
				: generateMAC(key, generateSHA1(data), "0000000000000000", 2, 2);
	}

	/**
	 * 生成密钥校验值。密钥校验值的计算采用3DES-ECB算法， 以16Bytes密钥对数据“0x00 00 00 00 00 00 00
	 * 00”进行加密操作，并左取计算结果的outlength字节作为密钥校验值
	 * 
	 * @param key
	 *            待校验密钥 暂时固定为16字节
	 * @param outlength
	 *            返回的校验值字节长度 一般为3字节
	 * @return 密钥校验值 为null表示操作失败
	 * @throws SecurityException
	 */
	public static String generateKeyCheck(String key, int outlength)
			throws SecurityException {

		String dataTemp = desecb(key, "0000000000000000", 0);
		if (dataTemp.length() >= outlength * 2) {
			return dataTemp.substring(0, outlength * 2);
		}

		return null;
	}

	/**
	 * 通过加密机接口获取安全通道密钥
	 * 
	 * @param keyVersion
	 *            密钥版本
	 * @param keyIndex
	 *            密钥索引 参见《手机支付系统OTA平台-加密机接口规范》A.5
	 * @param divNum
	 *            密钥分散级数，应大于等于0
	 * @param domainAID
	 *            安全域AID
	 * @param imsi
	 *            卡的IMSI
	 * @return 一组特定类型的密钥 map key：密钥类型 目前有：ENC(1)、MAC(2)、DEK(3)、DAP(4)、TOKEN(5
	 *         暂不支持)、RECEIPT(暂不支持)、KIC(0)、KID(0)、KIK(暂不支持) map
	 *         value：密钥值(暂时固定为16字节) 外部程序通过key获取的值为null表示通过加密机获取该类型的密钥失败
	 */
	public static Map<Integer, String> generateSecureDomainKey(int keyVersion,
			int keyIndex, int divNum, String domainAID, String imsi) {
		Map<Integer, String> sdKey = new HashMap<Integer, String>();

		// int algFlag = 0x82; //分散算法
		// int[] keysLen = new int[4];
		// byte[] keys = new byte[64];
		String strKey = "";
		// IMSI左补零补足8Bytes
		int imsiLength = 16 - imsi.length();
		for (int i = 0; i < imsiLength; i++)
			imsi = "0" + imsi;
		// 判断密钥索引
		switch (keyIndex) {
		case 0x41:// 0x41 Kic根密钥

			// KIC:0x0E
			String rightKic = Int2HexStr(keyVersion, 2) + "0E";
			int kicLength = 16 - rightKic.length();
			for (int i = 0; i < kicLength; i++)
				rightKic = "0" + rightKic;
			// 计算 IMSI 异或 （Key Version +Key类型）
			String kic = doReverse(doXOR(imsi, rightKic)); // 先异或再取反
			kicLength = 32 - kic.length();
			for (int i = 0; i < kicLength; i++)
				kic = "0" + kic;
			// 调用加密机接口
			// OTAHSM.OTAAPI.GenerateAndExportSDKey(keyVersion,keyIndex,algFlag,divNum,str2bytes(domainAID+kic),keysLen,keys);
			// strKey = bytesToHexString(keys);
			switch (keyVersion) {
			case 0x10:
				strKey = "C8C9045CBD795C5303FF2A5089104F3E";
				break;
			case 0x11:
				strKey = "86E9408747FF12E2C47B18F19D5ADD41";
				break;
			case 0x12:
				strKey = "16B96743F7D1E7BA90E4EF447C1D4146";
				break;
			case 0x13:
				strKey = "B2D19983940F5119161BE4289359D2B0";
				break;
			default:
				strKey = "C8C9045CBD795C5303FF2A5089104F3E";
			}
			sdKey.put(0, strKey);

			break;
		case 0x42:// 0x42 Kid根密钥

			// KID:0x0C
			String rightKid = Int2HexStr(keyVersion, 2) + "0C";
			int kidLength = 16 - rightKid.length();
			for (int i = 0; i < kidLength; i++)
				rightKid = "0" + rightKid;
			// 计算 IMSI 异或（Key Version +Key类型）
			String kid = doReverse(doXOR(imsi, rightKid)); // 先异或再取反
			kidLength = 32 - kid.length();
			for (int i = 0; i < kidLength; i++)
				kid = "0" + kid;
			// 调用加密机接口
			// OTAHSM.OTAAPI.GenerateAndExportSDKey(keyVersion,keyIndex,algFlag,divNum,str2bytes(domainAID+kid),keysLen,keys);
			// strKey = bytesToHexString(keys);
			switch (keyVersion) {
			case 0x10:
				strKey = "855D06477874A68EC145AB8707004597";
				break;
			case 0x11:
				strKey = "B5A4A9B3F81B8C2F888E09B4655CF2D6";
				break;
			case 0x12:
				strKey = "D8FE8115AB0E80E5F115EF69E06C3D1F";
				break;
			case 0x13:
				strKey = "61F825277B859A3827EE0A7071DABBD3";
				break;
			default:
				strKey = "855D06477874A68EC145AB8707004597";
			}
			sdKey.put(0, strKey);

			break;
		case 0x61:// 0x61 RFM根密钥
			break;
		case 0x81:// 0x81 业务下载根密钥
			break;
		case 0x21: // 0x21 安全域根密钥
		case 0x01: // 0x01 应用主控根密钥

			// S-ENC:0x0E
			String rightEnc = Int2HexStr(keyVersion, 2) + "0E";
			int encLength = 16 - rightEnc.length();
			for (int i = 0; i < encLength; i++)
				rightEnc = "0" + rightEnc;
			// 计算 IMSI 异或（Key Version +Key类型）
			String enc = doReverse(doXOR(imsi, rightEnc)); // 先异或再取反
			encLength = 32 - enc.length();
			for (int i = 0; i < encLength; i++)
				enc = "0" + enc;
			// 调用加密机接口
			// OTAHSM.OTAAPI.GenerateAndExportSDKey(keyVersion,keyIndex,algFlag,divNum,str2bytes(domainAID+enc),keysLen,keys);
			// strKey = bytesToHexString(keys);
			switch (keyVersion) {
			case 0x10:
				strKey = "0C450B7A3A77BF039C92627BE1F684AA";
				break;
			case 0x11:
				strKey = "4B3D365CD5501D2BB2A97B601E4EE2C1";
				break;
			case 0x12:
				strKey = "AF22C8AD892AA964512E4D95F14AFFF7";
				break;
			case 0x13:
				strKey = "940FA3C8BB51C37C97DE4C4DF8C18B47";
				break;
			default:
				strKey = "0C450B7A3A77BF039C92627BE1F684AA";
			}
			sdKey.put(1, strKey);

			// S-MAC:0x0C
			String rightMac = Int2HexStr(keyVersion, 2) + "0C";
			int macLength = 16 - rightMac.length();
			for (int i = 0; i < macLength; i++)
				rightMac = "0" + rightMac;
			// 计算 IMSI 异或（Key Version +Key类型）
			String mac = doReverse(doXOR(imsi, rightMac)); // 先异或再取反
			macLength = 32 - mac.length();
			for (int i = 0; i < macLength; i++)
				mac = "0" + mac;
			// OTAHSM.OTAAPI.GenerateAndExportSDKey(keyVersion,keyIndex,algFlag,divNum,str2bytes(domainAID+mac),keysLen,keys);
			// strKey = bytesToHexString(keys);
			switch (keyVersion) {
			case 0x10:
				strKey = "978BC1B22678CB97F35840E56125AEAF";
				break;
			case 0x11:
				strKey = "6AE2B35F6A62233FAC30D26A74E32919";
				break;
			case 0x12:
				strKey = "B7089CA8B4B667BD613EDF19F1A3CF42";
				break;
			case 0x13:
				strKey = "ABE9DB98BA738EBC4FBD5B1908D01FC2";
				break;
			default:
				strKey = "978BC1B22678CB97F35840E56125AEAF";
			}
			sdKey.put(2, strKey);

			// DEK:0x0D
			String rightDek = Int2HexStr(keyVersion, 2) + "0D";
			int dekLength = 16 - rightDek.length();
			for (int i = 0; i < dekLength; i++)
				rightDek = "0" + rightDek;
			// 计算 IMSI 异或（Key Version +Key类型）
			String dek = doReverse(doXOR(imsi, rightDek)); // 先异或再取反
			dekLength = 32 - dek.length();
			for (int i = 0; i < dekLength; i++)
				dek = "0" + dek;
			// 调用加密机接口
			// OTAHSM.OTAAPI.GenerateAndExportSDKey(keyVersion,keyIndex,algFlag,divNum,str2bytes(domainAID+dek),keysLen,keys);
			// strKey = bytesToHexString(keys);
			switch (keyVersion) {
			case 0x10:
				strKey = "73C89A3C8133EFEB053674FE76AB9185";
				break;
			case 0x11:
				strKey = "6DACA3224ACA8EA3E6BE0900854931FB";
				break;
			case 0x12:
				strKey = "515169A69A4F3D893EB9F076C8B75588";
				break;
			case 0x13:
				strKey = "ACF9218C602D8C7F074DC9519289E191";
				break;
			default:
				strKey = "73C89A3C8133EFEB053674FE76AB9185";
			}
			sdKey.put(3, strKey);

			// DAP:0x0A
			String rightDap = Int2HexStr(keyVersion, 2) + "0A";
			int dapLength = 16 - rightDap.length();
			for (int i = 0; i < dapLength; i++)
				rightDap = "0" + rightDap;
			// 计算 IMSI 异或（Key Version +Key类型）
			String dap = doReverse(doXOR(imsi, rightDap)); // 先异或再取反
			dapLength = 32 - dap.length();
			for (int i = 0; i < dapLength; i++)
				dap = "0" + dap;
			// 调用加密机接口
			// OTAHSM.OTAAPI.GenerateAndExportSDKey(keyVersion,keyIndex,algFlag,divNum,str2bytes(domainAID+dap),keysLen,keys);
			// strKey = bytesToHexString(keys);
			switch (keyVersion) {
			case 0x10:
				strKey = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
				break;
			case 0x11:
				strKey = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
				break;
			case 0x12:
				strKey = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
				break;
			case 0x13:
				strKey = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
				break;
			default:
				strKey = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
			}
			sdKey.put(4, strKey);

			break;
		case 0x02: // 0x02 应用维护根密钥
			break;
		case 0x03: // 0x03 消费根密钥
			break;
		case 0x04: // 0x04 充值根密钥
			break;
		case 0x05: // 0x05 TAC根密钥
			break;
		case 0x06: // 0x06 PIN重装根密钥
			break;
		case 0x07: // 0x07 空中充值根密钥
			break;
		default:
		}

		return sdKey;
	}

	/**
	 * 生成带MAc的apdu数据域
	 * 
	 * @param randomNum
	 *            16字节随机数
	 * @param keyMain
	 *            主密匙
	 * @param appData
	 *            应用数据
	 * @param timeMills
	 *            时间戳
	 * @param keyMac
	 *            Mac密匙
	 * @return 要透传的数据
	 * @throws SecurityException
	 */
	public static String generateTransMac(String randomNum, String keyMain,
			String command, byte[] timeMillis, String keyMac)
			throws SecurityException {
		String appdata = null;
		String command_head = null;
		if (command.length() < 256) {
			appdata = command.substring(10);
			command_head = command.substring(0, 10);
		} else {
			appdata = command.substring(12);
			command_head = command.substring(0, 12);
		}

		// 用主密匙keyMask使用3DES算法对16字节的随机数进行加密
		// //logger.debug("用主密钥加密随机数：" + randomNum);
		String processKey = SecurityUtil.desecb(keyMain, randomNum, 0);// 过程密钥
		// //logger.debug("获得过程密钥" + processKey);
		String mdata; // 加密后的密文
		String used_data; // N字节要发送的数据信息
		if (appdata != null)
			used_data = appdata + SecurityUtil.bytesToHexString(timeMillis);
		else {
			used_data = SecurityUtil.bytesToHexString(timeMillis);
		}

		// if(used_data.length()%16!=0){
		// 用过程密钥Key对“N字节要发送的数据信息+8字节的时间戳”进行3DES算法加密(填充80)

		// //logger.debug("用过程密钥加密数据：" + SecurityUtil.padding80(used_data));
		mdata = SecurityUtil.desecb(processKey,
				SecurityUtil.padding80(used_data), 0);// 过程密钥加密数据
		// //logger.debug("获得密文：" + mdata);
		// }else{
		// //用过程密钥Key对“N字节要发送的数据信息+8字节的时间戳”进行3DES算法加密(填充80)
		// logger.debug("用过程密钥加密数据："+used_data);
		// mdata=SecurityUtil.desecb(processKey,used_data,0);//过程密钥加密数据
		// logger.debug("获得密文："+mdata);
		// }

		// 随机数生成MAC过程密钥
		// //logger.debug("生成MAC过程密钥的随机数：" + randomNum);
		String processKey_mac = SecurityUtil.desecb(keyMac, randomNum, 0);
		// //logger.debug("随机数生成MAC过程密钥为：" + processKey_mac);

		String ICV = "0000000000000000";
		// 用MAC密钥计算data的MAC
		String MAC_data = command_head + mdata + randomNum;
		String mac;
		if (MAC_data.length() % 16 != 0) {
			// //logger.debug("用mac过程密钥加密数据：" +
			// SecurityUtil.padding80(MAC_data));
			mac = SecurityUtil.generateMAC(processKey_mac,
					SecurityUtil.padding80(MAC_data), ICV, 2, 1);
			// //logger.debug("获得4字节mac：" + mac);
		} else {
			// //logger.debug("用mac过程密钥加密数据：" + MAC_data);
			mac = SecurityUtil.generateMAC(processKey_mac, MAC_data, ICV, 2, 1);
			// //logger.debug("获得4字节mac：" + mac);
		}

		// 返回透传数据
		// //logger.debug("返回密文+mac:" + command_head + mdata + randomNum + mac);
		return command_head + mdata + randomNum + mac;

	}

	/**
	 * 生成带MAc的apdu数据域（传输数据，length是应用数据+时间戳+16字节随机数长度）
	 * 
	 * @param randomNum
	 *            16字节随机数
	 * @param keyMain
	 *            主密匙
	 * @param appData
	 *            应用数据
	 * @param timeMills
	 *            时间戳
	 * @param keyMac
	 *            Mac密匙
	 * @return 要透传的数据
	 * @throws SecurityException
	 */
	public static String generateTransLenMac(String randomNum, String keyMain,
			String command, byte[] timeMillis, String keyMac)
			throws SecurityException {
		String appdata = null;
		String command_head = null;
		if (command.length() < 256) {
			appdata = command.substring(10);
			command_head = command.substring(0, 10);
		} else {
			appdata = command.substring(12);
			command_head = command.substring(0, 12);
		}

		// 用主密匙keyMask使用3DES算法对16字节的随机数进行加密
		// logger.debug("用主密钥加密随机数：" + randomNum);
		String processKey = SecurityUtil.desecb(keyMain, randomNum, 0);// 过程密钥
		// logger.debug("获得过程密钥" + processKey);
		String mdata; // 加密后的密文
		String used_data; // N字节要发送的数据信息
		if (appdata != null)
			used_data = appdata + SecurityUtil.bytesToHexString(timeMillis);
		else {
			used_data = SecurityUtil.bytesToHexString(timeMillis);
		}

		// if(used_data.length()%16!=0){
		// 用过程密钥Key对“N字节要发送的数据信息+8字节的时间戳”进行3DES算法加密(填充80)

		// logger.debug("用过程密钥加密数据：" + SecurityUtil.padding80(used_data));
		mdata = SecurityUtil.desecb(processKey,
				SecurityUtil.padding80(used_data), 0);// 过程密钥加密数据
		// logger.debug("获得密文：" + mdata);
		// }else{
		// //用过程密钥Key对“N字节要发送的数据信息+8字节的时间戳”进行3DES算法加密(填充80)
		// logger.debug("用过程密钥加密数据："+used_data);
		// mdata=SecurityUtil.desecb(processKey,used_data,0);//过程密钥加密数据
		// logger.debug("获得密文："+mdata);
		// }

		// 随机数生成MAC过程密钥
		// logger.debug("生成MAC过程密钥的随机数：" + randomNum);
		String processKey_mac = SecurityUtil.desecb(keyMac, randomNum, 0);
		// logger.debug("随机数生成MAC过程密钥为：" + processKey_mac);

		String ICV = "0000000000000000";
		int index = (mdata + randomNum).length() / 2;
		if (command.length() >= 256) {
			byte[] len = new byte[2];
			len[0] = (byte) ((index >> 8) & 0xff);
			len[1] = (byte) (index & 0xff);
			byte[] commandHead = SecurityUtil.str2bytes(command_head);
			System.arraycopy(len, 0, commandHead, 4, 2);
			command_head = SecurityUtil.bytesToHexString(commandHead);
		} else {
			byte[] len = new byte[1];
			len[0] = (byte) (index & 0xff);
			byte[] commandHead = SecurityUtil.str2bytes(command_head);
			System.arraycopy(len, 0, commandHead, 4, 1);
			command_head = SecurityUtil.bytesToHexString(commandHead);
		}
		// 用MAC密钥计算data的MAC
		String MAC_data = command_head + mdata + randomNum;
		String mac;
		if (MAC_data.length() % 16 != 0) {
			// logger.debug("用mac过程密钥加密数据：" + SecurityUtil.padding80(MAC_data));
			mac = SecurityUtil.generateMAC(processKey_mac,
					SecurityUtil.padding80(MAC_data), ICV, 2, 1);
			// logger.debug("获得4字节mac：" + mac);
		} else {
			// logger.debug("用mac过程密钥加密数据：" + MAC_data);
			mac = SecurityUtil.generateMAC(processKey_mac, MAC_data, ICV, 2, 1);
			// logger.debug("获得4字节mac：" + mac);
		}
		// 返回透传数据
		// logger.debug("返回密文+mac:" + command_head + mdata + randomNum + mac);
		return command_head + mdata + randomNum + mac;

	}

	// /**
	// * 生成带Mac的apdu数据域（mac密钥）
	// *
	// * @param randomNum 16字节随机数
	// * @param keyMain 主密匙
	// * @param appData 应用数据
	// * @param timeMills 时间戳
	// * @param keyMac Mac密匙
	// * @return 要透传的数据
	// */
	// public static String generateKeyMac(String randomNum,String
	// keyMain,String command,byte[] timeMillis,String keyMac)
	// {
	// try{
	//
	// //用主密匙keyMask使用3DES算法对16字节的随机数进行加密
	// logger.debug("用主密钥加密随机数："+randomNum);
	// String processKey=SecurityUtil.desecb(keyMain,randomNum,0);//过程密钥
	// logger.debug("获得过程密钥"+processKey);
	//
	// // 生成SE的数据域
	// byte[] seCommandData = MakeCommand_SE(command, (byte)0x02, keyMain);
	// // SE的APDU指令头
	// byte[] seCommandHead = new byte[] {(byte) 0xf0, (byte) 0xc0, 0x00, 0x00,
	// 0x40};
	// byte[] seCommand = new byte[42];
	// System.arraycopy(seCommandHead, 0, seCommand, 0, 5);
	// System.arraycopy(seCommandData, 0, seCommand, 5, 37);
	//
	// // 生成带mac值的指令数据
	// String dataAddMac = generateTransLenMac(randomNum, keyMain,
	// bytesToHexString(seCommand), timeMillis, keyMac);
	//
	// //返回透传数据
	// logger.debug("返回Mac密匙密文+mac:"+dataAddMac);
	// return dataAddMac;
	//
	// }catch(Exception e){
	// e.getMessage();
	// }
	//
	// return null;
	// }

	/**
	 * HEX解码 将形如122A01 转换为0x12 0x2A 0x01
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] hexDecode(String data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (int i = 0; i < data.length(); i += 2) {
			String onebyte = data.substring(i, i + 2);
			int b = Integer.parseInt(onebyte, 16) & 0xff;
			out.write(b);
		}
		return out.toByteArray();
	}

	// /**
	// * 拼装报文 （更新密钥）
	// * @param code_value 16字节的新密钥值
	// * @param code_tag 密钥标签
	// * @param keyMain 用于加密新密钥的主密钥
	// * @return 拼装好的报文
	// */
	// public static byte[] MakeCommand_SE(String code_value , byte code_tag,
	// String keyMain)
	// {
	// byte[] SE_command_head = new byte[] { (byte) 0x80, (byte) 0xfb, 0x00,
	// 0x01,0x20};
	// SE_command_head[2]=code_tag;
	//
	// byte[] SE_command_data=new byte[32];
	// byte[] SE_command=new byte[37];
	//
	// String random=StringUtil.randomStr2Ascii(16);
	// byte[] random_byte=SecurityUtil.str2bytes(random);
	// logger.debug("生成随机数："+random);
	//
	// //用主密匙keyMask使用3DES算法对16字节的随机数进行加密
	// logger.debug("用主密钥加密随机数："+random);
	// String processKey=SecurityUtil.desecb(keyMain,random,0);//过程密钥
	// logger.debug("获得过程密钥"+processKey);
	//
	// //用过程密钥processKey使用3DES算法对16字节的新密钥进行加密
	// logger.debug("用过程密钥加密新密钥："+code_value);
	// String code_data=SecurityUtil.desecb(processKey,code_value,0);//密文
	// byte[] code=SecurityUtil.str2bytes(code_data);
	//
	// logger.debug("获得密文"+code_data);
	//
	// System.arraycopy(code, 0, SE_command_data, 0, 16);
	// System.arraycopy( random_byte, 0, SE_command_data, 16, 16);
	// System.arraycopy(SE_command_head, 0, SE_command, 0, 5);
	// System.arraycopy(SE_command_data, 0, SE_command, 5, 32);
	// logger.debug("SE更新密钥的报文"+bytesToHexString(SE_command));
	// return SE_command;
	// }

	/**
	 * 根据客户端传入的交易报文，来做mac校验
	 * 
	 * @author wangzt 20120301
	 * @param appData
	 *            交易报文
	 * @param keyMac
	 *            Mac密钥
	 * @return 校验成功或失败
	 * @throws SecurityException
	 */
	public static boolean volidMac(String appData, String keyMac)
			throws SecurityException {

		String real_mac = appData.substring(appData.length() - 8).toUpperCase();
		// logger.debug("返回数据的MAC为：" + real_mac);

		// 计算mac码
		String randomNum = appData.substring(appData.length() - 40,
				appData.length() - 8);// 得到16位随机数密文
		// logger.debug("返回数据的随机数为：" + randomNum);
		String processKey = SecurityUtil.desecb(keyMac, randomNum, 0);
		// logger.debug("随机数生成MAC过程密钥为：" + processKey);

		String ICV = "0000000000000000";
		String MAC_data = appData.substring(0, appData.length() - 8);
		String mac;
		if (MAC_data.length() % 16 != 0) {
			// logger.debug("用MAC密钥计算数据：" + SecurityUtil.padding80(MAC_data));
			mac = SecurityUtil.generateMAC(processKey,
					SecurityUtil.padding80(MAC_data), ICV, 2, 1);
			// logger.debug("返回数据的mac值为：" + mac);
		} else {
			// logger.debug("用MAC密钥计算数据：" + MAC_data);
			mac = SecurityUtil.generateMAC(processKey, MAC_data, ICV, 2, 1);
			// logger.debug("返回数据的mac值为：" + mac);
		}

		// 将计算出来的mac码的左边的4个字节与传入的客户端传入的mac码进行比较，如果一致说明校验成功，否则校验失败
		return mac.equals(real_mac);

	}

	/**
	 * 解密多惠拉客户端传过来的数据
	 * 
	 * @author wangzt 20120301
	 * @param appData
	 * @param keyMain
	 * @return
	 * @throws SecurityException
	 */
	public static String desData(String appData, String keyMain)
			throws SecurityException {

		String randomNum = appData.substring(appData.length() - 40,
				appData.length() - 8);// 得到16位随机数密文
		// logger.debug("返回数据的随机数为：" + randomNum);
		String processKey = SecurityUtil.desecb(keyMain, randomNum, 0);// 得到解密的过程密钥
		// logger.debug("随机数生成过程密钥为：" + processKey);

		// logger.debug("用过程密钥解密密文数据：" + appData.substring(0, appData.length() -
		// 44));
		String redata = SecurityUtil.desecb(processKey,
				appData.substring(0, appData.length() - 44), 1);
		// logger.debug("解密得到明文数据为：" + redata);
		return redata;

	}

	/**
	 * 计算CRC
	 * 
	 * @param data
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static char calcCRC(byte[] data, int length)
			throws SecurityException {
		if (data == null || data.length <= 0 || length <= 0) {
			throw new SecurityException("bad canshu");
		}

		char wCrc = 0xffff;
		int index = 0;
		byte ch;
		while (index < length) {
			ch = data[index];
			ch = (byte) (ch ^ (byte) (wCrc & 0x00ff));
			ch = (byte) (ch ^ (ch << 4));
			wCrc = (char) ((wCrc >> 8) ^ ((char) (ch & 0xff) << 8)
					^ ((char) (ch & 0xff) << 3) ^ ((char) (ch & 0xff) >> 4));

			index++;
		}
		return wCrc;
	}

	// 读取*.apk文件最后50K的数据
	public static byte[] readResFileData(String filePath)
			throws SecurityException {
		FileInputStream fis = null;
		DataInputStream dis = null;
		byte[] data = null;
		int size = 0;
		int length = 0;
		try {
			fis = new FileInputStream(new File(filePath));
			dis = new DataInputStream(fis);

			length = dis.available();

			int offset = 0;

			if (length > 51200) {
				offset = length - 51200;
				size = 51200;

				dis.skip(offset);
			} else {
				size = length;
			}

			data = new byte[size];

			dis.read(data, 0, size);

		} catch (FileNotFoundException e) {
			throw new SecurityException(e);
		} catch (IOException e) {
			throw new SecurityException(e);
		} finally {
			if (null != dis) {
				try {
					dis.close();
				} catch (IOException e) {
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}

		return data;
	}

	// 读取*.apk文件最后50K的数据
	public static byte[] readResFileData(File file) throws SecurityException {
		FileInputStream fis = null;
		DataInputStream dis = null;
		byte[] data = null;
		int size = 0;
		int length = 0;
		try {
			fis = new FileInputStream(file);
			dis = new DataInputStream(fis);

			length = dis.available();

			int offset = 0;

			if (length > 51200) {
				offset = length - 51200;
				size = 51200;

				dis.skip(offset);
			} else {
				size = length;
			}

			data = new byte[size];

			dis.read(data, 0, size);

		} catch (FileNotFoundException e) {
			throw new SecurityException(e);
		} catch (IOException e) {
			throw new SecurityException(e);
		} finally {
			if (null != dis) {
				try {
					dis.close();
				} catch (IOException e) {
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}

		return data;
	}

	public static String generalStringToAscii(int length) {

		int num = 1;
		String strRandom;

		for (int i = 0; i < length; i++) {
			num *= 10;
		}

		try {
			Thread.sleep(10);
			Random rand = new Random((new Date()).getTime());
			strRandom = Integer.toString(rand.nextInt(num));

			int len = strRandom.length();
			for (int i = 0; i < length - len; i++) {
				strRandom = "0" + strRandom;
			}

		} catch (InterruptedException e) {
			strRandom = "00000000";
		}

		StringBuffer sbu = new StringBuffer();
		char[] chars = strRandom.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			sbu.append((int) chars[i]);
		}
		return sbu.toString();
	}

	/**
	 * 
	 * 
	 * @param actionInfo
	 * 
	 * @return
	 * @throws SecurityException
	 */
	public static String decode(String actionInfo) throws SecurityException {
		//
		String randData = actionInfo.substring(0, 32);
		//
		String singData = actionInfo.substring(32, actionInfo.length());
		//
		String processKey = SecurityUtil.desecb(key, randData, 0);
		//
		String actionInfoString = SecurityUtil.desecb(processKey, singData, 1);
		//

		actionInfoString = SecurityUtil.hexStringToString(actionInfoString);

		//
		int num = actionInfoString.lastIndexOf("80");
		//
		if (num != -1) {
			actionInfoString = actionInfoString.substring(0, num);
		}
		//

		actionInfoString = new String(hexDecode(actionInfoString), UTF8);

		//
		return actionInfoString;
	}

	/**
	 * 鐟欙絽鐦戦弫鐗堝祦
	 * 
	 * @param actionInfo
	 *            actionInfo鐎靛棙鏋?
	 * @return 閺勫孩鏋?
	 * @throws SecurityException
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String decode(String key, String actionInfo)
			throws SecurityException {
		//
		String randData = actionInfo.substring(0, 32);
		//
		String singData = actionInfo.substring(32, actionInfo.length());
		//
		String processKey = SecurityUtil.desecb(key, randData, 0);
		//
		String actionInfoString = SecurityUtil.desecb(processKey, singData, 1);
		//

		actionInfoString = SecurityUtil.hexStringToString(actionInfoString);

		//
		int num = actionInfoString.lastIndexOf("80");
		//
		if (num != -1) {
			actionInfoString = actionInfoString.substring(0, num);
		}
		//
		try {
			actionInfoString = new String(hexDecode(actionInfoString), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SecurityException(e);
		}

		//
		return actionInfoString;
	}

	/**
	 * 閸旂姴鐦戦弫鐗堝祦
	 * 
	 * @param actionInfo
	 *            actionInfo閺勫孩鏋?
	 * @return actionInfo鐎靛棙鏋?
	 * @throws SecurityException
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String encode(String actionInfo) throws SecurityException {
		//
		String randData = generalStringToAscii(8) + generalStringToAscii(8);
		//
		String processKey = SecurityUtil.desecb(key, randData, 0);
		//
		try {
			actionInfo = SecurityUtil.padding80(SecurityUtil
					.bytesToHexString(actionInfo.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new SecurityException(e);
		}
		//
		actionInfo = SecurityUtil.encodeHexString(actionInfo);
		//
		actionInfo = SecurityUtil.desecb(processKey, actionInfo, 0);
		//
		String end = randData + actionInfo;

		return end;
	}

	/**
	 * 
	 * 
	 * @param actionInfo
	 *            actionInfo
	 * @return actionInfo
	 * @throws SecurityException
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String encode(String key, String actionInfo)
			throws SecurityException {
		//
		String randData = generalStringToAscii(8) + generalStringToAscii(8);
		//
		String processKey = SecurityUtil.desecb(key, randData, 0);
		//
		try {
			actionInfo = SecurityUtil.padding80(SecurityUtil
					.bytesToHexString(actionInfo.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new SecurityException(e);
		}
		//
		actionInfo = SecurityUtil.encodeHexString(actionInfo);
		//
		actionInfo = SecurityUtil.desecb(processKey, actionInfo, 0);
		//
		String end = randData + actionInfo;

		//
		return end;
	}

	/**
	 * RSA签名
	 * 
	 * @param key
	 *            RSA的密钥 公钥用X.509编码；私钥用PKCS#8编码
	 * @param data
	 *            输入数据
	 * @param mode
	 *            0-加密，1-解密
	 * @param type
	 *            0-私钥加密，公钥解密 1-公钥加密，私钥解密
	 * @return 签名后的数据 为null表示操作失败
	 */
	public static String generateRSA(String key, String data, int mode, int type) {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key strkey = null;
			if (mode != type) { // 生成公钥
				KeySpec keySpec = new X509EncodedKeySpec(
						Converts.stringToBytes(key)); // X.509编码
				strkey = keyFactory.generatePublic(keySpec);
			} else { // 生成私钥
				KeySpec keySpec = new PKCS8EncodedKeySpec(
						Converts.stringToBytes(key)); // PKCS#8编码
				strkey = keyFactory.generatePrivate(keySpec);
			}

			// 获得一个RSA的Cipher类，使用私钥加密
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			// RSA/ECB/PKCS1Padding

			// 初始化
			cipher.init(opmode, strkey);

			// 返回加解密结果
			return (Converts.bytesToHex(cipher.doFinal(Converts
					.stringToBytes(data)))).toUpperCase(); // 开始计算
		} catch (Exception e) {
			// logger.error(Constant.EXCEPTION, e);
		}
		return null;
	}

	/**
	 * md5加密
	 * 
	 * @throws SecurityException
	 */
	public final static String encodeByMD5(String s) throws SecurityException {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// byte[] btInput = Converts.hexToBytes(s);
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
			// } catch (Exception e) {

		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	public final static byte[] encodeByMD5Hex(String hex)
			throws SecurityException {
		try {
			byte[] btInput = Converts.hexToBytes(hex);
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			return md;
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	public final static String encodeByMD5HexStr(String hex)
			throws SecurityException {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = Converts.hexToBytes(hex);
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * CBC模式中的DES/3DES/TDES算法(数据不需要填充),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	 * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	 * 
	 * @param key
	 *            加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	 *            24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	 * @param data
	 *            待加/解密数据(长度必须是8的倍数)
	 * @param icv
	 *            初始链值(8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 */
	public static String descbc(String key, String data, String icv, int mode) {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;

			SecretKey keySpec = null;

			Cipher enc = null;

			// 判断密钥长度
			if (key.length() == 16) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(Converts.stringToBytes(key), "DES");
				// key

				// 生成算法
				enc = Cipher.getInstance("DES/CBC/NoPadding");
			} else if (key.length() == 32) {
				// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
				keySpec = new SecretKeySpec(Converts.stringToBytes(key
						+ key.substring(0, 16)), "DESede");
				// 将key前8个字节复制到keycbc的最后8个字节

				// 生成算法
				enc = Cipher.getInstance("DESede/CBC/NoPadding");
			} else if (key.length() == 48) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(Converts.stringToBytes(key),
						"DESede"); // key

				// 生成算法
				enc = Cipher.getInstance("DESede/CBC/NoPadding");
			} else {
				// logger.error("Key length is error");
				return null;
			}

			// 生成ICV
			IvParameterSpec ivSpec = new IvParameterSpec(
					Converts.stringToBytes(icv)); // icv
			// 初始化
			enc.init(opmode, keySpec, ivSpec);
			// 返回加解密结果
			return (Converts.bytesToHex(enc.doFinal(Converts
					.stringToBytes(data)))).toUpperCase(); // 开始计算
		} catch (Exception e) {
			// logger.error(Constant.EXCEPTION, e);
		}
		return null;
	}

	/**
	 * 填充80数据，首先在数据块的右边追加一个
	 * '80',如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充80的数据
	 * @return
	 */
	public static String padding80(String data) {
		int padlen = 8 - (data.length() / 2) % 8;
		StringBuffer padstr = new StringBuffer();
		for (int i = 0; i < padlen - 1; i++)
			padstr.append("00");
		data = data + "80" + padstr.toString();
		return data;
	}

	/**
	 * 填充paddingStr数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充的数据
	 * @return
	 */
	public static String padding(String data, String paddingStr) {
		int padlen = 8 - (data.length() / 2) % 8;
		if (padlen != 8) {
			StringBuffer padstr = new StringBuffer();
			for (int i = 0; i < padlen; i++)
				padstr.append(paddingStr);
			data += padstr;
			return data;
		} else {
			return data;
		}
	}

	/**
	 * 填充05数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x05到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充05的数据
	 * @return
	 */
	public static String padding05(String data) {
		return padding(data, "05");
	}

	/**
	 * 填充20数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x20到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充20的数据
	 * @return
	 */
	public static String padding20(String data) {
		return padding(data, "20");
	}

	/**
	 * 填充00数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充00的数据
	 * @return
	 */
	public static String padding00(String data) {
		return padding(data, "00");
	}

	/**
	 * ECB模式中的DES/3DES/TDES算法(数据不需要填充),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	 * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	 * 
	 * @param keyStr
	 *            加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	 *            24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	 * @param data
	 *            待加/解密数据(长度必须是8的倍数)
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 * @throws SecurityException
	 */
	public static String desecb(String keyStr, String data, int mode)
			throws SecurityException {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;
			SecretKey keySpec = null;
			Cipher enc = null;
			// 判断密钥长度
			if (keyStr.length() == 16) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(keyStr), "DES");// key
				// 生成算法
				enc = Cipher.getInstance("DES/ECB/NoPadding");
			} else if (keyStr.length() == 32) {
				// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(keyStr
						+ keyStr.substring(0, 16)), "DESede");// 将key前8个字节复制到keyecb的最后8个字节
				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else if (keyStr.length() == 48) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(keyStr), "DESede");// key
				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else {
				// logger.info("Key length is error");
				return null;
			}
			// 初始化
			enc.init(opmode, keySpec);
			// 返回加解密结果
			return (Converts.bytesToHex(enc.doFinal(str2bytes(data))))
					.toUpperCase();// 开始计算
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (NoSuchPaddingException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (IllegalBlockSizeException e) {
			throw new SecurityException(e);
		} catch (BadPaddingException e) {
			throw new SecurityException(e);
		}
	}

	public static byte[] desecb(byte[] keyByteArray, byte[] data, int mode)
			throws SecurityException {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;
			SecretKey keySpec = null;
			Cipher enc = null;
			// 判断密钥长度
			if (keyByteArray.length == 16) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(keyByteArray, "DES");// key
				// 生成算法
				enc = Cipher.getInstance("DES/ECB/NoPadding");
			} else if (keyByteArray.length == 32) {
				// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
				byte[] newKey = new byte[48];
				System.arraycopy(keyByteArray, 0, newKey, 0, 32);
				System.arraycopy(keyByteArray, 0, newKey, 32, 16);
				keySpec = new SecretKeySpec(newKey, "DESede");// 将key前8个字节复制到keyecb的最后8个字节
				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else if (keyByteArray.length == 48) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(keyByteArray, "DESede");// key
				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else {
				// logger.info("Key length is error");
				return null;
			}
			// 初始化
			enc.init(opmode, keySpec);
			// 返回加解密结果
			return enc.doFinal(data);// 开始计算
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (NoSuchPaddingException e) {
			throw new SecurityException(e);
		} catch (InvalidKeyException e) {
			throw new SecurityException(e);
		} catch (IllegalBlockSizeException e) {
			throw new SecurityException(e);
		} catch (BadPaddingException e) {
			throw new SecurityException(e);
		}
	}

	/**
	 * 将16进制组成的字符串转换成byte数组 例如 hex2Byte("0710BE8716FB"); 将返回一个byte数组
	 * b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	 * 
	 * @param src
	 *            待转换的16进制字符串
	 * @return
	 */
	public static byte[] str2bytes(String src) {
		if (src == null || src.length() == 0 || src.length() % 2 != 0) {
			return null;
		}
		int nSrcLen = src.length();
		byte byteArrayResult[] = new byte[nSrcLen / 2];
		StringBuffer strBufTemp = new StringBuffer(src);
		String strTemp;
		int i = 0;
		while (i < strBufTemp.length() - 1) {
			strTemp = src.substring(i, i + 2);
			byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
			i += 2;
		}
		return byteArrayResult;
	}

	/**
	 * des加解密 ， 3des加解密；补位80
	 * 
	 * @param key
	 *            16位des 32位3des
	 * @param data
	 *            加解密数据
	 * @param mode
	 *            0-加密" 1-解密
	 * @return
	 */
	public static String thrDes(String key, String data, int mode) {
		if (mode == 0) {
			data = byteToHex(data.getBytes());
			data = padding80(data);

		}
		String result = null;
		try {
			result = desecb(key, data, mode);
			if (mode == 1) {
				for (int i = 0; i < result.length(); i += 2) {
					String res = result.substring(i, i + 2);
					if (res.equals("80")) {
						result = result.substring(0, i);
						break;
					}
				}
				try {
					result = new String(hexToBytes(result), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	public static String byteToHex(byte[] buffer) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int iValue;
		for (int i = 0; i < buffer.length; i++) {
			iValue = buffer[i];
			if (iValue < 0)
				iValue += 256;
			hex = Integer.toString(iValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}
		return hexString.toString().toUpperCase();
	}

}