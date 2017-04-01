/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-23
 * Id: ProcessBindTransmiter.java,v 1.0 2017-1-23 下午2:58:59 Administrator
 */
package com.whty.smpp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.client.SmscMain;
import com.whty.smpp.pdu.BindTransmitter;
import com.whty.smpp.pdu.BindTransmitterResp;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardProtocolHandler;
import com.whty.smpp.util.LoggingUtilities;

/**
 * @ClassName ProcessBindTransmiter
 * @author Administrator
 * @date 2017-1-23 下午2:58:59
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ProcessBindTransmitter implements IMessageResponse {

	Logger logger = LoggerFactory.getLogger(ProcessBindTransmitter.class);
	private StandardProtocolHandler handler;
	Smsc smsc = Smsc.getInstance();

	public ProcessBindTransmitter(StandardProtocolHandler handler) {
		super();
		this.handler = handler;
	}
	
	public boolean authenticate(String systemid, String password) {
		
		for (int i=0;i<SmscMain.getSystemids().length;i++) {
			if (SmscMain.getSystemids()[i].equals(systemid))
				if (SmscMain.getPasswords()[i].equals(password))
					return true;
				else
					return false;
		}
		return false;		
	}
	
	public boolean isValidSystemId(String systemid) {
		
		for (int i=0;i<SmscMain.getSystemids().length;i++) {
			if (SmscMain.getSystemids()[i].equals(systemid))
				return true;
		}
		return false;		
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @param message
	 * @param len
	 * @throws Exception
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.message.IMessageResponse#processMessageResponse(byte[],
	 *      int)
	 */
	@Override
	public void processMessageResponse(byte[] message, int len)
			throws Exception {

		byte[] respMessage;

		// 打印消息
		LoggingUtilities.hexDump(": BIND_TRANSMITTER:", message, len);

		// 对message消息进行解码封装成pdu，
		// 获取到system_id，password，system_type，interface_version， address
		BindTransmitter smppmsg = new BindTransmitter();
		smppmsg.demarshall(message);

		// 打印pdu
		LoggingUtilities.logDecodedPdu(smppmsg);

		// 提交response对象
		BindTransmitterResp smppresp = new BindTransmitterResp(smppmsg,
				new String(Smsc.getSmscId()));

		// 校验session是否绑定，如果已经绑定返回
		if (handler.getSession().isBound()) {
			logger.debug("Session is already bound");
			handler.setWasInvalidBindState(true);
			respMessage = smppresp.errorResponse(smppresp.getCmd_id(),
					PduConstants.ESME_RINVBNDSTS, smppresp.getSeq_no());
			LoggingUtilities.hexDump(": BIND_TRANSMITTER (ESME_RINVBNDSTS)", respMessage, respMessage.length);
			LoggingUtilities.logDecodedPdu(smppresp);
			handler.getConnection().writeResponse(respMessage);
			return;
		}

		// 设置interface_version
		handler.getSession().setInterface_version(
				smppmsg.getInterface_version());

		// 认证system_id和password是否正确
		if (authenticate(smppmsg.getSystem_id(), smppmsg.getPassword())) {
			// 修改session状态
			handler.getSession().setBound(true);
			handler.getSession().setTransmitter(true);
			handler.getSession().setReceiver(false);
			handler.setFailedAuthentication(false);
			logger.info("New transmitter session bound to SMPPSim");
		} else {
			logger.debug("Bind failed authentication check.");
			handler.setFailedAuthentication(true);
			if (isValidSystemId(smppmsg.getSystem_id())) {
				// 无效密码
				smppresp.setCmd_status(PduConstants.ESME_RINVPASWD);
			} else {
				// 无效账户
				smppresp.setCmd_status(PduConstants.ESME_RINVSYSID);
			}
			handler.getSession().setBound(false);
			handler.getSession().setTransmitter(false);
			handler.getSession().setReceiver(false);
		}

		if (!handler.failedAuthentication()) {
			// 返回成功消息编码
			respMessage = smppresp.marshall();
		} else {
			// 返回错误消息
			respMessage = smppresp.errorResponse(smppresp.getCmd_id(),
					smppresp.getCmd_status(), smppresp.getSeq_no());
		}

		LoggingUtilities.hexDump(": BIND_TRANSMITTER_RESP:", respMessage, respMessage.length);
		LoggingUtilities.logDecodedPdu(smppresp);
		handler.getConnection().writeResponse(respMessage);

		if (!handler.failedAuthentication()) {
			logger.info("success authentication");
		}
	}

}
