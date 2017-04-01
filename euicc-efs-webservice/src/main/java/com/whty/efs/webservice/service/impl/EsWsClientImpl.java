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
import com.whty.efs.webservice.es.message.ES1RegisterEISRequest;
import com.whty.efs.webservice.es.message.ES1RegisterEISResponse;
import com.whty.efs.webservice.es.message.ES2DisableProfileRequest;
import com.whty.efs.webservice.es.message.ES2DisableProfileResponse;
import com.whty.efs.webservice.message.parse.CommonSOAPMessageGeneratorAndParser;
import com.whty.efs.webservice.message.parse.SOAPEntity;
import com.whty.efs.webservice.message.parse.SOAPMessageGeneratorAndParser;
import com.whty.efs.webservice.service.EsWsClient;

/**
 * 平台标准化wsdl实现
 */
@SuppressWarnings("deprecation")
public class EsWsClientImpl implements EsWsClient {

	protected MessageFactory messageFactory;
	protected SOAPMessage soapMessage;
	protected SOAPPart soapPart;
	protected SOAPEnvelope soapEnvelope;
	protected SOAPHeader soapHeader;
	protected SOAPBody soapBody;

	public void setWsdl_url_in_string(String wsdl_url_in_string) {
		this.wsdl_url_in_string = wsdl_url_in_string;
	}

	public static final String prefix = "add";
	public static final String namespaceURI = "http://namespaces.gsma.org/esim-messaging/3";

	/**webservice接口url(重要参数，在spring-client.xml中配置注入)*/
	private String wsdl_url_in_string;
	public static URL WSDL_URL;

	//private static final String SERVICE_NAME = "ES1SmSrService";
	//private static final String PORT_NAME = "SmSrES1Port";

	private Service service;
	private Dispatch<SOAPMessage> dispatcher;

	public EsWsClientImpl() throws SOAPException, MalformedURLException {
		super();
	}
	
	public void init(String serviceName,String portName) throws SOAPException, MalformedURLException {
		
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
		QName qName = new QName(namespaceURI, serviceName);
		service = Service.create(WSDL_URL, qName);

		dispatcher = service.createDispatch(new QName(namespaceURI, portName),
				SOAPMessage.class, Service.Mode.MESSAGE);
	}
	
	@Override
	public String es1RegisterEIS(EuiccEntity<ES1RegisterEISRequest> Entity)
			throws Exception {
		SOAPEntity<ES1RegisterEISRequest> soapEntity = SOAPEntity
				.createFromEuiccEntity(Entity, ES1RegisterEISRequest.class);

		SOAPMessageGeneratorAndParser<ES1RegisterEISRequest, ES1RegisterEISResponse> smgap = new CommonSOAPMessageGeneratorAndParser<ES1RegisterEISRequest, ES1RegisterEISResponse>();

		return smgap.invokeToSoap(
				this.soapMessage, soapEntity, ES1RegisterEISRequest.class,
				ES1RegisterEISResponse.class, this.dispatcher,namespaceURI,prefix);
	}

	

	@Override
	public String es2DisableProfile(EuiccEntity<ES2DisableProfileRequest> Entity)
			throws Exception {
		SOAPEntity<ES2DisableProfileRequest> soapEntity = SOAPEntity
				.createFromEuiccEntity(Entity, ES2DisableProfileRequest.class);

		SOAPMessageGeneratorAndParser<ES2DisableProfileRequest, ES2DisableProfileResponse> smgap = new CommonSOAPMessageGeneratorAndParser<ES2DisableProfileRequest, ES2DisableProfileResponse>();

		return smgap.invokeToSoap(
				this.soapMessage, soapEntity, ES2DisableProfileRequest.class,
				ES2DisableProfileResponse.class, this.dispatcher,namespaceURI,prefix);
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