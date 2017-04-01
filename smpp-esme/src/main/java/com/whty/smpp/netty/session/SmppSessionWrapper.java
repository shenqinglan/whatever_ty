package com.whty.smpp.netty.session;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.netty.handler.ISmppSessionListener;
import com.whty.smpp.netty.pdu.Pdu;
/**
 * @ClassName SmppSessionWrapper
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SmppSessionWrapper extends SimpleChannelHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(SmppSessionWrapper.class);

	private ISmppSessionListener listener;

	/**
	 * @param listener
	 */
	public SmppSessionWrapper(ISmppSessionListener listener) {
		super();
		this.listener = listener;
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-19
	 * @param ctx
	 * @param e
	 * @throws Exception
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		logger.info("smppSessionWrapper get message >>> {}",e.getMessage());
		if (e.getMessage() instanceof Pdu) {
            Pdu pdu = (Pdu)e.getMessage();
            this.listener.firePduReceived(pdu);
    	}
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-19
	 * @param ctx
	 * @param e
	 * @throws Exception
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see org.jboss.netty.channel.SimpleChannelHandler#channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info(e.toString());
        this.listener.fireChannelClosed();
	}
}
