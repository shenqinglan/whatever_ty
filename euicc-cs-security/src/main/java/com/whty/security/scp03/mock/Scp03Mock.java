package com.whty.security.scp03.mock;

import com.whty.security.scp03t.dataencryption.CreateKey;
/**
 * scp03二步命令模拟返回
 * @author Administrator
 *
 */
public class Scp03Mock {
	private String cardChallenge="156cd3e1c65abab3";
	private String keyMAC="11223344556677889910111213141516";
	private String resExternal="8500";
	
	/**
	 * 模拟scp03第一步命令initializeUpdate返回
	 * @return
	 * @throws Exception 
	 */
	public String initializeUpdateResponse(String hostChallenge) throws Exception{
		String context=hostChallenge+cardChallenge;
		//String cardCrypgram=ExternalAuthKeyInitial.cardCryp(ExternalAuthKeyInitial.S_MAC(keyMAC, context), context);
		String cardCrypgram=CreateKey.cardCryp(CreateKey.S_MAC(keyMAC, context), context);
		System.out.println("cardChallenge:"+cardChallenge);
		System.out.println("cardCrypgram:"+cardCrypgram);
		String response="84"+"20"+"11223344556677889910"+"112233"+cardChallenge+cardCrypgram+"000001";
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
