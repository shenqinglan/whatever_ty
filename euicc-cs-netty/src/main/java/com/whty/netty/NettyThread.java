package com.whty.netty;
/**
 * netty线程包装类
 * @author Administrator
 *
 */
public class NettyThread implements Runnable {

	private NettyHttpServer nettyHttpServer;
	private int port;
	private NettyHttpHandler httpHandler;

	public NettyThread(int port, NettyHttpHandler httpHandler) {
		nettyHttpServer = new NettyHttpServer();
		this.port = port;
		this.httpHandler = httpHandler;
	}

	public NettyHttpServer getNettyHttpServer() {
		return nettyHttpServer;
	}

	public void run() {
		nettyHttpServer.start(this.port, this.httpHandler);
	}
}
