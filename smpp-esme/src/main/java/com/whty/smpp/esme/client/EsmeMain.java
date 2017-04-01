package com.whty.smpp.esme.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;

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
import com.whty.framework.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.smpp.esme.Session;
import com.whty.smpp.esme.session.ReceiverConnection;
import com.whty.smpp.esme.session.ReceiverDeliverImpl;
import com.whty.smpp.esme.session.SendConnection;
import com.whty.smpp.esme.constants.Configuration;
import com.whty.smpp.esme.constants.SmppBindType;
/**
 * @ClassName EsmeMain
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class EsmeMain {

	private static Logger logger = LoggerFactory.getLogger(EsmeMain.class);
	private static int POOL_SIZE = 10;
	private SendConnection  connSend;
	private ReceiverConnection connRev;
	private Session sessionRev;
	private Session sessionSend;
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
	
	public static void main(String[] args) {

		// 加载配置文件
		initHomePath();
		initLoadByStart();
		EsmeMain main = new EsmeMain();
		
		// 绑定到端口号
		main.bind();
		
		// 心跳操作
		main.sendHeartBeat();
		main.revHeartBeat();
		
		// 开启serverSocket服务器
		main.startServerSocket();
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
		
		connSend = new SendConnection(config);
		connSend.setAutoReconnect(true);
		// 如果smpp连接上了smsc，同时进行bind认证
		connSend.connect(config.getHost(), config.getPort());
		connSend.setAutoReconnect(true);
		if (connSend.isConnected()) {
			sessionSend = connSend.getSession();
		}
		
		connRev = new ReceiverConnection(config, new ReceiverDeliverImpl());
		connRev.setAutoReconnect(true);
		// 如果smpp连接上了smsc，同时进行bind认证
		connRev.connect(config.getHost(), config.getPort());
		connRev.setAutoReconnect(true);
		if (connRev.isConnected()) {
			sessionRev = connRev.getSession();
		}
	}
	
	private void sendHeartBeat() {
		
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				try {
					sessionSend.heartbeat();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
        Timer timer = new Timer();
        timer.schedule(timerTask, 5000, 10000);
	}
	
	private void revHeartBeat() {
			
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				try {
					sessionRev.heartbeat();
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
    		int port = Integer.parseInt(SpringPropertyPlaceholderConfigurer.getStringProperty("smpp_server_port"));
	    	serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
	    	executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
	                .availableProcessors() * POOL_SIZE);
	    	logger.info("send sms Server started...port>>> {}",port);
	    	logger.info("Wait client connect...");
	    	while (true) {
	        	 final Socket socket = serverSocket.accept();
	             System.out.println("A client has connected");
	             executorService.execute(new Runnable() {
					@Override
					public void run() {
						try {
				        	DataInputStream input = new DataInputStream(socket.getInputStream());
				        	String line = input.readUTF();
				        	logger.info("submit data:"+line);
				        	Gson gson = new Gson();
				        	MsgSend msgSend = gson.fromJson(line, MsgSend.class);
				            byte[] submitResp = sessionSend.sendMsg(msgSend, 10000);
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
}
