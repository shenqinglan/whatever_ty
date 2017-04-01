/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: SessionSmppListenerImpl.java,v 1.0 2017-1-20 上午10:52:09 Administrator
 */
package com.whty.smpp.netty.handler;

import java.nio.channels.ClosedChannelException;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudhopper.commons.util.windowing.Window;
import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.whty.smpp.netty.constants.SmppConstants;
import com.whty.smpp.netty.constants.SmppSessionState;
import com.whty.smpp.netty.pdu.Pdu;
import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;
import com.whty.smpp.netty.transcoder.ISmppPduTranscoder;
import com.whty.smpp.netty.util.SequenceNumber;

/**
 * @ClassName SessionSmppListenerImpl
 * @author Administrator
 * @date 2017-1-20 上午10:52:09
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SessionSmppListenerImpl implements ISmppSessionListener {

	Logger logger = LoggerFactory.getLogger(SessionSmppListenerImpl.class);

	private ISmppSessionHandler sessionHandler;
	private final ISmppPduTranscoder transcoder;
	private final Window<Integer, PduRequest, PduResponse> sendWindow;
	private final SequenceNumber sequenceNumber;
	private final Channel channel;
	private final SmppSessionState state;

	public SessionSmppListenerImpl() {
		this(null, null, null, null, null, null);
	}

	/**
	 * @param sessionHandler
	 * @param sendWindow
	 */
	public SessionSmppListenerImpl(ISmppSessionHandler sessionHandler,ISmppPduTranscoder transcoder,
			Window<Integer, PduRequest, PduResponse> sendWindow,
			SequenceNumber sequenceNumber, Channel channel,
			SmppSessionState state) {
		this.sessionHandler = sessionHandler;
		this.transcoder = transcoder;
		this.sendWindow = sendWindow;
		this.sequenceNumber = sequenceNumber;
		this.channel = channel;
		this.state = state;
	}

	@Override
	public void setBound() {
		this.state.getState().set(SmppSessionState.STATE_BOUND);
		this.state.getBoundTime().set(System.currentTimeMillis());
	}
	
	@Override
	public void setSessionState(int state) {
		this.state.getState().set(state);
	}
	
	@Override
	public Channel getChannel() {
		// TODO Auto-generated method stub
		return channel;
	}
	
	@Override
	public SmppSessionState getSessionState() {
		// TODO Auto-generated method stub
		return state;
	}
	
	@Override
	public ISmppPduTranscoder getTranscoder() {
		// TODO Auto-generated method stub
		return transcoder;
	}
	
	@Override
	public Window<Integer, PduRequest, PduResponse> getSendWindow() {
		return sendWindow;
	}
	
	@Override
	public void closeSessionHandler() {
		sessionHandler = null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public WindowFuture<Integer, PduRequest, PduResponse> sendRequestPdu(
			PduRequest pdu, long timeoutMillis, long expireTimeoutMillis,
			boolean synchronous) {
		if (!pdu.hasSequenceNumberAssigned()) {
			pdu.setSequenceNumber(this.sequenceNumber.next());
		}

		// pdu编码
		ChannelBuffer buffer = transcoder.encode(pdu);

		WindowFuture<Integer, PduRequest, PduResponse> future = null;
		try {
			future = sendWindow.offer(pdu.getSequenceNumber(), pdu,
					timeoutMillis, expireTimeoutMillis, synchronous);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ChannelFuture channelFuture = null;
		try {
			channelFuture = this.channel.write(buffer).await();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!channelFuture.isSuccess()) {
			throw new RuntimeException(channelFuture.getCause().getMessage(),
					channelFuture.getCause());
		}

		return future;
	}

	@Override
	public void sendResponsePdu(PduResponse pdu) {
		if (!pdu.hasSequenceNumberAssigned()) {
			pdu.setSequenceNumber(this.sequenceNumber.next());
		}

		ChannelBuffer buffer = transcoder.encode(pdu);

		logger.info("send PDU: {}", pdu);

		// 写入pdu同时等待
		ChannelFuture channelFuture = null;
		try {
			channelFuture = this.channel.write(buffer).await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!channelFuture.isSuccess()) {
			throw new RuntimeException(channelFuture.getCause().getMessage(),
					channelFuture.getCause());
		}
	}

	/**
	 * @author Administrator
	 * @date 2017-1-20
	 * @param pdu
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.ISmppSessionListener#firePduReceived(com.whty.smpp.netty.pdu.Pdu)
	 */
	@Override
	public void firePduReceived(Pdu pdu) {
		PduRequest requestPdu = null;
		PduResponse responsePdu = null;
		int cmd = pdu.getCommandId();
		switch (cmd) {
			// 如果esme收到smsc主动发起的deliver消息
		case SmppConstants.CMD_ID_DELIVER_SM:
			logger.info("pdu receiver deliver message, received PDU: {}", pdu);
			requestPdu = (PduRequest) pdu;
			responsePdu = this.sessionHandler
					.firePduRequestReceived(requestPdu);
			if (responsePdu != null) {
				try {
					sendResponsePdu(responsePdu);
				} catch (Exception e) {
					logger.error("Unable to cleanly return response PDU: {}", e);
				}
			}
			break;
			// 如果esme收到smsc主动发起的data消息
		case SmppConstants.CMD_ID_DATA_SM:
			logger.info("pdu receiver data message, received PDU: {}", pdu);
			requestPdu = (PduRequest) pdu;
			responsePdu = this.sessionHandler
					.firePduRequestReceived(requestPdu);
			if (responsePdu != null) {
				try {
					sendResponsePdu(responsePdu);
				} catch (Exception e) {
					logger.error("Unable to cleanly return response PDU: {}", e);
				}
			}
			break;
		default:
			// 收到SMSC发给esme的返回消息，消息由esme主动发起
			responsePdu = (PduResponse) pdu;
			int receivedPduSeqNum = pdu.getSequenceNumber();

			try {
				WindowFuture<Integer, PduRequest, PduResponse> future = this.sendWindow
						.complete(receivedPduSeqNum, responsePdu);
				if (future != null) {
					logger.info("Found a future in the window for seqNum [{}]",
							receivedPduSeqNum);
					logger.info("Caller waiting for request: {}",
							future.getRequest());
				}
			} catch (InterruptedException e) {
				logger.error(
						"Interrupted while attempting to process response PDU: ",
						e);
			}
			return;
		}
	}

	/**
	 * @author Administrator
	 * @date 2017-1-20
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.ISmppSessionListener#fireChannelClosed()
	 */
	@Override
	public void fireChannelClosed() {
		if (this.sendWindow.getSize() > 0) {
			logger.info("Channel closed and has [{}] outstanding requests, need cancelled immediately",this.sendWindow.getSize());
			Map<Integer, WindowFuture<Integer, PduRequest, PduResponse>> requests = this.sendWindow.createSortedSnapshot();
			Throwable cause = new ClosedChannelException();
			for (WindowFuture<Integer, PduRequest, PduResponse> future : requests.values()) {
				if (future.isCallerWaiting()) {
					logger.debug("Caller waiting on request [{}], cancelling it with a channel closed exception",future.getKey());
					try {
						future.fail(cause);
					} catch (Exception e) {
					}
				}
			}
		}

		if (state.isUnbinding() || state.isClosed()) {
			logger.debug("Unbind/close was requested, ignoring channelClosed event");
		} else {
			setSessionState(SmppSessionState.STATE_CLOSED);
			logger.info("channel unexpected close");
		}
	}

	@Override
	public PduResponse sendRequestAndGetResponse(PduRequest requestPdu,
			long timeoutInMillis, long expireTimeoutMillis) {
		WindowFuture<Integer, PduRequest, PduResponse> future = sendRequestPdu(
				requestPdu, timeoutInMillis, expireTimeoutMillis, true);
		boolean completedWithinTimeout = false;
		try {
			completedWithinTimeout = future.await();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!completedWithinTimeout) {
			future.cancel();
			throw new RuntimeException("Unable to get response within ["
					+ timeoutInMillis + " ms]");
		}

		if (future.isSuccess()) {
			return future.getResponse();
		} else if (future.getCause() != null) {
			Throwable cause = future.getCause();
			if (cause instanceof ClosedChannelException) {
				throw new RuntimeException(
						"Channel was closed after sending request, but before receiving response",
						cause);
			} else {
				throw new RuntimeException(cause.getMessage(), cause);
			}
		} else if (future.isCancelled()) {
			throw new RuntimeException("Request was cancelled");
		} else {
			throw new RuntimeException(
					"Unable to sendRequestAndGetResponse successfully (future was in strange state)");
		}
	}

	@Override
	public void firePduRequestExpired(PduRequest pduRequest) {
		logger.info("request expired >>> {}", pduRequest);
	}
}
