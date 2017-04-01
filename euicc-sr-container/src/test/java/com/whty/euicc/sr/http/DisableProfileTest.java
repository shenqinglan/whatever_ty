package com.whty.euicc.sr.http;


import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.DisableProfileByHttpsReqBoy;
import com.whty.euicc.packets.message.request.DisableProfileReqBoy;
import com.whty.euicc.packets.message.request.ProfiledisableSmsPorReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;
/**
 * SM-SR的禁用Profile测试用例类
 * @author Administrator
 *
 */
public class DisableProfileTest {
	@Test
	public void disableProfileByHttps()throws Exception{
        MsgHeader header = new MsgHeader("disableProfileByHttps");
        DisableProfileByHttpsReqBoy requestBody = new DisableProfileByHttpsReqBoy();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void disableProfile()throws Exception{
        MsgHeader header = new MsgHeader("disableProfile");
        DisableProfileReqBoy requestBody = new DisableProfileReqBoy();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("02");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void disableProfileSms()throws Exception{
        MsgHeader header = new MsgHeader("disableProfileBySms");
        DisableProfileReqBoy requestBody = new DisableProfileReqBoy();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
		
	
		
	@Test
	public void disableProfilePor()throws Exception{
        MsgHeader header = new MsgHeader("smsDisPor");
        ProfiledisableSmsPorReqBody requestBody = new ProfiledisableSmsPorReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}

}
