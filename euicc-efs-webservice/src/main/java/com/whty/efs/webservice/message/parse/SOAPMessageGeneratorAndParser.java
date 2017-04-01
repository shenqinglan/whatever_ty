package com.whty.efs.webservice.message.parse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Dispatch;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

import com.whty.efs.packets.message.Header;
import com.whty.efs.webservice.util.DateTimeConvert;

/**
 * 
 * @author Administrator
 *
 * @param <T> 请求Body数据类型
 * @param <E> 返回Body数据类型
 */
public abstract class SOAPMessageGeneratorAndParser<T , E> {
		
	//请求相关
	private SOAPMessage soapMessage;
	private SOAPHeader soapHeader;
	private SOAPBody soapBody;
	
	private SOAPEntity<T> soapEntity;
	private Header header;
	private T body;
	
	private Dispatch<SOAPMessage> dispatcher;
	
	//响应相关
	private SOAPMessage respSOAPMessage;
	private SOAPHeader respSOAPHeader;
	private SOAPBody respSOAPBody;
	private Class<?> bodyType;
	
	private SOAPEntity<E> respSOAPEntity = new SOAPEntity<E>();
	private Header respHeader = respSOAPEntity.getHeader();
	private E respBody = respSOAPEntity.getBody();
	private Class<?> respBodyType;
	
