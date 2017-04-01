package com.whty.euicc.profile.pincodes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.whty.euicc.profile.basehandler.BaseHandler;
import com.whty.euicc.profile.mf.MfHandlerTest;

public class PinCodesHandler {
	public static void main(String[] args) throws Exception {
		InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("pinCodes.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		new BaseHandler().handler(br);
	}
}
