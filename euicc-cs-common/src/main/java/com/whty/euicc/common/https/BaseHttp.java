package com.whty.euicc.common.https;




import com.whty.euicc.common.https.HttpsUtil;
import com.whty.euicc.common.https.SslContextFactory;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
/**
 * http请求类
 * @author Administrator
 *
 */
public class BaseHttp {
	
	public static byte[] doPost(String str)throws Exception{
		String SR_URL = SpringPropertyPlaceholderConfigurer.getStringProperty("sr_url");
        return doPost(SR_URL, str);
	}
	
	public static byte[] doPost(String url,String str)throws Exception{
	    HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        return HttpsUtil.doPost2(url, str.getBytes());
	}
	
}
