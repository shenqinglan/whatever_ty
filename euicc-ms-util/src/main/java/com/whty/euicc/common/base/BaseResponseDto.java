package com.whty.euicc.common.base;

public class BaseResponseDto {
	
	public BaseResponseDto(boolean success){
		this.success = success;
	}
	
	public BaseResponseDto(boolean success, String msg){
		this.success = success;
		this.msg = msg;
	}

	private boolean success;
	private String msg;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
