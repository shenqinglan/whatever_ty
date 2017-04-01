package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.SetFallBackAttrBySmsReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

public class SetFallBackAttrTest {
	@Test
	public void fallBackTest() throws Exception{
		 MsgHeader header = new MsgHeader("setFallBackAttrBySms");
		 SetFallBackAttrBySmsReqBody requestBody = new SetFallBackAttrBySmsReqBody();
			requestBody.setEid("89001012012341234012345678901224");
			requestBody.setIccid("00");
			EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
			String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
			byte[] msgBype = BaseHttp.doPost(json);
	        System.out.println(new String(msgBype));
		}
	

}
