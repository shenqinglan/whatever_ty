package com.whty.euicc.tasks.utils;


public class ApduAssert {
	/**
	 * true断言
	 *
	 * @param value
	 * @param message
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:45:27
	 */
	public static void isTrue(boolean value,String message) throws ApduException{
		isTrue(value, new ApduException(message));		
	}
	/**
	 * true断言<br>
	 * 如果条件表达式为false，抛出异常
	 * 
	 * @param condition
	 * @param e		申明的异常
	 * @throws KmsException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:45:27
	 */
	public static void isTrue(boolean condition, ApduException e) throws ApduException {
		if (!condition) {
			throw e;
		}
	}
}
