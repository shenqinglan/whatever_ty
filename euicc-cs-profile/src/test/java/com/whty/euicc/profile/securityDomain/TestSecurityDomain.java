package com.whty.euicc.profile.securityDomain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class TestSecurityDomain {
	@Test
	public void SecurityDomain() throws Exception{
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("securitDomain.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		new BaseHandler().handler(br);
	}
	
}
