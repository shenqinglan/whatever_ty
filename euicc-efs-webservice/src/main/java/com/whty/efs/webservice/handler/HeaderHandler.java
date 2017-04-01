package com.whty.efs.webservice.handler;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.cache.CacheUtil;
import com.whty.efs.common.constant.Constant;
import com.whty.efs.packets.message.Header;
import com.whty.efs.webservice.message.parse.HeaderManager;
import com.whty.efs.webservice.util.DateTimeConvert;

/**
 * 头部处理类：用于在请求和响应之前对soap头部进行拆包和装包过程
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static Logger logger = LoggerFactory.getLogger(HeaderHandler.class);
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
		SOAPBody soapBody = soapEnvelope.getBody();
		SOAPHeader soapHeader = soapEnvelope.getHeader();

		log(soapMessage);

		//如果没有头部 添加头部
		if (soapHeader == null) {
			soapHeader = soapEnvelope.addHeader();
		}
		
		soapEnvelope.addNamespaceDeclaration(Constant.WS.SOAP_XSD_PREFIX,
				Constant.WS.SOAP_XSD_NAMESPACE);
		soapHeader.addNamespaceDeclaration(Constant.WS.SOAP_XSD_PREFIX,
				Constant.WS.SOAP_XSD_NAMESPACE);
		soapBody.addNamespaceDeclaration(Constant.WS.SOAP_XSD_PREFIX,
				Constant.WS.SOAP_XSD_NAMESPACE);
		Header header = HeaderManager.getHeader();

		// 由于返回的头有顺序，故只能一个个的添加头信息
		// Map<String, String> map = BeanUtils.describe(header);
		// for(Entry<String, String> entry : map.entrySet()) {
		//
		// SOAPFactory soapFactory = SOAPFactory.newInstance();
		//
		// // localName a String giving the local name
		// // prefix a String giving the prefix of the namespace
		// // uri a String giving the URI of the namespace
		// Name headerName = soapFactory.createName(entry.getKey(),
		// Constant.SOAP_XSD_PREFIX,Constant.SOAP_XSD_NAMESPACE);
		// SOAPHeaderElement soapHeaderElement =
		// soapHeader.addHeaderElement(headerName);
		// soapHeaderElement.setValue(entry.getValue());
		// }

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
		soapHeader.addChildElement("version", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getVersion(),""));
		soapHeader.addChildElement("sender", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getSender(),""));
		soapHeader.addChildElement("receiver", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getReceiver(),""));
		soapHeader.addChildElement("sendTime", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getSendTime(),""));
		soapHeader.addChildElement("msgType", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getMsgType(),""));
		soapHeader.addChildElement("tradeNO", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getTradeNO(),""));
		soapHeader.addChildElement("tradeRefNO", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getTradeRefNO(),""));
		soapHeader.addChildElement("sessionID", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getSessionID(),""));
		soapHeader.addChildElement("deviceType", Constant.WS.SOAP_XSD_PREFIX)
				.setValue(StringUtils.defaultIfBlank(header.getDeviceType(),""));
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

		while (it.hasNext()) {
			SOAPHeaderElement headerElement = (SOAPHeaderElement) it.next();
			Name headerName = headerElement.getElementName();
			/*if("sender".equals(headerName.getLocalName())){
				String sender = headerElement.getValue();
				sender = Constant.Plugin.ACCESS_SENDER + sender;
				
				//验证请求头是否在合法路由中
				if (StringUtils.isBlank(sender)){
					throw new SecurityException("请求头验证失败");
				}
				String res = CacheUtil.getString(sender);
				if(!Boolean.parseBoolean(res)){
					throw new SecurityException("请求头验证失败");
				}
			}*/
			map.put(headerName.getLocalName(), headerElement.getValue());
		}
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
