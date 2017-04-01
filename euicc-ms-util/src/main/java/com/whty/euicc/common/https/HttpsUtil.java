package com.whty.euicc.common.https;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;


public class HttpsUtil {

	private static int TIMEOUT = 60000;
	private static SSLSocketFactory factory = null;

	public static void setSSLSocketFactory(SSLSocketFactory factory) {
		HttpsUtil.factory = factory;
	}

	public static byte[] doPost2(String url, byte[] data) throws Exception {
		HttpsURLConnection httpsConn = null;
		OutputStream output = null;
		try {
			URL url1 = new URL(url);
			HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
			return true;
			}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			httpsConn = (HttpsURLConnection) url1.openConnection();
			httpsConn.setRequestProperty("Content-Type", "text/json");
			httpsConn.setDoOutput(true);
			httpsConn.setDoInput(true);
			httpsConn.setRequestMethod("POST");
			httpsConn.setUseCaches(false);
			httpsConn.setConnectTimeout(TIMEOUT);
			httpsConn.setReadTimeout(TIMEOUT);
			if(factory != null)httpsConn.setSSLSocketFactory(factory);
			httpsConn.connect();
			output = httpsConn.getOutputStream();
			output.write(data);
			output.flush();

		} finally {
			if (null != output) {
				output.close();
			}
		}
		InputStream is = null;
		BufferedReader reader = null;
		ByteArrayOutputStream baos = null;
		try {
			System.out.println("send ok");
			int code = httpsConn.getResponseCode();
			System.out.println("code " + code);
			//System.out.println(httpsConn.getResponseMessage());

			// 璇诲彇鍝嶅簲鍐呭
			if (code == 200) {
				is = httpsConn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] b = new byte[1024];
				while ((len = is.read(b)) != -1) {
					baos.write(b, 0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				reader.close();
			}
			if (null != is) {
				is.close();
			}
			httpsConn.disconnect();
		}
		return baos.toByteArray();
	}
}
