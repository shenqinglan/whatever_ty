package com.whty.euicc.common.https;

import com.whty.euicc.common.constant.StaticConfig;
import com.whty.euicc.common.utils.HttpUtil;




public class BaseHttp {
	
	public static byte[] doPost(String str)throws Exception{
        return doPost(StaticConfig.getDpUrl(), str);
	}
	
	public static byte[] doPost(String url,String str)throws Exception{
	    //HttpsUtil.setSSLSocketFactory(SslContextFactory.getClientContext().getSocketFactory());
        return HttpUtil.doPost2(url, str.getBytes());
	}
	
}
