package com.whty.euicc.rsp.tls;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.HandleNotificationReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.tls.client.ecc.EccCardShakeHandsClient;

public class HandleNotificationTest {
	String path = "/gsma/rsp2/es9plus/handleNotification";
	String type = "application/json";
	
	@Test
	public void handleNotificationTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		String data2 = EuiccMsgParse.prepareHttpParam(path, type, "");
		String serverResult = new EccCardShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 8765, data2, null);
		System.out.println("serverResult >> \n" + serverResult);
	}
	
	private String reqBodyByBean(){
		HandleNotificationReq req = new HandleNotificationReq();
		req.setProfileInstallationResultData("BF275380081122334455667700BF2F19800101810207800C04000102035A0AA0000102030405060709060A2B0601040182F5460204A21FA01D4F10A0000005591010FFFFFFFF890000160004093007A0053003800100");
		req.setEuiccSignPIR("5F37405EC0F76E187097FD7F7C8B8D12B6A219C6DC76751FB0E7595705DB67968285461E43480E63449C8AC5DE99708C9EA414926C3A3EF36B3AAC7B97E87007E2B946");
		return MessageHelper.gs.toJson(req);
	}

}
