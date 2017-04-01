package com.whty.euicc.sr.handler.netty.notify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.common.utils.HttpClientSoap;

public class ES7Notification {
	private static final String _url = SpringPropertyPlaceholderConfigurer.getStringProperty("es7_notify_url");
	// 是否通知标识符
	private final static String notifyYes = "1";
 		
    private void es7HandleSMSRChangeNotification() throws IOException {
		String xml = inputStream2String(ES7Notification.class.getClassLoader().getResourceAsStream("ES7HandleSMSRChangeNotification.xml"));
    	HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
	}
    /**
	 *  通知SM-SR2已成功删除EIS
	 * @param eis
	 * @throws Exception
	 */
	public void handleNotifyInSrChange() throws Exception {
		String isOpenNotify = SpringPropertyPlaceholderConfigurer.getStringProperty("is_open_notify");
		if(StringUtils.equals(isOpenNotify, notifyYes) ){
			ES7Notification notify = new ES7Notification();
			notify.es7HandleSMSRChangeNotification();
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
