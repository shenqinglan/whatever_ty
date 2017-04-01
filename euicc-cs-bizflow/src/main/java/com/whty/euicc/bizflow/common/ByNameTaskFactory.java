package com.whty.euicc.bizflow.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.whty.euicc.bizflow.template.XmlJob;
import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.tasks.common.BasicTask;

@Service("ByNameTask")
public class ByNameTaskFactory implements TaskFactory, org.springframework.context.ApplicationContextAware {
	static ApplicationContext applicationContext;
	/**
	 * 根据job code得到job类并执行。
	 * 
	 * @param xmlJob
	 * @param cmd
	 */
	@Override
	public BasicTask getTask(XmlJob xmlJob , BaseCommand<RequestMsgBody,ResponseMsgBody> cmd)  {
		// 从step中得到jobCode
		String jobCode = xmlJob.getCode();
		
		@SuppressWarnings("static-access")
		BasicTask basicTask = this.applicationContext.getBean(jobCode, BasicTask.class);
		
		// 根据任务执行命令
		basicTask.setBaseCommand(cmd);
		
		return basicTask;
	}
	
	
	/**
	 * 根据job code得到job类并执行。
	 * 
	 * @param jobCode
	 * @param cmd
	 */
	@Override
	public BasicTask getTask(String jobCode , BaseCommand<RequestMsgBody,ResponseMsgBody> cmd)  {
		@SuppressWarnings("static-access")
		BasicTask basicTask = this.applicationContext.getBean(jobCode, BasicTask.class);
		
		// 根据任务执行命令
		basicTask.setBaseCommand(cmd);
		
		return basicTask;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext1) throws BeansException {
		applicationContext = applicationContext1;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
}
