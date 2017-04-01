package com.whty.efs.webservice.service.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.request.AppApplyBody;
import com.whty.efs.packets.message.request.AppPersonialBody;
import com.whty.efs.packets.message.request.AppQueryBody;
import com.whty.efs.packets.message.response.AppApplyResp;
import com.whty.efs.packets.message.response.AppPersonialResp;
import com.whty.efs.packets.message.response.AppQueryResp;
import com.whty.efs.webservice.es.message.ES1RegisterEISRequest;
import com.whty.efs.webservice.es.message.ES1RegisterEISResponse;
import com.whty.efs.webservice.message.AppPersonialRequest;
import com.whty.efs.webservice.message.AppPersonialResponse;
import com.whty.efs.webservice.message.AppQueryRequest;
import com.whty.efs.webservice.message.AppQueryResponse;
import com.whty.efs.webservice.message.parse.CommonSOAPMessageGeneratorAndParser;
import com.whty.efs.webservice.message.parse.SOAPEntity;
import com.whty.efs.webservice.message.parse.SOAPMessageGeneratorAndParser;
import com.whty.efs.webservice.service.CommonWsClient;

/**
 * 平台标准化wsdl实现
 */
@SuppressWarnings("deprecation")
public class CommonWsClientImpl implements CommonWsClient {

	protected MessageFactory messageFactory;
	protected SOAPMessage soapMessage;
	protected SOAPPart soapPart;
	protected SOAPEnvelope soapEnvelope;
	protected SOAPHeader soapHeader;
	protected SOAPBody soapBody;

	public void setWsdl_url_in_string(String wsdl_url_in_string) {
		this.wsdl_url_in_string = wsdl_url_in_string;
	}

	public static final String prefix = "tsm";
	public static final String namespaceURI = "http://www.tathing.com";

	/**webservice接口url(重要参数，在spring-client.xml中配置注入)*/
	private String wsdl_url_in_string;
	public static URL WSDL_URL;

	private static final String SERVICE_NAME = "EnterFrontService";
	private static final String PORT_NAME = "EnterFrontServicePort";

	private Service service;
	private Dispatch<SOAPMessage> dispatcher;

	public CommonWsClientImpl() throws SOAPException, MalformedURLException {
		super();
	}
	
