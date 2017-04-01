package com.whty.security;

import org.junit.Test;

import com.whty.security.scp03.Scp03;
import com.whty.security.scp03.bean.InitializeUpdateBean;
import com.whty.security.scp03.bean.InitializeUpdateRespBean;
import com.whty.security.scp03.mock.Scp03Mock;

public class Scp03Test {

	private String keyENC="11223344556677889910111213141516";
	private String keyMAC="11223344556677889910111213141516";
	private String keyVer="01";
	private String securedLevel="33";
	
	@Test
	public void scp03Test() throws Exception{
		Scp03 scp = new Scp03();
		Scp03Mock mock = new Scp03Mock();
		
		//first step  initialize update apdu
		InitializeUpdateBean initUpdateBean = scp.initializeUpdateCmd(keyVer);
		String initializeApdu = initUpdateBean.getApdu();
		System.out.println("initializeApdu:"+initializeApdu);
		String hostChallenge = initUpdateBean.getHostChallenge();
		
		//first step  initialize update check
		String receiveApdu = mock.initializeUpdateResponse(hostChallenge);
		InitializeUpdateRespBean initUpdateRespBean = scp.checkInitializeUpdateResp(receiveApdu, hostChallenge, keyMAC, keyENC);
		
		if(!initUpdateRespBean.isCheckResult()){
			System.out.println("initalizeUpdate check is error");
		}
		
		
		//second step  external auth apdu 
		hostChallenge = initUpdateRespBean.getHostChallenge();
		String cardChallenge = initUpdateRespBean.getCardChallenge();
		String S_MAC = initUpdateRespBean.getS_MAC();
		String S_ENC = initUpdateRespBean.getS_ENC();
		String perMac="00000000000000000000000000000000" ;
		String encCounter="000000";
		String externalApdu = scp.externalAuthCmd(securedLevel, hostChallenge, cardChallenge, S_MAC,perMac);
		System.out.println("externalApdu:"+externalApdu);
		
		//second step external auth check
		String receiveExternalApdu = mock.externalAuthResponse();
		int resp = scp.checkExternalAuthResp(receiveExternalApdu);
		System.out.println("external check:"+resp);
		
		
		//three encryption data
		encCounter = scp.addEncCounter(encCounter);
		String encryptionDate = scp.encryptionData("0123456789", encCounter, S_ENC, S_MAC,perMac);
		System.out.println("encryptionDate:"+encryptionDate);
	}
}
