package com.whty.efs.common.util;



import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class MyHttpPostHelper {

	public static HttpPost getHttpPost(String url) {
		return new HttpPost(url);
	}

	@SuppressWarnings("deprecation")
	public static UrlEncodedFormEntity buildUrlEncodedFormEntity(
			Map<String, String> keyValuePairs, String encoding) {
		if (encoding == null) {
			encoding = HTTP.UTF_8;
		}

		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		if (keyValuePairs != null) {
			Set<String> keys = keyValuePairs.keySet();

			for (String key : keys) {
				String value = keyValuePairs.get(key);
				BasicNameValuePair param = new BasicNameValuePair(key, value);
				params.add(param);
			}
		}

		try {
			return new UrlEncodedFormEntity(params, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DefaultHttpClient getDefaultHttpClient() {
		return new DefaultHttpClient();
	}

	public static HttpEntity buildUrlEncodedFormEntity(
			Map<String, String> keyValuePairs) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		if (keyValuePairs != null) {
			Set<String> keys = keyValuePairs.keySet();

			for (String key : keys) {
				String value = keyValuePairs.get(key);
				BasicNameValuePair param = new BasicNameValuePair(key, value);
				params.add(param);
			}
		}

		try {
			return new UrlEncodedFormEntity(params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
