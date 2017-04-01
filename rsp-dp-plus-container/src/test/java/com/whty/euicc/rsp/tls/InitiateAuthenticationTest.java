package com.whty.euicc.rsp.tls;

import org.junit.Test;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.InitiateAuthenticationReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.tls.client.ecc.EccCardShakeHandsClient;

public class InitiateAuthenticationTest {
	String path = "/gsma/rsp2/es9plus/initiateAuthentication";
	String type = "application/json";
	
	@Test
	public void connectTest()throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path,type,reqBodyByBean());
		String data2 = EuiccMsgParse.prepareHttpParam(path,type,"");
		String serverResult = new EccCardShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 8765, data2, "");
		System.out.println("serverResult >> \n" + serverResult);
	}
	
	private String reqBodyByBean(){
		InitiateAuthenticationReq req = new InitiateAuthenticationReq();
		req.setEuiccChallenge("8010000102030405060708090A0B0C0D0E0F");
		req.setEuiccInfo1("BF20818D8203020000A94204143bd3f57b34f44436e08f028b5101c0e0a9b0986e0414012a83706174def23d357de977dbc3eeac5a66e00414856bf2b42e451591a70107c09548a0111b25df61AA4204143bd3f57b34f44436e08f028b5101c0e0a9b0986e0414012a83706174def23d357de977dbc3eeac5a66e00414856bf2b42e451591a70107c09548a0111b25df61");
		req.setSmdpAddress("830400010203");
		return MessageHelper.gs.toJson(req);
	}
	
}
