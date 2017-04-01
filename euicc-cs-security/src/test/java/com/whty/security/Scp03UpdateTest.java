package com.whty.security;

import org.junit.Test;

import com.whty.security.scp03forupdate.Scp03ForUpdate;

public class Scp03UpdateTest {
	String respData = "";
	String status = "9000";
	String secureLevel = "33";
	String S_RMAC = "2327E2DFDBA5923E96A7A93F03B9E1DF";
	String S_ENC = "8C7E947F86FEBB6E04F05C2BB21D3C9E";
	String encCounter = "000001";
	String perMac = "";
	String hostChanllenge = "";
	String cardChanllenge = "";
	
	@Test
    public void updateTest() throws Exception {
    	String reString = Scp03ForUpdate.gpRespScp03(hostChanllenge,cardChanllenge,encCounter,respData, status, secureLevel,S_ENC, S_RMAC,perMac);
		System.out.println(reString);
		
	}
			
}
