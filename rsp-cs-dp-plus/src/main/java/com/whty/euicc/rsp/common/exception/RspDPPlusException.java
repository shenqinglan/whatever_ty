/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-12-13
 * Id: RspDPPlusException.java,v 1.0 2016-12-13 上午9:10:08 Administrator
 */
package com.whty.euicc.rsp.common.exception;

/**
 * @ClassName RspDPPlusException
 * @author Administrator
 * @date 2016-12-13 上午9:10:08
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class RspDPPlusException extends RuntimeException {

	/**
	 * @Fields serialVersionUID TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param code
	 * @param message
	 */
	public RspDPPlusException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	private String code;
	private String message;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
