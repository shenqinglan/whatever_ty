/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-12
 * Id: SpringContextHolder.java,v 1.0 2017-1-12 上午10:48:12 Administrator
 */
package com.whty.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @ClassName SpringContextHolder
 * @author Administrator
 * @date 2017-1-12 上午10:50:38
 * @Description TODO(获取applicationContext对象)
 */
@Service
public class SpringContextHolder implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
     
    public static <T>T getBean(String beanName , Class<T>clazz) {
        return applicationContext.getBean(beanName , clazz);
    }
    
    public static <T> T getBean(Class<T> cls){
        return applicationContext.getBean(cls);

    }
}
