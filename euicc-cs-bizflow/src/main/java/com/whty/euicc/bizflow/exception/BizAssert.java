package com.whty.euicc.bizflow.exception;

import com.whty.euicc.common.exception.Assert;
import com.whty.euicc.common.exception.InvalidParameterException;

public class BizAssert extends Assert{

	/**
	 * 期望值断言
	 * 
	 * @param value
	 * @param expect
	 * @param message
	 * @throws InvalidParameterException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:34:20
	 */
	public static void isEquals(Object value, Object expect, String message) throws BizException {
		isEquals(value, expect, new BizException(message));
	}

	/**
	 * true断言
	 *
	 * @param value
	 * @param message
	 * @throws InvalidParameterException
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:45:27
	 */
	public static void isTrue(boolean value,String message) throws BizException{
		isTrue(value, new BizException(message));		
	}
	
}
