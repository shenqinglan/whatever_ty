package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.MasterDeleteByHttpsReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;
/**
 * SM-SR的主删除测试用例类
 * @author Administrator
 *
 */
public class MasterDeleteTest {
	@Test
	public void masterDeleteByHttps()throws Exception{
        MsgHeader header = new MsgHeader("masterDeleteByHttps");
        MasterDeleteByHttpsReqBody requestBody = new MasterDeleteByHttpsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void masterDeleteBySms()throws Exception{
        MsgHeader header = new MsgHeader("masterDeleteBySms");
        MasterDeleteByHttpsReqBody requestBody = new MasterDeleteByHttpsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
}
