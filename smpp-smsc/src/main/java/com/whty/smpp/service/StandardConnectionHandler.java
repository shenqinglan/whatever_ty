package com.whty.smpp.service;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.client.SmscMain;
import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName StandardConnectionHandler
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class StandardConnectionHandler implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(StandardConnectionHandler.class);
	
	// smsc的实例
	private Smsc smsc = Smsc.getInstance();
	
	// 消息处理类
	private StandardProtocolHandler handler;
	
	// socket连接标志
	boolean isConnected = false;
	byte[] message;
	private ServerSocket ss;
	
	// socket输入输出流
	InputStream is = null;
	OutputStream os = null;
	Socket socket = null;
	
	/**
	 * @return
	 */
	public StandardProtocolHandler getHandler() {
		return handler;
	}

	/**
	 * @param socket
	 */
	public void setSs(ServerSocket socket) {
		ss = socket;
	}

	public StandardConnectionHandler() {
	}

	public StandardConnectionHandler(ServerSocket useThisServerSocket) {
		ss = useThisServerSocket;
	}

	@Override
	public void run() {
		runThread();
	}

	private void runThread() {

		do {

			// 等待连接
			do {
				try {

					// socket等待接收客户端的消息
					logger.info("StandardConnectionHandler waiting for connection");
					socket = ss.accept();
					logger.info("StandardConnectionHandler accepted a connection");

					// 连接标记置为真，退出循环
					isConnected = true;

					// 获取到输入流
					is = socket.getInputStream();

					// 获取到输出流
					os = socket.getOutputStream();

					// 获取到handler实例
					Class c = Class.forName(SmscMain
							.getProtocolHandlerClassName());
					handler = (StandardProtocolHandler) c.newInstance();
					handler.setConnection(this);
					logger.info("Protocol handler is of type "
							+ handler.getName());
				} catch (Exception exception) {
					logger.error("Exception processing connection: "
							+ exception.getMessage());
					logger.error("Exception is of type: "
							+ exception.getClass().getName());
					exception.printStackTrace();
					try {
						socket.close();
					} catch (Exception e) {
						logger.error("Could not close socket following exception");
						e.printStackTrace();
					}
				}
			} while (!isConnected);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// 等待消息处理
			do // until UNBIND or state violation
			{
				try {
					// 读取输入流
					logger.debug("read packet");
					readPacketInto(is);
					int len = 0;
			        int cmd = 0;
			        int status = 0;
			        len = PduUtilities.getIntegerValue(message, 0, 4);
			        cmd = PduUtilities.getIntegerValue(message, 4, 4);
			        status = PduUtilities.getIntegerValue(message, 8, 4);
					logger.info("read packet end message >>> len:{}, cmd:{}, status:{}", len,cmd,status);

					// switch语句对消息进行分类处理
					handler.processMessage(message);
				} catch (SocketException se) {
					logger.info("Socket exception: probably connection closed by client without UNBIND");
					se.printStackTrace();

					// 对handler的session绑定状态置为false
					handler.getSession().setBound(false);

					// 如果session是receiver，那么receiverUnBound的数目减少
					// 在运行 runningMoService 时，BoundReceiverCount 会增多
					if (handler.getSession().isReceiver()) {
						smsc.receiverUnbound();
					}

					// 连接标记置为假
					isConnected = false;
				} catch (Exception exception) {
					logger.info(exception.getMessage());
					exception.printStackTrace();
					try {
						socket.close();
					} catch (Exception e) {
						logger.error("Could not close socket following exception");
						e.printStackTrace();
					}
					handler.getSession().setBound(false);
					isConnected = false;
				}
			} while (isConnected);
			
			logger.debug("leaving connection handler main loop");
			
		} while (true);
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @return
	 * @Description TODO(判断是否绑定)
	 */
	protected boolean isBound() {
		if (isConnected) {
			return handler.getSession().isBound();
		} else {
			return false;
		}
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @return
	 * @Description TODO(判断是否receiver消息)
	 */
	protected boolean isReceiver() {
		return handler.getSession().isReceiver();
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @param address
	 * @return
	 * @Description TODO(rangeAddress匹配正则表达式)
	 */
	protected boolean addressIsServicedByReceiver(String address) {
		if (isConnected) {
			return handler.addressIsServicedByReceiver(address);
		} else {
			return false;
		}
	}

	private static final int getBytesAsInt(byte i_byte) {
		return i_byte & 0xff;
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @param is
	 * @return
	 * @throws IOException
	 * @Description TODO(读取消息到message)
	 */
	private int readPacketInto(InputStream is) throws IOException {
		logger.debug("starting readPacketInto");
		int len;
		byte[] packetLen = new byte[4];
		logger.debug("reading cmd_len");
		packetLen[0] = (byte) is.read();
		packetLen[1] = (byte) is.read();
		packetLen[2] = (byte) is.read();
		packetLen[3] = (byte) is.read();
		logger.debug("Got cmd_len");

		// put that into the packet header
		len = (getBytesAsInt(packetLen[0]) << 24)
				| (getBytesAsInt(packetLen[1]) << 16)
				| (getBytesAsInt(packetLen[2]) << 8)
				| (getBytesAsInt(packetLen[3]));

		if (packetLen[3] == -1) {
			logger.error("packetLen[3] == -1, throwing EOFException");
			throw new EOFException();
		}

		logger.debug("Reading " + len + " bytes");

		message = new byte[len];
		message[0] = packetLen[0];
		message[1] = packetLen[1];
		message[2] = packetLen[2];
		message[3] = packetLen[3];
		for (int i = 4; i < len; i++)
			message[i] = (byte) is.read();
		logger.debug("exiting readPacketInto");
		return len;
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @param response
	 * @throws IOException
	 * @Description TODO(response消息写入到输出流)
	 */
	public void writeResponse(byte[] response) throws IOException {
		os.write(response);
		os.flush();
	}

	/**
	 * @author Administrator
	 * @date 2017-1-23
	 * @throws IOException
	 * @Description TODO(关闭服务端的socket连接)
	 */
	public void closeConnection() throws IOException {
		os.flush();
		os.close();
		socket.close();
		isConnected = false;
	}
}
