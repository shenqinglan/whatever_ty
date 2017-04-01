package com.whty.euicc.profile.mf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;

public class MfHandlerTest {
	@Test
	public void headerTest()throws Exception {
		// 缓冲流读取文件
		try {
			InputStream in = MfHandlerTest.class.getClassLoader().getResourceAsStream("akaParameter.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			System.out.println(new BaseHandler().handler(br));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
