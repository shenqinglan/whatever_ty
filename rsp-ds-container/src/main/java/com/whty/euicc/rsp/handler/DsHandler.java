package com.whty.euicc.rsp.handler;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.handler.base.AbstractHandler;
/**
 * 接收dp plus or lpa请求路由处理
 * @author Administrator
 *
 */
@Service("msgBusiTypeDs")
public class DsHandler extends AbstractHandler{
	
	final static Logger logger = LoggerFactory.getLogger(DsHandler.class);
	protected static final Gson GSON = new Gson();

	private static Charset UTF8 = Charset.forName("UTF-8");
	
	public byte[] handle(String requestStr) {
		logger.info("==========接收来自dp plus or lpa 消息==========");

		logger.debug("请求消息明文 : {}", requestStr);
        
		byte[] resp =  httpPostResponseNoEnc("80E6");
		
		logger.debug("resp:{}",new String(resp));
		
		return resp;
	}
	
	

}
