package com.whty.euicc.profile.end;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class TestEnd {
	public static void main(String[] args) throws Exception {
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("end.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		new BaseHandler().handler(br);
	}
		
	
}
