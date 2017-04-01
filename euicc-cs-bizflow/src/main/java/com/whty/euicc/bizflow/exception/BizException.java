package com.whty.euicc.bizflow.exception;

import com.whty.euicc.common.exception.EuiccBaseException;

public class BizException extends EuiccBaseException {

	private static final long serialVersionUID = -1463788440407966391L;

	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 业务处理异常编码
	 * @return
	 */
	public String getCode(){
		return "000006";
	}
}
