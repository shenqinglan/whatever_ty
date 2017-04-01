package com.whty.security.scp03t.scp03t.mock;

import com.whty.security.scp03t.dataencryption.CreateKey;



/**
 * SCP03t模拟返回类
 * @author Administrator
 *
 */
public class Scp03tMock {
	private String cardChallenge="3DB6E52A59E0AF8E";
	private String keyMAC="11223344556677889910111213141516";
	private String resExternal="8500";
	private String keyVer="30";
	/**
	 * 模拟scp03第一步命令initializeUpdate返回
	 * @return
	 * @throws Exception 
	 */
	public String initializeUpdateResponse(String hostChallenge) throws Exception{
		String context=hostChallenge+cardChallenge;
		String cardCrypgram=CreateKey.cardCryp(CreateKey.S_MAC(keyMAC, context), context);
		System.out.println("cardChallenge:"+cardChallenge);
		System.out.println("cardCrypgram:"+cardCrypgram);
		String response="84"+"20"+"00000000000000000000"+keyVer+"0370"+cardChallenge+cardCrypgram+"000001";
		System.out.println("resInitializeUpdate:"+response);
		return response;
	}
	
	/**
	 * 模拟scp03第二步命令externalAuth返回
	 * @return
	 * @throws Exception 
	 */
	public String externalAuthResponse(){
		return resExternal;
	}
}
