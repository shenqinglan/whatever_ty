package com.whty.euicc.command.common;

import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;

public interface CommandFactory {

	BaseCommand<RequestMsgBody,ResponseMsgBody> getCommand(MsgHeader msgHeader, RequestMsgBody body) throws InvalidParameterException;
}

	