package com.whty.euicc.rsp.tls.es9;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.es9plus.HandleNotificationReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class HandleNotificationTest {
	String path = "/gsma/rsp2/es9plus/smdp/handleNotification";
	String type = "application/json";
	
	@Test
	public void handleNotificationTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		new HttpsShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 2222);
	}
	
	private String reqBodyByBean(){
		HandleNotificationReq req = new HandleNotificationReq();
		req.setPendingNotification("notificationHere");
		return MessageHelper.gs.toJson(req);
	}

}
