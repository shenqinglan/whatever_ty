package com.whty.euicc.common.utils;

// Copyright (C) 2012 WHTY

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by IntelliJ IDEA. User: fibiger Date: 2009-05-05 Time: 10:18:48 To
 * change this template use File | Settings | File Templates.
 */
public class GPMethods
{

  private static final Logger                logger            = LoggerFactory.getLogger(GPMethods.class);

  public static final String   EXCEPTION               = "Exception: ";
  /**
   * SHA-1摘要 应用场景 算install指令中的HASH值
   *
   * @param data
   *          要计算SHA-1摘要的数据
   * @return 16进制字符串形式的SHA-1消息摘要，一般为20字节 为null表示操作失败
   */
  public static String generateSHA1(String data)
  {
    try
    {
      // 使用getInstance("算法")来获得消息摘要,这里使用SHA-1的160位算法
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

      // 开始使用算法
      messageDigest.update(Converts.stringToBytes(data));

      // 输出算法运算结果
      byte[] hashValue = messageDigest.digest(); // 20位字节

      return (Converts.bytesToHex(hashValue)).toUpperCase();
    } catch (Exception e)
    {
      logger.debug(e.getMessage());
    }
    return null;
  }

	/**
	 * CBC模式中的DES/3DES/TDES算法(数据需要填充80),支持8、16、24字节的密钥 说明：3DES/TDES解密算法 等同与 先用前8个字节密钥DES解密 再用中间8个字节密钥DES加密 最后用后8个字节密钥DES解密 一般前8个字节密钥和后8个字节密钥相同
	 *
	 * @param key
	 *            加解密密钥(8字节:DES算法 16字节:3DES/TDES算法 24个字节:3DES/TDES算法,一般前8个字节密钥和后8个字节密钥相同)
	 * @param data
	 *            待加/解密数据
	 * @param icv
	 *            初始链值(8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
	 * @param mode
	 *            0-加密，1-解密
	 * @return 加解密后的数据 为null表示操作失败
	 */
	public static String descbcNeedPadding80(String key, String data, String icv, int mode) {
		return SecurityUtil.descbc(key, padding80(data), icv, mode);
	}


  /**
   * 产生MAC算法3,即Single DES加上最终的TDES MAC,支持8、16字节的密钥 这也叫Retail
   * MAC,它是在[ISO9797-1]中定义的MAC算法3，带有输出变换3、没有截断，并且用DES代替块加密
   *
   * @param key
   *          密钥(8字节:CBC/DES算法 16字节:先CBC/DES，再完成3DES/TDES算法)
   * @param data
   *          要计算MAC的数据
   * @param icv
   *          初始链向量 (8个字节) 一般为8字节的0x00 icv=GPConstant.icvRand
   * @param padding
   *          0：填充0x00 (PIM专用) 1：填充0x20 (SIM卡专用 必须输出8个字节) 2：填充0x80
   *          (GP卡用)3:填充0x05（卡门户应用）
   * @param outlength
   *          返回的MAC长度 1：4个字节 2：8个字节
   * @return
   */
  public static String generateMAC(String key, String data, String icv, int padding, int outlength)
  {
    try
    {
      if (key.length() == 32 || key.length() == 16)
      {
        byte[] leftKey = new byte[8];
        byte[] rightKey = new byte[8];
        System.arraycopy(Converts.stringToBytes(key), 0, leftKey, 0, 8);
        // 将key复制一份到leftKey

        byte[] icvTemp = Converts.stringToBytes(icv);
        // 将icv复制一份到icvTemp

        // 实际参与计算的数据
        byte[] dataTemp = null;

        if (padding == 0)
        {
          dataTemp = Converts.stringToBytes(padding00(data));
          // 填充0x00
        } else if (padding == 1)
        {
          dataTemp = Converts.stringToBytes(padding20(data));
          // 填充0x20
        } else if (padding == 2)
        {
          dataTemp = Converts.stringToBytes(padding80(data));
          // 填充0x80
        } else if (padding == 3)
        {
          dataTemp = Converts.stringToBytes(padding05(data));
        } // 填充0x05
        //data数据异常时，dataTemp为null，防止调用dataTemp.length报错
        if(dataTemp == null) {
            logger.error("convert data into byte error");
            logger.error("data:" + data);
        	return null;
        }
        int nCount = dataTemp.length / 8;
        for (int i = 0; i < nCount; i++)
        {
          for (int j = 0; j < 8; j++)
          {
            // 初始链值与输入数据异或
            icvTemp[j] ^= dataTemp[i * 8 + j];
          }
          des(leftKey, icvTemp, 0); // DES加密
        }
        if (key.length() == 32)
        { // 如果key的长度是16个字节
          System.arraycopy(Converts.stringToBytes(key), 8, rightKey, 0, 8); // 将key复制一份到rightKey
          des(rightKey, icvTemp, 1); // DES解密
          des(leftKey, icvTemp, 0); // DES加密
        }
        String mac = (Converts.bytesToHex(icvTemp)).toUpperCase();
        return (outlength == 1 && padding != 1) ? mac.substring(0, 8) : mac; // 返回结果
      } else
      {
        logger.error("Key length is error");
      }
    } catch (Exception e)
    {
      logger.error(EXCEPTION, e);
    }
    return null;
  }

