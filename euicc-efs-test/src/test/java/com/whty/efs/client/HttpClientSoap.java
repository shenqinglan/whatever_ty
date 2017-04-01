package com.whty.efs.client;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class HttpClientSoap {
	public static String httpClientPostForSoap(String _url, final String requestXml) {
		DefaultHttpClient httpClient = null;
		try {
			httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(_url); // webservice服务地址
			HttpEntity re = new StringEntity(requestXml, HTTP.UTF_8);
			httppost.setHeader("Content-Type",
					"application/soap+xml; charset=utf-8");
			httppost.setEntity(re);
			HttpResponse response = httpClient.execute(httppost); // 调用接口
			// 输出的xml
			System.out
					.println("httppost.getEntity() == EntityUtils.toString:   "
							+ EntityUtils.toString(httppost.getEntity()));
			// 返回是否成功的状态
			int statusCode = response.getStatusLine().getStatusCode(); 
			System.out.println(statusCode);
			if (statusCode == 200) {
				// 获得输出的字符串
				String xmlString = EntityUtils.toString(response.getEntity());
				System.out.println(formatXML(xmlString));
				return xmlString;
			}else if(statusCode == 202){
				System.out.println("202 in");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接
		}
		return null;
	}

	public static String formatXML(String inputXML) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(inputXML));
		String requestXML = null;
		XMLWriter writer = null;
		if (document != null) {
			try {
				StringWriter stringWriter = new StringWriter();
				OutputFormat format = new OutputFormat(" ", true);
				writer = new XMLWriter(stringWriter, format);
				writer.write(document);
				writer.flush();
				requestXML = stringWriter.getBuffer().toString();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return requestXML;
	}
}
