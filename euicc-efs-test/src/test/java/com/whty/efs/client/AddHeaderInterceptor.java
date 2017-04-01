package com.whty.efs.client;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 客户端拦截器http://www.yeetrack.com/?p=581
 * 服务端拦截器http://www.tuicool.com/articles/F7RfUv
 * 增加SoapHeader
 * @author Administrator
 *
 */
public class AddHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private String userId;
	private String userPass;

	public AddHeaderInterceptor() {
		super(Phase.PREPARE_SEND);
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		List headers=msg.getHeaders(); 
        headers.add(getHeader("version", "youthflies"));
        headers.add(getHeader("sender", "youthflies"));
        headers.add(getHeader("receiver", "youthflies"));
        headers.add(getHeader("sendTime", "youthflies"));
        headers.add(getHeader("msgType", "youthflies"));
        headers.add(getHeader("tradeNO", "youthflies"));
        headers.add(getHeader("tradeRefNO", "youthflies"));
        headers.add(getHeader("sessionID", "youthflies"));
        headers.add(getHeader("deviceType", "youthflies"));
	}
	
	//http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl
    private Header getHeader(String key, String value) 
    { 
        QName qName=new QName("http://www.tathing.com", key,"add"); 

        Document document=DOMUtils.createDocument(); 
        Element element=document.createElementNS("http://www.tathing.com",key); 
        //Element element=document.createElement(key); 
        element.setTextContent(value); 
        
        SoapHeader header=new SoapHeader(qName, element); 
        return(header); 
    }

}
