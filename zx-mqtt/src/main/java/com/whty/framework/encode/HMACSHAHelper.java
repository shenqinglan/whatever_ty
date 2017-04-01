package com.whty.framework.encode;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.framework.common.HexUtil;

public class HMACSHAHelper {
	private static final String HMAC_SHA1 = "HmacSHA1";
	
	private static Logger logger = LoggerFactory.getLogger(HMACSHAHelper.class);

	/**
	 * 生成签名数据
	 * 
	 * @param data
	 *            待加密的数据
	 * @param key
	 *            加密使用的 key
	 * @return 生成十六进制字符串
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA1Signature(byte[] data, byte[] key)
			throws InvalidKeyException, NoSuchAlgorithmException {
		SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data);
		return HexUtil.bytes2HexString(rawHmac);
	}
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		String api_key = "87d5bd3767e841799a2de1501a634230";
		String secret_key = "100a713c3a2c4690bf36545ba1a3fa61";
		String result = getSHA1Signature(api_key.getBytes(), secret_key.getBytes());
		logger.info("result >>> {}",result);
	}
}