  /**
   * 填充00数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
   *
   * @param data
   *          待填充00的数据
   * @return
   */
  public static String padding00(String data1)
  {
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
   * 填充05数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x05到数据块的右边，直到数据块的长度是8的倍数。
   *
   * @param data
   *          待填充05的数据
   * @return
   */
  public static String padding05(String data1)
  {
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
   * 填充20数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x20到数据块的右边，直到数据块的长度是8的倍数。
   *
   * @param data
   *          待填充20的数据
   * @return
   */
  public static String padding20(String data1)
  {
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
   *          待填充80的数据
   * @return
   */
  public static String padding80(String data1)
  {
    String data = data1;
    int padlen = 8 - (data.length() / 2) % 8;
    String padstr = "";
    for (int i = 0; i < padlen - 1; i++)
    {
      padstr += "00";
    }
    data = data + "80" + padstr;
    return data;
  }

  /**
   * ECB模式中的DES算法(数据不需要填充)
   *
   * @param key
   *          加解密密钥(8个字节)
   * @param data
   *          输入:待加/解密数据(长度必须是8的倍数) 输出:加/解密后的数据
   * @param mode
   *          0-加密，1-解密
   * @return
   */
  public static void des(byte[] key, byte[] data, int mode)
  {
    try
    {
      if (key.length == 8)
      {
        // 判断加密还是解密
        int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

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
    } catch (Exception e)
    {
      // System.out.println("error");
      logger.error(EXCEPTION, e);
    }
  }

  public static String padding(String data1, String pad)
  {
    String data = data1;
    int padlen = 8 - (data.length() / 2) % 8;
    if (padlen != 8)
    {
      String padstr = "";
      for (int i = 0; i < padlen; i++)
      {
        padstr += pad;
      }
      data += padstr;
      return data;
    } else
    {
      return data;
    }
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
	public static String generateRSA(String n, String e, String d, String data, int mode, int type) {
		try {
			// 判断加密还是解密
			int opmode = (mode == 0) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			BigInteger bigN = new BigInteger(n,16);
			Key key = null;

			if (mode != type) { // 生成公钥
				BigInteger bigE = new BigInteger(e,16);
				KeySpec keySpec = new RSAPublicKeySpec(bigN, bigE);
				key = keyFactory.generatePublic(keySpec);

			} else { // 生成私钥
				BigInteger bigD = new BigInteger(d);
				KeySpec keySpec = new RSAPrivateKeySpec(bigN, bigD);
				key = keyFactory.generatePrivate(keySpec);
			}
			// 获得一个RSA的Cipher类，使用私钥加密
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding","BC");
			// RSA/ECB/PKCS1Padding
			logger.debug("nopadding:");
			// 初始化
			cipher.init(opmode, key);


			return (Converts.bytesToHex(cipher.doFinal(Converts.stringToBytes(data)))).toUpperCase(); // 开始计算
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
			len = "82" + Converts.intToHex(loadFileLen, GPConstant.APDU_TLV_LEN);
		} else {
			return "";
		}
		return len;
	}


	/**
	 * 转换TLV格式 tag + value.len + value
	 * @param tag
	 * @param value
	 * @param stdLen 取值 0 1，为1时表示用标准TLV格式中的长度表示方法来表示，即需要加81，82等等
	 * 为0时使用非标准长度表示方法，为1时使用标准TLV长度表示方法。
	 * @return
	 */
	public static String toTlv(String tag,String value,int stdLen){
		String len = "";
		if(stdLen == 1){
			len = getLength(value.length()/2);
		}else if(stdLen == 0){
			len = Integer.toHexString(value.length()/2).toUpperCase();
		}
		return tag + len + value;
	}

	static{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	public static void main(String[] args) {
		//
		  Provider[] providers = Security.getProviders();
		  for (int i = 0; i < providers.length; i++) {
		   System.out.println(providers[i].getName()+":"+providers[i].getClass().getName());
		   //org.bouncycastle.jce.provider.BouncyCastleProvider
		  }

		 }
}
