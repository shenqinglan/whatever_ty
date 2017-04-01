/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-2-8
 * Id: SMPPTester.java,v 1.0 2017-2-8 上午10:04:47 Administrator
 */
package com.whty.smpp.socket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.google.gson.Gson;
import com.whty.framework.netty.MsgSend;
import com.whty.framework.netty.NettyThread;
import com.whty.framework.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.smpp.socket.Session;
import com.whty.smpp.socket.constants.Configuration;
import com.whty.smpp.socket.constants.SmppBindType;
import com.whty.smpp.socket.session.SMPPConnection;
import com.whty.smpp.socket.session.SMPPDeliverImpl;

/**
 * @ClassName SMPPTester
 * @author Administrator
 * @date 2017-2-8 上午10:04:47
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SocketTransceiverMain {

	private static Logger logger = LoggerFactory.getLogger(SocketTransceiverMain.class);
	private static int POOL_SIZE = 10;
	private SMPPConnection conn;
	private Session session;
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss:SSS");
	private static final String ESME_HOME = "ESME_HOME";

	public static void initHomePath() {
		String path = System.getenv(ESME_HOME);
		if (null == path) {
			path = System.getProperty(ESME_HOME);
			if (null == path) {
				path = new File("").getAbsolutePath();
				System.setProperty(ESME_HOME, path);
			}
		}
		logger.info("SMPP_EMSE:\t{}", path);
	}

	public static void initLoadByStart() {
		String path = System.getProperty(ESME_HOME);
		initLog(path);
		loadSpringContext(path);
	}
	
	private static void initLog(String path) {
		LoggerContext context = (LoggerContext) LoggerFactory
				.getILoggerFactory();
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			configurator.doConfigure(path + "/conf/logback.xml");
		} catch (JoranException je) {
			je.printStackTrace();
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(context);
		logger.info("initLog complete.");
	}

	private static ApplicationContext loadSpringContext(String path) {
		File homeDir = new File(path);
		File root = new File(homeDir, "/conf/smppESME.xml");
		return new FileSystemXmlApplicationContext("file:"
				+ root.getAbsolutePath());
	}
	
	private void bind() {
		Configuration config = new Configuration();
		config.setName("Tester.Session.0");
		config.setType(SmppBindType
				.getBindType(SpringPropertyPlaceholderConfigurer
						.getStringProperty("bind_type")));
		config.setHost(SpringPropertyPlaceholderConfigurer
				.getStringProperty("smsc_host"));
		config.setPort(Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("smsc_port")));
		config.setConnectTimeout(Long
				.parseLong(SpringPropertyPlaceholderConfigurer
						.getStringProperty("connect_timeout")));
		config.setSystemId(SpringPropertyPlaceholderConfigurer
				.getStringProperty("system_id"));
		config.setPassword(SpringPropertyPlaceholderConfigurer
				.getStringProperty("password"));
		config.setSendInterval(Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("send_interval")));	
		
		conn = new SMPPConnection(config, new SMPPDeliverImpl());
		conn.setAutoReconnect(true);
		// 如果smpp连接上了smsc，同时进行bind认证
		conn.connect(config.getHost(), config.getPort());
		if (conn.isConnected()) {
			session = conn.getSession();
		}
	}

	public static void main(String[] args) {

		// 加载配置文件
		initHomePath();
		initLoadByStart();
		SocketTransceiverMain main = new SocketTransceiverMain();
		
		// 绑定到端口号
		main.bind();
		
		// 心跳操作
		main.heartBeat();
		
		// 开启serverSocket服务器
		main.startServerSocket();
	}
	
	private void heartBeat() {
		
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				try {
					session.heartbeat();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
        Timer timer = new Timer();
        timer.schedule(timerTask, 5000, 10000);
	}
	
	private void startServerSocket() {
    	ServerSocket serverSocket = null;
        ExecutorService executorService = null;
    	try {
	    	serverSocket = ServerSocketFactory.getDefault().createServerSocket(
	    			Integer.parseInt(SpringPropertyPlaceholderConfigurer.getStringProperty("smpp_server_port")));
	    	executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
	                .availableProcessors() * POOL_SIZE);
	    	System.out.println("send sms Server started...");
	    	System.out.println("Wait client connect...");
	    	while (true) {
    		 
	        	 final Socket socket = serverSocket.accept();
	             System.out.println("A client has connected");
	             executorService.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
				        	DataInputStream input = new DataInputStream(socket.getInputStream());
				        	String line = input.readUTF();
				        	System.out.println("submit data:"+line);
				        	Gson gson = new Gson();
				        	MsgSend msgSend = gson.fromJson(line, MsgSend.class);
				            byte[] submitResp = session.sendMsg(msgSend, 10000);
				            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				            out.writeUTF(gson.toJson(submitResp));
				        	input.close();
				        	out.close();
				            socket.close();
						} catch (Exception e) {
		                 e.printStackTrace();
						}
					}
	             });
             
    		 }		
    	}catch (Exception e) {
			 e.printStackTrace();
		 }
    }
	
	private void startNettySmpp() {
		
		// 启动ESME服务器
		String strSmppPort = StringUtils.defaultIfBlank(
				SpringPropertyPlaceholderConfigurer
						.getStringProperty("smpp_server_port"), "8844");
		String strSmppHost = StringUtils.defaultIfBlank(
				SpringPropertyPlaceholderConfigurer
						.getStringProperty("smsc_host"), "127.0.0.1");
		int smppPort = Integer.parseInt(strSmppPort);
		NettyThread httpThread = new NettyThread(strSmppHost, smppPort, conn.getSession());
		new Thread(httpThread).start();
	}
}
