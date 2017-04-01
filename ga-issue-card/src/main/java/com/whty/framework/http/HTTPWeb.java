/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-12
 * Id: HTTPWeb.java,v 1.0 2017-1-12 上午10:27:21 Administrator
 */
package com.whty.framework.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName HTTPWeb
 * @author Administrator
 * @date 2017-1-12 上午10:27:21
 * @Description TODO(http请求客户端工具类)
 */
public class HTTPWeb {

	public static String post(String url ) {
		return post(url,new HashMap<String, Object>());
	}
	
	public static String get(String url ) {
		return get(url,new HashMap<String, Object>());
	}
	
	public static String post(String url , Map<String, ? extends Object > data) {
		Long sdate=System.currentTimeMillis();
		HttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
			if(data != null){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : data.keySet()) {
					if(StringUtils.isBlank(key)||data.get(key)==null){
						continue;
					}
					if(data.keySet() instanceof Date){
						Date dd=(Date) data.keySet();
						String value="";
						try{
							value=DateFormatUtils.format(dd, "yyyy-MM-dd HH:mm:ss");
						}catch(Exception e0){
							value=DateFormatUtils.format(dd, "yyyy-MM-dd");
						}
						params.add(new BasicNameValuePair(key, value));
					}else{
					params.add(new BasicNameValuePair(key, data.get(key).toString()));
					}
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			}
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity == null) return null;
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			Long edate=System.currentTimeMillis();
			System.out.println(url+" 参数  "+JSON.toJSONString(data)+" 用时  "+(edate-sdate)+"ms");
			String result=buffer.toString();
			if(result.startsWith("<htm")||result.trim().equals("")){
				System.out.println(url+" 参数  "+JSON.toJSONString(data)+" 请求结果异常 "+result);
			}
			return result;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out.println(url+"出现链接异常;参数"+JSON.toJSONString(data));
			return null;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param url
	 * @param json
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static String post(String url , JSONObject json ) {
		Map<String, String> data = new HashMap<String, String>();
		for (String key : json.keySet()) {
			if(key==null||data.get(key)==null){
				continue;
			}
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		return post(url, data);
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param url
	 * @param data
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static String get(String url , Map<String, ? extends Object> data){
		HttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		try {
			URIBuilder uriBuilder = new URIBuilder().setPath(url);
			if(data != null){
				for (String key : data.keySet()) {
					uriBuilder.setParameter(key, data.get(key).toString());
				}
			}
			httpGet.setURI(uriBuilder.build());
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity == null) return null;
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			return buffer.toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param url
	 * @param json
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static String get(String url , JSONObject json ) {
		Map<String, String> data = new HashMap<String, String>();
		for (Object key : json.keySet()) {
			if(key==null||data.get(key)==null){
				continue;
			}
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		return get(url, data);
	}
}
