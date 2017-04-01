package com.whty.efs.common.common;



import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * *****************************************************************************<br>
 * 工程名称: Midam二期<br>
 * 模块功能：包含des加解密的工具方法，String和byte相互转换的工具方法<br>
 * 作 者:  杨明<br>
 * 单 位：武汉天喻信息 研发中心 <br>
 * 开发日期：2013-5-16 上午10:40:54 <br>
 * *****************************************************************************<br>
 */
public final class DesTools {
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
			if (key.length() == 16) {// 8位
				// 生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(key), "DES");// key

				// 生成算法
				enc = Cipher.getInstance("DES/ECB/NoPadding");
			} else if (key.length() == 32) {// 16位
				// 计算加解密密钥，即将16个字节的密钥转换成24个字节的密钥，24个字节的密钥的前8个密钥和后8个密钥相同,并生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(key
						+ key.substring(0, 16)), "DESede");// 将key前8个字节复制到keyecb的最后8个字节

				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else if (key.length() == 48) {// 24位
				// 生成安全密钥
				keySpec = new SecretKeySpec(str2bytes(key), "DESede");// key

				// 生成算法
				enc = Cipher.getInstance("DESede/ECB/NoPadding");
			} else {
//				LogUtil.e(TAG, "Key length is error");
				return null;
			}

			// 初始化
			enc.init(opmode, keySpec);

			// 返回加解密结果
			return (bytesToHexString(enc.doFinal(str2bytes(data))))
					.toUpperCase();// 开始计算
		} catch (Exception e) {
//			LogUtil.e(TAG, e.getMessage());
		}
		return null;
	}



	/**
	 * 填充80数据，首先在数据块的右边追加一个'80',
	 * 如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	 * @param data
	 *            待填充80的数据
	 * @return
	 */
	public static String padding80(String data) {
		int padlen = 8 - (data.length() / 2) % 8;
		String padstr = "";
		for (int i = 0; i < padlen - 1; i++)
			padstr += "00";
		data = data + "80" + padstr;
		return data;
	}
	
	public static String padding00(String data) {
		int padlen = 8 - (data.length() / 2) % 8;
		String padstr = "";
		for (int i = 0; i < padlen - 1; i++)
			padstr += "00";
		data = data + "00" + padstr;
		return data;
	}

	/**
	 * 对数据进行加密
	 * 
	 * @param key
	 *            加密的key
	 * @param data
	 *            要加密的数据
	 * @return
	 */
	public static String encrypt(String key, String data) {
		String dData = null;
		try {
			if (data != null) {
//				byte[] dataByte = data.getBytes();
//				String hexStr = bytesToHexString(dataByte);
				String tempData = padding80(data);
				dData = desecb(key, tempData, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dData;
	}

	/**
	 * 对数据进行解密
	 * 
	 * @param key
	 *            解密的key
	 * @param data
	 *            要解密的数据
	 * @return
	 */
	public static String decrypt(String key, String data) {
		String value = null;
		try {
			if (data != null) {
				value = desecb(key, data.trim(), 1);
				value = unPadding80(value);
//				byte[] edata = str2bytes(value);
//				result = new String(edata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
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
						int x = Integer.parseInt(tempStr.substring(start + 2),
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
     * 将16进制组成的字符串转换成byte数组 例如 hex2Byte("0710BE8716FB"); 
     * 将返回一个byte数组 b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
     *
     * @param str 待转换的16进制字符串
     * @return
     */
    public static byte[] str2bytes(String src){
        if (src == null || src.length() == 0 || src.length() % 2 != 0)
        {
            return null;
        }
        int nSrcLen = src.length();
        byte byteArrayResult[] = new byte[nSrcLen / 2];
        StringBuffer strBufTemp = new StringBuffer(src);
        String strTemp;
        int i = 0;
        while (i < strBufTemp.length() - 1)
        {
            strTemp = src.substring(i, i + 2);
            byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
            i += 2;
        }
        return byteArrayResult;
    }
    
    /**
     * 将byte数组转换成16进制组成的字符串 例如 一个byte数组 b[0]=0x07;b[1]=0x10;...b[5]=0xFB; byte2hex(b); 将返回一个字符串"0710BE8716FB"
     *
     * @param bytes 待转换的byte数组
     * @return
     */
	public static String bytesToHexString(byte[] bytes){
        if (bytes == null)
        {
            return "";
        }
        StringBuffer buff = new StringBuffer();
        int len = bytes.length;
        for (int j = 0; j < len; j++)
        {
            if ((bytes[j] & 0xff) < 16)
            {
                buff.append('0');
            }
            buff.append(Integer.toHexString(bytes[j] & 0xff));
        }
        return buff.toString().toUpperCase();
    }
	
	public static String unPaddingAhead0(String src){
		if (src == null || src.length() == 0) {
			return src;
		}
		while (src.startsWith("0")){
			src = src.substring(1);
		}
		return src;
	}
	/**
	 * 生成8个字节的通信随机数
	 * 
	 * @return String
	 */
	public static String tradeHexRand16() {
		StringBuffer strBufHexRand = new StringBuffer();
		Random rand = new Random(System.currentTimeMillis());
		int index;
		// 随机数字符
		char charArrayHexNum[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'0', 'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0; i < 16; i++) {
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

	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str.toUpperCase();
	}
}
