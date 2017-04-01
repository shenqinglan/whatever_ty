package com.whty.flow.statehandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.flow.err.ExecutionError;
import com.whty.flow.impl.context.FlowMapContext;
import com.whty.flow.impl.context.FlowStateHandler;
import com.whty.euicc.bizflow.common.ByNameTaskFactory;
import com.whty.euicc.bizflow.common.TaskFactory;
import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.SecurityException;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.tasks.common.BasicTask;
import com.whty.euicc.tasks.exception.TaskException;

public class EUICCStateHandler extends FlowStateHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(EUICCStateHandler.class);

	@Override
	public String handle(FlowMapContext context) throws ExecutionError {
		TaskFactory factory = ByNameTaskFactory.getApplicationContext()
				.getBean(TaskFactory.class);
		try {
			System.out.println("####################################["
					+ context.getState().getFlow().getId() + "] state:["
					+ context.getState().getId() + "] job["
					+ context.getState().getParam("job") + "]");
			@SuppressWarnings("unchecked")
			BaseCommand<RequestMsgBody, ResponseMsgBody> cmd = (BaseCommand<RequestMsgBody, ResponseMsgBody>) context
					.getParam("cmd");
			BasicTask basicTask = factory.getTask((String) context.getState()
					.getParam("job"), cmd);
			// 根据任务执行命令
			basicTask.setBaseCommand(cmd);
			logger.debug("运行原子任务:{}", basicTask.getClass().getSimpleName());
			/************************************************/
			/** 运行分析任务 **/
			/************************************************/
			basicTask.run();
			// 任务正常运行后处理
			basicTask.afterRun();
			context.addParam("cmd", cmd);
			return basicTask.getStatus();
		} catch (TaskException e1) {
			throw new ExecutionError(context.getState(), null, e1, "state:["
					+ context.getState().getId() + ":"
					+ context.getState().getName() + "] job["
					+ context.getState().getParam("job")
					+ "] handle TaskException", context);
		} catch (SecurityException e2) {
			throw new ExecutionError(context.getState(), null, e2, "state:["
					+ context.getState().getId() + ":"
					+ context.getState().getName() + "] job["
					+ context.getState().getParam("job")
					+ "] handle SecurityException", context);
		} catch (InvalidParameterException e3) {
			throw new ExecutionError(context.getState(), null, e3, "state:["
					+ context.getState().getId() + ":"
					+ context.getState().getName() + "] job["
					+ context.getState().getParam("job")
					+ "] handle InvalidParameterException", context);
		} catch (Exception e4) {
			throw new ExecutionError(context.getState(), null, e4, "state:["
					+ context.getState().getId() + ":"
					+ context.getState().getName() + "] job["
					+ context.getState().getParam("job")
					+ "] handle OtherException", context);
		}
	}
}
