package com.whty.euicc.sr.http;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.whty.euicc.common.https.HttpsUtil;
import com.whty.euicc.common.https.SslContextFactory;

/**
 * 前台调用模拟测试
 * 运营商接入模拟测试
 * @author Administrator
 *
 */
public class NettyTest {
	@Test
	public void nettyTest() throws Exception{
		String url = "https://127.0.0.1:9999";
		String tradeNO = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
        String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2014-10-11 11:32:01\",\"msgType\":\"tath.016.001.01\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"cardNO\":\"12000004000000500094\",\"appInstalledTag\":\"00\"}}";
        HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
        System.out.println(new String(msgBype));
	}

}
