package com.whty.efs.common.https;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP通讯工具
 * */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static final String $req_method_post = "POST";
	private static final String $property_name_contentType = "Content-Type";
	private static final String $property_value_contentType = "text/json";
	private static final String $property_name_cache = "Cache-Control";
	private static final String $property_value_cache = "no-cache";
	
	private static int TIMEOUT = 180000;
	private static final int TYPE_HTTP = 0x01;
	private static final int TYPE_HTTPS = 0x02;
	
	/**
     * 取得客户端真实ip
     *
     * @param request
     * @return 客户端真实ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        logger.debug("1- X-Forwarded-For ip={}", ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.debug("2- Proxy-Client-IP ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.debug("3- WL-Proxy-Client-IP ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.debug("4- HTTP_CLIENT_IP ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.debug("5- HTTP_X_FORWARDED_FOR ip={}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.debug("6- getRemoteAddr ip={}", ip);
        }
        logger.debug("finally ip={}", ip);
        return ip;
    }

	//private static final int _minute = 1000 * 5;// 5秒后超时

	public static byte[] doGet(String strURL, Map<String, String> argsMap)
			throws Exception {
		return doPost(strURL, argsMap);
	}

	public static byte[] doGet(String strURL, byte[] b) throws Exception {
		return doPost(strURL, b);
	}
	
	public static String doPost(String url, String json) {
		logger.info("url" + url);
		String result= null;
        // 创建一个默认的HttpClient
       HttpClient httpclient = new DefaultHttpClient();
        try {
              // 以post方式请求
             HttpPost http = new HttpPost(url);
             http.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
              // 创建响应处理器处理服务器响应内容
             ResponseHandler<String> responseHandler = new BasicResponseHandler();
              // 执行请求并获取结果
             result = httpclient.execute(http, responseHandler);
       } catch (Exception e) {
             logger.error("执行http post请求异常：", e);
       } finally {
              // 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
             httpclient.getConnectionManager().shutdown();
       }
        return result;

	}

	/**
	 * post方式提交请求
	 * */
	public static byte[] doPost(String strURL, Map<String, String> argsMap)
			throws Exception {
		String args = parseParamMap(argsMap);
		logger.debug("发送参数：{}",args);
		System.out.println("发送参数:"+args);
		return doPost(strURL, args.getBytes());
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
			logger.debug(ex.getMessage(), ex);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException fx) {
				logger.debug(fx.getMessage(), fx);
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException fx) {
				logger.debug(fx.getMessage(), fx);
			}
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException fx) {
				logger.debug(fx.getMessage(), fx);
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
//	@SuppressWarnings("deprecation")
//	public static byte[] getResp(String url , Map<String , Object> req , int type) throws ClientProtocolException, IOException {
//		HttpResponse response = null;
//
//		HttpPost post = new HttpPost(url);
//		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
//		post.addHeader("charset", HTTP.UTF_8);
//		Map<String, String> data = new HashMap<String, String>();
//
//		for(Entry<String, Object> entry : req.entrySet()) {
//			data.put(entry.getKey() , (String)entry.getValue());
//		}
//		
//		UrlEncodedFormEntity myEtity = MyHttpPostHelper.buildUrlEncodedFormEntity(data, HTTP.UTF_8);
//		post.setEntity(myEtity);
//		switch (type) {
//		case TYPE_HTTP:
//			HttpParams params = new BasicHttpParams();
//			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//			HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
//			HttpConnectionParams.setSoTimeout(params, TIMEOUT);
//			response = new DefaultHttpClient(params).execute(post);
//			break;
//		case TYPE_HTTPS:
////			response = getNewHttpClient().execute(post);
//			break;
//		default:
//			break;
//		}
//		// response = new DefaultHttpClient().execute(post);
//		response.setHeader("Cache-Control", "no-cache");
//
//		StatusLine status = response.getStatusLine();
//		switch (status.getStatusCode()) {
//		case 200:
//			break;
//		case 304:
//			// request.getEventHandler().endData(null, 0);
//			return null;
//		default:
//			// request.getEventHandler().endData(null, 0);
//			try {
//				throw new IOException("HTTP error: " + status.getReasonPhrase()
//						+ " CODE:" + status.getStatusCode());
//			} catch (IOException e2) {
//				e2.printStackTrace();
//			}
//		}
//
//		HttpEntity entity = response.getEntity();
//		byte[] body = null;
//		if (entity != null) {
//			try {
//				int contentLength = (int) entity.getContentLength();
//				if (contentLength > 0) {
//					body = new byte[contentLength];
//					// DataInputStream dis = new
//					// DataInputStream(entity.getContent());
//					InputStream in = entity.getContent();
//					int offset = 0;
//					int length = contentLength;
//					try {
//						while (length > 0) {
//							int result = in.read(body, offset, length);
//							offset += result;
//							length -= result;
//						}
//					} finally {
//						try {
//							in.close();
//							// request.mLoadListener.loaded(body,
//							// contentLength);
//							// if(length == 0)
//							// CallbackProxy.getHandler().onFinishResourceLoading(body,
//							// contentLength, request.mLoadListener);
//						} catch (IOException e) {
//							logger.info("cmcc_dps", "Error closing input stream: "
//									+ e.getMessage());
//						}
//					}
//				} else {
//					ByteArrayBuilder dataBuilder = new ByteArrayBuilder();
//					body = new byte[8192];
//					InputStream in = entity.getContent();
//					int result = 0;
//					int count = 0;
//					int lowWater = body.length / 2;
//					try {
//						while (result != -1) {
//							result = in.read(body, count, body.length - count);
//							if (result != -1) {
//								logger.info("cmcc_dps", "################result:"
//										+ result);
//								count += result;
//							}
//							if (result == -1 || count >= lowWater) {
//								dataBuilder.append(body, 0, count);
//								count = 0;
//							}
//							if (result == -1) {
//								if (dataBuilder.getByteSize() > 0) {
//									byte[] cert = new byte[dataBuilder
//											.getByteSize()];
//									int offset = 0;
//									while (true) {
//										ByteArrayBuilder.Chunk c = dataBuilder
//												.getFirstChunk();
//										if (c == null)
//											break;
//										if (c.mLength != 0) {
//											System.arraycopy(c.mArray, 0, cert,
//													offset, c.mLength);
//											offset += c.mLength;
//										}
//										c.release();
//									}
//								}
//							}
//						}
//					} finally {
//						try {
//							in.close();
//						} catch (IOException e) {
//							logger.info("cmcc_dps", "Error closing input stream: "
//									+ e.getMessage());
//						}
//					}
//
//				}
//			} catch (IllegalStateException e1) {
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			} finally {
//				if (entity != null) {
//					try {
//						entity.consumeContent();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		return body;
//	}
}
