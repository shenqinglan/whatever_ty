package com.whty.euicc.rsp.tls;

import org.junit.Test;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.DownloadOrderReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.tls.client.ecc.EccCardShakeHandsClient;

public class DownloadOrderTest {
	String path = "/gsma/rsp2/es2plus/downloadOrder"; 
	String type = "application/json";
	
	@Test
	public void downloadOrderTest() throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path, type, reqBodyByBean());
		String data2 = EuiccMsgParse.prepareHttpParam(path,type,"");
		String serverResult = new EccCardShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 8765, data2, "");
		System.out.println("serverResult >> \n" + serverResult);
	}

	private String reqBodyByBean() {
		DownloadOrderReq req = new DownloadOrderReq();
		req.setEid("8900112233445566778899AABBCCDDEE");
//		req.setIccid("00");
		req.setProfileType("TELECOM");
		//电信：17762491839 联通：18611159401
		req.setMsisdn("17762491839");
		return MessageHelper.gs.toJson(req);
	}

}
