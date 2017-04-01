package com.whty.framework.netty;

/**
 * netty线程包装类
 * @author Administrator
 *
 */
public class NettyThread implements Runnable {

	private NettyServer nettyServer;
	private String host;
	private int port;
	private NettySession session;

	public NettyThread(String host, int port, NettySession session) {
		nettyServer = new NettyServer();
		this.host = host;
		this.port = port;
		this.session = session;
	}

	public NettyServer getNettyServer() {
		return nettyServer;
	}

	public void run() {
		nettyServer.start(host, port, session);
	}
}
