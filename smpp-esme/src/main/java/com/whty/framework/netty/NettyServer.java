package com.whty.framework.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
/**
 * @ClassName NettyServer
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class NettyServer {
	
	static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    
	public void start(String host, int port, NettySession session){
		
		final NettySession msgSession = session;
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup,workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
						ch.pipeline().addLast(new HttpResponseEncoder());
						// server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
						ch.pipeline().addLast(new HttpRequestDecoder());
						NettyServerHandlerAdapter handler = new NettyServerHandlerAdapter();
						handler.setSession(msgSession);
						ch.pipeline().addLast(handler);
					}
				});
			
			//绑定端口、同步等待
			ChannelFuture future = serverBootstrap.bind(port).sync();
			System.out.println("server started at " + port);
			//等待服务监听端口关闭
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//退出，释放线程等相关资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
