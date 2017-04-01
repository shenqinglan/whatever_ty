package com.whty.smpp.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.netty.constants.SmppBindType;
import com.whty.smpp.netty.constants.SmppChannelConstants;
import com.whty.smpp.netty.exception.SmppChannelConnectException;
import com.whty.smpp.netty.exception.SmppChannelConnectTimeoutException;
import com.whty.smpp.netty.exception.SmppChannelException;
import com.whty.smpp.netty.exception.SmppTimeoutException;
import com.whty.smpp.netty.exception.UnrecoverablePduException;
import com.whty.smpp.netty.handler.ISmppSessionHandler;
import com.whty.smpp.netty.handler.SmppClientConnector;
import com.whty.smpp.netty.pdu.BaseBind;
import com.whty.smpp.netty.pdu.BaseBindResp;
import com.whty.smpp.netty.pdu.BindReceiver;
import com.whty.smpp.netty.pdu.BindTransceiver;
import com.whty.smpp.netty.pdu.BindTransmitter;
import com.whty.smpp.netty.session.ISmppSession;
import com.whty.smpp.netty.session.SmppSessionConfiguration;
import com.whty.smpp.netty.session.SmppSessionImpl;
import com.whty.smpp.netty.session.SmppSessionWrapper;
import com.whty.smpp.netty.transcoder.SessionSmppPduDecoder;
import com.whty.smpp.netty.util.DaemonExecutors;
/**
 * 
 * @ClassName DefaultSmppClient
 * @author Administrator
 * @date 2017-3-10 下午1:39:01
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DefaultSmppClient implements SmppClient {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSmppClient.class);

	private ChannelGroup channels;
	private SmppClientConnector clientConnector;
	private ExecutorService executors;
	private ClientSocketChannelFactory channelFactory;
	private ClientBootstrap clientBootstrap;
	private ScheduledExecutorService monitorExecutor;
	// shared instance of a timer for writeTimeout timing
	private final org.jboss.netty.util.Timer writeTimeoutTimer;

	/**
	 * Creates a new default SmppClient. Window monitoring and automatic
	 * expiration of requests will be disabled with no monitorExecutors. The
	 * maximum number of IO worker threads across any client sessions created
	 * with this SmppClient will be Runtime.getRuntime().availableProcessors().
	 * An Executors.newCachedDaemonThreadPool will be used for IO worker
	 * threads.
	 */
	public DefaultSmppClient() {
		this(DaemonExecutors.newCachedDaemonThreadPool());
	}

	/**
	 * Creates a new default SmppClient. Window monitoring and automatic
	 * expiration of requests will be disabled with no monitorExecutors. The
	 * maximum number of IO worker threads across any client sessions created
	 * with this SmppClient will be Runtime.getRuntime().availableProcessors().
	 * 
	 * @param executor
	 *            The executor that IO workers will be executed with. An
	 *            Executors.newCachedDaemonThreadPool() is recommended. The max
	 *            threads will never grow more than expectedSessions if NIO
	 *            sockets are used.
	 */
	public DefaultSmppClient(ExecutorService executors) {
		this(executors, Runtime.getRuntime().availableProcessors());
	}

	/**
	 * Creates a new default SmppClient. Window monitoring and automatic
	 * expiration of requests will be disabled with no monitorExecutors.
	 * 
	 * @param executor
	 *            The executor that IO workers will be executed with. An
	 *            Executors.newCachedDaemonThreadPool() is recommended. The max
	 *            threads will never grow more than expectedSessions if NIO
	 *            sockets are used.
	 * @param expectedSessions
	 *            The max number of concurrent sessions expected to be active at
	 *            any time. This number controls the max number of worker
	 *            threads that the underlying Netty library will use. If
	 *            processing occurs in a sessionHandler (a blocking op), be
	 *            <b>VERY</b> careful setting this to the correct number of
	 *            concurrent sessions you expect.
	 */
	public DefaultSmppClient(ExecutorService executors, int expectedSessions) {
		this(executors, expectedSessions, null);
	}

	/**
	 * Creates a new default SmppClient.
	 * 
	 * @param executor
	 *            The executor that IO workers will be executed with. An
	 *            Executors.newCachedDaemonThreadPool() is recommended. The max
	 *            threads will never grow more than expectedSessions if NIO
	 *            sockets are used.
	 * @param expectedSessions
	 *            The max number of concurrent sessions expected to be active at
	 *            any time. This number controls the max number of worker
	 *            threads that the underlying Netty library will use. If
	 *            processing occurs in a sessionHandler (a blocking op), be
	 *            <b>VERY</b> careful setting this to the correct number of
	 *            concurrent sessions you expect.
	 * @param monitorExecutor
	 *            The scheduled executor that all sessions will share to monitor
	 *            themselves and expire requests. If null monitoring will be
	 *            disabled.
	 */
	public DefaultSmppClient(ExecutorService executors, int expectedSessions,
			ScheduledExecutorService monitorExecutor) {
		this.channels = new DefaultChannelGroup();
		this.executors = executors;
		this.channelFactory = new NioClientSocketChannelFactory(this.executors,
				this.executors, expectedSessions);
		this.clientBootstrap = new ClientBootstrap(channelFactory);
		// we use the same default pipeline for all new channels - no need for a
		// factory
		this.clientConnector = new SmppClientConnector(this.channels);
		this.clientBootstrap.getPipeline().addLast(
				SmppChannelConstants.PIPELINE_CLIENT_CONNECTOR_NAME,
				this.clientConnector);
		this.monitorExecutor = monitorExecutor;
		// a shared instance of a timer for session writeTimeout timing
		this.writeTimeoutTimer = new org.jboss.netty.util.HashedWheelTimer();
	}

	public int getConnectionSize() {
		return this.channels.size();
	}

	@Override
	public void destroy() {
		// close all channels still open within this session "bootstrap"
		this.channels.close().awaitUninterruptibly();
		// clean up all external resources
		this.clientBootstrap.releaseExternalResources();
		// stop the writeTimeout timer
		this.writeTimeoutTimer.stop();
	}

	protected BaseBind createBindRequest(SmppSessionConfiguration config)
			throws UnrecoverablePduException {
		BaseBind bind = null;
		if (config.getType() == SmppBindType.TRANSCEIVER) {
			bind = new BindTransceiver();
		} else if (config.getType() == SmppBindType.RECEIVER) {
			bind = new BindReceiver();
		} else if (config.getType() == SmppBindType.TRANSMITTER) {
			bind = new BindTransmitter();
		} else {
			throw new UnrecoverablePduException(
					"Unable to convert SmppSessionConfiguration into a BaseBind request");
		}
		bind.setSystemId(config.getSystemId());
		bind.setPassword(config.getPassword());
		bind.setSystemType(config.getSystemType());
		bind.setInterfaceVersion(config.getInterfaceVersion());
		bind.setAddressRange(config.getAddressRange());
		return bind;
	}

	@Override
	public ISmppSession bind(SmppSessionConfiguration config,
			ISmppSessionHandler sessionHandler) {
		SmppSessionImpl session = null;
		try {
			session = doOpen(config, sessionHandler);

			doBind(session, config, sessionHandler);
		} catch (SmppTimeoutException e) {
			e.printStackTrace();
		} catch (SmppChannelException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (session != null
					&& !session.getSmppSessionListener().getSessionState()
							.isBound()) {
				try {
					session.close();
				} catch (Exception e) {
				}
			}
		}
		return session;
	}

	protected void doBind(ISmppSession session,
			SmppSessionConfiguration config, ISmppSessionHandler sessionHandler) {
		BaseBind bindRequest = null;
		try {
			bindRequest = createBindRequest(config);
		} catch (UnrecoverablePduException e1) {
			e1.printStackTrace();
		}
		BaseBindResp bindResp = null;

		try {
			bindResp = session.bind(bindRequest, config.getBindTimeout());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected SmppSessionImpl doOpen(SmppSessionConfiguration config,
			ISmppSessionHandler sessionHandler) throws SmppTimeoutException,
			SmppChannelException, InterruptedException {
		// create and connect a channel to the remote host
		Channel channel = null;
		try {
			channel = createConnectedChannel(config.getHost(),
					config.getPort(), config.getConnectTimeout());
		} catch (SmppTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmppChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// tie this new opened channel with a new session
		return createSession(channel, config, sessionHandler);
	}

	protected SmppSessionImpl createSession(Channel channel,
			SmppSessionConfiguration config, ISmppSessionHandler sessionHandler)
			throws SmppTimeoutException, SmppChannelException,
			InterruptedException {
		SmppSessionImpl session = new SmppSessionImpl(config, channel,
				sessionHandler, monitorExecutor);

		// add a new instance of a decoder (that takes care of handling frames)
		channel.getPipeline().addLast(
				SmppChannelConstants.PIPELINE_SESSION_PDU_DECODER_NAME,
				new SessionSmppPduDecoder(session.getSmppSessionListener()
						.getTranscoder()));

		// create a new wrapper around a session to pass the pdu up the chain
		channel.getPipeline().addLast(
				SmppChannelConstants.PIPELINE_SESSION_WRAPPER_NAME,
				new SmppSessionWrapper(session.getSmppSessionListener()));

		return session;
	}

	protected Channel createConnectedChannel(String host, int port,
			long connectTimeoutMillis) throws SmppTimeoutException,
			SmppChannelException, InterruptedException {
		// a socket address used to "bind" to the remote system
		InetSocketAddress socketAddr = new InetSocketAddress(host, port);

		// set the timeout
		this.clientBootstrap.setOption("connectTimeoutMillis",
				connectTimeoutMillis);

		ChannelFuture connectFuture = this.clientBootstrap.connect(socketAddr);

		connectFuture.awaitUninterruptibly();

		if (connectFuture.isCancelled()) {
			throw new InterruptedException("connectFuture cancelled by user");
		} else if (!connectFuture.isSuccess()) {
			if (connectFuture.getCause() instanceof org.jboss.netty.channel.ConnectTimeoutException) {
				throw new SmppChannelConnectTimeoutException(
						"Unable to connect to host [" + host + "] and port ["
								+ port + "] within " + connectTimeoutMillis
								+ " ms", connectFuture.getCause());
			} else {
				throw new SmppChannelConnectException(
						"Unable to connect to host [" + host + "] and port ["
								+ port + "]: "
								+ connectFuture.getCause().getMessage(),
						connectFuture.getCause());
			}
		}

		return connectFuture.getChannel();
	}

}
