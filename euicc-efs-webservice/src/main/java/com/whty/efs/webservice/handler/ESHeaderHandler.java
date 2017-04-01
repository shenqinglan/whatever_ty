package com.whty.efs.webservice.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.whty.efs.common.constant.Constant;
import com.whty.efs.packets.message.Header;
import com.whty.efs.webservice.message.parse.HeaderManager;
import com.whty.efs.webservice.util.DateTimeConvert;

/**
 * 头部处理类：用于在请求和响应之前对soap头部进行拆包和装包过程
 */
public class ESHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static Logger logger = LoggerFactory.getLogger(ESHeaderHandler.class);
	private static boolean isDebugEabled = logger.isDebugEnabled();
	
	@Override
	public boolean handleMessage(SOAPMessageContext messagecontext) {
		Boolean outboundProperty = (Boolean) messagecontext
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!outboundProperty.booleanValue()) {
			try {
				if (isDebugEabled)
					logger.debug("【开始处理客户端请求】");
				readRequestHeader(messagecontext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (isDebugEabled)
					logger.debug("【返回请求结果到客户端】");
				writeResponseHeader(messagecontext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}


	@Override
	public boolean handleFault(SOAPMessageContext messagecontext) {
		return false;
	}

	@Override
	public void close(MessageContext messagecontext) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	private void attacheErrorMessage(SOAPMessage errorMessage, String cause) {
		try {
			SOAPBody soapBody = errorMessage.getSOAPPart().getEnvelope()
					.getBody();
			SOAPFault soapFault = soapBody.addFault();
			soapFault.setFaultString(cause);
			throw new SOAPFaultException(soapFault);
		} catch (SOAPException e) {
		}

	}

	public void writeResponseHeader(SOAPMessageContext messagecontext)
			throws SOAPException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		SOAPMessage soapMessage = messagecontext.getMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		//获取soap属性
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		//SOAPBody soapBody = soapEnvelope.getBody();
		SOAPHeader soapHeader = soapEnvelope.getHeader();

		log(soapMessage);

		//如果没有头部 添加头部
		if (soapHeader == null) {
			soapHeader = soapEnvelope.addHeader();
		}
		
		soapEnvelope.addNamespaceDeclaration(Constant.WS.SOAP_XSD_PREFIX_ES_HEAD,
				Constant.WS.SOAP_XSD_NAMESPACE_ES_ADD);
		soapEnvelope.addNamespaceDeclaration(Constant.WS.SOAP_XSD_PREFIX_ES_BODY,
				Constant.WS.SOAP_XSD_NAMESPACE_ES);
		/*soapHeader.addNamespaceDeclaration(Constant.WS.SOAP_XSD_PREFIX_ES_HEAD,
				Constant.WS.SOAP_XSD_NAMESPACE_ES);
		soapBody.addNamespaceDeclaration(Constant.WS.SOAP_XSD_PREFIX_ES_BODY,
				Constant.WS.SOAP_XSD_NAMESPACE_ES);*/
		Header header = HeaderManager.getHeader();

		//创建响应头部
		createHeaderChildElements(soapHeader, header);
	}

	/**
	 * 创建请求头部
	 * @param soapHeader
	 * @param header 
	 * @throws SOAPException
	 */
	private void createHeaderChildElements(SOAPHeader soapHeader, Header header)
			throws SOAPException {
		if(header == null)return;
		SOAPElement relatesTo = soapHeader.addChildElement("RelatesTo", Constant.WS.SOAP_XSD_PREFIX_ES_HEAD);
		relatesTo.setAttribute("RelationshipType", "http://www.w3.org/2005/08/addressing/reply");
		relatesTo.setValue("001");
		SOAPElement from = soapHeader.addChildElement("From",Constant.WS.SOAP_XSD_PREFIX_ES_HEAD);
		from.addChildElement("Address", Constant.WS.SOAP_XSD_PREFIX_ES_HEAD).setValue(String.valueOf(header.getAddress()));
		from.addChildElement("ReferenceParameters", Constant.WS.SOAP_XSD_PREFIX_ES_HEAD).setValue(String.valueOf(header.getReferenceParameters()));
		from.addChildElement("Metadata", Constant.WS.SOAP_XSD_PREFIX_ES_HEAD).setValue(String.valueOf(header.getMetadata()));
		soapHeader.addChildElement("To", Constant.WS.SOAP_XSD_PREFIX_ES_HEAD).setValue(String.valueOf(header.getTo()));
		soapHeader.addChildElement("MessageID", Constant.WS.SOAP_XSD_PREFIX_ES_HEAD).setValue(String.valueOf(header.getMessageID()));
		soapHeader.addChildElement("Action", Constant.WS.SOAP_XSD_PREFIX_ES_HEAD).setValue(String.valueOf(header.getAction()));
		
	}

	/**
	 * 读取请求头部
	 * @param messagecontext
	 * @throws SOAPException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void readRequestHeader(SOAPMessageContext messagecontext)
			throws SOAPException, IOException, IllegalAccessException,
			InvocationTargetException {
		//获取请求头部
		SOAPMessage soapMessage = messagecontext.getMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		SOAPHeader soapHeader = soapEnvelope.getHeader();
		
		log(soapMessage);

		if (soapHeader == null) {
			soapHeader = soapEnvelope.addHeader();
			attacheErrorMessage(soapMessage, "No SOAP header");
		}

		//遍历获取请求头部信息
		Iterator<?> it = soapHeader.extractAllHeaderElements();
		Map<String, Object> map = new HashMap<String, Object>();
		getHeaderToMap(it,map);
		Header header = new Header();
		ConvertUtils.register(new DateTimeConvert(), java.util.Date.class);
		//利用map构建头部
		BeanUtils.populate(header, map);
		//向threadlocal中设置头部
		if(!validHeader(header)){
			throw new SecurityException("请求头验证失败");
		}
		HeaderManager.setHeader(header);
	}


	private void getHeaderToMap(Iterator<?> it,Map<String, Object> map) {
		while (it.hasNext()) {
			SOAPElement headerElement = (SOAPElement) it.next();
			if (null == headerElement.getValue() && headerElement.getChildElements().hasNext()) {
				getHeaderToMap(headerElement.getChildElements(),map);
			}
			Name headerName = headerElement.getElementName();
			map.put(captureName(headerName.getLocalName()), headerElement.getValue());
		}
	}
	
	public static String captureName(String name) {
       return name.substring(0, 1).toLowerCase() + name.substring(1);
      
    }

	/**
	 * log
	 * @param soapMsg
	 */
	private void log(SOAPMessage soapMsg) {
		try {
			if (isDebugEabled)
				soapMsg.writeTo(System.out); // Line 3
		} catch (SOAPException ex) {
			logger.error(ex.toString());
		} catch (IOException ex) {
			logger.error(ex.toString());
		}
	}
	
	/**
	 * 校验报文头中的sender、receivdr和msgType
	 * @param header
	 * @return
	 */
	private boolean validHeader(Header header){
		if(null == header)return false;
		String sender = header.getSender();
		String receivdr = header.getReceiver();
		String msgType = header.getMsgType();
		//if(StringUtils.isBlank(sender) || StringUtils.isBlank(receivdr) || StringUtils.isBlank(msgType))return false;
		return true;
	}
}
