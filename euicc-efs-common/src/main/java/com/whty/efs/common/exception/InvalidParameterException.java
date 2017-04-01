package com.whty.efs.common.exception;

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
