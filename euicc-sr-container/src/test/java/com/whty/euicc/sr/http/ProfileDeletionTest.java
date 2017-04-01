package com.whty.euicc.sr.http;


import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.DeleteProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.ProfileDeletionReqBody;
import com.whty.euicc.packets.message.request.ProfileSmsProReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

public class ProfileDeletionTest {
	@Test
	public void profileDeleteByHttps()throws Exception{
        MsgHeader header = new MsgHeader("deleteProfileByHttps");
        DeleteProfileByHttpsReqBody requestBody = new DeleteProfileByHttpsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("8901");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void profileDelete()throws Exception{
        MsgHeader header = new MsgHeader("profileDeletion");
        ProfileDeletionReqBody requestBody = new ProfileDeletionReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void profileDeletePor()throws Exception{
        MsgHeader header = new MsgHeader("smsPor");
        ProfileSmsProReqBody requestBody = new ProfileSmsProReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}

}
