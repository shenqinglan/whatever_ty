package com.whty.euicc.handler;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.telecom.http.tls.test.Util;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.spring.SpringContextHolder;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.util.StringUtils;
/**
 * 接收卡片请求路由处理
 * @author Administrator
 *
 */
@Service("msgBusiTypeSr")
public class SrHandler extends AbstractHandler{
	
	final static Logger logger = LoggerFactory.getLogger(SrHandler.class);

	
	/**
	 * 下发apdu或者其它形式命令给eUICC卡片
	 * @param requestStr
	 * @return
	 */
	public byte[] handle(String requestStr) {
		logger.info("==========sr handle接收来自卡片消息==========");
		
		String reqMsg = requestStr;

		
		logger.debug("sr请求消息明文 : {}", reqMsg);
		String eid = TlsMessageUtils.getEid(requestStr);
		String smsTrigger = CacheUtil.getString(eid);//eid
        if(StringUtils.isEmpty(smsTrigger)){
        	return null;
        }
        
        SmsTrigger eventTrigger = new Gson().fromJson(smsTrigger,SmsTrigger.class);
        String eventType = eventTrigger.getEventType();
		
		AbstractHandler msgHandler = (AbstractHandler) SpringContextHolder.getBean(eventType);
		
		byte[] respMsg = msgHandler.handle(reqMsg);
		
		printLog(respMsg);
	
		return respMsg;
	}

	private void printLog(byte[] respMsg) {
		try {
			String message=Util.byteArrayToHexString(respMsg, "");
			int endOfDbl0D0A=message.indexOf("0D0A0D0A")+"0D0A0D0A".length();
			logger.debug("响应消息明文 : {}", Util.hexToASCII(message.substring(0, endOfDbl0D0A)) + message.substring(endOfDbl0D0A));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查eUICC卡片返回结果
	 * @param requestStr
	 * @return
	 */
	public boolean checkProcessResp(String requestStr) {
		logger.info("==========sr checkProcessResp 接收来自卡片消息==========");
		
		String reqMsg = requestStr;

		logger.debug("请求消息明文 : {}", reqMsg);
		String eid = TlsMessageUtils.getEid(requestStr);
		String smsTrigger = CacheUtil.getString(eid);//eid
        if(StringUtils.isEmpty(smsTrigger)){
        	return false;
        }
        
        SmsTrigger eventTrigger = new Gson().fromJson(smsTrigger,SmsTrigger.class);
        String eventType = eventTrigger.getEventType();
    	
		
        AbstractHandler msgHandler = (AbstractHandler) SpringContextHolder
				.getBean(eventType);
		
		boolean flag = msgHandler.checkProcessResp(reqMsg);

		logger.debug("处理返回结果 : {}", flag);
		return flag;
	}
	

}
