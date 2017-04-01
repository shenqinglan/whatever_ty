package com.whty.efs.common.exception;

public abstract class EuiccBaseException extends Exception implements ErrorCode{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4213325874246140537L;

	/**
	 * 获取异常编码
	 * @return
	 */
	public abstract String getCode();

	public EuiccBaseException() {
		super();
	}

	public EuiccBaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EuiccBaseException(String arg0) {
		super(arg0);
	}

	public EuiccBaseException(Throwable arg0) {
		super(arg0);
	}
	
}
