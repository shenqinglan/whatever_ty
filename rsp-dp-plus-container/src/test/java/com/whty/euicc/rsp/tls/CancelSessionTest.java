package com.whty.euicc.rsp.tls;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.CancelSessionReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.tls.client.ecc.EccCardShakeHandsClient;

public class CancelSessionTest {
	String path = "/gsma/rsp2/es9plus/cancelSession";
	String type = "application/json";
	
	@Test
	public void cancelSessionTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		String data2 = EuiccMsgParse.prepareHttpParam(path, type, "");
		String serverResult = new EccCardShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 8765, data2, null);
		System.out.println("serverResult >> \n" + serverResult);
		
	}

	private String reqBodyByBean() {
		CancelSessionReq req = new CancelSessionReq();
		req.setTransactionId("80081122334455667700");
		req.setEuiccCancelSessionSigned("301980081122334455667701810A2B0601040182F5460204820100");
		req.setEuiccCancelSessionSignature("5F3740F60FE9A21CDBC49CAE4DC8840BCCDAAD41E475AD899D29DF74D48925F9CF3F7E195E0CDE2CC8F3BEBE169FDA2612AEE939AC26C8BE9CAF4CA725F8ADAE4946BA");
		return MessageHelper.gs.toJson(req);
	}

}
