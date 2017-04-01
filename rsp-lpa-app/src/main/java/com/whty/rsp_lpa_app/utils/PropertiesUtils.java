package com.whty.rsp_lpa_app.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
	private static Properties pro;
	
	static {
		try {
			pro = new Properties();
			InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("lpa.properties");
			pro.load(in);
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		return pro.getProperty(key);
	}
}
