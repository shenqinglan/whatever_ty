package com.whty.euicc.tasks.basetask.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.whty.euicc.common.exception.NullParameterException;
import com.whty.euicc.data.common.constant.EuiccConstant;
import com.whty.euicc.tasks.common.BasicTask;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service("appInit")
public class AppInit extends BasicTask {

	private static final Logger logger = LoggerFactory.getLogger(AppQuery.class);

	@Override
	public void run() throws NullParameterException {
		logger.debug("task:appInit");
		setStatus(EuiccConstant.TaskStatus.ONE);
	}
}
