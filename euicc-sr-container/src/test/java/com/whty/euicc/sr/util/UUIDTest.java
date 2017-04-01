package com.whty.euicc.sr.util;

import java.util.UUID;

import org.junit.Test;

public class UUIDTest {
	@Test
	public void test(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		System.out.println(uuid);
		System.out.println(uuid.length());
	}

}
