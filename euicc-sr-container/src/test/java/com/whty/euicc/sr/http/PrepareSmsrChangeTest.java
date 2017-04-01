package com.whty.euicc.sr.http;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.PrepareSmsrChangeReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

public class PrepareSmsrChangeTest {
private String json = null;
	
	@Before
	public void init() throws Exception{
		MsgHeader header = new MsgHeader("prepareSmsrChange");
		PrepareSmsrChangeReqBody requestBody = new PrepareSmsrChangeReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setCurrentSmsrId("9180");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
	}
	
	@Test
	public void prepareSmsrChangeTest() throws Exception{
		byte[] msgBype = BaseHttp.doPost(json);
	    System.out.println(new String(msgBype));
	}	

}
