/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-12
 * Id: SpringPropertyPlaceholderConfigurer.java,v 1.0 2017-1-12 上午10:39:22 Administrator
 */
package com.whty.framework.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @ClassName SpringPropertyPlaceholderConfigurer
 * @author Administrator
 * @date 2017-1-12 上午10:39:43
 * @Description TODO(加载配置文件)
 */
public class SpringPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private static Map<String, String> ctxPropertiesMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	/**
	 * 获取Property
	 * @param name Property名称
	 * @return
	 */
	public static String getStringProperty(String name){
		if(ctxPropertiesMap == null)return null;
		return ctxPropertiesMap.get(name);
	}
}
