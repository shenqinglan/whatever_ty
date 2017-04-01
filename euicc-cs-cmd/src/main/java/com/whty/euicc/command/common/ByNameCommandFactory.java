package com.whty.euicc.command.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;

@Service("ByNameCommand")
public class ByNameCommandFactory implements CommandFactory, org.springframework.context.ApplicationContextAware {
	ApplicationContext applicationContext;

	/**
	 * 根据请求数据，获取对应 Command 实例
	 * 
	 * @param header
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @throws InvalidParameterException 
	 * @date 2014年9月24日 下午2:10:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaseCommand<RequestMsgBody, ResponseMsgBody> getCommand(MsgHeader header, RequestMsgBody body) throws InvalidParameterException {
		String msgType = header.getMsgType();

		
//		TaskAssert.isNotEmpty(msgType,"消息类型为空");
		
		return (BaseCommand<RequestMsgBody, ResponseMsgBody>) this.applicationContext.getBean(msgType, header,body);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
