package com.whty.euicc.rsp.handler;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.spring.SpringContextHolder;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.handler.base.AbstractHandler;
/**
 * 接收LPA请求路由处理
 * @author Administrator
 *
 */
@Service("msgBusiTypeDpPlus")
public class DpPlusHandler extends AbstractHandler{
	
	final static Logger logger = LoggerFactory.getLogger(DpPlusHandler.class);
	protected static final Gson GSON = new Gson();

	private static Charset UTF8 = Charset.forName("UTF-8");
	
	public byte[] handle(String requestStr) {
		logger.info("==========接收来自DP PLUS消息==========");
		
		logger.debug("请求消息明文 : {}", requestStr);
		
		String path = TlsMessageUtils.getPath(requestStr);
		
		AbstractHandler msgHandler = (AbstractHandler) SpringContextHolder.getBean(path);
		
		byte[] respMsg = msgHandler.handle(requestStr);
		
		logger.debug("resp:{}",new String(respMsg));
		
		return respMsg;
	}
	
	

}
