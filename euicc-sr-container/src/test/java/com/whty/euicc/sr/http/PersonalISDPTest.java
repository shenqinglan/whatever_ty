package com.whty.euicc.sr.http;


import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.sr.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.PersonalISDPReqBody;
import com.whty.euicc.sr.handler.tls.personal.FirstStoreDataforISDPApdu;
import com.whty.security.ecc.ECCUtils;
/**
 * SM-SR的个人化ISD-P测试用例类
 * @author Administrator
 *
 */
public class PersonalISDPTest {
	private final String P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
	private final String A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
	private final String B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
	private final String Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
	private final String Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
	private final String N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
	private final String H="1";
	
	private FirstStoreDataforISDPApdu first = new FirstStoreDataforISDPApdu();
	
	private String json = null;
	@Before
	public void init()throws Exception{
		initPerson();
	}
	
	private void initPerson() {
		String keyPairs = ECCUtils.createECCKeyPair(P, A, B, Gx, Gy, N, H);
		String certDpEcdsa=new String(first.firstStoreDataForISDPApdu());
		String epkDp = "04"+keyPairs.substring(64);
		String eskEcdsa="7C766F81C0C7FD7139EDCB7B51EFBA2E49841D725A45B8B11D9AA144D5076037";
        MsgHeader header = new MsgHeader("personalISDP");
        PersonalISDPReqBody requestBody = new PersonalISDPReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		requestBody.setCertDpEcdsa(certDpEcdsa);
		requestBody.setEskDp(eskEcdsa);
		requestBody.setEpkDp(epkDp);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		
	}

	
	@Test
	public void allPersonalIsdP()throws Exception{
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void allPersonalIsdP_116()throws Exception{
		byte[] msgBype = BaseHttp.doPost(BaseHttp.url_116,json);
        System.out.println(new String(msgBype));
	}
}
