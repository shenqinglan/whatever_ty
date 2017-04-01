package com.whty.euicc.sr.util;

import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.AppApplyReqBody;

public class GsonTest {
	Gson GSON = new Gson();
	
	@Test
	public void test(){
		// 未申请
		MsgHeader header = new MsgHeader("retrieveEISBySr");

		AppApplyReqBody requestBody = new AppApplyReqBody();//(AppApplyReqBody) this.getMsgBody();
		requestBody.setIdNo("001");
		requestBody.setAppAID("002");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);

		// 将报文转换为json
		String json = GSON.toJson(euiccMsg, EuiccMsg.class);
		System.out.println("未加密的报文为："+ json);
	}

}
