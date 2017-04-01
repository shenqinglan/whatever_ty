package com.whty.efs.packets.message;

import com.whty.efs.packets.constant.Constant;
import com.whty.efs.packets.message.response.BaseRespBody;
import com.whty.efs.packets.message.response.ResponseMsgBody;
import com.whty.efs.packets.message.response.RspnMsg;


/**
 * 请求报文
 * 
 * @author gaofeng
 * 
 */
public class EuiccEntity<B extends Body> {

	public EuiccEntity() {
	}

	/**
	 * 将TsmMsg转成字节数组
	 * @return
	 */
	public byte[] toBytes(){
		return Constant.gson.toJson(this, EuiccEntity.class).getBytes(Constant.UTF8);
	}
	
	public EuiccEntity(Header header, B body) {
		this.header = header;
		this.body = body;
	}
	/**
	 * 消息数据构造
	 * @param header 消息头
	 * @param bizStatus 处理状态
	 * @param bizStatusCode 状态码
	 */
	public static EuiccEntity<ResponseMsgBody> buildEuiccMsg(Header header, String bizStatus, String bizStatusCode) {
		return new EuiccEntity<ResponseMsgBody>(header, new BaseRespBody(new RspnMsg(bizStatus, bizStatusCode)));
	}

	/**
	 * 消息数据构造
	 * @param header 消息头
	 * @param bizStatus 处理状态
	 * @param bizStatusCode 状态码
	 * @param message 处理消息
	 */
	public static EuiccEntity<ResponseMsgBody> buildEuiccMsg(Header header, String bizStatus, String bizStatusCode,String message) {
		return new EuiccEntity<ResponseMsgBody>(header, new BaseRespBody(new RspnMsg(bizStatus, bizStatusCode, message)));
	}
	/**
	 * 消息数据构造
	 * @param header 消息头
	 * @param bizStatus 处理状态
	 * @param bizStatusCode 状态码
	 * @param message 处理消息
	 */
	public static EuiccEntity<ResponseMsgBody> buildEuiccMsg(Header header, String bizStatus, String bizStatusCode,Exception e) {
		
		String message = printStackTrace(e, new StringBuilder()).toString();
		
		return new EuiccEntity<ResponseMsgBody>(header, new BaseRespBody(new RspnMsg(bizStatus, bizStatusCode, message)));
	}
	
	/** 报文头 */
	private Header header;
	/** 报文体 */
	private B body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

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
