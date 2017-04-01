package org.bulatnig.smpp.util;

import java.io.InputStream;
import java.util.Properties;
/**
 * @ClassName PropertiesUtil
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
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
