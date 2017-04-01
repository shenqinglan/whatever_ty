package com.whty.euicc.rsp.packets.message;

import com.whty.euicc.rsp.packets.constant.Constant;


/**
 * 请求报文
 * 
 * @author gaofeng
 * 
 */
public class EuiccMsg<B extends MsgBody> {

	public EuiccMsg() {
	}

	
	public EuiccMsg(B body) {
		this.body = body;
	}
	/**
	 * 消息数据构造
	 * @param header 消息头
	 * @param bizStatus 处理状态
	 * @param bizStatusCode 状态码
	 *//*
	public static EuiccMsg<ResponseMsgBody> buildEuiccMsg(String bizStatus, String bizStatusCode) {
		return new EuiccMsg<ResponseMsgBody>(new BaseRespBody(new RspnMsg(bizStatus, bizStatusCode)));
	}

	*//**
	 * 消息数据构造
	 * @param header 消息头
	 * @param bizStatus 处理状态
	 * @param bizStatusCode 状态码
	 * @param message 处理消息
	 *//*
	public static EuiccMsg<ResponseMsgBody> buildEuiccMsg(String bizStatus, String bizStatusCode,String message) {
		return new EuiccMsg<ResponseMsgBody>(new BaseRespBody(new RspnMsg(bizStatus, bizStatusCode, message)));
	}
	*//**
	 * 消息数据构造
	 * @param header 消息头
	 * @param bizStatus 处理状态
	 * @param bizStatusCode 状态码
	 * @param e 处理消息
	 *//*
	public static EuiccMsg<ResponseMsgBody> buildEuiccMsg(String bizStatus, String bizStatusCode,Exception e) {
		
		String message = printStackTrace(e, new StringBuilder()).toString();
		
		return new EuiccMsg<ResponseMsgBody>(new BaseRespBody(new RspnMsg(bizStatus, bizStatusCode, message)));
	}*/
	
	
	/** 报文体 */
	private B body;

	

	public B getBody() {
		return body;
	}

	public void setBody(B body) {
		this.body = body;
	}
	
	
	/**
	 * 输出逐层异常信息
	 * 
	 * @param e
	 * @return
	 */
	private static StringBuilder printStackTrace(Throwable e, StringBuilder sb) {

		synchronized (e) {
			sb.append(e.getMessage());
			sb.append("::");

			Throwable ourCause = e.getCause();
			if (ourCause != null)
				printStackTrace(ourCause, sb);
		}
		
		return sb;
	}
}
