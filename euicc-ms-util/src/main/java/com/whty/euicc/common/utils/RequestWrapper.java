package com.whty.euicc.common.utils;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {

	private Map<String, String[]> params;

	public RequestWrapper(HttpServletRequest request,
			Map<String, String[]> newParams) {
		super(request);

		this.params = newParams;
	}

	@Override
	public String getParameter(String name) {
		String result = "";

		Object v = params.get(name);
		if (v == null) {
			result = null;
		} else if (v instanceof String[]) {
			String[] strArr = (String[]) v;
			if (strArr.length > 0) {
				result = strArr[0];
			} else {
				result = null;
			}
		} else if (v instanceof String) {
			result = (String) v;
		} else {
			result = v.toString();
		}

		return result;
	}

	@Override
	public Map getParameterMap() {
		return params;
	}

	@Override
	public Enumeration getParameterNames() {
		return new Vector(params.keySet()).elements();
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] result = null;

		Object v = params.get(name);
		if (v == null) {
			result = null;
		} else if (v instanceof String[]) {
			result = (String[]) v;
		} else if (v instanceof String) {
			result = new String[] { (String) v };
		} else {
			result = new String[] { v.toString() };
		}

		return result;
	}

}
