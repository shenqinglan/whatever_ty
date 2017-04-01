package com.whty.euicc.dp.http;

import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.PersonalAllISDPReqBody;
import com.whty.euicc.packets.message.request.PersonalISDPReqBody;
import com.whty.euicc.dp.http.base.BaseHttp;

/**
 * 个人化dp测试
 * @author zyj
 *
 */
public class PersonalISDPTest {
	private String json = null;
	@Before
	public void init()throws Exception{
		String certDpEcdsa = "2281B180E20900AC3A01A97F2181A59301024201025F2001029501885F2404214501017303C801017f4946B041046466E042804FAAC48F839EA53E85D0B8B93F2F015028A472F07F3227AF408170ACFB39D198BA7D0DCFF3DE5032A6CC8F6ACC84EF556BFE530DEC0FF75F2AF59AF001005F3740F7BA7DE1E625D5721A5756F9B9D8D1A25D1667300801BE063AE1FED8CA9E0107B12F00500EEE49D82A5065542E0A38FDD86E276A804BF859CB5528C0457CC830";
        MsgHeader header = new MsgHeader("personalAllISDP");
        PersonalAllISDPReqBody requestBody = new PersonalAllISDPReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		requestBody.setCertDpEcdsa(certDpEcdsa);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
	}
	
	@Test
	public void personalIsdP()throws Exception{
		String certDpEcdsa = "11111111";
        MsgHeader header = new MsgHeader("personalISDP");
        PersonalISDPReqBody requestBody = new PersonalISDPReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		requestBody.setCertDpEcdsa(certDpEcdsa);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void personalAllIsdP()throws Exception{
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void personalAllIsdP_116()throws Exception{
		byte[] msgBype = BaseHttp.doPost(BaseHttp.urlByDp_116,json);
        System.out.println(new String(msgBype));
	}
}
