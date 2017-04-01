package com.whty.euicc.profile.rfm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class TestRfm {
	@Test
	public  void Rfm() throws Exception {
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("rfm.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		new BaseHandler().handler(br);
	}
}
