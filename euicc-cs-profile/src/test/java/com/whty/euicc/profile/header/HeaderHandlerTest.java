package com.whty.euicc.profile.header;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.profile.basehandler.BaseHandler;

public class HeaderHandlerTest {
	@Test
	public void headerTest()throws Exception {
		try {
			// 缓冲流读取文件
			InputStream in = HeaderHandlerTest.class.getClassLoader().getResourceAsStream("chinamobile.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			System.out.println(new BaseHandler().handler(br));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	 

}
