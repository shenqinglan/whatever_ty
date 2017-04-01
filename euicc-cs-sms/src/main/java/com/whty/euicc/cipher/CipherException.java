package com.whty.euicc.cipher;
/**
 * 加密异常类
 * @author Administrator
 *
 */
public class CipherException extends Exception {
	private static final long serialVersionUID = 2110261157654967765L;

	public CipherException() {
	}

	public CipherException(String string) {
		super(string);
	}
}
