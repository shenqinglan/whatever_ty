package com.whty.euicc.rsp.tls;

import org.junit.Test;

import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class DsTest {
	@Test
	public void connectTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().clientTest(eid,"127.0.0.1",1111,1);
	}
}
