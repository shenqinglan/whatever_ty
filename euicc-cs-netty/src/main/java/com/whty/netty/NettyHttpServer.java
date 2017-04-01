package com.whty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
/**
 * netty http服务
 * @author Administrator
 *
 */
public class NettyHttpServer {
	private static final String PROTOCOL = "TLS";
	private SSLContext sslContext = null;

	public void start(int port,NettyHttpHandler httpHandler) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		final NettyHttpHandler msgHandler = httpHandler;

			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							if (sslContext != null) {
								SSLEngine sslEngine = sslContext.createSSLEngine();
								sslEngine.setEnabledCipherSuites(new String[] { "SSL_RSA_WITH_RC4_128_MD5",
										"SSL_RSA_WITH_RC4_128_SHA", "TLS_RSA_WITH_AES_128_CBC_SHA",
										"TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
										"SSL_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA",
										"SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA",
										"SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
										"SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
										"SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
										"SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA"});
								sslEngine.setUseClientMode(false);
								sslEngine.setNeedClientAuth(true);
								pipeline.addLast(new SslHandler(sslEngine));
							}
							// server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
							pipeline.addLast(new HttpResponseEncoder());
							// server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
							pipeline.addLast(new HttpRequestDecoder());
							HttpHandlerAdapter handler = new HttpHandlerAdapter();
							handler.setHttpHandler(msgHandler);
							pipeline.addLast(handler);
						}
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			try {
				ChannelFuture f = b.bind(port).sync();
				System.out.println("server started at " + port);
				f.channel().closeFuture().sync();
			}
			catch (Exception e) {
				throw new RuntimeException("Failed to start netty server", e);
			} finally {
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
			

	}

	/**
	 * 设置SSL证书参数
	 * @param keyStoreFilePath
	 * @param keyStorePassword
	 * @param keyStoreType
	 */
	public void setSSLContext(String keyStoreFilePath, String keyStorePassword, String keyStoreType) {
		File serverFile = new File(keyStoreFilePath);
		try {
			KeyStore ks = KeyStore.getInstance(keyStoreType);
			ks.load(new FileInputStream(serverFile), keyStorePassword.toCharArray());

			// Set up key manager factory to use our key store
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, keyStorePassword.toCharArray());

			// truststore
			KeyStore ts = KeyStore.getInstance(keyStoreType);
			ts.load(new FileInputStream(serverFile), keyStorePassword.toCharArray());

			// set up trust manager factory to use our trust store
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ts);

			// Initialize the SSLContext to work with our key managers.
			sslContext = SSLContext.getInstance(PROTOCOL);
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to create ssl engine", e);
		}
	}
}
