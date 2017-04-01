package com.whty.euicc.profile.pukCodes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class PukCodesHandler {
	@Test
	public void pukCodesTest() throws Exception {
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("pukCode.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		System.out.println(new BaseHandler().handler(br));
	}
}
