package com.whty.euicc.common.exception;
/**
 * 无效参数异常类
 * @author Administrator
 *
 */
public class InvalidParameterException extends EuiccBaseException {

	private static final long serialVersionUID = -3201818081077952581L;

	public InvalidParameterException() {
		super();
	}

	public InvalidParameterException(String message) {
		super(message);
	}
	
	public String getCode(){
		return "000001";
	}
}
