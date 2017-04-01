/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: ISmppSessionListener.java,v 1.0 2017-1-20 上午10:50:27 Administrator
 */
package com.whty.smpp.netty.handler;

import org.jboss.netty.channel.Channel;

import com.cloudhopper.commons.util.windowing.Window;
import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.whty.smpp.netty.constants.SmppSessionState;
import com.whty.smpp.netty.pdu.Pdu;
import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;
import com.whty.smpp.netty.transcoder.ISmppPduTranscoder;

/**
 * @ClassName ISmppSessionListener
 * @author Administrator
 * @date 2017-1-20 上午10:50:27
 * @Description TODO(Netty 框架处理接收到的消息)
 */
public interface ISmppSessionListener {
	
	// 设置session的状态
	public void setBound();
	
	public void setSessionState(int state);
	
	public Channel getChannel();
	
	public ISmppPduTranscoder getTranscoder();
	
	public void closeSessionHandler();
	
	public SmppSessionState getSessionState();
	
	public Window<Integer, PduRequest, PduResponse> getSendWindow();
		
	// 发送请求消息
	public WindowFuture<Integer,PduRequest,PduResponse> sendRequestPdu(PduRequest pdu, long timeoutMillis, long expireTimeoutMillis, boolean synchronous);
	
	// 发送响应消息
	public void sendResponsePdu(PduResponse pdu);
	
	// 发送消息并返回
	public PduResponse sendRequestAndGetResponse(PduRequest requestPdu,
			long timeoutInMillis, long expireTimeoutMillis);
	
	// 接收消息并返回
	public void firePduReceived(Pdu pdu);
	
	// 和smpp模拟器断开通道
	public void fireChannelClosed();
	
	// 判断请求数据超时
	public void firePduRequestExpired(PduRequest pduRequest);
}
