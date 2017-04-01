package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.EnableProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.EnableProfileReqBody;
import com.whty.euicc.packets.message.request.ProfileEnableSmsPorReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;
/**
 * SM-SR的启用Profile测试用例类
 * @author Administrator
 *
 */
public class EnableProfileTest {
	@Test
	public void enableProfileByHttps()throws Exception{
        MsgHeader header = new MsgHeader("enableProfileByHttps");
        EnableProfileByHttpsReqBody requestBody = new EnableProfileByHttpsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void enableProfileBySms()throws Exception{
        MsgHeader header = new MsgHeader("enableProfileBySms");
        EnableProfileReqBody requestBody = new EnableProfileReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void profileEnablePor()throws Exception{
        MsgHeader header = new MsgHeader("smsEnPor");
        ProfileEnableSmsPorReqBody requestBody = new ProfileEnableSmsPorReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
}
