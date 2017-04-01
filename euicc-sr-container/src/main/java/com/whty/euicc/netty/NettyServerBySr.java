package com.whty.euicc.netty;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.handler.NettySrHandler;
import com.whty.netty.NettyThread;
/**
 * 启动sr netty
 * @author Administrator
 *
 */
public class NettyServerBySr {
	private static final String EUICC_HOME = "EUICC_HOME";
	public static void startSrNetty() {
		String euiccPortStr = StringUtils.defaultIfBlank(SpringPropertyPlaceholderConfigurer.getStringProperty("sr_port_https"), "9999");
		int	euiccPort = Integer.parseInt(euiccPortStr);
		NettySrHandler nettySrHandler = new NettySrHandler();
		String path = System.getProperty(EUICC_HOME);
		// 设置netty线程个数
		System.setProperty("io.netty.eventLoopThreads", 200 + "");
		NettyThread euiccNettyThread = new NettyThread(euiccPort, nettySrHandler);
		euiccNettyThread.getNettyHttpServer().setSSLContext(path+"/conf/server.keystore", "123456", "JKS");
		new Thread(euiccNettyThread).start();
		
		//开放http服务
		System.setProperty("io.netty.eventLoopThreads", 200 + "");
		NettySrHandler nettySrHandlerHttp = new NettySrHandler();
		String euiccPortStrForHttp = StringUtils.defaultIfBlank(SpringPropertyPlaceholderConfigurer.getStringProperty("sr_port_http"), "6666");
		int	euiccPortHttp = Integer.parseInt(euiccPortStrForHttp);
		NettyThread httpThread = new NettyThread(euiccPortHttp, nettySrHandlerHttp);
		new Thread(httpThread).start();	
		}
}
