package com.whty.euicc.rsp.tls.es2;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.es2plus.ReleaseProfileReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class ReleaseProfileTest {
	String path = "/gsma/rsp2/es2plus/smdp/releaseProfile";
	String type = "application/json";
	
	@Test
	public void releaseProfileTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean()); 
		System.out.println(data);
		new HttpsShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 2222);
	}

	private String reqBodyByBean() {
		ReleaseProfileReq req = new ReleaseProfileReq();
		req.setIccid("123456789000");
		return MessageHelper.gs.toJson(req);
	}

}
