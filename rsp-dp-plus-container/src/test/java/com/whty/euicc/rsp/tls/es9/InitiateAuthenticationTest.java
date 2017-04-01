/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-14
 * Id: InitiateAuthenticationTest.java,v 1.0 2016-12-14 上午9:54:23 Administrator
 */
package com.whty.euicc.rsp.tls.es9;

import org.junit.Test;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.es9plus.InitiateAuthenticationReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

/**
 * @ClassName InitiateAuthenticationTest
 * @author Administrator
 * @date 2016-12-14 上午9:54:23
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class InitiateAuthenticationTest {

	String path = "/gsma/rsp2/es9plus/smdp/initiateAuthentication";
	String type = "application/json";
	
	@Test
	public void connectTest()throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path,type,reqBodyByBean());
        new HttpsShakeHandsClient().clientTestByRsp(data,"127.0.0.1",2222);
	}
	
	private String reqBodyByBean(){
		InitiateAuthenticationReq req = new InitiateAuthenticationReq();
		req.setEuiccChallenge("ZVVpY2NDaGFsbGVuZ2VFeGFtcGxlQmFzZTY0oUFZuQnNZVE5D");
		req.setEuiccInfo1("RmVHRnRjR3hsUW1GelpUWTBvVUZadVFuTlpWRTU");
		req.setSmdpAddress("smdp.gsma.com");
		return MessageHelper.gs.toJson(req);
	}
	
}
