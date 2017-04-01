package com.whty.euicc.netty;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.handler.NettyDpHandler;
import com.whty.netty.NettyThread;
/**
 * 启动dp netty
 * @author Administrator
 *
 */
public class NettyServerByDp {
	private static final String EUICC_HOME = "EUICC_HOME";
	public static void startDpNetty() {
		String euiccPortStr = StringUtils.defaultIfBlank(SpringPropertyPlaceholderConfigurer.getStringProperty("dp_port_https"), "8888");
		int	euiccPort = Integer.parseInt(euiccPortStr);
		NettyDpHandler nettyDpHandler = new NettyDpHandler();
		String path = System.getProperty(EUICC_HOME);
		// 设置netty线程个数
		//开放https服务
		System.setProperty("io.netty.eventLoopThreads", 200 + "");
		NettyThread euiccNettyThread = new NettyThread(euiccPort, nettyDpHandler);
		euiccNettyThread.getNettyHttpServer().setSSLContext(path+"/conf/server.keystore", "123456", "JKS");
		new Thread(euiccNettyThread).start();	
		
		//开放http服务
		System.setProperty("io.netty.eventLoopThreads", 200 + "");
		NettyDpHandler nettyDpHandlerHttp = new NettyDpHandler();
		String euiccPortStrForHttp = StringUtils.defaultIfBlank(SpringPropertyPlaceholderConfigurer.getStringProperty("dp_port_http"), "7777");
		int	euiccPortHttp = Integer.parseInt(euiccPortStrForHttp);
		NettyThread httpThread = new NettyThread(euiccPortHttp, nettyDpHandlerHttp);
		new Thread(httpThread).start();	
		
	}

}
