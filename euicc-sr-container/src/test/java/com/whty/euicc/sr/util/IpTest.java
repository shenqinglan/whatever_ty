package com.whty.euicc.sr.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class IpTest {
	@Test
	public void ipTest() throws UnknownHostException{
		 String ip = InetAddress.getLocalHost().getHostAddress();  
		 System.out.println(ip);
	}
	
	
}
