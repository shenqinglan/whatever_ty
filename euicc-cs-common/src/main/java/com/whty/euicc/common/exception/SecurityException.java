package com.whty.euicc.common.exception;
/**
 * 安全异常类
 * @author Administrator
 *
 */
public class SecurityException extends EuiccBaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6096608114594184375L;

	@Override
	public String getCode() {
		return "000004";
	}

	public SecurityException() {
		super();
	}

	public SecurityException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SecurityException(String arg0) {
		super(arg0);
	}

	public SecurityException(Throwable arg0) {
		super(arg0);
	}

}
