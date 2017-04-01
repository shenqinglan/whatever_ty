package com.whty.euicc.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
/**
 * 加载bean类
 * @author Administrator
 *
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
