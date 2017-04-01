package com.whty.euicc.sr.http;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.POL2Type;
import com.whty.euicc.packets.message.request.UpdatePolilcyRulesReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;

public class UpdatePolicyRulesTest {
	@Test
	public void test()throws Exception{
		String eid  = "89001012012341234012345678901224";
		String iccid  = "00";
		String smSrId  = "1";
		POL2Type pol2 = new POL2Type();
		pol2.setSubject("PROFILE");
		pol2.setAction("DISABLE");
		pol2.setQualification("Auto-delete");
        MsgHeader header = new MsgHeader("updatePolicyRules");
        UpdatePolilcyRulesReqBody requestBody = new UpdatePolilcyRulesReqBody();
		requestBody.setEid(eid);
		requestBody.setIccid(iccid);
		requestBody.setSmSrId(smSrId);
		requestBody.setPol2Rules(pol2);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
}
