package com.whty.euicc.common.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1403035305110421824L;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
