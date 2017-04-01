package com.whty.euicc.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
/**
 * 日期转换类s
 * @author Administrator
 *
 */
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
			return new SimpleDateFormat(DateUtil.yyyy_MM_dd_HH_mm_ss_EN).parse(valueStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}