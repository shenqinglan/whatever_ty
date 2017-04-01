// Copyright (C) 2012 WHTY
package com.whty.efs.plugins.tycard.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by IntelliJ IDEA. User: fibiger Date: 2009-05-05 Time: 10:18:48 To
 * change this template use File | Settings | File Templates.
 */
public class GPMethods {

	private static final Logger logger = LoggerFactory.getLogger(GPMethods.class);
	/** 保存卡的临时会话信息 ，key:手机号码 value:会话信息 */
	//private static Map<String, KeySessionInfo> keySessionInfoMap = new HashMap<String, KeySessionInfo>();

	

	// private static String ip = "10.8.40.136";
	// private static String port = "12002";

	// public static String ip = "10.8.15.200";
	// public static String port = "12002";
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
		byte[] byte1 = Converts.stringToBytes(b1);
		byte[] byte2 = Converts.stringToBytes(b2);

		byte[] result = new byte[byte1.length];
		for (int i = 0; i < byte1.length; i++) {
			int temp = (byte1[i] ^ byte2[i]) & 0xff;
			result[i] = (byte) temp;
		}
		return Converts.bytesToHex(result).toUpperCase();
	}

	public static String padding(String data1, String pad) {
		String data = data1;
		int padlen = 8 - (data.length() / 2) % 8;
		if (padlen != 8) {
			String padstr = "";
			for (int i = 0; i < padlen; i++) {
				padstr += pad;
			}
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
	public static String padding05(String data1) {
		// String data = data1;
		// int padlen = 8 - (data.length() / 2) % 8;
		// if (padlen != 8) {
		// String padstr = "";
		// for (int i = 0; i < padlen; i++) {
		// padstr += "05";
		// }
		// data += padstr;
		// return data;
		// }
		// else {
		// return data;
		// }
		return padding(data1, "05");
	}

	/**
	 * 填充00数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	 *
	 * @param data
	 *            待填充00的数据
	 * @return
	 */
	public static String padding00(String data1) {
		// String data = data1;
		// int padlen = 8 - (data.length() / 2) % 8;
		// if (padlen != 8) {
		// String padstr = "";
		// for (int i = 0; i < padlen; i++) {
		// padstr += "00";
		// }
		// data += padstr;
		// return data;
		// }
		// else {
		// return data;
		// }
		return padding(data1, "00");
	}

	/**
	 * 填充20数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x20到数据块的右边，直到数据块的长度是8的倍数。
	 *
	 * @param data
	 *            待填充20的数据
	 * @return
	 */
	public static String padding20(String data1) {
		// String data = data1;
		// int padlen = 8 - (data.length() / 2) % 8;
		// if (padlen != 8) {
		// String padstr = "";
		// for (int i = 0; i < padlen; i++) {
		// padstr += "20";
		// }
		// data += padstr;
		// return data;
		// }
		// else {
		// return data;
		// }
		return padding(data1, "20");
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
				logger.error("Key length is error");
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
			logger.error("Exception: ", e);
		}
		return null;
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
	 *            初始链值(8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 */
	public static String descbcNeedPadding80(String key, String data,
			String icv, int mode) {
		return descbc(key, padding80(data), icv, mode);
	}

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
			logger.error("Exception: ", e);
		}
		return null;
	}

	// /**
	// * ECB模式中的DES/3DES/TDES算法(数据需要填充80),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与
	// * 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	// *
	// * @param key 加解密密钥(8字节:DES算法 16字节:3DES/TDES算法
	// * 24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	// * @param data 待加/解密数据
	// * @param mode 0-加密，1-解密
	// * @return 加解密后的数据 为null表示操作失败
	// */
	// public static String desecbNeedPadding80(String key, String data, int
	// mode)
	// {
	// return desecb(key, padding80(data), mode);
	// }
	//
	public static String sessionkey(String key, String derivationstr, int mode) {
		/*
		 * String enscu=sn1+sn2+"f0"+id+sn1+sn2+"0f"+id; key=
		 * GPMethods.desecb(key, enscu, 0);
		 */
		/*
		 * <--! %derivation=0181+%sqccounter+@strset(12,00) <--!
		 * %left=@left(%derivation,8) <--! %right=@right(%derivation,8) <--!
		 * %x1=@desjs(%sDEK,%left,00) <--! %x2=@desjs(%sDEK,@xor(%right,%x1),00)
		 * <--! %SKUDEK=%x1+%x2
		 */
		String left = derivationstr.substring(0, 16);
		String right = derivationstr.substring(16);
		// byte[] bleft = CommonMethods.str2bytes(left);
		String x1 = GPMethods.desecb(key, left, 0);
		// String x1 = CommonMethods.bytesToHexString(bleft);

		String xor = GPMethods.doXOR(right, x1);
		// byte[] bxor = CommonMethods.str2bytes(xor);
		String x2 = GPMethods.desecb(key, xor, 0);

		return x1 + x2;

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
	 */
	public static void des(byte[] key, byte[] data, int mode) {
		try {
			if (key.length == 8) {
				// 判断加密还是解密
				int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE
						: Cipher.DECRYPT_MODE;

				// 生成安全密钥
				SecretKey keySpec = new SecretKeySpec(key, "DES"); // key

				// 生成算法
				Cipher enc = Cipher.getInstance("DES/ECB/NoPadding");

				// 初始化
				enc.init(opmode, keySpec);

				// 加解密结果
				byte[] temp = enc.doFinal(data); // 开始计算
				// for(byte i : temp){
				// System.out.println(i+"------------------------------------");
				// }
				System.arraycopy(temp, 0, data, 0, 8); // 将加解密结果复制一份到data
			}
		} catch (Exception e) {
			// System.out.println("error");
			logger.error("Exception: ", e);
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
	 *            初始链向量 (8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
	 * @param padding
	 *            0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80
	 *            (GP卡用)3:填充0x05（卡门户应用）
	 * @param outlength
	 *            返回的MAC长度 1：4个字节 2：8个字节
	 * @return
	 */
	public static String generateMAC(String key, String data, String icv,
			int padding, int outlength) {
		try {
			if (key.length() == 32 || key.length() == 16) {
				byte[] leftKey = new byte[8];
				byte[] rightKey = new byte[8];
				System.arraycopy(Converts.stringToBytes(key), 0, leftKey, 0, 8);
				// 将key复制一份到leftKey

				byte[] icvTemp = Converts.stringToBytes(icv);
				// 将icv复制一份到icvTemp

				// 实际参与计算的数据
				byte[] dataTemp = null;

				if (padding == 0) {
					dataTemp = Converts.stringToBytes(padding00(data));
					// 填充0x00
				} else if (padding == 1) {
					dataTemp = Converts.stringToBytes(padding20(data));
					// 填充0x20
				} else if (padding == 2) {
					dataTemp = Converts.stringToBytes(padding80(data));
					// 填充0x80
				} else if (padding == 3) {
					dataTemp = Converts.stringToBytes(padding05(data));
				} // 填充0x05

				int nCount = dataTemp.length / 8;
				for (int i = 0; i < nCount; i++) {
					for (int j = 0; j < 8; j++) {
						// 初始链值与输入数据异或
						icvTemp[j] ^= dataTemp[i * 8 + j];
					}
					des(leftKey, icvTemp, 0); // DES加密
				}
				if (key.length() == 32) { // 如果key的长度是16个字节
					System.arraycopy(Converts.stringToBytes(key), 8, rightKey,
							0, 8); // 将key复制一份到rightKey
					des(rightKey, icvTemp, 1); // DES解密
					des(leftKey, icvTemp, 0); // DES加密
				}
				String mac = (Converts.bytesToHex(icvTemp)).toUpperCase();
				return (outlength == 1 && padding != 1) ? mac.substring(0, 8)
						: mac; // 返回结果
			} else {
				logger.error("Key ：" + key);
				logger.error("Key length is error");
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return null;
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
	 *            （<b>参数icv没用到</b>）<s>初始链向量 (8个字节) 一般为8字节的0x00
	 *            icv=GPConstant.icvRand</s>
	 * @param padding
	 *            对data填充 0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80
	 *            (GP卡用)先填80再看长度
	 * @param outlength
	 *            （<b>参数outlength没用到</b>）<s> 返回的MAC长度 1：4个字节 2：8个字节</s>
	 * @return 始终返回mac的后8个字节
	 */
	public static String generateMACAlg1(String key, String data, String icv,
			int padding, int outlength) {
		try {
			if (key.length() == 32 || key.length() == 48) {
				// byte[] icvTemp = Converts.stringToBytes(icv); //
				// 将icv复制一份到icvTemp

				// 实际参与计算的数据
				byte[] dataTemp = null;

				if (padding == 0) {
					dataTemp = Converts.stringToBytes(padding00(data));
					// 填充0x00
				} else if (padding == 1) {
					dataTemp = Converts.stringToBytes(padding20(data));
					// 填充0x20
				} else {
					dataTemp = Converts.stringToBytes(padding80(data));
					// 填充0x80
				}
				String mac = descbc(key, Converts.bytesToHex(dataTemp), icv, 0)
						.toUpperCase();

				return mac.substring(mac.length() - 16);
				/*
				 * int nCount = dataTemp.length / 8; for (int i = 0; i < nCount;
				 * i++) { for (int j = 0; j < 8; j++) // 初始链值与输入数据异或 {
				 * icvTemp[j] ^= dataTemp[i * 8 + j]; } String resulticv =
				 * desecb(key, bytesToHexString(icvTemp), 0); // 3DES/TDES加密
				 * System.arraycopy(str2bytes(resulticv), 0, icvTemp, 0, 8);
				 * //System.arraycopy(str2bytes(resulticv), 0, icvTemp, 0, 8);
				 * // 将icv复制一份到icvTemp }
				 */
				// String mac = (bytesToHexString(icvTemp)).toUpperCase();
				/*
				 * return (outlength == 1 && padding != 1) ? mac.substring(0, 8)
				 * : mac;
				 */// 返回结果
			} else {
				logger.error("Key length is error");
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return null;
	}

	/**
	 * SHA-1摘要 应用场景 算install指令中的HASH值
	 *
	 * @param data
	 *            要计算SHA-1摘要的数据
	 * @return 16进制字符串形式的SHA-1消息摘要，一般为20字节 为null表示操作失败
	 */
	public static String generateSHA1(String data) {
		try {
			// 使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

			// 开始使用算法
			messageDigest.update(Converts.stringToBytes(data));

			// 输出算法运算结果
			byte[] hashValue = messageDigest.digest(); // 20位字节

			return (Converts.bytesToHex(hashValue)).toUpperCase();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return null;
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
			logger.error("Exception: ", e);
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

	public static void main(String[] args) throws Exception {
	}

	// /**
	// * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
	// *
	// * @param val
	// * 待转换整数
	// * @param len
	// * 指定长度
	// * @return String
	// */
	// public static String int2HexStr(int val, int len) {
	// String result = Integer.toHexString(val).toUpperCase(); // EEEEEEEEE
	// int rLen = result.length();
	// if (rLen > len) {
	// return result.substring(rLen - len, rLen);
	// }
	// if (rLen == len) {
	// return result;
	// }
	// StringBuffer strBuff = new StringBuffer(result);
	// for (int i = 0; i < len - rLen; i++) {
	// strBuff.insert(0, '0');
	// }
	// return strBuff.toString();
	// }

	// *********************************2012-06-06新增的方法******************
	public static String getStatus(int str, String seid) {

		return "";
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
	 */
	public static String generateSHA1withRSA(String n, String d, String data) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			BigInteger bigN = new BigInteger(n);
			BigInteger bigD = new BigInteger(d);
			KeySpec keySpec = new RSAPrivateKeySpec(bigN, bigD);
			PrivateKey key = keyFactory.generatePrivate(keySpec);

			// 使用私钥签名
			Signature sig = Signature.getInstance("SHA1WithRSA"); // SHA1WithRSA
			sig.initSign(key);
			sig.update(Converts.stringToBytes(data));

			// 返回加密结果
			return (Converts.bytesToHex(sig.sign())).toUpperCase(); // 开始计算
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成带SHA-1摘要的RSA签名
	 * 
	 * @param key
	 *            DAP或者Token私钥 用PKCS#8编码
	 * @param data
	 *            要签名的原始数据
	 * @return 签名后的数据 为null表示操作失败
	 */
	public static String generateSHA1withRSA(String key, String data)
			throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		KeySpec keySpec = new PKCS8EncodedKeySpec(Converts.stringToBytes(key));
		// PKCS#8编码
		// System.out.println(keySpec);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		// 使用私钥签名
		Signature sig = Signature.getInstance("SHA1WithRSA"); // SHA1WithRSA
		sig.initSign(privateKey);
		sig.update(Converts.stringToBytes(data));

		// 返回加密结果
		return (Converts.bytesToHex(sig.sign())).toUpperCase(); // 开始计算

	}

	/**
	 * 填充FF数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0xFF到数据块的第2个字节后，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充FF的数据
	 * @return
	 */
	public static String paddingFF(String data1) {
		String data = data1;
		int padlen = 8 - (data.length() / 2) % 8;
		if (padlen != 8) {
			String padstr = "";
			for (int i = 0; i < padlen; i++) {
				padstr += "FF";
			}
			data = data.substring(0, 4) + padstr + data.substring(4);
			return data;
		} else {
			return data;
		}
	}

	


	// /**
	// * 将16进制组成的字符串转换成byte数组 例如 hex2Byte("0710BE8716FB"); 将返回一个byte数组
	// * b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	// *
	// * @param src
	// * 待转换的16进制字符串
	// * @return
	// */
	// public static byte[] str2bytes(String src) {
	// if (src == null || src.length() == 0 || src.length() % 2 != 0) {
	// return null;
	// }
	// int nSrcLen = src.length();
	// byte byteArrayResult[] = new byte[nSrcLen / 2];
	// StringBuffer strBufTemp = new StringBuffer(src);
	// String strTemp;
	// int i = 0;
	// while (i < strBufTemp.length() - 1) {
	// strTemp = src.substring(i, i + 2);
	// byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
	// i += 2;
	// }
	// return byteArrayResult;
	// }

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
		for (int i = 0; i < data.length; i++) {
			crc32 = lGenCRC32(crc32, data[i]);
		}
		if (lastblock == 1) {
			crc32 = ~crc32;
		}
		return long2HexStr(crc32, 8);
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
		firstByte = (0x000000FF & ((int) buf[0]));
		secondByte = (0x000000FF & ((int) buf[1]));
		thirdByte = (0x000000FF & ((int) buf[2]));
		fourthByte = (0x000000FF & ((int) buf[3]));
		return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
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
	 *            初始链向量 (8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
	 * @param padding
	 *            0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80 (GP卡用)
	 * @param outlength
	 *            返回的MAC长度 1：4个字节 2：8个字节
	 * @return
	 */
	public static String generateMACAlg4(String key, String data, String icv,
			int padding, int outlength) {
		try {
			byte[] leftKey = new byte[8];
			byte[] middleKey = new byte[8];
			byte[] rightKey = new byte[8];

			if (key.length() == 48) {
				System.arraycopy(Converts.stringToBytes(key), 16, rightKey, 0,
						8); // 将key的最右边8个字节复制一份到rightKey
			} else if (key.length() == 32) {
				System.arraycopy(Converts.stringToBytes(key), 8, rightKey, 0, 8);
				// 将key的最右边8个字节复制一份到rightKey
			} else {
				logger.debug("Key length is error");
				return null;
			}

			System.arraycopy(Converts.stringToBytes(key), 0, leftKey, 0, 8);
			// 将key的最左边8个字节复制一份到leftKey
			System.arraycopy(Converts.stringToBytes(key), 8, middleKey, 0, 8);
			// 将key的中间8个字节复制一份到middleKey

			byte[] icvTemp = Converts.stringToBytes(icv); // 将icv复制一份到icvTemp

			// 实际参与计算的数据
			byte[] dataTemp = null;

			if (padding == 0) {
				dataTemp = Converts.stringToBytes(GPMethods.padding00(data));
				// 填充0x00
			} else if (padding == 1) {
				dataTemp = Converts.stringToBytes(GPMethods.padding20(data));
				// 填充0x20
			} else {
				dataTemp = Converts.stringToBytes(GPMethods.padding80(data));
				// 填充0x80
			}

			int nCount = dataTemp.length / 8;
			for (int i = 0; i < nCount; i++) {
				for (int j = 0; j < 8; j++) {
					// 初始链值与输入数据异或
					icvTemp[j] ^= dataTemp[i * 8 + j];
				}
				GPMethods.des(leftKey, icvTemp, 0); // DES加密
				if (i == 0) {
					GPMethods.des(rightKey, icvTemp, 0); // DES加密
				}
				if (i == nCount - 1) {
					GPMethods.des(middleKey, icvTemp, 0); // DES加密
				}
			}
			String mac = (Converts.bytesToHex(icvTemp)).toUpperCase();
			return (outlength == 1 && padding != 1) ? mac.substring(0, 8) : mac;
			// 返回结果
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return null;
	}

	// /**
	// * 将byte数组转换成16进制组成的字符串 例如 一个byte数组 b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	// * byte2hex(b); 将返回一个字符串"0710BE8716FB"
	// *
	// * @param bytes
	// * 待转换的byte数组
	// * @return
	// */
	// public static String bytesToHexString(byte[] bytes) {
	// if (bytes == null) {
	// return "";
	// }
	// StringBuffer buff = new StringBuffer();
	// int len = bytes.length;
	// for (int j = 0; j < len; j++) {
	// if ((bytes[j] & 0xff) < 16) {
	// buff.append('0');
	// }
	// buff.append(Integer.toHexString(bytes[j] & 0xff));
	// }
	// return buff.toString();
	// }

	/**
	 * 生成CRC32
	 *
	 * @param lOldCRC
	 *            the crc32 value
	 * @param ByteVal
	 *            the new data byte
	 * @return
	 */
	public static long lGenCRC32(long lOldCRC, byte byteVal) {
		long tabVal;
		int j;
		tabVal = ((lOldCRC) ^ byteVal) & 0xff;
		for (j = 8; j > 0; j--) {
			if ((tabVal & 1) == 1) {
				tabVal = (tabVal >> 1) ^ 0xEDB88320L;
			} else {
				tabVal >>= 1;
			}
		}
		return tabVal ^ (((lOldCRC) >> 8) & 0x00FFFFFFL);
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
	public static String long2HexStr(long val, int len) {
		String result = Long.toHexString(val).toUpperCase();
		int rLen = result.length();
		if (rLen > len) {
			return result.substring(rLen - len, rLen);
		}
		if (rLen == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		for (int i = 0; i < len - rLen; i++) {
			strBuff.insert(0, '0');
		}
		return strBuff.toString();
	}

	/**
	 *
	 * @param hash
	 * @param eLen
	 * @return
	 */
	public static String paddingRsaPcks(String hash, int eLen) {
		String ps = "FF";
		String ret = "0001";
		String t = "00" + "3021300906052b0e03021a05000414" + hash;
		int psLen = eLen - (t.length() + ret.length()) / 2;
		for (int i = 0; i < psLen; i++) {
			ret += ps;
		}
		ret += t;
		return ret;
	}

	/**
	 * 填充00数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充00的数据
	 * @return
	 */
	public static String padding00Left(String data1) {
		String data = data1;
		int padlen = 8 - (data.length() / 2) % 8;
		if (padlen != 8) {
			String padstr = "";
			for (int i = 0; i < padlen; i++) {
				padstr += "00";
			}
			data = padstr + data;
			return data;
		} else {
			return data;
		}
	}

	

	/**
	 * 获取分散数据
	 * 
	 * @param seid
	 * @param sdAid
	 * @param keyVer
	 * @param keyInx
	 * @return
	 */
	public static String getScatterData(String seid, String sdAid,
			String keyVer, String keyInx) {
		if (seid != null) {
			if (seid.length() >= 16) {
				// 截取
				seid = seid.substring(seid.length() - 16, seid.length());
			} else {
				// 填充
				seid = GPMethods.padding00Left(seid);
			}
		}

		if (sdAid != null) {
			if (sdAid.length() >= 16) {
				// 截取
				sdAid = sdAid.substring(sdAid.length() - 16, sdAid.length());
			} else {
				// 填充
				sdAid = GPMethods.padding00Left(sdAid);
			}
		}

		String data = new StringBuilder().append(keyVer).append("000000000000").append(keyInx).toString();
		StringBuilder sb = new StringBuilder();
		sb.append(seid);
		sb.append(GPMethods.doXOR(sdAid, data));
		return sb.toString();
	}

//	public static Map<String, KeySessionInfo> getKeySessionInfoMap() {
//		return keySessionInfoMap;
//	}

//	public static void setKeySessionInfoMap(
//			Map<String, KeySessionInfo> keySessionInfoMap) {
//		GPMethods.keySessionInfoMap = keySessionInfoMap;
//	}

	
	/**
	 * 临时用的方法（为了测试密管替换过程密钥接口用）
	 * 
	 * @param ats
	 * @param sdAid
	 * @param keyVer
	 * @param Keyindex
	 * @param divData
	 * @param session_flag
	 * @param dekSdAid
	 * @param xx
	 * @return
	 * @throws Exception
	 */
	public static String getPutKey(String ats, String sdAid, String keyVer,
			String Keyindex, String divData, String session_flag,
			String dekSdAid, String xx){
		String key = "";
		key = "404142434445464748494a4b4c4d4e4f";
		// String kdektemp = GPMethods.descbc(kmac1, divData,
		// "0000000000000000", 0);
		// return GPMethods.desecb("404142434445464748494a4b4c4d4e4f",key,0);
		String kdektemp = GPMethods.descbc("404142434445464748494a4b4c4d4e4f",
				divData, "0000000000000000", 0);
		return GPMethods.desecb(kdektemp, key, 0);
	}

	/**
	 * 生成len个字节的十六进制随机数字符串 例如:len=8 则可能会产生 DF15F0BDFADE5FAF 这样的字符串 *
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

	

	public static String RSA_PCKS1(String data) {
		String ssignData = "3021300906052b0e03021a05000414"
				+ GPMethods.generateSHA1(data);
		int cemLen = 128 - ssignData.length() / 2 - 3;
		String sEm = "0001" + paddingStr("FF", cemLen) + "00" + ssignData;
		return sEm;
	}

	public static String paddingStr(String data, int sum) {
		if (data.length() == 0 || sum == 0)
			return data;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sum; i++) {
			sb.append(data);
		}
		return sb.toString();
	}
	
}
