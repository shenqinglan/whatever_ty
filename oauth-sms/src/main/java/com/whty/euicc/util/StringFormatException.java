package com.whty.euicc.util;
/**
 * 异常说明类
 * @author Administrator
 *
 */
public class StringFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public StringFormatException(String message) {
		super(message);
	}

	public StringFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public StringFormatException(Throwable cause) {
		super(cause);
	}
}