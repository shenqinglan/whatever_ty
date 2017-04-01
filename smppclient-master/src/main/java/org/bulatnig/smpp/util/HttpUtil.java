package org.bulatnig.smpp.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP通讯工具
 * */
public class HttpUtil {

	private static final String $req_method_post = "POST";
	private static final String $property_name_contentType = "Content-Type";
	private static final String $property_value_contentType = "text/json";
	private static final String $property_name_cache = "Cache-Control";
	private static final String $property_value_cache = "no-cache";
	
	private static int TIMEOUT = 30000;

	@SuppressWarnings("unused")
	private static final int _minute = 1000 * 5;// 5秒后超时

	public static byte[] doGet(String strURL, Map<String, String> argsMap)
			throws Exception {
		return doPost(strURL, argsMap);
	}

	public static byte[] doGet(String strURL, byte[] b) throws Exception {
		return doPost(strURL, b);
	}

	/**
	 * post方式提交请求
	 * */
	public static byte[] doPost(String strURL, Map<String, String> argsMap)
			throws Exception {
		byte[] b = parseParamMap(argsMap).getBytes();
		return doPost(strURL, b);
	}

	/**
	 * post方式提交请求 \ strURL请求地址\ argsMap参数键值对
	 * */
	public static byte[] doPost(String strURL, byte[] b) throws Exception {
		// StringBuffer sbReturn = new StringBuffer("");
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader br = null;
		byte[] data = null;

		try {
			url = new URL(strURL);
			httpConnection = (HttpURLConnection) url.openConnection();

			httpConnection.setRequestMethod($req_method_post);
			httpConnection.setRequestProperty($property_name_contentType,
					$property_value_contentType);
			httpConnection.setRequestProperty($property_name_cache,
					$property_value_cache);
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			/*
			 * httpConnection.setConnectTimeout(_minute);
			 * httpConnection.setReadTimeout(_minute);
			 */

			// 发送请求
			out = httpConnection.getOutputStream();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			// 接收返回
			in = httpConnection.getInputStream();
			data = IOUtils.toByteArray(in);
			/*
			 * br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			 * String strRead = ""; while ((strRead = br.readLine()) != null) {
			 * sbReturn.append(strRead); sbReturn.append($line_feed); }
			 */
		} catch (IOException ex) {
//			logger.debug(ex.getMessage(), ex);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException fx) {
//				logger.debug(fx.getMessage(), fx);
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException fx) {
//				logger.debug(fx.getMessage(), fx);
			}
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException fx) {
//				logger.debug(fx.getMessage(), fx);
			}
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
		return data;
	}

	/**
	 * 解析请求参数键值对
	 * */
	private static String parseParamMap(Map<String, String> argsMap)
			throws Exception {
		StringBuffer sbParams = new StringBuffer("");
		boolean nofirst = false;
		Iterator<Entry<String, String>> itor = argsMap.entrySet().iterator();
		while (itor.hasNext()) {
			if (nofirst)
				sbParams.append("&");
			Entry<String, String> entryKey = itor.next();

			sbParams.append(entryKey.getKey());
			sbParams.append("=");
			sbParams.append(URLEncoder.encode(entryKey.getValue(), "utf-8"));
			nofirst = true;
		}
		return sbParams.toString();
	}

	public static byte[] doPost2(String url, byte[] data) throws IOException {
		HttpURLConnection httpConn = null;

		OutputStream output = null;
		try {
			// URL url = new URL("http://"+getLocalIP()+"/tsm/tsmService");
			URL url1 = new URL(url);

			httpConn = (HttpURLConnection) url1.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			httpConn.setDoOutput(true);
			httpConn.setConnectTimeout(TIMEOUT);  
			httpConn.setReadTimeout(TIMEOUT); 
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-Type", "text/json");
			httpConn.connect();

			output = httpConn.getOutputStream();

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
			int code = httpConn.getResponseCode();
			System.out.println("code " + code);
			System.out.println(httpConn.getResponseMessage());

			// 读取响应内容
			if (code == 200) {
				is = httpConn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] b = new byte[1024];
				while ((len = is.read(b)) != -1) {
					baos.write(b, 0, len);
				}
			}
		} catch (Exception e) {
		} finally {
			if (null != reader) {
				reader.close();
			}
			if (null != is) {
				is.close();
			}
		}
		return baos.toByteArray();
	}
	
	/**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }

}
