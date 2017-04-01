package com.whty.euicc.dp.http;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.dp.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.MasterDeleteByDpReqBody;
/**
 * SM-DP的主删除测试用例类
 * @author Administrator
 *
 */
public class MasterDeleteTest {
	String json = null;
	@Before
	public void init()throws Exception{
		MsgHeader header = new MsgHeader("masterDeleteByDp");
		MasterDeleteByDpReqBody requestBody = new MasterDeleteByDpReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
	}
	@Test
	public void masterDeleteByHttps()throws Exception{
        
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void masterDeleteByHttps_116()throws Exception{
		byte[] msgBype = BaseHttp.doPost(BaseHttp.urlByDp_116,json);
        System.out.println(new String(msgBype));
	}
}
