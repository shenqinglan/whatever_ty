/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014年5月6日
 * Id: HTTPWeb.java,v 1.0 2014年5月6日 上午10:41:13 admin
 */
package com.whty.oauth.platform.util;

import java.io.BufferedReader;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName HTTPWeb
 * @author admin
 * @date 2014年5月6日 上午10:41:13
 * @Description http服务请求交互
 */
public class HTTPWeb {
	
	private static Logger logger = LoggerFactory.getLogger(HTTPWeb.class);
	
	/**
	 * @author admin
	 * @date 2014年5月6日
	 * @param url 请求服务路径
	 * @param data 请求参数对象
	 * @return 请求结果
	 * @Description 执行一个http的web请求
	 */
	public static String post(String url , Map<String, ? extends Object > data) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
			if(data != null){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : data.keySet()) {
					if(key==null||StringUtils.isBlank(key)||data.get(key)==null||StringUtils.isBlank(data.get(key).toString()))
						continue;
					params.add(new BasicNameValuePair(key, data.get(key).toString()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			}
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpPost.setConfig(config);
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity == null) return null;
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
			return null;
	    } catch (IOException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn("服务器关闭连接失败", e);
			}
		}
	}
	
	/**
	 * @author admin
	 * @date 2014年5月6日
	 * @param url 请求服务路径
	 * @param data 请求参数对象
	 * @return 请求结果
	 * @Description 执行一个http的web请求
	 */
	public static String post(String url , Map<String, ? extends Object > data , String userAgent) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
			if(data != null){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : data.keySet()) {
					params.add(new BasicNameValuePair(key, data.get(key).toString()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
				httpPost.setHeader("User-Agent",  userAgent + "_Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.76 Safari/537.36");
			}
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpPost.setConfig(config);
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity == null) return null;
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
			return null;
	    } catch (IOException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn("服务器关闭连接失败", e);
			}
		}
	}
	
	/**
	 * @author admin
	 * @date 2014年5月6日
	 * @param url 请求服务路径
	 * @param json 请求参数对象
	 * @return 请求结果
	 * @Description 执行一个http的web请求
	 */
	public static String post(String url , JSONObject json ) {
		Map<String, String> data = new HashMap<String, String>();
		for (Object key : json.keySet()) {
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		return post(url, data);
	}
	
	/**
	 * @author admin
	 * @date 2014年5月6日
	 * @param url  请求服务路径
	 * @param json  请求参数对象
	 * @return 请求结果
	 * @Description 执行一个http的web请求
	 */
	public static String post(String url , String json){
		return post(url, JSONObject.parseObject(json));
	}
	
	/**
	 * @author admin
	 * @date 2014年5月7日
	 * @param url 请求服务路径
	 * @param data 请求参数
	 * @return 请求结果
	 * @Description 通过一个get请求获得数据
	 */
	public static String get(String url , Map<String, ? extends Object> data){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		try {
			URIBuilder uriBuilder = new URIBuilder().setPath(url);
			if(data != null){
				for (String key : data.keySet()) {
					uriBuilder.setParameter(key, data.get(key).toString());
				}
			}
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpGet.setConfig(config);
			httpGet.setURI(uriBuilder.build());
			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity == null) return null;
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
		} catch (ClientProtocolException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
		} catch (IOException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
		} finally {
			try {
//				System.out.println("关闭连接");
				client.close();
			} catch (IOException e) {
				logger.warn("关闭连接失败",e);
			}
		}
		return null;
	}
	
	/**
	 * @author admin
	 * @date 2014年5月6日
	 * @param url 请求服务路径
	 * @param json 请求参数对象
	 * @return 请求结果
	 * @Description 执行一个http的web请求
	 */
	public static String get(String url , JSONObject json ) {
		Map<String, String> data = new HashMap<String, String>();
		for (Object key : json.keySet()) {
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		return get(url, data);
	}
	
	/**
	 * @author admin
	 * @date 2014年5月6日
	 * @param url  请求服务路径
	 * @param json  请求参数对象
	 * @return 请求结果
	 * @Description 执行一个http的web请求
	 */
	public static String get(String url , String json){
		return get(url, JSONObject.parseObject(json));
	}
	
	/**
	 * @author admin
	 * @date 2014年5月28日
	 * @param url  请求服务路径
	 * @param data 请求参数对象
	 * @return 请求结果
	 * @Description 直接post一个body字符串
	 */
	public static String postBody(String url , String data){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
			httpPost.setEntity(new StringEntity(data));
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpPost.setConfig(config);
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity == null) return null;
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
			return null;
	    } catch (IOException e) {
			logger.warn("服务器请求失败..URL:"+url, e);
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn("关闭连接失败"+e);
			}
		}
	}
	
}
