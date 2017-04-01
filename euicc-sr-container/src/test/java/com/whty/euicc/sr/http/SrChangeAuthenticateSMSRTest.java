package com.whty.euicc.sr.http;

import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.AuthenticateSMSRReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

/**
 * sr1 apdu测试
 * @author zyj
 *
 */
public class SrChangeAuthenticateSMSRTest {

	@Test
	public void srChangeKeyExchangeTest()throws Exception{
		String certSrEcdsa="2281B180E20900AC3A01A97F2181A59301014201015F2001019501885F2404214501017303C801027f4946B041046466E042804FAAC48F839EA53E85D0B8B93F2F015028A472F07F3227AF408170ACFB39D198BA7D0DCFF3DE5032A6CC8F6ACC84EF556BFE530DEC0FF75F2AF59AF001005F3740F1B7D2080AABCEE4EEE484130279F075CEF5A34C27AC87B0F8413DF8D3548A3C4666F06A812A079E740B1956566585F49F65D6A559F5C5B41C3795D1524A63DF";
		
        MsgHeader header = new MsgHeader("authenticateSMSR");
        AuthenticateSMSRReqBody requestBody = new AuthenticateSMSRReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		requestBody.setCertSrEcdsa(certSrEcdsa);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
}
