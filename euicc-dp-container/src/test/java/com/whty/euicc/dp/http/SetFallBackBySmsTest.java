package com.whty.euicc.dp.http;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.dp.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.SetFallBackByDpAndSmsReqBody;

public class SetFallBackBySmsTest {
	private String json = null;
	@Before
	public void init()throws Exception{
        MsgHeader header = new MsgHeader("setFallBackAttrByDpAndSms");
        SetFallBackByDpAndSmsReqBody requestBody = new SetFallBackByDpAndSmsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
	}
	@Test
	public void setFallBackTest() throws Exception{
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
		
	}

}