	public String invokeToSoap(SOAPMessage soapMessage , 
				SOAPEntity<T> soapEntity , 
				Class<?> bodyType,
				Class<?> respBodyType , 
				String namespaceURI , 
				String prefix , 
				Dispatch<SOAPMessage> dispatcher) throws Exception {
		
		try {
			this.bodyType = bodyType;
			this.respBodyType = respBodyType;
			
			//预处理
			this.soapMessage = soapMessage;
			this.soapHeader = this.soapMessage.getSOAPHeader();
			this.soapBody = this.soapMessage.getSOAPBody();
			
			this.soapEntity = soapEntity;
			this.header = this.soapEntity.getHeader();
			this.body = this.soapEntity.getBody();
			
			preOperator(this.soapMessage , this.soapEntity , namespaceURI , prefix , dispatcher);
			
			//1  创建请求头消息
			createHeaderChildElements(this.soapHeader , this.header , prefix);
			
			//2  写入请求消息体
			writeRequestBody(this.soapBody, this.body, this.bodyType , prefix, namespaceURI);
			
			System.out.println("clinet 请求消息 为 :");
			this.soapMessage.writeTo(System.out);
			
			//3 执行请求操作,获取请求结果
			this.dispatcher = dispatcher;
			this.respSOAPMessage = this.dispatcher.invoke(soapMessage);
			this.respSOAPHeader = this.respSOAPMessage.getSOAPHeader();
			this.respSOAPBody = this.respSOAPMessage.getSOAPBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		System.out.println();
		System.out.println("clinet 从服务器返回的消息 为 :");
		return getResp(respSOAPMessage);
		
	}

	private String getResp(SOAPMessage soapMessage){
		try {
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			// Get reply content
			Source source = soapMessage.getSOAPPart().getContent();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(bos);
			tf.transform(source, result);
			return new String(bos.toByteArray());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public SOAPEntity<E> invoke(SOAPMessage soapMessage , 
			SOAPEntity<T> soapEntity , 
			Class<?> bodyType,
			Class<?> respBodyType , 
			String namespaceURI , 
			String prefix , 
			Dispatch<SOAPMessage> dispatcher) throws SOAPException, IllegalAccessException, InvocationTargetException, InstantiationException, JAXBException, IOException {
	
		this.bodyType = bodyType;
		this.respBodyType = respBodyType;
		
		//预处理
		this.soapMessage = soapMessage;
		this.soapHeader = this.soapMessage.getSOAPHeader();
		this.soapBody = this.soapMessage.getSOAPBody();
		
		this.soapEntity = soapEntity;
		this.header = this.soapEntity.getHeader();
		this.body = this.soapEntity.getBody();
		
		preOperator(this.soapMessage , this.soapEntity , namespaceURI , prefix , dispatcher);
		
		//1  创建请求头消息
		createHeaderChildElements(this.soapHeader , this.header , prefix);
		
		//2  写入请求消息体
		writeRequestBody(this.soapBody, this.body, this.bodyType , prefix, namespaceURI);
		
		System.out.println("clinet 请求消息 为 :");
		this.soapMessage.writeTo(System.out);
		
		//3 执行请求操作,获取请求结果
		this.dispatcher = dispatcher;
		this.respSOAPMessage = this.dispatcher.invoke(soapMessage);
		this.respSOAPHeader = this.respSOAPMessage.getSOAPHeader();
		this.respSOAPBody = this.respSOAPMessage.getSOAPBody();
		
		System.out.println();
		System.out.println("clinet 从服务器返回的消息 为 :");
		this.respSOAPMessage.writeTo(System.out);
		
		//4读返回消息头
		this.respHeader = readResponseHeader(this.respSOAPHeader);
		
		//5读返回消息体
		this.respBody = readResponseBody(this.respSOAPBody , this.respBodyType);
		
		this.respSOAPEntity.setHeader(this.respHeader);
		this.respSOAPEntity.setBody(this.respBody);
		
		afterOperator(this.respSOAPMessage , this.respSOAPEntity);
		
		return this.respSOAPEntity;
	}
	
	public abstract SOAPEntity<E> invoke(SOAPMessage soapMessage,
			SOAPEntity<T> soapEntity, 
			Class<?> bodyType, Class<?> respBodyType,
			Dispatch<SOAPMessage> dispatcher) throws SOAPException, IllegalAccessException,InvocationTargetException, InstantiationException, JAXBException,IOException;

	/**
	 * 调用invoke前置处理操作，每个实现client根据自身需求选择实现
	 */
	protected void preOperator(SOAPMessage soapMessage , SOAPEntity<T> soapEntity , String namespaceURI , String prefix , Dispatch<SOAPMessage> dispatcher) {
		
	}
	
	/**
	 * 调用invoke后置处理操作，每个实现client根据自身需求选择实现
	 */
	protected void afterOperator(SOAPMessage respSOAPMessage , SOAPEntity<E> respSOAPEntity) {
		
	}
	
	
	
	/**
	 * 创建请求头消息,所有所有请求的消息头公用
	 * @param header
	 * @throws SOAPException
	 */
	protected void createHeaderChildElements(SOAPHeader soapHeader , Header header , String prefix) throws SOAPException {	
		soapHeader.addChildElement("version", prefix).setValue(String.valueOf(header.getVersion()));;
		soapHeader.addChildElement("sender", prefix).setValue(header.getSender());;
		soapHeader.addChildElement("receiver", prefix).setValue(header.getReceiver());;
		soapHeader.addChildElement("sendTime", prefix).setValue(header.getSendTime());;
		soapHeader.addChildElement("msgType", prefix).setValue(header.getMsgType());;
		soapHeader.addChildElement("tradeNO", prefix).setValue(String.valueOf(header.getTradeNO()));;
		soapHeader.addChildElement("tradeRefNO", prefix).setValue(String.valueOf(header.getTradeRefNO()));;
		soapHeader.addChildElement("sessionID", prefix).setValue(String.valueOf(header.getSessionID()));;
		soapHeader.addChildElement("deviceType", prefix).setValue(String.valueOf(header.getDeviceType()));;
	}
	

	
	/**
	 * 创建请求消息体
	 * @param soapBody
	 * @param body
	 * @param prefix
	 * @param namespaceURI
	 * @throws SOAPException
	 * @throws JAXBException 
	 */
	public void writeRequestBody(SOAPBody soapBody , T body , Class<?> bodyType , String prefix , String namespaceURI) throws SOAPException, JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(bodyType);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		Annotation annotation = bodyType.getAnnotation(XmlRootElement.class);
		String localPort = bodyType.getSimpleName();
		if (annotation != null) {
			XmlRootElement xmlElement = (XmlRootElement)annotation;
			localPort = xmlElement.name();
		}
		@SuppressWarnings("unchecked")
		JAXBElement<T> jaxbElement = new JAXBElement<T>(new QName(namespaceURI, localPort, prefix), (Class<T>) bodyType, body);
		jaxbMarshaller.marshal(jaxbElement , soapBody);
	}

	public Header readResponseHeader(SOAPHeader respSOAPHeader) throws IllegalAccessException, InvocationTargetException {

		Iterator<?> it = respSOAPHeader.extractAllHeaderElements();
		
		Map<String , Object> map = new HashMap<String, Object>();
		
        while (it.hasNext()) {
            SOAPHeaderElement headerElement = (SOAPHeaderElement) it.next();
            Name headerName = headerElement.getElementName();
            map.put(headerName.getLocalName(), headerElement.getValue());
        }
        
        Header header = new Header();
        
        ConvertUtils.register(new DateTimeConvert(), java.util.Date.class);
        BeanUtils.populate(header, map);
        
        return header;

	};
	
	@SuppressWarnings("unchecked")
	public E readResponseBody(SOAPBody respSOAPBody , Class<?> respBodyType) throws SOAPException, JAXBException {
		
		JAXBContext jc = JAXBContext.newInstance(this.respBodyType);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		
		@SuppressWarnings("rawtypes")
		JAXBElement jaxbElement = unmarshaller.unmarshal(respSOAPBody.extractContentAsDocument(), respBodyType);
		this.respBody = (E) jaxbElement.getValue();
		
        return this.respBody;
	}

	public abstract String invokeToSoap(SOAPMessage soapMessage,
			SOAPEntity<T> soapEntity, Class<?> bodyType, Class<?> respBodyType,
			Dispatch<SOAPMessage> dispatcher,String namespaceURI,String prefix) throws SOAPException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, JAXBException, IOException, Exception;;
}
