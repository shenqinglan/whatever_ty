package com.whty.efs.client;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.efs.common.util.DateUtil;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.Header;
import com.whty.efs.packets.message.request.AppQueryBody;
import com.whty.efs.packets.message.response.AppQueryResp;
import com.whty.efs.webservice.service.impl.CommonWsClientImpl;

public class CxfCommonTest {
	private final String _url = EnterFrontService.WSDL_LOCATION.toString();
	
	private CommonWsClientImpl client = null;
	
	private EuiccEntity<AppQueryBody> appQuery = null;
	
	private Gson gson = new Gson();
	
	@Before
	public void init()throws Exception{
		client = new CommonWsClientImpl();
		client.setWsdl_url_in_string(_url);
		client.init();
		
		Header header = new Header();
		header.setReceiver("whty");
		header.setSender("whty");// 前置定义的合法发送方
		header.setTradeNO("");
		header.setTradeRefNO("");
		header.setMsgType("tath.112.002.01");
		header.setSendTime(DateUtil.dateToDateString(new Date()));
		header.setVersion("01");
		header.setDeviceType("0911");

		AppQueryBody requestBody = new AppQueryBody();
		requestBody.setSeID("001");
		requestBody.setAppInstalledTag("002");
		
		appQuery = new EuiccEntity<AppQueryBody>(header,requestBody);
	}

	@Test
	public  void connect()throws Exception {
		EuiccEntity<AppQueryResp> returnTsm = client.appQuery(appQuery);
		String returnStr = gson.toJson(returnTsm);
		System.out.println(returnStr);
	}
	
	@Test
	public  void connectToString()throws Exception {
		String returnStr = client.appQueryToSoap(appQuery);
		System.out.println(returnStr);
	}
	
	@Test
	public  void forJson()throws Exception {
		/*EuiccEntity<AppQueryBody> appQueryTsm = gson.fromJson(
				AbstractConvertor.buildJsonReader(data1),
				new TypeToken<EuiccEntity<AppQueryBody>>() {
				}.getType());*/
	}

}
