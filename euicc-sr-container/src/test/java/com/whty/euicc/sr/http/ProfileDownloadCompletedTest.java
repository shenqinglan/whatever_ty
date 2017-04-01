package com.whty.euicc.sr.http;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.common.https.HttpsUtil;
import com.whty.euicc.common.https.SslContextFactory;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.EnableProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.ProfileDownloadCompletedReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

public class ProfileDownloadCompletedTest {
	private String url = "https://127.0.0.1:9999";
	private String tradeNO = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
	//@Test

	public void installProfileComplete()throws Exception{
        String str =  "{\"header\":{\"tradeNO\":\""+tradeNO+"\",\"deviceType\":\"888\",\"sendTime\":\"2016-7-25 11:32:01\",\"msgType\":\"profileDownloadCompleted\",\"tradeRefNO\":\"0\",\"version\":\"01\"},\"body\":{\"eid\":\"89001012012341234012345678901224\",\"iccid\":\"00\",\"isdPAid\":\"A0000005591010FFFFFFFF8900001300\"}}";

        HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        byte[] msgBype =HttpsUtil.doPost2(url, str.getBytes());
        System.out.println(new String(msgBype));
	}
	@Test
	public void installProfileCompleted()throws Exception{
		String isdPAid = "A0000005591010FFFFFFFF8900001300";
        MsgHeader header = new MsgHeader("profileDownloadCompleted");
        ProfileDownloadCompletedReqBody requestBody = new ProfileDownloadCompletedReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		requestBody.setIsdPAid(isdPAid);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
}
