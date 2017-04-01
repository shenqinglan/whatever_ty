package com.whty.euicc.common.utils;
/**
 * 安全异常类
 * @author Administrator
 *
 */
public class SecurityException extends Exception {

	private static final long serialVersionUID = -5673215184177045824L;

	public SecurityException() {
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

}
