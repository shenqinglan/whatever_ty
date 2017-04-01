package com.whty.efs.common.https;

import com.whty.efs.common.spring.SpringPropertyPlaceholderConfigurer;

public class BaseHttp {
	public final static String urlByDp = SpringPropertyPlaceholderConfigurer.getStringProperty("dp_url");
	public final static String urlBySr = SpringPropertyPlaceholderConfigurer.getStringProperty("sr_url");
	public final static String urlSmpp = SpringPropertyPlaceholderConfigurer.getStringProperty("smpp_url");
	public static byte[] doPostByDp(String str)throws Exception{
		 return doPost(urlByDp,str);
	}
	public static byte[] doPostBySr(String str)throws Exception{
       return doPost(urlBySr,str);
	}
	
	public static byte[] doPostBySmpp(String str)throws Exception{
      return HttpUtil.doPost2(urlSmpp, str.getBytes());
	}
	
	public static byte[] doPosts(String url,String str)throws Exception{
	    HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        return HttpsUtil.doPost2(url, str.getBytes());
	}
	
	public static byte[] doPost(String url,String str)throws Exception{
        return HttpUtil.doPost2(url, str.getBytes());
	}
	
}
