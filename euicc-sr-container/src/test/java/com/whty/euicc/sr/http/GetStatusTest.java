package com.whty.euicc.sr.http;


import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.GetStatusByHttpsReqBody;
import com.whty.euicc.packets.message.request.GetStatusBySmsReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;
/**
 * SM-SR的状态查询测试用例类
 * @author Administrator
 *
 */
public class GetStatusTest {
	@Test
	public void getStatusByHttps()throws Exception{
        MsgHeader header = new MsgHeader("getStatusByHttps");
        GetStatusByHttpsReqBody requestBody = new GetStatusByHttpsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("8901");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void getStatusBySms()throws Exception{
        MsgHeader header = new MsgHeader("getStatusBySms");
        GetStatusBySmsReqBody requestBody = new GetStatusBySmsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
}
