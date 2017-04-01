package com.whty.euicc.common.exception;

import java.util.Collection;
/**
 * 断言常量
 * @author Administrator
 *
 */
public class Assert{	

	/**
	 * 非空指针断言
	 * 
	 * @param value
	 * @param message
	 * @throws NullParameterException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:18:33
	 */
	public static void isNotNull(Object value, String message) throws NullParameterException {
		if (null == value) {
			throw new NullParameterException(message);
		}
	}

	/**
	 * 非空断言
	 * 
	 * @param value
	 * @param message
	 * @author baojw@whty.com.cn
	 * @throws InvalidParameterException
	 * @date 2014年10月21日 上午9:18:57
	 */
	public static void isNotEmpty(String value, String message) throws InvalidParameterException {
		if (null == value || value.trim().length() == 0) {
			throw new InvalidParameterException(message);
		}
	}

	/**
	 * 非空断言
	 * 
	 * @param value
	 * @param message
	 * @author baojw@whty.com.cn
	 * @throws InvalidParameterException
	 * @date 2014年10月21日 上午9:24:43
	 */
	public static void isNotEmpty(Collection<?> value, String message) throws InvalidParameterException {
		if (null == value || value.isEmpty()) {
			throw new InvalidParameterException(message);
		}
	}

	/**
	 * 期望值断言
	 * 
	 * @param value
	 * @param expect
	 * @param e
	 * @throws InvalidParameterException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:34:20
	 */
	public static <E extends Exception> void  isEquals(Object value, Object expect, E e) throws E {
		isTrue((expect == null && value != null) || (!expect.equals(value)), e);
	}

	/**
	 * true断言<br>
	 * 如果条件表达式为false，抛出异常
	 * 
	 * @param condition
	 * @param e
	 *            申明的异常
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:45:27
	 */
	public static <E extends Exception> void isTrue(boolean condition, E e) throws E {
		if (!condition) {
			throw e;
		}
	}
}
