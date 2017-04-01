// Copyright (C) 2012 WHTY
package com.whty.efs.common.common;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.efs.common.constant.Constant;
import com.whty.efs.common.constant.GPConstant;
import com.whty.efs.common.util.Converts;

/**
 * Created by IntelliJ IDEA. User: fibiger Date: 2009-05-05 Time: 10:18:48 To
 * change this template use File | Settings | File Templates.
 */
public class GPMethods {

	private static final Logger logger = LoggerFactory.getLogger(GPMethods.class);
	
	/**
	 * ECB模式中的DES/3DES/TDES算法(数据不需要填充),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	 * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	 *
	 * @param key
	 *            加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	 *            24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	 * @param data
	 *            待加/解密数据(长度必须是8的倍数)
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 */
	public static String desecb(String key, String data, int mode) {
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
				enc = Cipher.getInstance("DES/ECB/NoPadding");
			} else if (key.length() == 32) {
				// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
				keySpec = new SecretKeySpec(Converts.stringToBytes(key
						+ key.substring(0, 16)), "DESede");
				// 将key前8个字节复制到keyecb的最后8个字节

				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else if (key.length() == 48) {
				// 生成安全密钥
				keySpec = new SecretKeySpec(Converts.stringToBytes(key),
						"DESede"); // key

				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else {
				logger.error("Key length is error");
				return null;
			}

			// 初始化
			enc.init(opmode, keySpec);

			// 返回加解密结果
			return (Converts.bytesToHex(enc.doFinal(Converts
					.stringToBytes(data)))).toUpperCase(); // 开始计算
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return null;
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
	 */
	public static String generateRSA(String n, String e, String d, String data,
			int mode, int type) {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE;

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			BigInteger bigN = new BigInteger(n);
			Key key = null;

			if (mode != type) { // 生成公钥
				BigInteger bigE = new BigInteger(e);
				KeySpec keySpec = new RSAPublicKeySpec(bigN, bigE);
				key = keyFactory.generatePublic(keySpec);

			} else { // 生成私钥
				BigInteger bigD = new BigInteger(d);
				KeySpec keySpec = new RSAPrivateKeySpec(bigN, bigD);
				key = keyFactory.generatePrivate(keySpec);
			}

			// 获得一个RSA的Cipher类，使用私钥加密
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			// RSA/ECB/PKCS1Padding
			// logger.debug("nopadding:");
			// 初始化
			cipher.init(opmode, key);

			// byte[] dataB=Converts.stringToBytes(data);
			// int inputLen = dataB.length;
			// ByteArrayOutputStream out = new ByteArrayOutputStream();
			// int offSet = 0;
			// byte[] cache;
			// int i = 0;
			// int MAX_ENCRYPT_BLOCK = 117;
			// // 对数据分段加密
			// while (inputLen - offSet > 0) {
			// if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
			// cache = cipher.doFinal(dataB, offSet, MAX_ENCRYPT_BLOCK);
			// } else {
			// cache = cipher.doFinal(dataB, offSet, inputLen - offSet);
			// }
			// out.write(cache, 0, cache.length);
			// i++;
			// offSet = i * MAX_ENCRYPT_BLOCK;
			// }
			// byte[] encryptedData = out.toByteArray();
			// out.close();
			// return (Converts.bytesToHex(encryptedData))
			// .toUpperCase();
			// 返回加解密结果
			return (Converts.bytesToHex(cipher.doFinal(Converts
					.stringToBytes(data)))).toUpperCase(); // 开始计算
		} catch (Exception e1) {
			logger.debug(e1.getMessage());
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得长度L编码<br>
	 * '00' - '7F'<br>
	 * '81 80'- '81 FF'<br>
	 * '82 01 00' - '82 FF FF'<br>
	 * 
	 * @param loadFile_len
	 *            注意，这里是字节长度，不是字符长度。调用前要算出字节长度
	 * @return
	 */
	public static String getLength(int loadFileLen1) {
		int loadFileLen = loadFileLen1;
		String len = "";
		if (loadFileLen <= 127) { // 7F
			len = Converts.intToHex(loadFileLen, GPConstant.SUB_TLV_LEN);
		} else if (loadFileLen <= 256) { // FF
			len = "81" + Converts.intToHex(loadFileLen, GPConstant.SUB_TLV_LEN);
		} else if (loadFileLen <= 65535) { // FFFF
			len = "82"
					+ Converts.intToHex(loadFileLen, GPConstant.APDU_TLV_LEN);
		} else {
			return "";
		}
		return len;
	}
	
	/**
	 * 填充80数据，首先在数据块的右边追加一个
	 * '80',如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	 *
	 * @param data
	 *            待填充80的数据
	 * @return
	 */
	public static String padding80(String data1) {
		String data = data1;
		int padlen = 8 - (data.length() / 2) % 8;
		String padstr = "";
		for (int i = 0; i < padlen - 1; i++) {
			padstr += "00";
		}
		data = data + "80" + padstr;
		return data;
	}
	
	/**
	 * 逆向去掉填80
	 *
	 * @param data
	 * @return
	 */
	public static String unPadding80(String data) {
		int len = data.length();
		int padlen = (len / 2) % 8;
		if (padlen != 0) {
			return data;
		} else {
			String tempStr = data.substring(data.length() - 16);
			for (int i = 0; i < 8; i++) {
				int start = 2 * i;
				String temp = tempStr.substring(start, start + 2);
				if ("80".equals(temp)) {
					if (i == 7) {
						return data.substring(0, len - 2);
					} else {
						long x = Long.parseLong(tempStr.substring(start + 2),
								16);
						if (x == 0) {
							return data.substring(0, len - 16 + start);
						}
					}
				}
			}
			return data;
		}
	}

	
	  /**
	   * CBC模式中的DES/3DES/TDES算法(数据需要填充80),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	   * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	   *
	   * @param key
	   *          加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	   *          24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	   * @param data
	   *          待加/解密数据
	   * @param icv
	   *          初始链值(8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
	   * @param mode
	   *          0-加密，1-解密
	   * @return 加解密后的数据 为null表示操作失败
	   */
	  public static String descbcNeedPadding80(String key, String data, String icv, int mode)
	  {
	    return descbc(key, padding80(data), icv, mode);
	  }
	  
	  /**
	   * CBC模式中的DES/3DES/TDES算法(数据不需要填充),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	   * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	   *
	   * @param key
	   *          加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	   *          24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	   * @param data
	   *          待加/解密数据(长度必须是8的倍数)
	   * @param icv
	   *          初始链值(8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
	   * @param mode
	   *          0-加密，1-解密
	   * @return 加解密后的数据 为null表示操作失败
	   */
	  public static String descbc(String key, String data, String icv, int mode)
	  {
	    try
	    {
	      // 判断加密还是解密
	      int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

	      SecretKey keySpec = null;

	      Cipher enc = null;

	      // 判断密钥长度
	      if (key.length() == 16)
	      {
	        // 生成安全密钥
	        keySpec = new SecretKeySpec(Converts.stringToBytes(key), "DES");
	        // key

	        // 生成算法
	        enc = Cipher.getInstance("DES/CBC/NoPadding");
	      } else if (key.length() == 32)
	      {
	        // 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
	        keySpec = new SecretKeySpec(Converts.stringToBytes(key + key.substring(0, 16)), "DESede");
	        // 将key前8个字节复制到keycbc的最后8个字节

	        // 生成算法
	        enc = Cipher.getInstance("DESede/CBC/NoPadding");
	      } else if (key.length() == 48)
	      {
	        // 生成安全密钥
	        keySpec = new SecretKeySpec(Converts.stringToBytes(key), "DESede"); // key

	        // 生成算法
	        enc = Cipher.getInstance("DESede/CBC/NoPadding");
	      } else
	      {
	        logger.error("Key length is error");
	        return null;
	      }

	      // 生成ICV
	      IvParameterSpec ivSpec = new IvParameterSpec(Converts.stringToBytes(icv)); // icv

	      // 初始化
	      enc.init(opmode, keySpec, ivSpec);

	      // 返回加解密结果
	      return (Converts.bytesToHex(enc.doFinal(Converts.stringToBytes(data)))).toUpperCase(); // 开始计算
	    } catch (Exception e)
	    {
	      logger.error(Constant.constant.EXCEPTION, e);
	    }
	    return null;
	  }

}