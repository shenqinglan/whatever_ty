/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: SessionSmppPduDecoder.java,v 1.0 2017-1-20 上午9:49:14 Administrator
 */
package com.whty.smpp.esme.transcoder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @ClassName SessionSmppPduDecoder
 * @author Administrator
 * @date 2017-1-20 上午9:49:14
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SessionSmppPduDecoder extends FrameDecoder {
	
	private final ISmppPduTranscoder transcoder;

	/**
	 * @param transcoder
	 */
	public SessionSmppPduDecoder(ISmppPduTranscoder transcoder) {
		super();
		this.transcoder = transcoder;
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @param ctx
	 * @param channel
	 * @param buffer
	 * @return
	 * @throws Exception
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		return transcoder.decode(buffer);
	}

}
