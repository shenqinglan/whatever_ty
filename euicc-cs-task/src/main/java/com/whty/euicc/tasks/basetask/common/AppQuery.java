package com.whty.euicc.tasks.basetask.common;

import com.whty.euicc.common.exception.NullParameterException;
import com.whty.euicc.data.common.constant.EuiccConstant;
import com.whty.euicc.tasks.common.BasicTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 查询应用列表<br>
 * 00:未安装、未个人化、未激活<br>
 * 01:已安装、未个人化、未激活<br>
 * 02:已安装、未个人化、未激活<br>
 * 03:已安装、已个人化、未激活
 * 
 * @author xlp
 * @since 2014-9-25
 * 
 */

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service("appQuery")
public class AppQuery extends BasicTask {

	private static final Logger logger = LoggerFactory.getLogger(AppQuery.class);

	@Override
	public void run() throws NullParameterException {
		logger.debug("task:appQuery");
		setStatus(EuiccConstant.TaskStatus.ONE);
	}
}
