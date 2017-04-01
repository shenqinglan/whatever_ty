package com.whty.euicc.tls;

import static com.whty.euicc.tls.demo.HttpsClient.callSr;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class SrTest {
	@Test
	public void connect()throws Exception{
		String tradeNO = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
        String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2014-10-11 11:32:01\",\"msgType\":\"tath.016.001.01\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"cardNO\":\"12000004000000500094\",\"appInstalledTag\":\"00\"}}";
        callSr(str);
        //new HttpsShakeHandsClient().callSr(str);
	}
}
