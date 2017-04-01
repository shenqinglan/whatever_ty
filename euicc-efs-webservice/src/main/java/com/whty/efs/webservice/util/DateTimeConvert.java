package com.whty.efs.webservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

import com.whty.efs.common.constant.Constant;

public class DateTimeConvert implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		if (value.getClass() == Date.class) {
			return value;
		}
		if (value.getClass() != String.class) {
			throw new RuntimeException("type not match");
		}
		String valueStr = (String) value;
		
		try {
			return new SimpleDateFormat(Constant.Common.DATE_TIME_FORMAT).parse(valueStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}