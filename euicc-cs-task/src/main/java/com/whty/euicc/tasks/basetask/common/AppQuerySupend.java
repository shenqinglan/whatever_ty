package com.whty.euicc.tasks.basetask.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.SecurityException;
import com.whty.euicc.data.common.constant.EuiccConstant;
import com.whty.euicc.tasks.common.BasicTask;
import com.whty.euicc.tasks.exception.TaskException;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service("appQuerySupend")
public class AppQuerySupend extends BasicTask{
	
	private static final Logger logger = LoggerFactory.getLogger(AppQuerySupend.class);

	@Override
	public void run() throws TaskException, InvalidParameterException,
			SecurityException {
		logger.debug("task:appQuerySupend");
		setStatus(EuiccConstant.TaskStatus.ONE);
		
	}

}
