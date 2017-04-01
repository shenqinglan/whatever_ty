package com.whty.efs.common.constant;

import java.nio.charset.Charset;

import com.google.gson.Gson;

public class Constant {
	
	public interface Common{
		/** 字符集 */
		public static Charset ENCODE_UTF8 = Charset.forName("UTF-8");
		public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
		public static final String DATE_FORMAT = "yyyy-MM-dd";
		public static final String TIME_FORMAT = "HH:mm:ss";
		
		public static final String DEFAULT_SECURITY_NAME = "3DES";
		
		public static final String successCode="0000";
		
		public static final String service="service";
	}
	
	public interface Plugin{
		/**
		 * 默认报文业务类型(发送webservice请求)
		 */
		public final static String DEFAULT = "standardHttpMsgHandler";
		/**
		 * 洪城报文业务类型
		 */
		public final static String HC = "HC";
		/**
		 * 厦门报文业务类型
		 */
		public final static String XM = "XM";
		/**
		 * 重庆报文业务类型
		 */
		public final static String CQ = "CQ";
		/**
		 * sp-tsm报文业务类型
		 */
		public final static String SP_TSM = "SP_TSM";
		/**
		 * webservice报文业务类型
		 */
		public final static String WEB_SERVICE = "standardWSMsgHandler";
		
		/**
		 * webservice报文业务类型
		 */
		public final static String PERSONAL = "personal";
		
		public final static String ACTIVATE = "activate";
		
		/**他信至第三方**/
		public static final int TATHING_2_THIRD = 1;
		
		/**第三方至他信**/
		public static final int THIRD_2_TATHING = 2;
		
		/**他信到第三方Http*/
		public static final int TATHING_2_THIRD_HTTP = 3;
		
		/**路由信息前缀*/
		public static final String ROUTER_INFO = "ROUTER_";
		/**路由信息前缀*/
		public static final String ACCESS_SENDER = "SEND_";
		/**路由前缀**/
		public static final String ROUTER_PREFIX = "e_router_";
		/**厦门交易密钥前缀**/
		public static final String XIAMEN_REDIS = "XIAMEN_REDIS_"; 
		/**洪城交易密钥前缀**/
		public static final String HONGCHENG_REDIS = "HONGCHENG_REDIS_"; 
	}
	
	public interface WS{
		public static final String SOAP_XSD_PREFIX_ES_HEAD = "add";
		public static final String SOAP_XSD_PREFIX_ES_BODY = "ns";
		public static final String SOAP_XSD_NAMESPACE_ES = "http://namespaces.gsma.org/esim-messaging/3";
		public static final String SOAP_XSD_NAMESPACE_ES_ADD = "http://www.w3.org/2005/08/addressing";
		public static final String SOAP_XSD_PREFIX = "tsm";
		public static final String SOAP_XSD_NAMESPACE = "http://www.tathing.com";
		
	}
	
	public interface Util{
		/** GSON */
		public static final Gson gson = new Gson();
		
	}
	
	public interface constant{
		public static final String   EXCEPTION               = "Exception: ";
	}
}
