package com.whty.euicc.rsp.tls.es2;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.es2plus.DownloadOrderReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class DownloadOrderTest {
	String path = "/gsma/rsp2/es2plus/smdp/downloadOrder"; 
	String type = "application/json";
	
	@Test
	public void downloadOrderTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		System.out.println(data);
		new HttpsShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 2222);
	}

	private String reqBodyByBean() {
		DownloadOrderReq req = new DownloadOrderReq();
		req.setEid("876543210000");
		req.setIccid("000012345678");
		req.setProfileType("profileTypeHere");
		return MessageHelper.gs.toJson(req);
	}

}
