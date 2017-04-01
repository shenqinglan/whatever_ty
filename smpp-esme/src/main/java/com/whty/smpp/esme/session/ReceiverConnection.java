package com.whty.smpp.esme.session;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.esme.Deliver;
import com.whty.smpp.esme.Message;
import com.whty.smpp.esme.Reader;
import com.whty.smpp.esme.Session;
import com.whty.smpp.esme.Writer;
import com.whty.smpp.esme.constants.Address;
import com.whty.smpp.esme.constants.Configuration;
import com.whty.smpp.esme.constants.SmppBindType;
import com.whty.smpp.esme.transcoder.ISmppPduTranscoder;
import com.whty.smpp.esme.transcoder.SmppPduTranscoderContextImpl;
import com.whty.smpp.esme.transcoder.SmppPduTranscoderImpl;
import com.whty.smpp.esme.util.SequenceNumber;
/**
 * 
 * @ClassName ReceiverConnection
 * @author Administrator
 * @date 2017-3-10 下午1:40:20
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ReceiverConnection implements Closeable{
	
	private final ISmppPduTranscoder transcoder;
	private final SequenceNumber sequenceNumber;
	private final Configuration config;
	private final Deliver deliver;

	private SmppBindType bindType;
	private String systemId;
	private String password;
	private String systemType;
	private byte interfaceVersion;
	private Address addressRange;
	
	public ReceiverConnection() {
		this(null, null);
	}

	public ReceiverConnection(Configuration config, Deliver deliver) {
		super();
        this.autoReconnect =
        this.keepAlive = true;
        this.keepAliveInterval = 9000;
        this.sendInterval = 50;
        this.queue = new LinkedBlockingQueue<Message>();
		this.transcoder = new SmppPduTranscoderImpl(new SmppPduTranscoderContextImpl());
		this.sequenceNumber = new SequenceNumber();
		this.config = config;
		this.deliver = deliver;
	}
	
	public SmppBindType getBindType() {
		return bindType;
	}

	public void setBindType(SmppBindType bindType) {
		this.bindType = bindType;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public byte getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(byte interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}
	
	public Address getAddressRange() {
		return addressRange;
	}

	public void setAddressRange(Address addressRange) {
		this.addressRange = addressRange;
	}

	protected Session createSession() {
		return new ReceiverSession(this, false, config, deliver);
	}

	protected Writer createWriter(OutputStream output) {
		return new ReceiverWriter(output, transcoder, sequenceNumber);
	}

	protected Reader createReader(InputStream input) {
		return new ReceiverReader(input,transcoder);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("smpp:[systemId=").append(systemId).append(",")
				.append("host=").append(getHost()).append(",").append("port=")
				.append(getPort()).append(",").append("password=")
				.append(password).append(",").append("bindType=")
				.append(bindType).append("]");
		return buffer.toString();
	}
	
	protected static final Logger log = LoggerFactory.getLogger(ReceiverConnection.class);
    private String host;
    private int port;

    private Socket socket;
    private SafeThread heartbeat;
    private SafeThread receiver;
    private boolean autoReconnect;
    private boolean keepAlive;
    private int keepAliveInterval;
    private int sendInterval;
    private Reader in;
    private Writer out;
    private Queue<Message> queue;
    private Session session;
    
	public SafeThread getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(SafeThread heartbeat) {
		this.heartbeat = heartbeat;
	}

	public SafeThread getReceiver() {
		return receiver;
	}

	public void setReceiver(SafeThread receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the in
	 */
	public Reader getIn() {
		return in;
	}

	/**
	 * @param in the in to set
	 */
	public void setIn(Reader in) {
		this.in = in;
	}

	/**
	 * @return the out
	 */
	public Writer getOut() {
		return out;
	}

	/**
	 * @param out the out to set
	 */
	public void setOut(Writer out) {
		this.out = out;
	}

	/**
	 * @return the queue
	 */
	public Queue<Message> getQueue() {
		return queue;
	}

	/**
	 * @param queue the queue to set
	 */
	public void setQueue(Queue<Message> queue) {
		this.queue = queue;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @param keepAlive the keepAlive to set
	 */
	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

    public Socket getSocket() {
        return socket;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public boolean isClosed() {
        return socket == null || socket.isClosed();
    }

    public Boolean getKeepAlive(){
        return keepAlive;
    }

    public void setKeepAlive(Boolean value){
        keepAlive = value;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(int value) {
        this.keepAliveInterval = value;
    }

    public int getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(int sendInterval) {
        this.sendInterval = sendInterval;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    public Session getSession() {
        return session;
    }

    public void send(Message message) {
        if(!isConnected()) {
             queue.offer(message);
        } else {
            Message msg = (Message)queue.poll();
            if(msg != null) {
                send(msg);
            }
            try {
                out.write(message);
                onSend(message);
            } catch (IOException ex) {
                queue.offer(message);
                disconnect();
                onError("socket connection send msg fail,retry:" + message, ex);
            }
        }
    }

    public void connect(String host, int port) {
        this.host = host;
        this.port = port;
        this.connect();
    }

    public void connect() {
        try {
            if ((this.port <= 0) || (this.port > 65535)) {
                log.error(String.format("port error:%d", this.port));
                throw new IndexOutOfBoundsException(String.format("port error:%d", this.port));
            }
            this.socket = new Socket();
            this.socket.setKeepAlive(keepAlive);
            this.socket.connect(new InetSocketAddress(host, port));
            this.out = createWriter(this.socket.getOutputStream());
            this.in = createReader(this.socket.getInputStream());
            this.startThreads();
            this.onConnect();
        } catch (Exception ex) {
            onError("socket connect failure", ex);
        }
    }

    public void disconnect() {

        killThreads();

        if (socket != null) {
            try {
                socket.shutdownInput();
            } catch (IOException ex) { /* do nothing */ }
            try {
                socket.shutdownOutput();
            } catch (IOException ex) { /* do nothing */ }
            try {
                socket.close();
                socket = null;
                in = null;
                out = null;
            } catch (IOException ex) { /* do nothing */ }
        }

        onDisconnect();
    }

    @Override
    public void close() {
        queue.clear();
        autoReconnect = false;
        if(isConnected()){
            disconnect();
        }
        onClose();
    }

    protected void heartbeat() throws IOException {
        Session session = getSession();
        if(session != null && session.isAuthenticated()){
            session.heartbeat();
        }
    }

    protected void onReceive(Message message) throws IOException {
        log.info("recv: " + message);
        if(message != null){
            Session session = getSession();
            if(session != null){
                session.process(message);
            }
        }
    }

    protected void onSend(Message message) throws IOException {
        log.info("send: " + message);
    }

    protected void onError(String message) {
        log.error(String.format("%s host=%s,port=%d", message, this.getHost(), this.getPort()));
    }

    protected void onError(String message, Exception error) {
        log.error(String.format("%s host=%s,port=%d", message, this.getHost(), this.getPort()), error);
    }

    protected void onConnect() {
        log.info(String.format("socket connect success host=%s,port=%d %tc%n", this.getHost(), this.getPort(), new Date()));
        if(session == null){
            session = createSession();
        }
        if(session.authenticate()){
            sendQueue();
        }
    }

    protected void onDisconnect() {
        log.info(String.format("socket disconnect success host=%s,port=%d %tc%n", this.getHost(), this.getPort(), new Date()));
        if(autoReconnect){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) { }
            connect();
        }
    }

    protected void onClose() {
        log.info(String.format("socket close success host=%s,port=%d %tc%n", this.getHost(), this.getPort(), new Date()));
    }

    protected void sendQueue(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sendQueue(sendInterval);
            }
        }, "queue");
        t.setDaemon(true);
        t.start();
    }

    private void sendQueue(int speed){
        do{
            if(isConnected()) {
                Message msg = (Message)queue.poll();
                if(msg != null){
                    send(msg);
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException ex) { }
                }
            } else break;
        } while (queue.size() > 0);
    }

    private void startThreads(){
        if (this.keepAlive && this.keepAliveInterval > 0) {
            this.heartbeat = new SafeThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(keepAliveInterval);
                    } catch (InterruptedException ex) { }
                    //检查建立socket连接
                    if (isConnected()){
                        try {
                            if(queue.isEmpty()){
                                heartbeat();
                            }
                        } catch (IOException ex) {
                            log.error("heartbeat", ex);
                        }
                    }
                }
            }, "heartbeat");
            this.heartbeat.start();
        }

        this.receiver = new SafeThread(new Runnable() {
            @Override
            public void run() {
                //建立socket连接
                if (isConnected()) {
                    try {
                        Message msg = in.read();
                        if (msg != null) {
                            onReceive(msg);
                        } else {
                            //返回空,关闭socket
                            //disconnect();
                            //onError("socket connection receive msg null");
                        }
                    } catch (IOException ex) {
                        //主线程退出
                        disconnect();
                        onError("socket connection receive msg error: " + ex.getMessage(), ex);
                    }
                }
            }
        }, "receiver");
        this.receiver.start();
    }

    private void killThreads(){
        if (this.heartbeat != null) {
            this.heartbeat.kill();
            this.heartbeat = null;
        }
        if(this.receiver != null){
            this.receiver.kill();
            this.receiver = null;
        }
    }

    private final class SafeThread extends Thread {
        private boolean alive = true;

        public SafeThread(Runnable target, String name) {
            super(target, name);
            setDaemon(false);
        }

        public void kill() {
            //安全退出线程
            this.alive = false;
        }

        @Override
        public final void run() {
            while (alive) {
                try {
                    super.run();
                } catch (Exception ex) {
                    log.error("thread error 1", ex);
                } catch (Throwable t) {
                    log.error("thread error 2", t);
                }
            }
        }
    }
    
}