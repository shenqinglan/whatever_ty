package com.whty.smpp.netty.session;

import java.util.concurrent.ScheduledExecutorService;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudhopper.commons.util.windowing.Window;
import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.cloudhopper.commons.util.windowing.WindowListener;
import com.google.gson.Gson;
import com.whty.framework.netty.MsgSend;
import com.whty.smpp.netty.constants.Address;
import com.whty.smpp.netty.constants.SmppConstants;
import com.whty.smpp.netty.constants.SmppSessionState;
import com.whty.smpp.netty.handler.ISmppSessionHandler;
import com.whty.smpp.netty.handler.ISmppSessionListener;
import com.whty.smpp.netty.handler.SessionSmppHandlerImpl;
import com.whty.smpp.netty.handler.SessionSmppListenerImpl;
import com.whty.smpp.netty.pdu.BaseBind;
import com.whty.smpp.netty.pdu.BaseBindResp;
import com.whty.smpp.netty.pdu.EnquireLink;
import com.whty.smpp.netty.pdu.EnquireLinkResp;
import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;
import com.whty.smpp.netty.pdu.SubmitSm;
import com.whty.smpp.netty.pdu.SubmitSmResp;
import com.whty.smpp.netty.pdu.Unbind;
import com.whty.smpp.netty.transcoder.ISmppPduTranscoder;
import com.whty.smpp.netty.transcoder.SmppPduTranscoderContextImpl;
import com.whty.smpp.netty.transcoder.SmppPduTranscoderImpl;
import com.whty.smpp.netty.util.SequenceNumber;
/**
 * @ClassName SmppSessionImpl
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@SuppressWarnings("rawtypes")
public class SmppSessionImpl implements ISmppSession, WindowListener<Integer, PduRequest, PduResponse> {

	private static final Logger logger = LoggerFactory
			.getLogger(SmppSessionImpl.class);
	
	private final SmppSessionConfiguration configuration;
	
	private final ISmppSessionListener sessionListener;
	
	private byte interfaceVersion;

	private ScheduledExecutorService monitorExecutor;
	
	public SmppSessionConfiguration getConfiguration() {
		 return configuration;
	}
	
	@Override
	public ISmppSessionListener getSmppSessionListener() {
		// TODO Auto-generated method stub
		return sessionListener;
	}

	public SmppSessionImpl(SmppSessionConfiguration configuration,
			Channel channel, ISmppSessionHandler sessionHandler,
			ScheduledExecutorService monitorExecutor) {
		this.configuration = configuration;
		this.monitorExecutor = monitorExecutor;
		SequenceNumber sequenceNumber = new SequenceNumber();
		SmppSessionState state = new SmppSessionState();
		ISmppSessionHandler handler = (sessionHandler == null ? new SessionSmppHandlerImpl() : sessionHandler);
		ISmppPduTranscoder transcoder =  new SmppPduTranscoderImpl(new SmppPduTranscoderContextImpl());
		Window<Integer, PduRequest, PduResponse> sendWindow = null ;
		if (monitorExecutor != null
				&& configuration.getWindowMonitorInterval() > 0) {
			sendWindow = new Window<Integer, PduRequest, PduResponse>(
					configuration.getWindowSize(), monitorExecutor,
					configuration.getWindowMonitorInterval(), this,
					configuration.getName() + ".Monitor");
		} else {
			sendWindow = new Window<Integer, PduRequest, PduResponse>(
					configuration.getWindowSize());
		}
		
		this.sessionListener = new SessionSmppListenerImpl(handler, transcoder,sendWindow, sequenceNumber, channel, state);
	}

	@Override
	public BaseBindResp bind(BaseBind request, long timeoutInMillis) {
		boolean bound = false;
		try {
			sessionListener.setSessionState(SmppSessionState.STATE_BINDING);
			PduResponse response =  sessionListener.sendRequestAndGetResponse(request, timeoutInMillis, configuration.getRequestExpiryTimeout());
			BaseBindResp bindResponse = (BaseBindResp) response;

			if (bindResponse == null
					|| bindResponse.getCommandStatus() != SmppConstants.STATUS_OK) {
				throw new RuntimeException("bind error");
			}

			// 成功绑定
			bound = true;

			// 版本号赋值
			this.interfaceVersion = SmppConstants.VERSION_3_4;

			return bindResponse;
		} finally {
			if (bound) {
				// this session is now successfully bound & ready for processing
				sessionListener.setBound();
			} else {
				// the bind failed, we need to clean up resources
				try {
					this.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	

	@Override
	public void unbind(long timeoutInMillis) {
		if (sessionListener.getChannel().isConnected()) {
			sessionListener.setSessionState(SmppSessionState.STATE_UNBINDING);
			try {
				sessionListener.sendRequestAndGetResponse(new Unbind(), timeoutInMillis, configuration.getRequestExpiryTimeout());
			} catch (Exception e) {
				logger.warn("Did not cleanly receive an unbind response to our unbind request, safe to ignore: "
						+ e.getMessage());
			}
		} else {
			logger.info("Session channel is already closed, not going to unbind");
		}

		close(timeoutInMillis);
	}

	@Override
	public void close() {
		close(5000);
	}

	public void close(long timeoutInMillis) {
		if (sessionListener.getChannel().isConnected()) {
			sessionListener.setSessionState(SmppSessionState.STATE_UNBINDING);
			if (sessionListener.getChannel().close().awaitUninterruptibly(timeoutInMillis)) {
				logger.info("Successfully closed");
			} else {
				logger.info("Unable to cleanly close channel");
			}
		}
		sessionListener.setSessionState(SmppSessionState.STATE_CLOSED);
	}

	@Override
	public void destroy() {
		close();
		sessionListener.getSendWindow().destroy();
		sessionListener.closeSessionHandler();
	}

	@Override
	public EnquireLinkResp enquireLink(EnquireLink request, long timeoutInMillis) {
		PduResponse response = sessionListener.sendRequestAndGetResponse(request, timeoutInMillis, configuration.getRequestExpiryTimeout());
		return (EnquireLinkResp) response;
	}

	@Override
	public SubmitSmResp submit(SubmitSm request, long timeoutMillis) {
		PduResponse response = sessionListener.sendRequestAndGetResponse(request, timeoutMillis, configuration.getRequestExpiryTimeout());
		return (SubmitSmResp) response;
	}

	@Override
	public void expired(WindowFuture<Integer, PduRequest, PduResponse> future) {
		sessionListener.firePduRequestExpired(future.getRequest());
	}

	@Override
	public byte[] sendMsg(MsgSend msgSend, long timeoutMillis) {
        SubmitSm submit = new SubmitSm();
        submit.setServiceType("");
        submit.setSourceAddress(new Address((byte)0x00, (byte)0x00, msgSend.getSrc()));
        submit.setDestAddress(new Address((byte)0x00, (byte)0x00, msgSend.getDest()));
        submit.setEsmClass((byte)0x40);
        submit.setProtocolId((byte)0x00);
        submit.setPriority((byte)0x00);
        submit.setScheduleDeliveryTime(null);
        submit.setValidityPeriod(null);
    	submit.setRegisteredDelivery("1".equals(msgSend.getReport()) ? SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_REQUESTED : SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_NOT_REQUESTED);
    	submit.setReplaceIfPresent((byte)0x00);
    	submit.setDataCoding((byte)0x01);
    	submit.setDefaultMsgId((byte)0x00);
    	try {
			submit.setShortMessage(msgSend.getMsg().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        SubmitSmResp submitResp = null;
		try {
			submitResp = submit(submit, 30000);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Gson gson = new Gson();
		byte[] submitMsg = gson.toJson(submitResp).getBytes();
		return submitMsg;
	}
}
