package com.whty.smpp.netty.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

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

import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.google.gson.Gson;
import com.whty.framework.netty.MsgSend;
import com.whty.framework.netty.NettyThread;
import com.whty.framework.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.framework.utils.StringUtil;
import com.whty.smpp.netty.constants.Address;
import com.whty.smpp.netty.constants.SmppBindType;
import com.whty.smpp.netty.constants.SmppConstants;
import com.whty.smpp.netty.handler.SessionSmppHandlerImpl;
import com.whty.smpp.netty.pdu.EnquireLink;
import com.whty.smpp.netty.pdu.EnquireLinkResp;
import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;
import com.whty.smpp.netty.pdu.SubmitSm;
import com.whty.smpp.netty.pdu.SubmitSmResp;
import com.whty.smpp.netty.session.ISmppSession;
import com.whty.smpp.netty.session.SmppSessionConfiguration;
/**
 * 
 * @ClassName NettyTransceiverMain
 * @author Administrator
 * @date 2017-3-10 下午1:38:57
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class NettyTransceiverMain {

	private static Logger logger = LoggerFactory.getLogger(NettyTransceiverMain.class);
	private static int POOL_SIZE = 10;
	private static ISmppSession session;
	private SmppSessionConfiguration config0;
	private DefaultSmppClient clientBootstrap ;
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
	
	private static ApplicationContext loadSpringContext(String path) {
		File homeDir = new File(path);
		File root = new File(homeDir, "/conf/smppESME.xml");
		return new FileSystemXmlApplicationContext("file:" + root.getAbsolutePath());
	}
	
	private static void initLog(String path) {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
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
	
	public static void main(String[] args) {

		// 加载配置文件
		initHomePath();
		initLoadByStart();
		NettyTransceiverMain main = new NettyTransceiverMain();
		
		// 绑定到端口号，获取session
		main.bind();
		
		// 执行心跳操作
		main.heartBeat();

		// 开启serverSocket服务器
		main.startServerSocket();
	}
	
	private void heartBeat() {
		TimerTask timerTask = new TimerTask() {
			int index = 0;
			@Override
			public void run() {
				try {
					EnquireLinkResp enquireLinkResp = session.enquireLink(
							new EnquireLink(), 10000);
					++index;
					logger.info("enquire_link_resp #"+index+":{}",enquireLinkResp);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
        Timer timer = new Timer();
        timer.schedule(timerTask, 5000, 10000);
	}
	
	private void bind() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();
		ScheduledThreadPoolExecutor monitorExecutor = (ScheduledThreadPoolExecutor) Executors
				.newScheduledThreadPool(1, new ThreadFactory() {
					private AtomicInteger sequence = new AtomicInteger(0);

					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						t.setName("SmppClientSessionWindowMonitorPool-"
								+ sequence.getAndIncrement());
						return t;
					}
				});

		clientBootstrap = new DefaultSmppClient(
				Executors.newCachedThreadPool(), 1, monitorExecutor);
		SessionSmppHandlerImpl sessionHandler = new SessionSmppHandlerImpl();

		config0 = new SmppSessionConfiguration();
		config0.setWindowSize(1);
		config0.setName("Tester.Session.0");
		config0.setType(SmppBindType
				.getBindType(SpringPropertyPlaceholderConfigurer
						.getStringProperty("bind_type")));
		config0.setHost(SpringPropertyPlaceholderConfigurer
				.getStringProperty("smsc_host"));
		config0.setPort(Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("smsc_port")));
		config0.setConnectTimeout(Long
				.parseLong(SpringPropertyPlaceholderConfigurer
						.getStringProperty("connect_timeout")));
		config0.setSystemId(SpringPropertyPlaceholderConfigurer
				.getStringProperty("system_id"));
		config0.setPassword(SpringPropertyPlaceholderConfigurer
				.getStringProperty("password"));
		config0.setRequestExpiryTimeout(30000);
		config0.setWindowMonitorInterval(15000);

		ISmppSession session0 = null;

		try {
			logger.info("smpp esme server. binding");
			session0 = clientBootstrap.bind(config0, sessionHandler);
			session = session0;
		} catch (Exception e) {
			logger.error("", e);
		}
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
		
				            SubmitSm submit = new SubmitSm();
				            submit.setServiceType("");
				            submit.setSourceAddress(new Address((byte)0x00, (byte)0x00, msgSend.getSrc()));
				            submit.setDestAddress(new Address((byte)0x00, (byte)0x00, msgSend.getDest()));
				            submit.setEsmClass((byte)0x40);
				            submit.setProtocolId((byte)0x7F);
				            submit.setDataCoding((byte)0xF6);
				            submit.setPriority((byte)0x00);
				            submit.setScheduleDeliveryTime(null);
				            submit.setValidityPeriod(null);
				        	submit.setRegisteredDelivery("1".equals(msgSend.getReport()) ? SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_REQUESTED : SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_NOT_REQUESTED);
				        	submit.setReplaceIfPresent((byte)0x00);
				        	submit.setDefaultMsgId((byte)0x00);
				        	submit.setShortMessage(StringUtil.hexString2Bytes(msgSend.getMsg()));
				        	
				            SubmitSmResp submitResp = session.submit(submit, 30000);
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
	
	private static void startNettySmpp() {
		
		// 启动ESME服务器
		String strSmppPort = StringUtils.defaultIfBlank(
				SpringPropertyPlaceholderConfigurer
						.getStringProperty("smpp_server_port"), "8844");
		String strSmppHost = StringUtils.defaultIfBlank(
				SpringPropertyPlaceholderConfigurer
						.getStringProperty("smsc_host"), "127.0.0.1");
		int smppPort = Integer.parseInt(strSmppPort);
		NettyThread httpThread = new NettyThread(strSmppHost, smppPort, session);
		new Thread(httpThread).start();
	}
	
	private static void bind1() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();
		ScheduledThreadPoolExecutor monitorExecutor = (ScheduledThreadPoolExecutor) Executors
				.newScheduledThreadPool(1, new ThreadFactory() {
					private AtomicInteger sequence = new AtomicInteger(0);

					@Override
					public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						t.setName("SmppClientSessionWindowMonitorPool-"
								+ sequence.getAndIncrement());
						return t;
					}
				});

		DefaultSmppClient clientBootstrap = new DefaultSmppClient(
				Executors.newCachedThreadPool(), 1, monitorExecutor);
		SessionSmppHandlerImpl sessionHandler = new SessionSmppHandlerImpl();

		SmppSessionConfiguration config0 = new SmppSessionConfiguration();
		config0.setWindowSize(1);
		config0.setName("Tester.Session.0");
		config0.setType(SmppBindType
				.getBindType(SpringPropertyPlaceholderConfigurer
						.getStringProperty("bind_type")));
		config0.setHost(SpringPropertyPlaceholderConfigurer
				.getStringProperty("smsc_host"));
		config0.setPort(Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("smsc_port")));
		config0.setConnectTimeout(Long
				.parseLong(SpringPropertyPlaceholderConfigurer
						.getStringProperty("connect_timeout")));
		config0.setSystemId(SpringPropertyPlaceholderConfigurer
				.getStringProperty("system_id"));
		config0.setPassword(SpringPropertyPlaceholderConfigurer
				.getStringProperty("password"));
		config0.setRequestExpiryTimeout(30000);
		config0.setWindowMonitorInterval(15000);

		ISmppSession session0 = null;

		try {
			session0 = clientBootstrap.bind(config0, sessionHandler);
			session = session0;

			System.out.println("Press any key to send enquireLink #1");
			//System.in.read();

			EnquireLinkResp enquireLinkResp1 = session0.enquireLink(
					new EnquireLink(), 10000);
			logger.info("enquire_link_resp #1: commandStatus ["
					+ enquireLinkResp1.getCommandStatus() + "="
					+ enquireLinkResp1.getResultMessage() + "]");

			System.out.println("Press any key to send enquireLink #2");
			//System.in.read();

			// demo of an "asynchronous" enquireLink call - send it, get a
			// future,
			// and then optionally choose to pick when we wait for it
			WindowFuture<Integer, PduRequest, PduResponse> future0 = session0
					.getSmppSessionListener().sendRequestPdu(new EnquireLink(),
							100000, 100000, true);
			if (!future0.await()) {
				logger.error("Failed to receive enquire_link_resp within specified time");
			} else if (future0.isSuccess()) {
				EnquireLinkResp enquireLinkResp2 = (EnquireLinkResp) future0
						.getResponse();
				logger.info("enquire_link_resp #2: commandStatus ["
						+ enquireLinkResp2.getCommandStatus() + "="
						+ enquireLinkResp2.getResultMessage() + "]");
			} else {
				logger.error("Failed to properly receive enquire_link_resp: "
						+ future0.getCause());
			}

			System.out.println("Press any key to send submit #1");
			//System.in.read();

			String text160 = "Lorem dolor sit amet, consectetur adipiscing elit. Proin feugiat, leo id commodo tincidunt, nibh diam ornare est, vitae accumsan risus lacus sed sem metus.";
			byte[] textBytes = text160.getBytes();

			SubmitSm submit0 = new SubmitSm();

			// add delivery receipt
			submit0.setRegisteredDelivery(SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_REQUESTED);

			submit0.setSourceAddress(new Address((byte) 0x03, (byte) 0x00,
					"40404"));
			submit0.setDestAddress(new Address((byte) 0x01, (byte) 0x01,
					"44555519205"));
			submit0.setShortMessage(textBytes);

			SubmitSmResp submitResp = session0.submit(submit0, 10000000);

			logger.info("submitResp:{}", submitResp);

			logger.info("sendWindow.size: {}", session0
					.getSmppSessionListener().getSendWindow().getSize());

			System.out.println("Press any key to unbind and close sessions");
			//System.in.read();

			session0.unbind(5000);
		} catch (Exception e) {
			logger.error("", e);
		}

		if (session0 != null) {
			logger.info("Cleaning up session... (final counters)");
			session0.destroy();
		}

		logger.info("Shutting down client bootstrap and executors...");
		clientBootstrap.destroy();
		executor.shutdownNow();
		monitorExecutor.shutdownNow();

		logger.info("Done. Exiting");
	}

}
