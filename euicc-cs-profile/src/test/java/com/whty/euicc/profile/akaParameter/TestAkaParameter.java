package com.whty.euicc.profile.akaParameter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class TestAkaParameter {
	@Test
	public void AkaParameter() throws Exception {
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("akaParameter.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		System.out.println(new BaseHandler().handler(br));
	}
}
