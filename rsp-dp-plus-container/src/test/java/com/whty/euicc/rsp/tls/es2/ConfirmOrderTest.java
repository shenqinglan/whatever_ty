package com.whty.euicc.rsp.tls.es2;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.es2plus.ConfirmOrderReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class ConfirmOrderTest {
	String path = "/gsma/rsp2/es2plus/smdp/confirmOrder";
	String type = "application/json";
	
	@Test
	public void confirmOrderTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		System.out.println(data);
		new HttpsShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 2222);
	}

	private String reqBodyByBean() {
		ConfirmOrderReq req = new ConfirmOrderReq();
		req.setIccid("123456789000");
		req.setEid("000987654321");
		req.setMatchingId("agagag");
		req.setConfirmationCode("confirmationCode");
		req.setSmdsAddress("smds.gsma.com");
		req.setReleaseFlag(true);
		return MessageHelper.gs.toJson(req);
	}

}
