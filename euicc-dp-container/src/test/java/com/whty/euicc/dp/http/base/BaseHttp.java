package com.whty.euicc.dp.http.base;



import com.whty.euicc.common.https.HttpsUtil;
import com.whty.euicc.common.https.SslContextFactory;
import com.whty.euicc.common.utils.HttpUtil;


public class BaseHttp {
	public final static String urlByDp_http = "http://127.0.0.1:7777";
	public final static String urlByDp = "https://127.0.0.1:8888";
	public final static String urlBySr = "https://127.0.0.1:9999";
	public final static String urlByDp_116 = "https://10.8.40.116:8888";
	public final static String urlBySr_116 = "https://10.8.40.116:9999";
	
	public static byte[] doPostByDp(String str)throws Exception{
		 return doPost(urlByDp,str);
	}
	public static byte[] doPostBySr(String str)throws Exception{
        return doPost(urlBySr,str);
	}
	public static byte[] doPost(String url,String str)throws Exception{
	    HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        return HttpsUtil.doPost2(url, str.getBytes());
	}
	
	public static byte[] doPostForHttp(String url,String str)throws Exception{
        return HttpUtil.doPost2(url, str.getBytes());
	}
}
