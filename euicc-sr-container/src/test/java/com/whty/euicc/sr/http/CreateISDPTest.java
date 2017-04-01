package com.whty.euicc.sr.http;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.CreateISDPReqBody;
import com.whty.euicc.packets.message.request.RetrieveEISReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;
/**
 * SM-SR的创建ISD-P测试用例类
 * @author Administrator
 *
 */
public class CreateISDPTest {
	private String json = null;
	private String create_json = null;
	@Before
	public void init()throws Exception{
		initRetrieve();
		initCreate();
	}

	private void initCreate() {
		MsgHeader header = new MsgHeader("createISDP");
        CreateISDPReqBody requestBody = new CreateISDPReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("8901");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		create_json = new Gson().toJson(euiccMsg, EuiccMsg.class);
	}

	private void initRetrieve() {
		MsgHeader header = new MsgHeader("retrieveEISBySr");
        RetrieveEISReqBody requestBody = new RetrieveEISReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
	}
	
	@Test
	public void retrieveEIS()throws Exception{
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void retrieveEIS_116()throws Exception{
		byte[] msgBype = BaseHttp.doPost(BaseHttp.url_116,json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void retrieveEISHttp_116()throws Exception{
		for(int i=0;i<1000;i++){
			byte[] msgBype = BaseHttp.doPostForHttp(BaseHttp.nginx_http_url_116,json);
	        System.out.println(new String(msgBype));
		}
		
	}
	
	@Test
	public void retrieveEISHttp_GZ()throws Exception{
		byte[] msgBype = BaseHttp.doPostForHttp(BaseHttp.url_gz,json);
        System.out.println(new String(msgBype));
		
	}
	
	@Test
	public void createISDP()throws Exception{
		byte[] msgBype = BaseHttp.doPost(create_json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void createISDP_116()throws Exception{
		byte[] msgBype = BaseHttp.doPost(BaseHttp.url_116,create_json);
        System.out.println(new String(msgBype));
	}

}
