package com.whty.smpp.demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.whty.framework.http.HTTPWeb;

public class HttpTest {
	
	@Test
	public void deliver() {
		String url = "http://localhost:8080/euicc-efs-container/smsNetty/receiveSms";
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("src", "123456");
		json.put("msg", "deliver msg");
		String data = HTTPWeb.post(url, json);
		System.out.println("data >>> "+data);
	}
}
