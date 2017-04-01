package com.whty.euicc.rsp.tls;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.ConfirmOrderReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;
import com.whty.tls.client.ecc.EccCardShakeHandsClient;

public class ConfirmOrderTest {
	String path = "/gsma/rsp2/es2plus/confirmOrder";
	String type = "application/json";
	
	@Test
	public void confirmOrderTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		String data2 = EuiccMsgParse.prepareHttpParam(path,type,"");
		String serverResult = new EccCardShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 8765, data2, "");
		System.out.println("serverResult >> \n" + serverResult);
	}

	private String reqBodyByBean() {
		ConfirmOrderReq req = new ConfirmOrderReq();
		//Operator可根据DownloadOrder接口服务器返回的iccid传入
		req.setIccid("A000010203040506070A");
		req.setEid("8900112233445566778899AABBCCDDEE");
		req.setMatchingId("0102030405060708");
		req.setComfirmationCode("1234");
		req.setSmdsAddress("smds.gsma.com");
		req.setReleaseFlag(true);
		return MessageHelper.gs.toJson(req);
	}

}
