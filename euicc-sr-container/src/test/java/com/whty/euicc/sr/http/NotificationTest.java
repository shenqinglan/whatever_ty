package com.whty.euicc.sr.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.common.https.HttpsUtil;
import com.whty.euicc.common.https.SslContextFactory;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.ReceiveNotificationBySmsReqBody;
import com.whty.euicc.packets.message.request.ReceivePorReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;
/**
 * SM-SR的SMS通知测试用例类
 * @author Administrator
 *
 */
public class NotificationTest {
	private String url = "https://127.0.0.1:9999";
	private String tradeNO = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());

	@Test
	public void receiveRespBySmsTest()throws Exception{
        MsgHeader header = new MsgHeader("receiveNotificationBySms");
        ReceiveNotificationBySmsReqBody requestBody = new ReceiveNotificationBySmsReqBody();
        requestBody.setPhoneNo("18213001300");
        requestBody.setTpud("02710000130A00000100000001910000AF802302900000009000");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void receiveNotificationBySmsTest()throws Exception{
        MsgHeader header = new MsgHeader("receiveNotificationBySms");
        ReceiveNotificationBySmsReqBody requestBody = new ReceiveNotificationBySmsReqBody();
        requestBody.setTpud("E1354C10890010120123412340123456789012244D01024E0200004F10A0000005591010FFFFFFFF890000130014081122334455667788");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void receiveNotificationBySms1Test()throws Exception{
		String apdu = "E1354C10890010120123412340123456789012244D01024E0200004F10A0000005591010FFFFFFFF890000130014081122334455667788";
		String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"receiveNotificationBySms\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"tpud\":\""+apdu+"\"}}";
	    HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void receiveNotificationBySmsTest2()throws Exception{
        MsgHeader header = new MsgHeader("receivePor");
        ReceivePorReqBody requestBody = new ReceivePorReqBody();
        requestBody.setTpud("02710000340A00001300000002D50000AB27800101232200000000000000000000300370BE9E38C3D0E672AAA2951ECA8E7316C500000390009000");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void receiveNotificationBySmsTest3()throws Exception{
        MsgHeader header = new MsgHeader("receivePor");
        ReceivePorReqBody requestBody = new ReceivePorReqBody();
        requestBody.setTpud("02710000340A00001300000002D50000AB27800101232200000000000000000000300370BE9E38C3D0E672AAA2951ECA8E7316C500000490009000");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void receiveNotificationBySmsTest4()throws Exception{
        MsgHeader header = new MsgHeader("receivePor");
        ReceivePorReqBody requestBody = new ReceivePorReqBody();
        requestBody.setTpud("02710000140A00001300000000350000AB0780010123029000");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void receiveNotificationBySmsTest5()throws Exception{
        MsgHeader header = new MsgHeader("receivePor");
        ReceivePorReqBody requestBody = new ReceivePorReqBody();
        requestBody.setTpud("027100001C0A00001300000000360000AB0F800101230A701DC3A5BD0EE89C9000");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
}
