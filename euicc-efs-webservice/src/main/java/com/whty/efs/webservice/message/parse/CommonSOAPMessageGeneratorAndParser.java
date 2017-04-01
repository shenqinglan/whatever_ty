package com.whty.efs.webservice.message.parse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;

import com.whty.efs.webservice.service.impl.CommonWsClientImpl;

public class CommonSOAPMessageGeneratorAndParser<T , E> extends SOAPMessageGeneratorAndParser<T, E>{
	
	
	
	
	@Override
	public SOAPEntity<E> invoke(SOAPMessage soapMessage,
			SOAPEntity<T> soapEntity, 
			Class<?> bodyType, Class<?> respBodyType,
			Dispatch<SOAPMessage> dispatcher) throws SOAPException, IllegalAccessException,InvocationTargetException, InstantiationException, JAXBException,IOException {
		String namespaceURI = CommonWsClientImpl.namespaceURI;
		String prefix = CommonWsClientImpl.prefix;
		return super.invoke(soapMessage, soapEntity, bodyType, respBodyType,
				namespaceURI, prefix, dispatcher);

	}
	
	@Override
	public String invokeToSoap(SOAPMessage soapMessage,
			SOAPEntity<T> soapEntity, 
			Class<?> bodyType, Class<?> respBodyType,
			Dispatch<SOAPMessage> dispatcher,String namespaceURI,String prefix) throws Exception {

		return super.invokeToSoap(soapMessage, soapEntity, bodyType, respBodyType,
				namespaceURI, prefix, dispatcher);

	}
	
}
