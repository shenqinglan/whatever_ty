package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.UpdateSrAddressParaByHttpsReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

/**
 * SM-SR的更新SMSR地址参数测试用例类
 * @author Administrator
 *
 */
public class UpdateSrAddressParaTest {
	@Test
	public void updateSrAddressParaByHttps()throws Exception{
        MsgHeader header = new MsgHeader("updateSrAddressParaByHttps");
        UpdateSrAddressParaByHttpsReqBody requestBody = new UpdateSrAddressParaByHttpsReqBody();
		requestBody.setIsdRAid("A0000005591010FFFFFFFF8900000100");
		requestBody.setSrAddressPara("0685214365");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
}
