package com.whty.euicc.bizflow.template;


import com.whty.euicc.common.exception.Assert;

public class TplAssert extends Assert {

	/**
	 * 期望值断言
	 * 
	 * @param value
	 * @param expect
	 * @param message
	 * @throws BizTemplateException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:34:20
	 */
	public static void isEquals(Object value, Object expect, String message) throws BizTemplateException {
		isEquals(value, expect, new BizTemplateException(message));
	}
	
	/**
	 * true断言
	 *
	 * @param value
	 * @param message
	 * @throws BizTemplateException
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月21日 上午9:45:27
	 */
	public static void isTrue(boolean value,String message) throws BizTemplateException{
		isTrue(value, new BizTemplateException(message));		
	}
}
