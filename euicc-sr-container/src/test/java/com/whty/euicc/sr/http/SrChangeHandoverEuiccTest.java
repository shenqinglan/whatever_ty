package com.whty.euicc.sr.http;

import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.SrChangeSendReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

/**
 * sr2 HandoverEuicc测试
 * @author 11
 *
 */
public class SrChangeHandoverEuiccTest {
	@Test
	public void srChangeHandoverEuiccTest() throws Exception{
		MsgHeader header = new MsgHeader("srChangeSend");
		SrChangeSendReqBody requestBody = new SrChangeSendReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setTargetSmsrId("0819");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
	    System.out.println(new String(msgBype));
	}	
    
}
