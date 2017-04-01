package com.whty.efs.webservice.jaxb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.whty.efs.common.constant.Constant;

/**
 * 日期时间处理 由于soap中对于时间不能进行直接转化 需要将其转化为字符串后进行抽象
 */
public class XSDDateTimeCustomBinder {
	
	public static Date parseDateTime(String s) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.Common.DATE_TIME_FORMAT);
		Date date = null;
		try {
			date = dateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String printDateTime(Date dt) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.Common.DATE_TIME_FORMAT);
		String str = null;
		str = dateFormat.format(dt);
		return str;
	}
}