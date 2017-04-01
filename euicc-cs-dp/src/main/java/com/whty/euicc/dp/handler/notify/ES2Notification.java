package com.whty.euicc.dp.handler.notify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;


import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.common.utils.HttpClientSoap;
import com.whty.euicc.common.utils.SecurityUtil;

public class ES2Notification {
	private static final String _url = SpringPropertyPlaceholderConfigurer.getStringProperty("es2_notify_url");
 	//是否通知标识符
	private final static String notifyYes = "1";

	public void es2HandleSMSRChangeNotification() throws java.lang.Exception {
		 String xml = inputStream2String(ES2Notification.class.getClassLoader().getResourceAsStream("ES2HandleSMSRChangeNotification.xml"));
		 HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
	 }
	 
	 public void es2HandleProfileDisabledNotification(String eid, String iccid) throws java.lang.Exception {
		 eid = SecurityUtil.encodeHexString(eid);
		 String xml = inputStream2String(ES2Notification.class.getClassLoader().getResourceAsStream("ES2HandleProfileDisabledNotification.xml"));
		 XMLGregorianCalendar call = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar ());
		 xml = messageFormat(xml, eid, iccid, call);
		 HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
	 }
	 
	 public void es2HandleProfileEnabledNotification(String eid, String iccid) throws java.lang.Exception {
		 eid = SecurityUtil.encodeHexString(eid);
		 String xml = inputStream2String(ES2Notification.class.getClassLoader().getResourceAsStream("ES2HandleProfileEnabledNotification.xml"));
		 XMLGregorianCalendar call = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar ());
		 xml = messageFormat(xml, eid, iccid, call);
		 HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
	 }
	 
	 public void es2HandleProfileDeletedNotification(String eid, String iccid) throws java.lang.Exception {
		 eid = SecurityUtil.encodeHexString(eid);
		 String xml = inputStream2String(ES2Notification.class.getClassLoader().getResourceAsStream("ES2HandleProfileDeletedNotification.xml"));
		 XMLGregorianCalendar call = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar ());
		 xml = messageFormat(xml, eid, iccid, call);
		 HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);	 
	 }
	
    /**
	 * 对拥有被禁用或者被删除的Profile的MNO发送通知
	 * @param eid
	 * @param iccid
	 * @throws Exception
	 */
	public void handleNotifyInEnableProfile(String eid, String iccid, String state) throws Exception {
		String isOpenNotify = SpringPropertyPlaceholderConfigurer.getStringProperty("is_open_notify");
		if(StringUtils.equals(isOpenNotify, notifyYes) && (StringUtils.isNotBlank(iccid))
				&& (StringUtils.isNotBlank(eid))){
			if(StringUtils.equals(ProfileState.DIS_ENABLE, state)){
				es2HandleProfileDisabledNotification(eid, iccid);
			}else if(StringUtils.equals(ProfileState.DELETE, state)){
				es2HandleProfileDeletedNotification(eid, iccid);
			}
			
		}
	}

	/**
	 * 对拥有被启用的Profile的MNO发送通知
	 * @param eid
	 * @throws Exception
	 */
	public void handleNotifyInDisableProfile(String eid ,String iccid) throws Exception {
		String isOpenNotify = SpringPropertyPlaceholderConfigurer.getStringProperty("is_open_notify");
		if(StringUtils.equals(isOpenNotify, notifyYes) && (StringUtils.isNotBlank(eid))){
			es2HandleProfileEnabledNotification(eid, iccid);
		}		
	}
	
	/**
	 * 文件输入格式转换为String
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private String inputStream2String(InputStream is) throws IOException{
		   BufferedReader in = new BufferedReader(new InputStreamReader(is));
		   StringBuffer buffer = new StringBuffer();
		   String line = "";
		   while ((line = in.readLine()) != null){
		     buffer.append(line);
		   }
		   return buffer.toString();
	}
	
	/**
	 * 替换xml字符串中的{0}
	 * @param xmlString
	 * @param replaceString
	 * @return
	 */
	private String messageFormat(String xmlString, Object ... replaceString){
		String str = MessageFormat.format(xmlString, replaceString);
		return str;
	}
}
