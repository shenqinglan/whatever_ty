package com.whty.efs.common.bean;

public class RespMessage {
	private String code;
	private String message;
	private String data;
	
	public RespMessage(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public RespMessage(String code, String message, String data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	

}
