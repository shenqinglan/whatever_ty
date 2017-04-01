package com.whty.euicc.sr.http;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.common.utils.HttpClientSoap;
import com.whty.euicc.common.utils.XmlUtil;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.SrChangeSendReqBody;
import com.whty.euicc.sr.handler.netty.https.srchange.smsr2_receive.HandoverEuiccReceiveHandler;
import com.whty.euicc.sr.http.base.BaseHttp;

/**
 * smsr change 入口测试
 * @author zyj
 *
 */
public class SrChangeRootTest {
	
 	private static final String _url = "http://localhost:8080/euicc-efs-container/webservice/ES7SmSrService?wsdl";
	
	@Test
	public void SMSRChangeTest() throws Exception{
		MsgHeader header = new MsgHeader("srChangeSend");
		SrChangeSendReqBody requestBody = new SrChangeSendReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setTargetSmsrId("0819");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
	    System.out.println(new String(msgBype));
	}
	
	@Test
	public void SMSRChangeTestByHttp() throws Exception{
		String xml = XmlUtil.inputStream2String(HandoverEuiccReceiveHandler.class.getClassLoader().getResourceAsStream("es7HandleoverEUICC.xml"));
    	HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
	}

}
