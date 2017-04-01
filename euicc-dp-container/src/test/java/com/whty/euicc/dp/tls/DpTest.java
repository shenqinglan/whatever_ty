package com.whty.euicc.dp.tls;



import org.junit.Test;

import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class DpTest {
	String ip = "127.0.0.1";
	int port = 8091;
	
	
	@Test
	public void connect()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().clientTest(eid,ip,port,2);
	}
}
