package com.whty.euicc.dp.http;

import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.dp.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.GetStatusByDpReqBody;
/**
 * SM-DP的状态查询测试用例类
 * @author Administrator
 *
 */
public class GetStatusTest {
	private String json = null;
	@Before
	public void init()throws Exception{
		 MsgHeader header = new MsgHeader("getStatusByDp");
		 GetStatusByDpReqBody requestBody = new GetStatusByDpReqBody();
		 requestBody.setEid("89001012012341234012345678901224");
		 requestBody.setIccid("00");
		 EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		 json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		
	}
	@Test
	public void getStatusByHttps()throws Exception{
       
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void getStatusByHttps_116()throws Exception{
       
		byte[] msgBype = BaseHttp.doPost(BaseHttp.urlByDp_116,json);
        System.out.println(new String(msgBype));
	}
}
