package com.whty.euicc.tasks.utils;

public class ApduException extends Exception {

	private static final long serialVersionUID = 4453295958504994145L;

	public ApduException() {
	}

	public ApduException(String message) {
		super(message);
	}

	public ApduException(Throwable cause) {
		super(cause);
	}

	public ApduException(String message, Throwable cause) {
		super(message, cause);
	}
}
