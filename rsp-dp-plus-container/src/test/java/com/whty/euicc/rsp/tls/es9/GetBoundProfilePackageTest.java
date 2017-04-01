package com.whty.euicc.rsp.tls.es9;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.es9plus.GetBoundProfilePackageReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class GetBoundProfilePackageTest {
	String path = "/gsma/rsp2/es9plus/smdp/getBoundProfilePackage"; 
	String type = "application/json";
	
	@Test
	public void getBoundProfilePackageTest()throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path,type,reqBodyByBean());
        new HttpsShakeHandsClient().clientTestByRsp(data,"127.0.0.1",2222);
	}
	
	private String reqBodyByBean(){
		GetBoundProfilePackageReq req = new GetBoundProfilePackageReq();
		req.setTransactionId("1122334455667788");
		return MessageHelper.gs.toJson(req);
	}

}
