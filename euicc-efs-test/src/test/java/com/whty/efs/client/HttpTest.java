package com.whty.efs.client;

import org.junit.Test;

public class HttpTest {
	private static final String tsm_url = "http://localhost:8080/euicc-efs-container/webservice/EnterFrontService";
	private static final String es2_url = "http://localhost:8080/euicc-efs-container/webservice/ES2MnoService";
	private static final String es1_url = "http://localhost:8080/euicc-efs-container/webservice/ES1SmSrService";

	@Test
	public void testTSM() throws Exception {
		HttpClientSoap.httpClientPostForSoap(tsm_url, getRequestXmlForTsm());
	}

	/**
	 * http协议
	 * @throws Exception
	 */
	@Test
	public void testES() throws Exception {
		HttpClientSoap.httpClientPostForSoap(es2_url, getRequestXml());
	}
	
	/**
	 * 解析不出来header,todo
	 * @throws Exception
	 */
	@Test
	public void testESForOtherHeaderType() throws Exception {
		HttpClientSoap.httpClientPostForSoap(es2_url, getRequestXmlForOtherHeaderType());
	}
	
	@Test
	public void testESForOtherError() throws Exception {
		HttpClientSoap.httpClientPostForSoap(es1_url, getRequestXmlForError());
	}

	private static String getRequestXmlForTsm() {
		return "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tsm=\"http://www.tathing.com\"><SOAP-ENV:Header xmlns:tsm=\"http://www.tathing.com\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><tsm:version>01</tsm:version><tsm:sender>whty</tsm:sender><tsm:receiver>whty</tsm:receiver><tsm:sendTime>2016-09-12 15:25:02</tsm:sendTime><tsm:msgType>tath.112.002.01</tsm:msgType><tsm:tradeNO></tsm:tradeNO><tsm:tradeRefNO></tsm:tradeRefNO><tsm:sessionID>null</tsm:sessionID><tsm:deviceType>0911</tsm:deviceType></SOAP-ENV:Header><soap:Body xmlns:tsm=\"http://www.tathing.com\"><tsm:AppQueryRequest><tsm:seID>001</tsm:seID><tsm:appInstalledTag>002</tsm:appInstalledTag></tsm:AppQueryRequest></soap:Body></soap:Envelope>";
	}

	private static String getRequestXml() {
		return "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ns=\"http://namespaces.gsma.org/esim-messaging/3\" xmlns:add=\"http://www.w3.org/2005/08/addressing\"><soap:Header><add:Action>01</add:Action><add:MessageID>whty</add:MessageID><add:To>whty</add:To><add:From><add:Address>tath.112.002.01</add:Address><add:ReferenceParameters></add:ReferenceParameters><add:Metadata></add:Metadata></add:From></soap:Header><soap:Body><ns2:ES2-DisableProfileRequest xmlns:ns2=\"http://namespaces.gsma.org/esim-messaging/3\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\"><ns2:FunctionCallIdentifier>02</ns2:FunctionCallIdentifier><ns2:ValidityPeriod>2</ns2:ValidityPeriod><ns2:Eid>313233343536</ns2:Eid><ns2:Smsr-id>01</ns2:Smsr-id><ns2:Iccid>00</ns2:Iccid></ns2:ES2-DisableProfileRequest></soap:Body></soap:Envelope>";
	}
	private static String getRequestXmlForOtherHeaderType() {
		return "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://namespaces.gsma.org/esim-messaging/3\" xmlns:add=\"http://www.w3.org/2005/08/addressing\"><SOAP-ENV:Header><add:Action>01</add:Action><add:MessageID>whty</add:MessageID><add:To>whty</add:To><add:From><add:Address>tath.112.002.01</add:Address><add:ReferenceParameters></add:ReferenceParameters><add:Metadata></add:Metadata></add:From></SOAP-ENV:Header><soap:Body><ns2:ES2-DisableProfileRequest xmlns:ns2=\"http://namespaces.gsma.org/esim-messaging/3\" xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\"><ns2:FunctionCallIdentifier>02</ns2:FunctionCallIdentifier><ns2:ValidityPeriod>2</ns2:ValidityPeriod><Eid>313233343536</Eid><Smsr-id>01</Smsr-id><Iccid>00</Iccid></ns2:ES2-DisableProfileRequest></soap:Body></soap:Envelope>";
	}
	
	private static String getRequestXmlForError() {
		return "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:add=\"http://namespaces.gsma.org/esim-messaging/3\"><SOAP-ENV:Header><add:version>01</add:version><add:sender>whty</add:sender><add:receiver>whty</add:receiver><add:sendTime>2016-09-14 09:18:02</add:sendTime><add:msgType>tath.112.002.01</add:msgType><add:tradeNO/><add:tradeRefNO/><add:sessionID>null</add:sessionID><add:deviceType>0911</add:deviceType></SOAP-ENV:Header><SOAP-ENV:Body><add:ES1-RegisterEISRequest xmlns:ns4=\"http://www.w3.org/2000/09/xmldsig#\"><Eis><add:Smsr-id>123456</add:Smsr-id><add:Isd-r><add:Aid>31323334353637383930</add:Aid><add:Sin>3032</add:Sin><add:Sdin>3031</add:Sdin><add:Role>ISD-R</add:Role></add:Isd-r></Eis></add:ES1-RegisterEISRequest></SOAP-ENV:Body>";
}


	
}
