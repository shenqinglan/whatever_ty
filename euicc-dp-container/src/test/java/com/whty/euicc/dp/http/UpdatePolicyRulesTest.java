package com.whty.euicc.dp.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.dp.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.POL2Type;
import com.whty.euicc.packets.message.request.UpdatePolilcyRulesByDPReqBody;
import com.whty.euicc.packets.message.request.UpdatePolilcyRulesReqBody;

public class UpdatePolicyRulesTest {
	@Test
	public void test()throws Exception{
		String eid  = "89001012012341234012345678901224";
		String iccid  = "00";
		String smSrId  = "1";
		POL2Type pol2Rule = new POL2Type();
		pol2Rule.setSubject("PROFILE");
		pol2Rule.setAction("DISABLE");
		pol2Rule.setQualification("Not allowed");
        MsgHeader header = new MsgHeader("updatePolicyRulesByDP");
        UpdatePolilcyRulesByDPReqBody requestBody = new UpdatePolilcyRulesByDPReqBody();
		requestBody.setEid(eid);
		requestBody.setIccid(iccid);
		requestBody.setSmSrId(smSrId);
		requestBody.setPol2Rules(pol2Rule);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
}
