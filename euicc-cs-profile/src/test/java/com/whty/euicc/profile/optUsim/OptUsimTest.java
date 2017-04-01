package com.whty.euicc.profile.optUsim;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class OptUsimTest {
	@Test
	public void optUsim() throws Exception{
		try {
			InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("optusim.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			System.out.println(new BaseHandler().handler(br));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