	public void init() throws SOAPException, MalformedURLException {
		
//		System.setProperty("javax.net.ssl.keyStore","d:\\...cacerts"); 
//		System.setProperty("javax.net.ssl.keyStorePassword","xxxxx");   
		
		messageFactory = MessageFactory.newInstance();
		soapMessage = messageFactory.createMessage();
		soapPart = soapMessage.getSOAPPart();
		soapEnvelope = soapPart.getEnvelope();
		if (soapEnvelope.getHeader() == null) {
			soapHeader = soapEnvelope.addHeader();
		} else {
			soapHeader = soapEnvelope.getHeader();
		}
		soapBody = soapEnvelope.getBody();
		
		this.getSoapEnvelope().addNamespaceDeclaration(prefix, namespaceURI);
		this.getSoapHeader().addNamespaceDeclaration(prefix, namespaceURI);
		this.getSoapBody().addNamespaceDeclaration(prefix, namespaceURI);

		WSDL_URL = new URL(this.wsdl_url_in_string);
		QName qName = new QName(namespaceURI, SERVICE_NAME);
		service = Service.create(WSDL_URL, qName);

		dispatcher = service.createDispatch(new QName(namespaceURI, PORT_NAME),
				SOAPMessage.class, Service.Mode.MESSAGE);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String appQueryToSoap(EuiccEntity<AppQueryBody> Entity)
			throws Exception {

		SOAPEntity<AppQueryRequest> soapEntity = SOAPEntity
				.createFromEuiccEntity(Entity, AppQueryRequest.class);

		SOAPMessageGeneratorAndParser<AppQueryRequest, AppQueryResponse> smgap = new CommonSOAPMessageGeneratorAndParser<AppQueryRequest, AppQueryResponse>();

		return smgap.invokeToSoap(
				this.soapMessage, soapEntity, AppQueryRequest.class,
				AppQueryResponse.class, this.dispatcher,namespaceURI,prefix);

		
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public EuiccEntity<AppQueryResp> appQuery(EuiccEntity<AppQueryBody> Entity)
			throws Exception {

		SOAPEntity<AppQueryRequest> soapEntity = SOAPEntity
				.createFromEuiccEntity(Entity, AppQueryRequest.class);

		SOAPMessageGeneratorAndParser<AppQueryRequest, AppQueryResponse> smgap = new CommonSOAPMessageGeneratorAndParser<AppQueryRequest, AppQueryResponse>();

		SOAPEntity<AppQueryResponse> respSOAPEntity = smgap.invoke(
				this.soapMessage, soapEntity, AppQueryRequest.class,
				AppQueryResponse.class, this.dispatcher);

		EuiccEntity<AppQueryResp> respEntity = (EuiccEntity<AppQueryResp>) SOAPEntity
				.toEuiccEntity(respSOAPEntity, AppQueryResp.class);

		return respEntity;
	}
	
	



	
	@Override
	@SuppressWarnings("unchecked")
	public EuiccEntity<AppApplyResp> appApply(EuiccEntity<AppApplyBody> Entity)
			throws Exception {
		SOAPEntity<AppApplyBody> soapEntity = SOAPEntity
				.createFromEuiccEntity(Entity, AppApplyBody.class);

		SOAPMessageGeneratorAndParser<AppApplyBody, AppApplyResp> smgap = new CommonSOAPMessageGeneratorAndParser<AppApplyBody, AppApplyResp>();

		SOAPEntity<AppApplyResp> respSOAPEntity = smgap.invoke(
				this.soapMessage, soapEntity, AppApplyBody.class,
				AppApplyResp.class, this.dispatcher);

		EuiccEntity<AppApplyResp> respEntity = (EuiccEntity<AppApplyResp>) SOAPEntity
				.toEuiccEntity(respSOAPEntity, AppApplyResp.class);

		return respEntity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EuiccEntity<AppPersonialResp> appPersonial(
			EuiccEntity<AppPersonialBody> Entity) throws Exception {

		SOAPEntity<AppPersonialRequest> soapEntity = SOAPEntity
				.createFromEuiccEntity(Entity, AppPersonialRequest.class);

		SOAPMessageGeneratorAndParser<AppPersonialRequest, AppPersonialResponse> smgap = new CommonSOAPMessageGeneratorAndParser<AppPersonialRequest, AppPersonialResponse>();

		SOAPEntity<AppPersonialResponse> respSOAPEntity = smgap.invoke(
				this.soapMessage, soapEntity, AppPersonialRequest.class,
				AppPersonialResponse.class, this.dispatcher);

		EuiccEntity<AppPersonialResp> respEntity = (EuiccEntity<AppPersonialResp>) SOAPEntity
				.toEuiccEntity(respSOAPEntity, AppPersonialResp.class);

		return respEntity;

	}



	public MessageFactory getMessageFactory() {
		return messageFactory;
	}

	public void setMessageFactory(MessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}

	public SOAPMessage getSoapMessage() {
		return soapMessage;
	}

	public void setSoapMessage(SOAPMessage soapMessage) {
		this.soapMessage = soapMessage;
	}

	public SOAPPart getSoapPart() {
		return soapPart;
	}

	public void setSoapPart(SOAPPart soapPart) {
		this.soapPart = soapPart;
	}

	public SOAPEnvelope getSoapEnvelope() {
		return soapEnvelope;
	}

	public void setSoapEnvelope(SOAPEnvelope soapEnvelope) {
		this.soapEnvelope = soapEnvelope;
	}

	public SOAPHeader getSoapHeader() {
		return soapHeader;
	}

	public void setSoapHeader(SOAPHeader soapHeader) {
		this.soapHeader = soapHeader;
	}

	public SOAPBody getSoapBody() {
		return soapBody;
	}

	public void setSoapBody(SOAPBody soapBody) {
		this.soapBody = soapBody;
	}

	

	

}