package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.ConnectParaUpdateByHttpsReqBody;
import com.whty.euicc.packets.message.request.UpdateConnectivityParametersReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

public class ConnectParaUpdateTest {
	@Test
	public void connTest() throws Exception{
		 MsgHeader header = new MsgHeader("connectParaUpdateByHttps");
		    ConnectParaUpdateByHttpsReqBody requestBody = new ConnectParaUpdateByHttpsReqBody();
			requestBody.setEid("89001012012341234012345678901224");
			requestBody.setIccid("00");
			requestBody.setSeqCounter("00000E");
			requestBody.setSmsCenterNo("123456789123");
			EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
			String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
			byte[] msgBype = BaseHttp.doPost(json);
	        System.out.println(new String(msgBype));
		}
	
	@Test
	public void connUpTest() throws Exception{
		 MsgHeader header = new MsgHeader("updateConnectivityParametersBySms");
		 UpdateConnectivityParametersReqBody requestBody = new UpdateConnectivityParametersReqBody();
			requestBody.setEid("89001012012341234012345678901224");
			requestBody.setIccid("00");
			requestBody.setSmsCenterNo("8613250122353");
			EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
			String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
			byte[] msgBype = BaseHttp.doPost(json);
	        System.out.println(new String(msgBype));
		}

}
