package com.whty.euicc.common.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
/**
 * 加载配置文件类
 * @author Administrator
 *
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
