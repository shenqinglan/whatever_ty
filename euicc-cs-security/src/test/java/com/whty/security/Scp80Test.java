package com.whty.security;



import org.junit.Test;

import com.whty.security.aes.AesCbc;
import com.whty.security.aes.AesCmac;





public class Scp80Test {

	@Test
	public void aesCbc1Test()throws Exception{
		String data = AesCbc.aesCbc1("0987654321123456789012345678903409876543211234567890123456789034","12345678901234561234567890123456","00000000000000000000000000000000",0);
		System.out.println("nopadding encrypt/decrypt data:" +data);
	}
	@Test
	public void aesCbc2Test()throws Exception{
		String data = AesCbc.aesCbc2("09876543211234567890","12345678901234561234567890123456","00000000000000000000000000000000",0);
		System.out.println("padding encrypt/decrypt data:" +data);
	}
	
	@Test
	public void aesCmacTest() throws Exception {
		
		AesCmac mac = new AesCmac();
		String data = mac.calcuCmac(/*"09876543211234567890098765432123"*/"00701506001515B20202000000000F078151834F8419350702020403051F02390202003C0301270F3E0521D35A0E1589328A0A3137322E39362E302E318B0A303132333435363738398C182F7365727665722F61646D696E6167656E743F636D643D3100000000000000000000", "52DABF691AFD0E2B540CA6A09DEE3BF9");
		//String data = mac.calcuCmac("09876543211234567890","12345678901234561234567890123456");
		System.out.println("mac is:" +data);
		//System.out.println("the high 8 octets mac is:" + data.substring(0, 16));
		
		
	    }
	
	
	
		
	
	    
	 
	
}
