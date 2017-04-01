package com.whty.euicc.sr.http;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.whty.euicc.common.https.HttpsUtil;
import com.whty.euicc.common.https.SslContextFactory;
/**
 * SM-SR的scp03测试用例类
 * @author Administrator
 *
 */
public class Scp03Test {
	private String url = "https://127.0.0.1:9999";
	private String tradeNO = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
	
	@Test
	public void scp03()throws Exception{
		String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"scp03\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"eid\":\"89001012012341234012345678901224\"}}";
	    HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
        System.out.println(new String(msgBype));
	}
}
