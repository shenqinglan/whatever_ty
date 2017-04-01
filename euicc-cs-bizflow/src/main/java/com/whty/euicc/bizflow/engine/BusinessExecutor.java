// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.engine;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.euicc.bizflow.common.TaskFactory;
import com.whty.euicc.bizflow.constant.Constant;
import com.whty.euicc.bizflow.exception.BizAssert;
import com.whty.euicc.bizflow.exception.BizException;
import com.whty.euicc.bizflow.template.XmlStep;
import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.common.exception.Assert;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.exception.NullParameterException;
import com.whty.euicc.common.utils.SecurityException;
import com.whty.euicc.data.pojo.BusinessLog;
import com.whty.euicc.data.pojo.BusinessObject;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.tasks.common.BasicTask;
import com.whty.euicc.tasks.exception.TaskException;

/**
 * 模板引擎，处理模版的一个step
 * 
 * @author Administrator
 * 
 */
@Service
public class BusinessExecutor {

	private static final Logger logger = LoggerFactory.getLogger(BusinessExecutor.class);
	//private static final Logger dlog = LoggerFactory.getLogger("dlog");
	public static final String BUSINESSLOG = "businessLog";

	@Autowired
	private TaskFactory factory;

	public BasicTask execute(BusinessObject businessObject, XmlStep currentStep, BaseCommand<RequestMsgBody, ResponseMsgBody> cmd) throws InvalidParameterException, BizException {
		// 校验 FROM
		checkFrom(currentStep, cmd);

		// 校验 JOB
		checkJob(currentStep, cmd);

		BasicTask basicTask = this.factory.getTask(currentStep.getXmlJob(), cmd);
		
//		Assert.isNotNull(basicTask, MessageFormat.format("原子任务({})不存在",cmd.getMsgHeader().getMsgType()));
		
		logger.debug("运行原子任务:{}",basicTask.getClass().getSimpleName());
		try {
			/************************************************/
			/** 运行分析任务 **/
			/************************************************/
			basicTask.run();
			//任务正常运行后处理
			basicTask.afterRun();
		} catch (TaskException e) {
			throw new BizException(e);
		} catch (SecurityException e) {
			throw new BizException(e);
		}
		//	logger.error("Error happend when task running.");
			//saveBusInstanceLog(businessObject, currentStep, cmd, "1");

			//basicTask.setStatus(TsmConstant.TaskStatus.ZERO);

		//	throw new BusinessException(basicTask.getClass().getSimpleName() + " 原子任务执行异常", e);
		//} catch (InvalidParameterException e) {
		//	logger.error("Error happend when task running.");
			//saveBusInstanceLog(businessObject, currentStep, cmd, "1");

			//basicTask.setStatus(TsmConstant.TaskStatus.ZERO);

		//	throw new BusinessException(basicTask.getClass().getSimpleName() + " 原子任务执行异常", e);
		//}
		// 根据任务处理状态结果，跳转到下一任务
		//return currentStep.getXmlResult(basicTask.getStatus());
		return basicTask;
	}

	/**
	 * 校验from节点
	 * 
	 * @param businessObject
	 * @param currentStep
	 * @param cmd
	 * @return
	 * @throws BusinessException
	 * @throws NullParameterException
	 */
	private void checkFrom(XmlStep currentStep, BaseCommand<RequestMsgBody, ResponseMsgBody> cmd) throws BizException, NullParameterException {
		String fromCode = currentStep.getXmlForm().getCode();
		if (Constant.DOLLAR_FROM.equalsIgnoreCase(fromCode)) {
			// from的code属性如果是$from$，表示是外部的一个实体产生的消息，
			// 不用关心是哪个实体产生的，设置from参数为外部实体的code
			return;
		}

		// from 节点处理 from_code 等于#from# or #euicc #
		if (Constant.PROUD_FROM.equalsIgnoreCase(fromCode) || Constant.PROUD_TSM.equalsIgnoreCase(fromCode)) {
			String fromCommond = currentStep.getXmlForm().getCommand();
			Assert.isNotNull(fromCommond, "模板未配置form标签");
			BizAssert.isTrue(fromCommond.equals(cmd.getMsgHeader().getMsgType()), "模版配置的命令和接收的请求命令不匹配");
		}
	}

	private void checkJob(XmlStep currentStep, BaseCommand<RequestMsgBody, ResponseMsgBody> cmd) throws NullParameterException {
		String jobCode = currentStep.getXmlJob().getCode();
		Assert.isNotNull(jobCode, "模板未配置job标签");

		if (Constant.PROUD_TSM.equalsIgnoreCase(jobCode)) {
			
			// 表示该步骤不用执行原子任务
			// return false;
		}
	}

	/**
	 * 
	 * 步骤执行成功，步骤执行失败 日志记录
	 * 
	 * (为了定位问题方便，该表数据记录后，不需要随流程处理失败而回滚)
	 * 
	 * 业务实例日志记录（share参数入库） 需要判断from节点的last属性，决定终端是否无需参与后续交互，并记录终端完结状态
	 */
	@SuppressWarnings("unused")
	private void saveBusInstanceLog(BusinessObject businessObject, XmlStep currentStep, BaseCommand<RequestMsgBody, ResponseMsgBody> cmd, String result) {

		BusinessLog businessLog = new BusinessLog();
		businessLog.setExcuteDate(new Date());
		// businessLog.setMessage(cmd.getMessage());
		// businessLog.setParamMap(cmd.getShare()) ;
		businessLog.setResult(result);
		//businessLog.setStep(businessObject.getStep());
		businessLog.setStepDesc(currentStep.getName());
		businessLog.setXmlId(businessObject.getMsgType());
		// HashMap<String , String> sessionInfo = cmd.getSession() ;
		// businessLog.setApdu(sessionInfo.get(KeyConstant.Businessapdu)) ;
		// sessionInfo.put(KeyConstant.Businessapdu , "") ;
		// 先放到内存。等所有步骤执行完毕，再由另外线程保存到数据库

		// cacheService.cacheBusinessLog(businessObject.getId() , businessLog) ;

	}
}
