package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.FinaliseISDRhandoverReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

/**
 * sr2 apdu测试
 * @author zyj
 *
 */
public class SrChangeFinaliseISDRhandoverTest {
	@Test
	public void changeForScp80Test() throws Exception{
		MsgHeader header = new MsgHeader("srChangeForScp80");
		FinaliseISDRhandoverReqBody requestBody = new FinaliseISDRhandoverReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
	    System.out.println(new String(msgBype));
	}

}
