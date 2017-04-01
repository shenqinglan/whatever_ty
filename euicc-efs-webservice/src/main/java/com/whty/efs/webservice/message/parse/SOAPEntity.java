package com.whty.efs.webservice.message.parse;

import java.lang.reflect.InvocationTargetException;

import com.google.gson.Gson;
import com.whty.efs.packets.message.Body;
import com.whty.efs.packets.message.Header;
import com.whty.efs.packets.message.EuiccEntity;

public class SOAPEntity<T> {
	private Header header;
	
	private T body;
	
	public SOAPEntity() {
		
	}
	
	public SOAPEntity(Header header , T body) {
		this.header = header;
		this.body = body;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <E> SOAPEntity<E> createFromEuiccEntity(EuiccEntity<?> EuiccEntity , Class<?> soapBodyType) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		
		Header header = EuiccEntity.getHeader();
		
//		E body = (E) soapBodyType.newInstance();
//
//		ConvertUtils.register(new EntityRapduToSOAPRapduConvert(), Rapdu.class);
//		BeanUtils.copyProperties(body, EuiccEntity.getBody());
		
		Gson gson = new Gson();
		String EuiccEntityBodyJsonStr = gson.toJson(EuiccEntity.getBody());
		E body = (E) gson.fromJson(EuiccEntityBodyJsonStr, soapBodyType);
		
		SOAPEntity<E> soapEntity = new SOAPEntity<E>(header, body);
		
		return soapEntity;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static  EuiccEntity toEuiccEntity(SOAPEntity<?> soapEntity , Class<?> bodyType) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		
		Header header = soapEntity.getHeader();
		
//		Body body = (Body) bodyType.newInstance();
//		
//		ConvertUtils.register(new SOAPRspnMsgConvertToEntityRspnMsg(), RspnMsg.class);
//		BeanUtils.copyProperties(body, soapEntity.getBody());
		
		Gson gson = new Gson();
		String soapBodyJsonStr = gson.toJson(soapEntity.getBody());
		Body body = (Body)gson.fromJson(soapBodyJsonStr, bodyType);
		
		@SuppressWarnings("unchecked")
		EuiccEntity EuiccEntity = new EuiccEntity(header,body);
		
		return EuiccEntity;
	}
		
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
   