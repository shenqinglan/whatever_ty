package com.whty.euicc.sr.http.base;



import com.whty.euicc.common.https.HttpsUtil;
import com.whty.euicc.common.https.SslContextFactory;
import com.whty.euicc.common.utils.HttpUtil;

public class BaseHttp {
	private final static String url = "https://127.0.0.1:9999";
	public final static String url_116 = "https://10.8.40.116:9999";
	public final static String http_url_116 = "http://10.8.40.116:6666";
	public final static String nginx_http_url_116 = "http://10.8.40.116:2014";
	public final static String url_gz = "http://121.32.89.211:7689";
	
	public static byte[] doPost(String str)throws Exception{
	    return doPost(url,str);
	}
	
	public static byte[] doPost(String url,String str)throws Exception{
	    HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        return HttpsUtil.doPost2(url, str.getBytes());
	}
	
	public static byte[] doPostForHttp(String url,String str)throws Exception{
        return HttpUtil.doPost2(url, str.getBytes());
	}
	
}
