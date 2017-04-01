package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.CreateAdditionalKeySetReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

public class SrChangeCreateAdditionalKeySetTest {
	@Test
	public void createAdditionalKeySetTest() throws Exception{
		MsgHeader header = new MsgHeader("createAdditionalKeySet");
		CreateAdditionalKeySetReqBody requestBody = new CreateAdditionalKeySetReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setePK_SR_ECKA("2281A680E28901A13A0217A6159002030995011080018881011082010183010291007F494104C8A12565C76B64BA84305D4370704BEBCA4799DDACF035402F1FD85E180A1801AE64BCCC968632819373E3CAA48559930F7A9C34C0FDE9922FF2268A07CE5F575F3740AA559462E0238A57DD6156CBF626F336F12E7DE0C8AE174EDCD4A0F4CCA588CACA2A513E2752168C2CFA8ABEBDD844EA96359E33081E1976B301DD1AA86BBABC");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
	    System.out.println(new String(msgBype));
	}

}
