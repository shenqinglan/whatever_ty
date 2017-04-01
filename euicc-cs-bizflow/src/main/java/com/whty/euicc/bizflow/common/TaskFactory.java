package com.whty.euicc.bizflow.common;

import com.whty.euicc.bizflow.template.XmlJob;
import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.tasks.common.BasicTask;

public interface TaskFactory {
	BasicTask getTask(XmlJob xmlJob,
                      BaseCommand<RequestMsgBody, ResponseMsgBody> command);

	BasicTask getTask(String jobcode,
                      BaseCommand<RequestMsgBody, ResponseMsgBody> command);
}
