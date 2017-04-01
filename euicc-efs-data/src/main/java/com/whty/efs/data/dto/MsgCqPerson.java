package com.whty.efs.data.dto;

import java.io.Serializable;

/**
 * 重庆个人化TSM报文
 * @author wangnw
 *
 */
public class MsgCqPerson implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String code;//消息码
	private String errorCode="00";//错误码
	private String errorMsg = "正常";//错误信息
	private String msgBody;//经过工作密钥加密后的报文体
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
}