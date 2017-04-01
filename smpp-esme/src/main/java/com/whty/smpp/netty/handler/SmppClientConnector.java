package com.whty.smpp.netty.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;

/**
 * @ClassName SmppClientConnector
 * @author Administrator
 * @date 2017-2-7 上午9:06:48
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SmppClientConnector extends SimpleChannelUpstreamHandler {

    private ChannelGroup channels;

    public SmppClientConnector(ChannelGroup channels) {
        this.channels = channels;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // called every time a new channel connects
        channels.add(e.getChannel());
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // called every time a channel disconnects
        channels.remove(e.getChannel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        // the client smpp implementation relies on this to catch errors upstream
        // where we just pass it further upstream and basically discard it
        ctx.sendUpstream(e);
    }
}