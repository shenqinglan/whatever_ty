package com.whty.euicc.dp.http;

import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.EnableProfileByDpReqBody;
import com.whty.euicc.dp.http.base.BaseHttp;
/**
 * SM-DP的启用Profile测试用例类
 * @author Administrator
 *
 */
public class EnableProfileTest {
	private String json = null;
	@Before
	public void init()throws Exception{
		 MsgHeader header = new MsgHeader("enableProfileByDp");
	        EnableProfileByDpReqBody requestBody = new EnableProfileByDpReqBody();
			requestBody.setEid("89001012012341234012345678901224");
			requestBody.setIccid("00");
			EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		    json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		
	}
	@Test
	public void enableProfileByHttps()throws Exception{
       
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void enableProfileByHttps_116()throws Exception{
       
		byte[] msgBype = BaseHttp.doPost(BaseHttp.urlByDp_116,json);
        System.out.println(new String(msgBype));
	}
}
