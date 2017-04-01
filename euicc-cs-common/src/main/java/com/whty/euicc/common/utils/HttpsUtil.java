package com.whty.euicc.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * https工具类
 * @author Administrator
 *
 */
public class HttpsUtil {
	
	private static int TIMEOUT = 30000;
	private static SSLSocketFactory factory = null;
	
	public static void setSSLSocketFactory(SSLSocketFactory factory) {
		HttpsUtil.factory = factory;
	}
	
	public static byte[] doPost2(String url, byte[] data) throws Exception {
		URL url1 = new URL(url);
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		HttpsURLConnection httpsConn = (HttpsURLConnection) url1.openConnection();
		httpsConn.setRequestProperty("Content-Type", "text/json");
		httpsConn.setDoOutput(true);
		httpsConn.setDoInput(true);
		httpsConn.setRequestMethod("POST");
		httpsConn.setUseCaches(false);
		httpsConn.setReadTimeout(TIMEOUT);
		httpsConn.setSSLSocketFactory(factory);
		httpsConn.connect();
		OutputStream output = httpsConn.getOutputStream();
		output.write(data);
		output.flush();
		output.close();
		System.out.println("send ok");
		int code = httpsConn.getResponseCode();
		System.out.println("code " + code);
		System.out.println(httpsConn.getResponseMessage());

		byte[] buf = null;
		// 正常的响应编码
		if (code == HttpsURLConnection.HTTP_OK) {
			InputStream is = httpsConn.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = -1;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			buf = baos.toByteArray();
		}
		return buf;
	}
}
