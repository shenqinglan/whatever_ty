package com.whty.euicc.tls;

import static com.whty.euicc.tls.demo.HttpsClient.callDp;


import org.junit.Test;

import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class DpTest {
	@Test
	public void connect()throws Exception{
        String str =  "hello dp world!";
        //callDp(str);
        new HttpsShakeHandsClient().callSr(str,1);
	}
}
