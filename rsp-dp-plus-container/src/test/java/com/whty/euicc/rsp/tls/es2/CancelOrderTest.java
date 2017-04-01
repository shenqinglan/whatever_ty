package com.whty.euicc.rsp.tls.es2;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.es2plus.CancelOrderReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class CancelOrderTest {
	String path = "/gsma/rsp2/es2plus/smdp/cancelOrder";
	String type = "application/json";
	
	@Test
	public void cancelOrderTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		new HttpsShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 2222);
	}

	private String reqBodyByBean() {
		CancelOrderReq req = new CancelOrderReq();
		req.setIccid("123456789000");
		req.setEid("000987654321");
		req.setMatchingId("");
		req.setFinalProfileStatusIndicator("Available");
		return MessageHelper.gs.toJson(req);
	}

}
