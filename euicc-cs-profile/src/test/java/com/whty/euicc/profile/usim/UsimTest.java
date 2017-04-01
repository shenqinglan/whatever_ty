package com.whty.euicc.profile.usim;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class UsimTest {
	@Test
	public void testUsim() throws Exception{
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("usim1.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		new BaseHandler().handler(br);
	}
}
