package com.whty.euicc.profile.gsmAccess;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class GsmAccessTest {
	
	@Test
	public void gsmAccess(){
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("gsmaccess.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			System.out.println("der >>" + new BaseHandler().handler(br));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
