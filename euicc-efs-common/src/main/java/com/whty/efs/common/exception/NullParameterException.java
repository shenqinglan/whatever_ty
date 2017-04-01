package com.whty.efs.common.exception;


public class NullParameterException extends InvalidParameterException {

	private static final long serialVersionUID = -5612035368445652532L;

	public NullParameterException() {
		super();
	}
	
	public NullParameterException(String message) {
		super(message);
	}


	@SuppressWarnings("unused")
	private static String buildMessage(Class<?> paramClass, String paramName) {
		String message = "";
		StringBuilder sb = new StringBuilder();
		if (paramName == null) {
			sb.append("parameter");
		} else {
			sb.append(paramName);
		}

		if (null != paramClass) {
			sb.append(" <");
			sb.append(paramClass.getName());
			sb.append(">");
		}
		sb.append(" must not be null.");
		message = sb.toString();
		sb = null;
		return message;
	}
	
	@Override
	public String getCode() {
		return "000003";
	}
}