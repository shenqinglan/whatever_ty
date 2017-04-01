package com.whty.euicc.bizflow.template;

import com.whty.euicc.common.exception.EuiccBaseException;

/**
 * 业务模板异常<br>
 * <br>
 * 仅用于处理业务模板各种问题
 * 
 * @author baojw@whty.com.cn
 * @date 2014年10月13日 下午3:54:37
 */
public class BizTemplateException extends EuiccBaseException {

	private static final long serialVersionUID = -6675777793065576809L;

	/**
	 * 模板文件不存在
	 */
	public static final int ERROR_600000 = 600000; // 模板不存在或解析出错
	/**
	 * 模板解析异常
	 */
	public static final int ERROR_600001 = 600001; // 模板配置(from)有误:
													// command为空
	/**
	 * 模板文件读取异常
	 */
	public static final int ERROR_600002 = 600002; // 模板配置(from)有误:
													// 模版配置的命令和接收的请求命令不匹配
	/**
	 * 获取XmlStep对象的参数异常
	 */
	public static final int ERROR_600003 = 600003; // 模板配置(from)有误: 请求的命令未配置
	/**
	 * 未找到XmlStep对象
	 */
	public static final int ERROR_600004 = 600004; // 模板配置(job)有误: command为空
	public static final int ERROR_600005 = 600005; // 模板配置(from)有误:
													// code值和消息来源TSM不匹配
	public static final int ERROR_600006 = 600006;// 没有找到应用

	
	protected String getTitle() {
		return "BizTemplateException";
	}

	public BizTemplateException() {
		super();
	}
	private int errorCode;
	
	public BizTemplateException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.setErrorCode(errorCode);
	}

	public BizTemplateException(int errorCode, String message) {
		super(message);
		this.setErrorCode(errorCode);
	}

	public BizTemplateException(int errorCode, Throwable cause) {
		super(cause);
		this.setErrorCode(errorCode);
	}

//	public BizTemplateException(int errorCode) {
//		super(errorCode);
//	}

	public BizTemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizTemplateException(String message) {
		super(message);
	}

	public BizTemplateException(Throwable cause) {
		super(cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	private void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getCode() {
		return null;
	}
}
