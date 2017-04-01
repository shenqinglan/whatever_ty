package com.whty.euicc.tasks.utils;

import com.whty.euicc.common.exception.Assert;
import com.whty.euicc.tasks.exception.TaskException;

public class TaskAssert extends Assert {

	/**
	 * true断言<br>
	 * 如果条件表达式为false，抛出异常
	 * 
	 * @param condition
	 * @param message
	 *            异常消息
	 * @throws TaskException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:45:27
	 */
	public static void isTrue(boolean condition, String message) throws TaskException {
		isTrue(condition, new TaskException(message));
	}

	/**
	 * 期望值断言
	 * 
	 * @param value
	 * @param expect
	 * @param message
	 * @throws TaskException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:34:20
	 */
	public static void isEquals(Object value, Object expect, String message) throws TaskException {
		isEquals(value, expect, new TaskException(message));
	}
}
