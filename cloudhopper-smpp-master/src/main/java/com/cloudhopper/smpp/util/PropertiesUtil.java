package com.cloudhopper.smpp.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties pro;
	
	static {
		try {
			pro = new Properties();
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("smpp.properties");
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
