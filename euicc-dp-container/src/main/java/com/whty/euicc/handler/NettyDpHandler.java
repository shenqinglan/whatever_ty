package com.whty.euicc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.spring.SpringContextHolder;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.netty.NettyHttpHandler;

/**
 * 非卡端请求路由处理
 * @author Administrator
 *
 */
public class NettyDpHandler implements NettyHttpHandler{
	final static Logger logger = LoggerFactory.getLogger(NettyDpHandler.class);

	@Override
	public byte[] handle(String requestStr) {
		logger.info("==========接收来自非卡端请求路由处理消息==========");
		
		try {
			String reqMsg = requestStr;
			
			logger.debug("请求消息明文 : {}", reqMsg);
			
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			
			HttpHandler msgHandler = (HttpHandler) SpringContextHolder
					.getBean(reqMsgObject.getHeader().getMsgType());
			
			byte[] respMsg = msgHandler.handle(reqMsg);

			logger.debug("响应消息明文 : {}", new String(respMsg));

			return respMsg;
		} catch (Exception e) {
			logger.error("处理异常:{}",e.getMessage());
			RespMessage respMessage;
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
			return new Gson().toJson(respMessage).getBytes();
		}
	}

}
