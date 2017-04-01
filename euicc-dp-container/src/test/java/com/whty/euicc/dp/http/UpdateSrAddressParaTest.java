package com.whty.euicc.dp.http;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.dp.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.UpdateSrAddressParaByDPReqBody;

/**
 * SM-DP的更新SMSR地址参数测试用例类
 * @author Administrator
 *
 */
public class UpdateSrAddressParaTest {
	private String json = null;
	@Before
	public void init()throws Exception{
		 MsgHeader header = new MsgHeader("updateSrAddressParaByDP");
		 UpdateSrAddressParaByDPReqBody requestBody = new UpdateSrAddressParaByDPReqBody();
		 requestBody.setIsdRAid("A0000005591010FFFFFFFF8900000100");
		 requestBody.setSrAddressPara("0685214365");
		 EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		 json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		
	}
	@Test
	public void updateSMSRAddressingParametersByHttps()throws Exception{
       
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void updateSMSRAddressingParametersByHttps_116()throws Exception{
       
		byte[] msgBype = BaseHttp.doPost(BaseHttp.urlByDp_116,json);
        System.out.println(new String(msgBype));
	}
}
