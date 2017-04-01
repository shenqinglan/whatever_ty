package com.whty.euicc.profile.util;

import java.util.Arrays;

import org.junit.Test;

public class ToolTest {
	@Test
	public void encodeTest() {
		System.out.println(Tool.encode("GSMA Profile Package").length());
	}

	@Test
	public void compareTest() {
		System.out.println(Arrays.toString(Tool.compare(840)));
	}
	
	
	@Test
	public void decode(){
		System.out.print(Tool.decode("0D0A0D0A"));
		System.out.println(Tool.decode("0D0A0D0A"));
	}

}
