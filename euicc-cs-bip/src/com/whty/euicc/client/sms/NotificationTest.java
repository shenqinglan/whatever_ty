package com.whty.euicc.client.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationTest {
	private static final String url = "https://10.8.40.125:9999";
//	private static final String url = "https://10.8.40.128:9999";
	private static String tradeNO = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());

	public static void pushNotificationBySms(String apdu){
		try {
			String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"receiveNotificationBySms\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"tpud\":\""+apdu+"\"}}";
//			String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"receivePor\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"tpud\":\""+apdu+"\"}}";
			
			System.out.println("send msg is ----------->: " + str);
			HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
			byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
			System.out.println(new String(msgBype));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args)throws Exception{
		//String apdu = "E1354C10890010120123412340123456789012244D01024E0200004F10A0000005591010FFFFFFFF890000130014081122334455667788";
		String apdu ="02710000340A00001300000002D50000AB27800101232200000000000000000000300370BE9E38C3D0E672AAA2951ECA8E7316C500000390009000";
		NotificationTest.pushNotificationBySms(apdu);
	}
}
